package com.mdd.ela.service.base;


import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author dungmd
 * @created 1/14/2025 12:13 上午
 * @project e-learning-app
 */
@Service
public class BaseS3ServiceImpl implements BaseS3Service {
    @Value("${s3.bucket-name}")
    private String bucketName;
    @Value("${s3.region}")
    private String region;
    private final S3Client s3;

    public BaseS3ServiceImpl(S3Client s3) {
        this.s3 = s3;
    }

    @Override
    public String saveFile(MultipartFile file, String type) {
        String originalFilename = file.getOriginalFilename();
        String key = "public/" + type + "/" + UUID.randomUUID() + "_" + originalFilename;
        int count = 0;
        int maxTries = 3;
        while (true) {
            try {
                // Convert MultipartFile to byte array for direct upload
                s3.putObject(PutObjectRequest.builder()
                                .bucket(bucketName)
                                .key(key)
                                .acl(ObjectCannedACL.PUBLIC_READ)  // Đặt quyền công khai
                                .contentType("image/jpeg")
                                .build(),
                        RequestBody.fromBytes(file.getBytes()));

                return "https://" + bucketName + ".s3." + region + ".amazonaws.com/" + key;
            } catch (IOException e) {
                if (++count == maxTries) {
                    throw new RuntimeException("Failed to upload file after retries", e);
                }
            }
        }
    }

    @Override
    public byte[] downloadFile(String filename) {
        try {
            return s3.getObject(
                    GetObjectRequest.builder()
                            .bucket(bucketName)
                            .key(filename)
                            .build(),
                    ResponseTransformer.toBytes()
            ).asByteArray(); // Lấy nội dung file dưới dạng byte array
        } catch (S3Exception e) {
            throw new RuntimeException("Error downloading file: " + filename, e);
        }
    }

    @Override
    public String deleteFile(String filename) {
        s3.deleteObject(DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(filename)
                .build());
        return "File deleted successfully";
    }

    @Override
    public List<String> listAllFiles() {
        ListObjectsV2Response listObjectsResponse = s3.listObjectsV2(
                ListObjectsV2Request.builder()
                        .bucket(bucketName)
                        .build()
        );

        // Lấy danh sách key từ nội dung
        return listObjectsResponse.contents()
                .stream()
                .map(S3Object::key) // Đúng lớp `S3Object` từ SDK v2
                .collect(Collectors.toList());
    }




    @Override
    public Map<String, Object> createMultipartUploadRequest(String fileName, String uploadId) {
        String contentType = getMimeType(fileName);
        String key = contentType + "/" + uploadId + "_" + fileName;
        CreateMultipartUploadRequest createRequest = CreateMultipartUploadRequest.builder()
                .bucket(bucketName)
                .key(key)
                .contentType(contentType) // Đặt Content-Type cho file
                .build();
        CreateMultipartUploadResponse response = s3.createMultipartUpload(createRequest);
        return Map.of(
                "uploadId", response.uploadId(),
                "key", key);
    }

    @Override
    public List<CompletedPart> uploadChunk(String key, String uploadId, String tempDir, int totalChunk) throws IOException {
        File dir = new File(tempDir);
        if (!dir.exists() || !dir.isDirectory()) {
            throw new IOException("Temporary directory does not exist: " + tempDir);
        }

        File[] chunkFiles = dir.listFiles();
        if (chunkFiles == null || chunkFiles.length == 0) {
            throw new IOException("No chunk files found in: " + tempDir);
        }
        List<CompletedPart> completedPartList = new ArrayList<>();
        for (File chunkFile : chunkFiles) {
            // Xác định chunkIndex từ tên file
            int chunkIndex;
            try {
                chunkIndex = Integer.parseInt(chunkFile.getName());
            } catch (NumberFormatException e) {
                continue; // Nếu không phải số, bỏ qua
            }
            // Đọc dữ liệu từ file tạm
            byte[] data = Files.readAllBytes(chunkFile.toPath());

            UploadPartRequest request = UploadPartRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .uploadId(uploadId)
                    .partNumber(chunkIndex + 1)
                    .contentLength((long) data.length)
                    .build();
            // Upload chunk
            String eTag = s3.uploadPart(request, RequestBody.fromBytes(data)).eTag();


            CompletedPart completedPart = CompletedPart.builder()
                    .partNumber(chunkIndex + 1)
                    .eTag(eTag).build();

            completedPartList.add(completedPart);
        }
        return completedPartList;
    }

    @Override
    public String completeMultipartUploadRequest(String key, String uploadId, List<CompletedPart> completedPartList) throws IOException {
        completedPartList.sort(Comparator.comparingInt(CompletedPart::partNumber));
        // Tạo yêu cầu hoàn tất Multipart Upload
        CompleteMultipartUploadRequest completeRequest = CompleteMultipartUploadRequest.builder()
                .bucket(bucketName)
                .key(key)
                .uploadId(uploadId)
                .multipartUpload(CompletedMultipartUpload.builder()
                        .parts(completedPartList)
                        .build())
                .build();
        s3.completeMultipartUpload(completeRequest);
        PutObjectAclRequest aclRequest = PutObjectAclRequest.builder()
                .bucket("ela-demo-v1-mdd")
                .key(key)
                .acl(ObjectCannedACL.PUBLIC_READ)
                .build();
        s3.putObjectAcl(aclRequest);
        return s3.utilities().getUrl(GetUrlRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build()).toString();
    }

    @Override
    public void removeTempDir(String tempDir) throws IOException {
        File dir = new File(tempDir);
        if (!dir.exists() || !dir.isDirectory()) {
            throw new IOException("Temporary directory does not exist: " + tempDir);
        }
        File[] chunkFiles = dir.listFiles();
        if (chunkFiles == null || chunkFiles.length == 0) {
            throw new IOException("No chunk files found in: " + tempDir);
        }
        // Xóa các file chunk trong thư mục tạm
        for (File chunkFile : chunkFiles) {
            if (!chunkFile.delete()) {
                System.err.println("Warning: Không thể xóa file " + chunkFile.getAbsolutePath());
            }
        }
        // Xóa thư mục tempDir nếu nó rỗng
        if (dir.list().length == 0 && !dir.delete()) {
            System.err.println("Warning: Không thể xóa thư mục " + tempDir);
        }
    }

    private String getMimeType(String fileName) {
        String extension = fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();
        switch (extension) {
            case "mp4":
                return "video/mp4";
            case "mkv":
                return "video/x-matroska";
            case "avi":
                return "video/x-msvideo";
            case "webm":
                return "video/webm";
            default:
                return "application/octet-stream"; // Nếu không xác định được
        }
    }
}
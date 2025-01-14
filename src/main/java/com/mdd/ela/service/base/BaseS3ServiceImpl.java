package com.mdd.ela.service.base;



import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;


import java.io.IOException;
import java.util.List;
import java.util.UUID;
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
        String key = "public/" + type +"/"+ UUID.randomUUID() + "_" + originalFilename;
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
                    software.amazon.awssdk.core.sync.ResponseTransformer.toBytes()
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
}
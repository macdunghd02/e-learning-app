package com.mdd.ela.util;


import com.mdd.ela.exception.ElaRuntimeException;
import com.mdd.ela.util.constants.ConstantMessages;
import org.apache.commons.lang3.StringUtils;
import org.imgscalr.Scalr;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class StorageUtil {
    private StorageUtil() {
    }

    private static final int IMAGE_PIXEL_SIZE = 1000;

    public static String saveMultipartFile(MultipartFile file, String directoryLocation, String fileName) {
        Path storagePath = Paths.get(directoryLocation, fileName);
        try {
            file.transferTo(storagePath);
        } catch (Exception e) {
            return null;
        }
        return storagePath.toString();
    }

    public static boolean validateFileType(MultipartFile file, String[] acceptedChargingStationFile) {
        String contentType = file.getContentType();
        return !StringUtils.isBlank(contentType) && Arrays.asList(acceptedChargingStationFile).contains(contentType);
    }

    public static File resizeImage(File sourceFile, String fileExtension) throws IOException {
        try {
            BufferedImage bufferedImage = ImageIO.read(sourceFile);
            BufferedImage outputImage = Scalr.resize(bufferedImage, IMAGE_PIXEL_SIZE);
            File newImageFile = new File(sourceFile.getPath());
            ImageIO.write(outputImage, fileExtension, newImageFile);
            outputImage.flush();
            return newImageFile;
        } catch (IOException e) {
            throw new ElaRuntimeException("resizeFileError");
        }
    }

    public static boolean checkPixelSize(File file) throws IOException {
        BufferedImage bimg = ImageIO.read(file);
        int width = bimg.getWidth();
        int height = bimg.getHeight();
        return width <= IMAGE_PIXEL_SIZE && height <= IMAGE_PIXEL_SIZE;
    }

    public static void checkFileExist(String path) {
        if (!Files.exists(Path.of(path))) {
            throw new ElaRuntimeException("fileNotFound");
        }
    }

    public static String getFileExtension(MultipartFile file) {
        String extension = "";
        String fileName = file.getOriginalFilename();
        assert fileName != null;
        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            extension = fileName.substring(i + 1);
        }
        return extension;
    }

    public static FileInputStream getFileByLocationPath(String locationPath) {
        try {
            File file = ResourceUtils.getFile(locationPath);
            FileInputStream fileInputStream = new FileInputStream(file);
            return fileInputStream;
        } catch (FileNotFoundException e) {
            throw new ElaRuntimeException(ConstantMessages.FILE_NOT_FOUND);
        } catch (IllegalArgumentException e) {
            throw new ElaRuntimeException(ConstantMessages.FILE_NOT_FOUND);
        }
    }
}

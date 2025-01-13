package com.mdd.ela.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;

/**
 * @author dungmd
 * @created 1/13/2025 9:38 下午
 * @project e-learning-app
 */
@Configuration
public class S3Config {
    @Value("${s3.access-key}")
    private String accessKey;
    @Value("${s3.secret}")
    private String secret;
    @Value("${s3.region}")
    private String region;

    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
                .region(Region.of(region)) // Đặt vùng
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(accessKey, secret) // Cung cấp thông tin đăng nhập
                ))
                .serviceConfiguration(S3Configuration.builder()
                        .checksumValidationEnabled(true) // Kích hoạt xác thực checksum (tuỳ chọn)
                        .build()
                )
                .build();
    }
}
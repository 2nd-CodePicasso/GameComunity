package com.example.codePicasso.domain.chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.IOException;
import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final S3Client s3Client;
    private final String bucketName = "code-picasso-bucket";

    public String uploadFile(MultipartFile multipartFile) throws IOException {

        if (isFileExists(multipartFile.getOriginalFilename())) {
            return "https://" + bucketName + ".s3.amazonaws.com/" + multipartFile.getOriginalFilename();
        }

        InputStream inputStream = multipartFile.getInputStream();

        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(multipartFile.getOriginalFilename())
                .build();

        s3Client.putObject(objectRequest, RequestBody.fromInputStream(inputStream, multipartFile.getSize()));

        return "https://" + bucketName + ".s3.amazonaws.com/" + multipartFile.getOriginalFilename();
    }

    private boolean isFileExists(String fileName) {
        try {
            s3Client.headObject(b -> b.bucket(bucketName).key(fileName));
            return true;
        } catch (NoSuchKeyException e) {
            return false;
        }
    }
}

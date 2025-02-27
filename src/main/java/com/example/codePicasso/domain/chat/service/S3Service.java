package com.example.codePicasso.domain.chat.service;

import com.example.codePicasso.global.exception.base.ImageIoException;
import com.example.codePicasso.global.exception.enums.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.Duration;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final S3Client s3Client;
    private final String bucketName = "code-picasso-bucket";


    public String makePreSignedUrl(String fileName) {
        try (S3Presigner s3Presigner = S3Presigner.create()){
            PutObjectRequest objectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .build();

            PutObjectPresignRequest putObjectPresignRequest = PutObjectPresignRequest.builder()
                    .putObjectRequest(objectRequest)
                    .signatureDuration(Duration.ofMinutes(15))
                    .build();
            PresignedPutObjectRequest presignedPutObjectRequest = s3Presigner.presignPutObject(putObjectPresignRequest);
            URL url = presignedPutObjectRequest.url();
            return url.toString();

        } catch (Exception e) {
            throw new ImageIoException(ErrorCode.IMAGE_IOEXCEPTION);
        }
    }

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

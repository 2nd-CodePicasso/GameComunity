package com.example.codePicasso.domain.chat.service;

import com.example.codePicasso.domain.chat.dto.response.ImageResponse;
import com.example.codePicasso.domain.chat.entity.Image;
import com.example.codePicasso.domain.user.entity.User;
import com.example.codePicasso.domain.user.service.UserConnector;
import com.example.codePicasso.global.common.DtoFactory;
import com.example.codePicasso.global.exception.base.ImageIoException;
import com.example.codePicasso.global.exception.enums.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final S3Service s3Service;
    private final ImageConnector imageConnector;
    private final UserConnector userConnector;

    public ImageResponse addImage(MultipartFile imageRequest, Long userId) {
        String imageUrl = "";
        try {
            imageUrl = s3Service.uploadFile(imageRequest);
        } catch (IOException e) {
            throw new ImageIoException(ErrorCode.IMAGE_IOEXCEPTION);
        }

        if (imageConnector.exitsByImageUrlAndUserId(imageUrl,userId)) {
            return ImageResponse.builder()
                    .imageUrl(imageUrl)
                    .build();
        }

        User user = userConnector.findById(userId);

        Image image = Image.builder()
                .imageUrl(imageUrl)
                .user(user)
                .build();

        Image saveImage = imageConnector.save(image);

        return DtoFactory.toImageDto(saveImage);
    }
}

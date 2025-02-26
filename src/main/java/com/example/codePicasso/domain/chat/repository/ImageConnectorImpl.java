package com.example.codePicasso.domain.chat.repository;

import com.example.codePicasso.domain.chat.entity.Image;
import com.example.codePicasso.domain.chat.service.ImageConnector;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ImageConnectorImpl implements ImageConnector {

    private final ImageRepository imageRepository;

    @Override
    public Image save(Image image) {
        return imageRepository.save(image);
    }

    @Override
    public boolean exitsByImageUrlAndUserId(String imageUrl, Long userId) {
        return imageRepository.existsByImageUrlAndUserId(imageUrl,userId);
    }
}

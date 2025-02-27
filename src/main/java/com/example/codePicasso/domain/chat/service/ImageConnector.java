package com.example.codePicasso.domain.chat.service;

import com.example.codePicasso.domain.chat.entity.Image;
import org.springframework.stereotype.Component;

@Component
public interface ImageConnector {
    Image save(Image image);

    boolean exitsByImageUrlAndUserId(String imageUrl, Long userId);
}

package com.example.codePicasso.domain.post.dto;

import com.example.codePicasso.domain.post.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostEvent {
    private final Long id;
    private final String username;
    private final Long gameId;
    private final Long categoryId;
    private final String title;
    private final String description;
    private final Integer viewCount;
    private final String status;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public PostEvent(Post post) {
        this.id = post.getId();
        this.username = post.getUser().getNickname();
        this.gameId = post.getGame().getId();
        this.categoryId = post.getCategory().getId();
        this.title = post.getTitle();
        this.description = post.getDescription();
        this.viewCount = post.getViewCount();
        this.status = post.getStatus().name();
        this.createdAt = post.getCreatedAt();
        this.updatedAt = post.getUpdatedAt();
    }
}
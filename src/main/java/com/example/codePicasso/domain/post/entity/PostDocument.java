package com.example.codePicasso.domain.post.entity;

import lombok.Builder;
import org.springframework.data.elasticsearch.annotations.Document;

import java.time.LocalDateTime;

@Builder
@Document(indexName = "posts")
public class PostDocument {

    private Long id;
    private String username;
    private Long gameId;
    private Long categoryId;
    private String title;
    private String description;
    private Integer viewCount;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}

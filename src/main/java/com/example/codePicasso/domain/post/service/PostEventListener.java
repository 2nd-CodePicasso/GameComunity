//package com.example.codePicasso.domain.post.service;
//
//import com.example.codePicasso.domain.post.dto.PostEvent;
//import com.example.codePicasso.domain.post.entity.PostDocument;
//import com.example.codePicasso.domain.post.repository.PostDocumentRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.event.EventListener;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class PostEventListener {
//
//    private final PostDocumentRepository postDocumentRepository;
//
//    @EventListener
//    public void handlePostEvent(PostEvent post) {
//
//        PostDocument postDocument = PostDocument.builder()
//                .id(post.getId())
//                .gameId(post.getId())
//                .categoryId(post.getCategoryId())
//                .username(post.getUsername())
//                .description(post.getDescription())
//                .title(post.getTitle())
//                .viewCount(post.getViewCount())
//                .status(post.getStatus())
//                .createdAt(post.getCreatedAt())
//                .updatedAt(post.getUpdatedAt())
//                .build();
//        postDocumentRepository.save(postDocument);
//    }
//}

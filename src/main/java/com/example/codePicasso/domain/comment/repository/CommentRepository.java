package com.example.codePicasso.domain.comment.repository;

import com.example.codePicasso.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("""
                    SELECT new com.example.codePicasso.domain.comment.dto.response.CommentResponse
                    (c.id, p.id, u.nickname, c.text, c.createdAt, c.updatedAt)
                    FROM Comment c
                    LEFT JOIN c.user u
                    LEFT JOIN c.post p
                    WHERE p.id = :postId
            """)
    List<Comment> findCommentByPostId(Long postId);

    @Query("""
            SELECT c
            FROM Comment c
            LEFT JOIN c.user u
            WHERE c.id = :commentId AND u.id = :userId
            """)
    Optional<Comment> findByCommentIdAndUserId(Long commentId, Long userId);
}

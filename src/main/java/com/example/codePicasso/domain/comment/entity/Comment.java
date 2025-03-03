package com.example.codePicasso.domain.comment.entity;

import com.example.codePicasso.domain.post.entity.Post;
import com.example.codePicasso.domain.user.entity.User;
import com.example.codePicasso.global.common.TimeStamp;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comments")
@Builder
public class Comment extends TimeStamp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Column(nullable = false)
    private String text;

    // 부모 댓글 (댓글)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    // 자식 댓글 (대댓글)
    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    @BatchSize(size = 30)
    @Builder.Default
    @JsonIgnore
    private List<Comment> replies = new ArrayList<>();


    public void addReplies(Comment comment) {
        this.replies.add(comment);
        comment.parent = this;
    }

    // 댓글 수정
    public void updateComment(String text) {
        this.text = text;
    }

    // 대댓글 확인 메서드
    public boolean isReply() {
        return this.parent != null;
    }


}

package com.example.codePicasso.domain.post.entity;

import com.example.codePicasso.domain.category.entity.Category;
import com.example.codePicasso.domain.comment.entity.Comment;
import com.example.codePicasso.domain.game.entity.Game;
import com.example.codePicasso.domain.post.enums.PostStatus;
import com.example.codePicasso.domain.user.entity.User;
import com.example.codePicasso.global.common.TimeStamp;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "post",
        indexes = {
                // user_id 인덱스
                @Index(name = "idx_post_user_id", columnList = "user_id"),
                // game_id 인덱스
                @Index(name = "idx_post_game_id", columnList = "game_id"),
                // category_id 인덱스
                @Index(name = "idx_post_category_id", columnList = "category_id"),
                // status 인덱스
                @Index(name = "idx_post_game_status", columnList = "game_id, status"),
                // createdAt 인덱스
                @Index(name = "idx_post_createdAt", columnList = "createdAt"),
        }
)
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Builder.Default
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comment = new ArrayList<>();

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Integer viewCount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PostStatus status;

    public void updateCategories(Category category) {
        this.category = category;
    }

    public void updatePost(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public void changeStatusToRecommended() {
        this.status = PostStatus.RECOMMENDED;
    }

    public void increaseViewCount() {
        this.viewCount++;
    }
}

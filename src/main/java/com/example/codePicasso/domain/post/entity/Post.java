package com.example.codePicasso.domain.post.entity;

import com.example.codePicasso.domain.category.entity.Category;
import com.example.codePicasso.domain.game.entity.Game;
import com.example.codePicasso.domain.post.enums.PostStatus;
import com.example.codePicasso.domain.user.entity.User;
import com.example.codePicasso.global.common.TimeStamp;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
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

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Integer viewCount;

    @Column(nullable = false)
    private PostStatus status;

    @Builder
    public Post(User user, Game game, Category category, String title, String description) {
        this.user = user;
        this.game = game;
        this.category = category;
        this.title = title;
        this.description = description;
        this.viewCount = 0;
        this.status = PostStatus.NORMAL;
    }

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
}

package com.example.codePicasso.domain.posts.entity;

import com.example.codePicasso.domain.game.entity.Game;
import com.example.codePicasso.domain.posts.dto.response.PostResponse;
import com.example.codePicasso.domain.users.entity.User;
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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Categories categories;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Builder
    public Post(User user, Game game, Categories categories, String title, String description) {
        this.user = user;
        this.game = game;
        this.categories = categories;
        this.title = title;
        this.description = description;
    }

    public PostResponse toDto() {
        return PostResponse.builder().
                postId(id).
                gameId(game.getId()).
                categoryName(categories.getCategoryName()).
                title(title).
                description(description).
                createdAt(getCreatedAt()).
                updatedAt(getUpdatedAt()).
                build();
    }

    public void updateCategories(Categories categories) {
        this.categories = categories;
    }

    public void updatePost(String title, String description) {
        this.title = title;
        this.description = description;
    }
}

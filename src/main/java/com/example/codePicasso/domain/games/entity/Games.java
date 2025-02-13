package com.example.codePicasso.domain.games.entity;

import com.example.codePicasso.domain.users.entity.Admin;
import com.example.codePicasso.global.common.TimeStamp;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Games extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id", nullable = false)
    private Admin admin;

    @Column(nullable = false)
    private String gameTitle;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
}

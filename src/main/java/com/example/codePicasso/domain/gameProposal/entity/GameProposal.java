package com.example.codePicasso.domain.gameProposal.entity;

import com.example.codePicasso.domain.gameProposal.enums.ProposalStatus;
import com.example.codePicasso.domain.users.entity.Admin;
import com.example.codePicasso.domain.users.entity.User;
import com.example.codePicasso.global.common.TimeStamp;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameProposal extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id", nullable = false)
    private Admin admin;

    @Column(nullable = false)
    private String game_title;

    @Column(nullable = false)
    private ProposalStatus status;
}

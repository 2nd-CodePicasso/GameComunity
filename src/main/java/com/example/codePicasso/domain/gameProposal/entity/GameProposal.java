package com.example.codePicasso.domain.gameProposal.entity;

import com.example.codePicasso.domain.gameProposal.dto.response.GameProposalResponse;
import com.example.codePicasso.domain.gameProposal.enums.ProposalStatus;
import com.example.codePicasso.domain.users.entity.Admin;
import com.example.codePicasso.domain.users.entity.User;
import com.example.codePicasso.global.common.TimeStamp;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

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
    @JoinColumn(name = "admin_id")
    private Admin admin;

    @Column(nullable = false)
    private String gameTitle;

    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProposalStatus status;

    public GameProposalResponse toDto(){
        return GameProposalResponse.builder()
                .id(id)
                .userId(user.getId())
                .adminId(admin == null ? null : admin.getId())
                .gameTitle(gameTitle)
                .status(status)
                .build();
    }

    public void update(Admin admin, ProposalStatus status){
        this.admin = admin;
        this.status = status;
    }
}

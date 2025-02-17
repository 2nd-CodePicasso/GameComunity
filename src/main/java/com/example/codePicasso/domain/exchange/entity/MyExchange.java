package com.example.codePicasso.domain.exchange.entity;

import com.example.codePicasso.domain.exchange.dto.response.MyExchangeResponse;
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
public class MyExchange extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "exchange_id", nullable = false)
    private Exchange exchange;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private int contact;

    public MyExchangeResponse toDto(){
        return MyExchangeResponse.builder()
                .exchange(exchange)
                .user(user)
                .contact(contact)
                .build();
    }
}

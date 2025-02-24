package com.example.codePicasso.global.common;


import com.example.codePicasso.domain.category.dto.response.CategoryResponse;
import com.example.codePicasso.domain.category.entity.Category;
import com.example.codePicasso.domain.chat.dto.response.ChatResponse;
import com.example.codePicasso.domain.chat.entity.Chat;
import com.example.codePicasso.domain.exchange.dto.response.ExchangeResponse;
import com.example.codePicasso.domain.exchange.entity.Exchange;
import com.example.codePicasso.domain.game.dto.response.GameResponse;
import com.example.codePicasso.domain.game.entity.Game;
import com.example.codePicasso.domain.gameProposal.dto.response.GameProposalResponse;
import com.example.codePicasso.domain.gameProposal.entity.GameProposal;
import com.example.codePicasso.domain.post.dto.response.PostResponse;
import com.example.codePicasso.domain.post.entity.Post;
import com.example.codePicasso.domain.user.dto.response.AdminResponse;
import com.example.codePicasso.domain.user.dto.response.UserResponse;
import com.example.codePicasso.domain.user.entity.Admin;
import com.example.codePicasso.domain.user.entity.User;

public class DtoFactory {

    public static CategoryResponse toCategoryDto(Category category) {
        return CategoryResponse.builder()
                .categoryId(category.getId())
                .categoryName(category.getCategoryName())
                .build();
    }

    public static AdminResponse toAdminDto(Admin admin) {
        return AdminResponse.builder()
                .loginId(admin.getLoginId())
                .build();
    }

    public static UserResponse toUserDto(User user) {
        return UserResponse.builder().
                loginId(user.getLoginId()).
                nickname(user.getNickname()).
                build();
    }

    public static PostResponse toPostDto(Post post) {
        return PostResponse.builder().
                postId(post.getId()).
                gameId(post.getGame().getId()).
                categoryName(post.getCategory().getCategoryName()).
                title(post.getTitle()).
                nickname(post.getUser().getNickname()).
                description(post.getDescription()).
                createdAt(post.getCreatedAt()).
                updatedAt(post.getUpdatedAt()).
                build();
    }

    public static GameProposalResponse toGameProposalDto(GameProposal gameProposal){
        return GameProposalResponse.builder()
                .id(gameProposal.getId())
                .userLoginId(gameProposal.getUser().getLoginId())
                .adminLoginId(gameProposal.getAdmin() == null ? null : gameProposal.getAdmin().getLoginId())
                .gameTitle(gameProposal.getGameTitle())
                .description(gameProposal.getDescription())
                .status(gameProposal.getStatus())
                .build();
    }


    public static GameResponse toGameDto(Game game) {
        return GameResponse.builder()
                .id(game.getId())
                .gameTitle(game.getGameTitle())
                .gameDescription(game.getGameDescription())
                .created_at(game.getCreatedAt())
                .updated_at(game.getUpdatedAt())
                .build();
    }

    public static ExchangeResponse toExchangeDto(Exchange exchange){
        return ExchangeResponse.builder()
                .id(exchange.getId())
                .userId(exchange.getUser().getId())
                .gameId(exchange.getGame().getId())
                .title(exchange.getTitle())
                .price(exchange.getPrice())
                .description(exchange.getDescription())
                .quantity(exchange.getQuantity())
                .tradeType(exchange.getTradeType())
                .build();
    }


    public static ChatResponse toChatDto(Chat chats) {
        return ChatResponse.builder()
                .chatsId(chats.getId())
                .message(chats.getContent())
                .sender(chats.getUsername())
                .createdAt(chats.getCreatedAt())
                .build();
    }

}

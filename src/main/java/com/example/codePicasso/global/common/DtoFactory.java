package com.example.codePicasso.global.common;


import com.example.codePicasso.domain.category.dto.response.CategoryResponse;
import com.example.codePicasso.domain.category.entity.Category;
import com.example.codePicasso.domain.chat.dto.response.*;
import com.example.codePicasso.domain.chat.entity.*;
import com.example.codePicasso.domain.comment.dto.response.CommentResponse;
import com.example.codePicasso.domain.comment.dto.response.ReplyListResponse;
import com.example.codePicasso.domain.comment.dto.response.ReplyResponse;
import com.example.codePicasso.domain.comment.entity.Comment;
import com.example.codePicasso.domain.exchange.dto.response.ExchangeResponse;
import com.example.codePicasso.domain.exchange.dto.response.MyExchangeResponse;
import com.example.codePicasso.domain.exchange.entity.Exchange;
import com.example.codePicasso.domain.exchange.entity.MyExchange;
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
                .gameId(category.getGame().getId())
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
                postId(post.getId())
                .gameId(post.getGame().getId())
                .categoryName(post.getCategory().getCategoryName())
                .title(post.getTitle())
                .nickname(post.getUser().getNickname())
                .description(post.getDescription())
                .viewCount(post.getViewCount())
                .status(post.getStatus())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();
    }

    public static CommentResponse toCommentDto(Comment comment) {
        return CommentResponse.builder().
                commentId(comment.getId())
                .postId(comment.getPost().getId())
                .userId(comment.getUser().getId())
                .parentId(comment.getParent() != null ? comment.getParent().getId() : null)
                .nickname(comment.getUser().getNickname())
                .text(comment.getText())
                .createdAt(comment.getCreatedAt())
                .updatedAp(comment.getUpdatedAt())
                .replies(comment.getReplies().stream()
                        .map(DtoFactory::toCommentDto)
                        .toList())
                .build();
    }


    public static GameProposalResponse toGameProposalDto(GameProposal gameProposal) {
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

    public static ExchangeResponse toExchangeDto(Exchange exchange) {
        return ExchangeResponse.builder()
                .id(exchange.getId())
                .userId(exchange.getUser().getId())
                .gameId(exchange.getGame().getId())
                .title(exchange.getTitle())
                .price(exchange.getPrice())
                .description(exchange.getDescription())
                .contact(exchange.getContact())
                .quantity(exchange.getQuantity())
                .tradeType(exchange.getTradeType())
                .statustype(exchange.getStatusType())
                .build();
    }

    public static MyExchangeResponse toMyExchangeDto(MyExchange myExchange) {
        return MyExchangeResponse.builder()
                .exchange(myExchange.getExchange())
                .user(myExchange.getUser())
                .contact(myExchange.getContact())
                .build();
    }

    public static ChatResponse toChatDto(Chat chats) {
        return ChatResponse.builder()
                .chatsId(chats.getId())
                .message(chats.getContent())
                .username(chats.getUsername())
                .createdAt(chats.getCreatedAt())
                .build();
    }

    public static NotificationResponse toNotificationDto(Notification notification) {
        return NotificationResponse.builder()
                .notificationId(notification.getId())
                .chatRoomId(notification.getChatRoom().getId())
                .authorName(notification.getChat().getUsername())
                .content(notification.getChat().getContent())
                .writtenTime(notification.getChat().getCreatedAt())
                .createdTime(notification.getCreatedTime())
                .messageId(notification.getChat().getId())
                .proposerName(notification.getUser().getNickname())
                .build();
    }

    public static GlobalChatResponse toGlobalChatDto(GlobalChat chats) {
        return GlobalChatResponse.builder()
                .username(chats.getUsername())
                .chatsId(chats.getId())
                .message(chats.getContent())
                .createdAt(chats.getCreatedAt())
                .build();
    }

    public static RoomResponse toChatRoomDto(ChatRoom chatRoom) {
        return RoomResponse.builder()
                .roomId(chatRoom.getId())
                .username(chatRoom.getUser().getNickname())
                .roomName(chatRoom.getName())
                .isSecurity(chatRoom.isSecurity())
                .build();
    }

    public static ImageResponse toImageDto(Image saveImage) {
        return ImageResponse.builder()
                .imageUrl(saveImage.getImageUrl())
                .build();
    }
}

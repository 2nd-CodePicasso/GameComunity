package com.example.codePicasso.global.common;


import com.example.codePicasso.domain.category.dto.response.CategoryResponse;
import com.example.codePicasso.domain.category.entity.Category;
import com.example.codePicasso.domain.chat.dto.response.*;
import com.example.codePicasso.domain.chat.entity.Chat;
import com.example.codePicasso.domain.chat.entity.ChatRoom;
import com.example.codePicasso.domain.chat.entity.GlobalChat;
import com.example.codePicasso.domain.chat.entity.Notification;
import com.example.codePicasso.domain.comment.dto.response.CommentResponse;
import com.example.codePicasso.domain.comment.entity.Comment;
import com.example.codePicasso.domain.exchange.dto.response.ExchangeResponse;
import com.example.codePicasso.domain.exchange.dto.response.MyExchangeResponse;
import com.example.codePicasso.domain.exchange.entity.Exchange;
import com.example.codePicasso.domain.exchange.entity.MyExchange;
import com.example.codePicasso.domain.game.dto.response.GameResponse;
import com.example.codePicasso.domain.game.entity.Game;
import com.example.codePicasso.domain.gameProposal.dto.response.GameProposalResponse;
import com.example.codePicasso.domain.gameProposal.entity.GameProposal;
import com.example.codePicasso.domain.post.dto.response.PostListResponse;
import com.example.codePicasso.domain.post.dto.response.PostResponse;
import com.example.codePicasso.domain.post.entity.Post;
import com.example.codePicasso.domain.review.dto.response.ReviewResponse;
import com.example.codePicasso.domain.review.entity.Review;
import com.example.codePicasso.domain.user.dto.response.AdminResponse;
import com.example.codePicasso.domain.user.dto.response.UserResponse;
import com.example.codePicasso.domain.user.entity.Admin;
import com.example.codePicasso.domain.user.entity.User;
import org.springframework.data.domain.Page;

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

    public static PostResponse toPostResponseDto(PostResponse post) {
        return PostResponse.builder().
                postId(post.postId())
                .gameId(post.gameId())
                .categoryId(post.categoryId())
                .userId(post.userId())
                .categoryName(post.categoryName())
                .title(post.title())
                .nickname(post.nickname())
                .description(post.description())
                .viewCount(post.viewCount())
                .status(post.status())
                .createdAt(post.createdAt())
                .updatedAt(post.updatedAt())
                .build();
    }

    public static PostListResponse toPaginationDto(Page<PostResponse> postsPage) {
        return PostListResponse.builder()
                .postResponses(postsPage.getContent().stream()
                        .map(DtoFactory::toPostResponseDto)
                        .toList())
                .currentPage(postsPage.getNumber())
                .totalPages(postsPage.getTotalPages())
                .totalElements(postsPage.getTotalElements())
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
                .updatedAt(comment.getUpdatedAt())
                .replies(comment.getReplies().stream()
                        .map(DtoFactory::toCommentDto)
                        .toList())
                .build();
    }

    // 댓글 수정용
    public static CommentResponse toUpdateCommentDto(Comment comment) {
        return CommentResponse.builder().
                commentId(comment.getId())
                .postId(comment.getPost().getId())
                .userId(comment.getUser().getId())
                .nickname(comment.getUser().getNickname())
                .text(comment.getText())
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
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
                .build();
    }

    public static ExchangeResponse toExchangeResponseDto(ExchangeResponse exchange) {
        return ExchangeResponse.builder()
            .id(exchange.id())
            .userId(exchange.userId())
            .gameId(exchange.gameId())
            .title(exchange.title())
            .price(exchange.price())
            .description(exchange.description())
            .contact(exchange.contact())
            .quantity(exchange.quantity())
            .tradeType(exchange.tradeType())
            .build();
    }

    public static MyExchangeResponse toMyExchangeDto(MyExchange myExchange) {
        return MyExchangeResponse.builder()
                .id(myExchange.getId())
                .exchangeId(myExchange.getExchange().getId())
                .userId(myExchange.getUser().getId())
                .contact(myExchange.getContact())
                .statusType(myExchange.getStatusType())
                .build();
    }

    public static ReviewResponse toReviewDto(Review review) {
        return ReviewResponse.builder()
                .id(review.getId())
                .exchangeId(review.getExchange().getId())
                .userId(review.getUser().getId())
                .rating(review.getRating())
                .review(review.getReview())
                .build();
    }

    public static ChatResponse toChatDto(Chat chats) {
        return ChatResponse.builder()
                .chatsId(chats.getId())
                .roomId(chats.getChatRoom().getId())
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
                .imageUrl(chats.getImageUrl())
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

    public static GlobalChatDto toRabbitDto(GlobalChat globalChat) {
        return GlobalChatDto.builder()
                .username(globalChat.getUsername())
                .userId(globalChat.getUserId())
                .message(globalChat.getContent())
                .imageUrl(globalChat.getImageUrl())
                .createdAt(globalChat.getCreatedAt())
                .build();
    }
}

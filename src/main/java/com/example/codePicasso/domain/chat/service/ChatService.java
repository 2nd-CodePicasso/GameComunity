package com.example.codePicasso.domain.chat.service;

import com.example.codePicasso.domain.chat.dto.request.ChatRequest;
import com.example.codePicasso.domain.chat.dto.request.SecurityChatRequest;
import com.example.codePicasso.domain.chat.dto.response.ChatListResponse;
import com.example.codePicasso.domain.chat.dto.response.ChatResponse;
import com.example.codePicasso.domain.chat.dto.response.GlobalChatListResponse;
import com.example.codePicasso.domain.chat.dto.response.GlobalChatResponse;
import com.example.codePicasso.domain.chat.entity.Chat;
import com.example.codePicasso.global.common.DtoFactory;
import com.example.codePicasso.domain.chat.entity.ChatRoom;
import com.example.codePicasso.domain.chat.entity.GlobalChat;
import com.example.codePicasso.global.config.PasswordEncoder;
import com.example.codePicasso.global.exception.base.DuplicateException;
import com.example.codePicasso.global.exception.base.InvalidRequestException;
import com.example.codePicasso.global.exception.enums.ErrorCode;
import com.vdurmont.emoji.EmojiParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {

    private final GlobalChatConnector globalChatConnector;
    private final ChatConnector chatConnector;
    private final RoomConnector roomConnector;
    private final PasswordEncoder passwordEncoder;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Transactional
    public GlobalChatResponse addForAllRoomToMessage(ChatRequest chatsRequest, Long userId, String username) {
        String emoji = EmojiParser.parseToUnicode(chatsRequest.message());
        GlobalChat globalChat = chatsRequest.toEntityFromGlobalChat(userId,emoji,username);
        //메시지 브로커를 이용해서 저장하기 전에 발행
        simpMessagingTemplate.convertAndSend("/topic/hi",DtoFactory.toGlobalChatDto(globalChat));

        GlobalChat chats = globalChatConnector.save(globalChat);
        return DtoFactory.toGlobalChatDto(chats);
    }

    @Transactional(readOnly = true)
    public GlobalChatListResponse getChatsHistory(Long chatId, LocalDateTime lastTime,int size) {
        List<GlobalChat> chats = globalChatConnector.findAll(chatId,lastTime,size);
        return GlobalChatListResponse.builder()
                .chatsResponses(chats.stream().map(DtoFactory::toGlobalChatDto).toList())
                .build();
    }

    @Transactional
    public ChatResponse addForRoomToMessage(ChatRequest chatsRequest, Long roomId, Long userId, String username) {
        ChatRoom chatRoom = roomConnector.findById(roomId);
        String emoji = EmojiParser.parseToUnicode(chatsRequest.message());
        Chat chat = chatsRequest.toEntityFromChat(userId, chatRoom,emoji,username);
        Chat saveChat = chatConnector.save(chat);
        return DtoFactory.toChatDto(saveChat);
    }

    @Transactional(readOnly = true)
    public ChatListResponse getByRoomId(Long roomId, int size, Long chatId, LocalDateTime localDateTime) {
        if (roomConnector.isSecurityById(roomId)) {
            throw new DuplicateException(ErrorCode.UNAUTHORIZED_CHAT_ROOM);
        }
        List<Chat> chats = chatConnector.findAll(roomId,size,chatId,localDateTime);
        return ChatListResponse.builder()
                .chatResponses(chats.stream().map(DtoFactory::toChatDto).toList())
                .build();
    }

    @Transactional(readOnly = true)
    public ChatListResponse getSecurityChatsHistory(SecurityChatRequest securityChatRequest) {
        ChatRoom chatRoom = roomConnector.findById(securityChatRequest.roomId());
        if (!passwordEncoder.matches(securityChatRequest.password(), chatRoom.getPassword())) {
            throw new InvalidRequestException(ErrorCode.PW_ERROR);
        }
        List<Chat> chats = chatConnector.findAllByRoomId(securityChatRequest.roomId());

        return ChatListResponse.builder()
                .chatResponses(chats.stream().map(DtoFactory::toChatDto).toList())
                .build();
    }
}

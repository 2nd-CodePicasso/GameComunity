package com.example.codePicasso.domain.chat.service;

import com.example.codePicasso.domain.chat.dto.request.ChatRequest;
import com.example.codePicasso.domain.chat.dto.response.ChatListResponse;
import com.example.codePicasso.domain.chat.dto.response.ChatResponse;
import com.example.codePicasso.domain.chat.dto.response.GlobalChatResponse;
import com.example.codePicasso.domain.chat.entity.Chat;
import com.example.codePicasso.global.common.DtoFactory;
import com.example.codePicasso.domain.user.service.UserConnector;
import com.example.codePicasso.domain.chat.entity.ChatRoom;
import com.example.codePicasso.domain.chat.entity.GlobalChat;
import com.vdurmont.emoji.EmojiParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {

    private final GlobalChatConnector globalChatConnector;
    private final ChatConnector chatConnector;
    private final RoomConnector roomConnector;

    @Transactional
    public GlobalChatResponse addForAllRoomToMessage(ChatRequest chatsRequest, Long userId) {
        String emoji = EmojiParser.parseToUnicode(chatsRequest.message());
        GlobalChat globalChat = chatsRequest.toEntityFromGlobalChat(userId,emoji);
        GlobalChat chats = globalChatConnector.save(globalChat);
        return DtoFactory.toGlobalChatDto(chats);
    }

    @Transactional(readOnly = true)
    public List<GlobalChatResponse> getChatsHistory() {
        List<GlobalChat> chats = globalChatConnector.findAll();
        return chats.stream().map(GlobalChat::toDto).toList();
    }

    @Transactional
    public ChatResponse addForRoomToMessage(ChatRequest chatsRequest, Long roomId, Long userId) {
        ChatRoom chatRoom = roomConnector.findById(roomId);
        String emoji = EmojiParser.parseToUnicode(chatsRequest.message());
        Chat chat = chatsRequest.toEntityFromChat(userId, chatRoom,emoji);
        Chat saveChat = chatConnector.save(chat);
        return DtoFactory.toChatDto(saveChat);
    }

    @Transactional(readOnly = true)
    public List<ChatResponse> getByRoomId(Long roomId) {
        List<Chat> chats = chatConnector.findAllByRoomId(roomId);
        return chats.stream().map(DtoFactory::toChatDto).toList();
    }
}

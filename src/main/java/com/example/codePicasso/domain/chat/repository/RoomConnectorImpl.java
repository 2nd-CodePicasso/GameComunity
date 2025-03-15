package com.example.codePicasso.domain.chat.repository;

import com.example.codePicasso.domain.chat.entity.ChatRoom;
import com.example.codePicasso.domain.chat.entity.QChatRoom;
import com.example.codePicasso.domain.chat.service.RoomConnector;
import com.example.codePicasso.domain.user.entity.QUser;
import com.example.codePicasso.global.exception.base.NotFoundException;
import com.example.codePicasso.global.exception.enums.ErrorCode;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RoomConnectorImpl implements RoomConnector {
    private final RoomRepository roomRepository;
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public ChatRoom save(ChatRoom chatRoom) {
        return roomRepository.save(chatRoom);
    }

    @Override
    public List<ChatRoom> findAll() {
        QChatRoom chatRoom = QChatRoom.chatRoom;
        QUser user = QUser.user;

        return jpaQueryFactory.select(chatRoom)
                .from(chatRoom)
                .leftJoin(chatRoom.user, user).fetchJoin()
                .fetch();
    }

    @Override
    public ChatRoom findByName(String roomName) {
        return roomRepository.findByName(roomName)
                .orElseThrow(() -> new NotFoundException(ErrorCode.CHATTING_NOT_FOUND));
    }

    @Override
    public ChatRoom findByIdAndUserId(Long roomId, Long userId) {
        return roomRepository.findByIdAndUserId(roomId, userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.CHATTING_NOT_FOUND));
    }

    @Override
    public void deleteRoom(ChatRoom chatRoom) {
        roomRepository.delete(chatRoom);
    }

    @Override
    public ChatRoom findById(Long roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.CHATTING_NOT_FOUND));
    }

    public boolean isSecurityById(Long roomId) {
        return roomRepository.findIsSecurityById(roomId);
    }
}

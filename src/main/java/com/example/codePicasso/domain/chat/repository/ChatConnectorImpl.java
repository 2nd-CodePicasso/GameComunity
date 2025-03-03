package com.example.codePicasso.domain.chat.repository;

import com.example.codePicasso.domain.chat.entity.Chat;
import com.example.codePicasso.domain.chat.entity.QChat;
import com.example.codePicasso.domain.chat.service.ChatConnector;
import com.example.codePicasso.global.exception.base.NotFoundException;
import com.example.codePicasso.global.exception.enums.ErrorCode;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ChatConnectorImpl implements ChatConnector {

    private final ChatRepository chatsRepository;
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Chat save(Chat chats) {
        return chatsRepository.save(chats);
    }

    @Override
    public List<Chat> findAll(Long roomId, int size, Long chatId, LocalDateTime lastTime) {
        QChat chat = QChat.chat;
        return  jpaQueryFactory.selectFrom(chat)
                .where(chat.chatRoom.id.eq(roomId))
                .where(chat.createdAt.lt(lastTime)
                        .or(chat.createdAt.eq(lastTime)
                                .and(chat.id.lt(chatId))))
                .orderBy(chat.createdAt.desc(),chat.id.desc())
                .limit(size)
                .fetch();
    }

    @Override
    public List<Chat> findAllByRoomId(Long roomId) {
        return chatsRepository.findAllByChatRoomId(roomId);
    }

    @Override
    public Chat findById(Long aLong) {
        return chatsRepository.findById(aLong).orElseThrow(()-> new NotFoundException(ErrorCode.CHATTING_NOT_FOUND));
    }
}

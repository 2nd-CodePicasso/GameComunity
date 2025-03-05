package com.example.codePicasso.domain.chat.repository;

import com.example.codePicasso.domain.chat.dto.response.GlobalChatResponse;
import com.example.codePicasso.domain.chat.entity.GlobalChat;
import com.example.codePicasso.domain.chat.entity.QGlobalChat;
import com.example.codePicasso.domain.chat.service.GlobalChatConnector;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GlobalChatConnectorImpl implements GlobalChatConnector {

    private final GlobalChatRepository globalChatRepository;
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    @Transactional
    public GlobalChat save(GlobalChat chats) {
        return globalChatRepository.save(chats);
    }

    @Override
    public List<GlobalChat> findAll(Long chatId, LocalDateTime lastTime,int size) {
        QGlobalChat globalChat = QGlobalChat.globalChat;
        return jpaQueryFactory.selectFrom(globalChat)
                .where(globalChat.createdAt.lt(lastTime)
                        .or(globalChat.createdAt.eq(lastTime)
                                .and(globalChat.id.lt(chatId))))
                .orderBy(globalChat.createdAt.desc(),globalChat.id.desc())
                .limit(size)

                .fetch();
    }

    @Override
    @Transactional
    public void saveAll(List<GlobalChatResponse> messages) {
        QGlobalChat globalChat = QGlobalChat.globalChat;

        jpaQueryFactory.insert(globalChat)
                .columns(globalChat.username, globalChat.content, globalChat.imageUrl, globalChat.createdAt)
                .values(messages.stream()
                        .map(m -> new Object[]{m.username(), m.message(), m.imageUrl(), m.createdAt()})
                        .toList()
                )
                .execute();

    }
}

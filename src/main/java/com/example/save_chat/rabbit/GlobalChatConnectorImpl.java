//package com.example.save_chat.rabbit;
//
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import lombok.RequiredArgsConstructor;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Component
//@RequiredArgsConstructor
//public class GlobalChatConnectorImpl implements GlobalChatConnector {
//
//    private final GlobalChatRepository globalChatRepository;
//    private final JPAQueryFactory jpaQueryFactory;
//    private final JdbcTemplate jdbcTemplate;
//
//    @Override
//    @Transactional
//    public GlobalChat save(GlobalChat chats) {
//        return globalChatRepository.save(chats);
//    }
//
//    @Override
//    public List<GlobalChat> findAll(Long chatId, LocalDateTime lastTime, int size) {
//        QGlobalChat globalChat = QGlobalChat.globalChat;
//        return jpaQueryFactory.selectFrom(globalChat)
//                .where(globalChat.createdAt.lt(lastTime)
//                        .or(globalChat.createdAt.eq(lastTime)
//                                .and(globalChat.id.lt(chatId))))
//                .orderBy(globalChat.createdAt.desc(), globalChat.id.desc())
//                .limit(size)
//
//                .fetch();
//    }
//
//}

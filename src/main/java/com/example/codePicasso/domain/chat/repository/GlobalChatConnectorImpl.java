package com.example.codePicasso.domain.chat.repository;

import com.example.codePicasso.domain.chat.dto.response.GlobalChatDto;
import com.example.codePicasso.domain.chat.dto.response.GlobalChatResponse;
import com.example.codePicasso.domain.chat.entity.GlobalChat;
import com.example.codePicasso.domain.chat.entity.QGlobalChat;
import com.example.codePicasso.domain.chat.service.GlobalChatConnector;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GlobalChatConnectorImpl implements GlobalChatConnector {

    private final GlobalChatRepository globalChatRepository;
    private final JPAQueryFactory jpaQueryFactory;
    private final JdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public GlobalChat save(GlobalChat chats) {
        return globalChatRepository.save(chats);
    }

    @Override
    public List<GlobalChat> findAll(Long chatId, LocalDateTime lastTime, int size) {
        QGlobalChat globalChat = QGlobalChat.globalChat;
        return jpaQueryFactory.selectFrom(globalChat)
                .where(globalChat.createdAt.lt(lastTime)
                        .or(globalChat.createdAt.eq(lastTime)
                                .and(globalChat.id.lt(chatId))))
                .orderBy(globalChat.createdAt.desc(), globalChat.id.desc())
                .limit(size)

                .fetch();
    }

    public void saveAll(List<GlobalChatDto> globalChatDtos) {
        List<GlobalChat> chats = globalChatDtos.stream().map(GlobalChatDto::toEntity).toList();
        // message_type 컬럼 제거: global_chats 테이블에 필요한 컬럼만 지정
        String sql = "INSERT INTO global_chats (user_id, username, content, image_url, created_at) " +
                "VALUES (?, ?, ?, ?, ?)";

        // JdbcTemplate의 batchUpdate()를 사용하여 여러 건의 INSERT를 배치 처리
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                GlobalChat chat = chats.get(i);  // 리스트에서 i번째 GlobalChat 객체

                // 각 매개변수에 맞게 값을 설정
                ps.setLong(1, chat.getUserId());                    // user_id: Long
                ps.setString(2, chat.getUsername());                // username: String
                ps.setString(3, chat.getContent());                 // content: String
                ps.setString(4, chat.getImageUrl());                // image_url: String
                ps.setTimestamp(5, Timestamp.valueOf(chat.getCreatedAt())); // created_at: LocalDateTime -> Timestamp 변환
            }

            @Override
            public int getBatchSize() {
                return chats.size();
            }
        });
    }
}

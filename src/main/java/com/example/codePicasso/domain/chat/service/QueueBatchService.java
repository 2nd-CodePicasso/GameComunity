package com.example.codePicasso.domain.chat.service;

import com.example.codePicasso.domain.chat.dto.response.GlobalChatResponse;
import com.example.codePicasso.global.exception.base.RabbitException;
import com.example.codePicasso.global.exception.enums.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.GetResponse;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class QueueBatchService {

    private static final String QUEUE_NAME = "rabbit";
    private static final int BATCH_SIZE = 10;

    private final CachingConnectionFactory connectionFactory;
    private final ObjectMapper objectMapper;
    private final GlobalChatConnector globalChatConnector;

    private Connection connection;
    private Channel channel;


    @PostConstruct
    public void init() {
        try {
            this.connection = connectionFactory.createConnection();
            this.channel = connection.createChannel(false);
            log.info("RabbitMQ Connection, Channel 생성 완료");
        } catch (Exception e) {
            log.error("RabbitMQ 연결 실패: {}", e.getMessage());
            throw new RabbitException(ErrorCode.RABBIT_EXCEPTION);
        }
    }


    @Scheduled(fixedDelay = 1000)
    public void checkQueue() {
        try {
            if (channel == null || !channel.isOpen()) {
                log.warn("채널이 닫혀 있어 재연결 시도");
                this.channel = connection.createChannel(false);
            }

            long messageCount = channel.queueDeclarePassive(QUEUE_NAME).getMessageCount();

            if (messageCount >= BATCH_SIZE) {
                processBatch(channel);
            }
        } catch (Exception e) {
            log.error("RabbitMQ 채널 에러: {}", e.getMessage());
            throw new RabbitException(ErrorCode.RABBIT_EXCEPTION);
        }
    }


    private void processBatch(Channel channel) {
        List<GlobalChatResponse> messages = new ArrayList<>();
        List<Long> deliveryTags = new ArrayList<>();
        try {
            for (int i = 0; i < BATCH_SIZE; i++) {
                // autoAck를 false로 하여, 직접 ack를 전송 (처리 후 메시지 제거)
                GetResponse response = channel.basicGet(QUEUE_NAME, false);
                if (response != null) {
                    String message = new String(response.getBody(), "UTF-8");
                    GlobalChatResponse globalChatResponse = objectMapper.readValue(message, GlobalChatResponse.class);
                    messages.add(globalChatResponse);
                    deliveryTags.add(response.getEnvelope().getDeliveryTag());
                }
            }

                globalChatConnector.saveAll(messages);

            for (Long deliveryTag : deliveryTags) {
                channel.basicAck(deliveryTag, false);
            }
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new RabbitException(ErrorCode.RABBIT_EXCEPTION);
        }
    }

    @PreDestroy
    public void cleanup() {
        try {
            if (channel != null && channel.isOpen()) {
                channel.close();
            }
            if (connection != null && connection.isOpen()) {
                connection.close();
            }
            log.info("RabbitMQ Connection, Channel 정상 종료");
        } catch (Exception e) {
            log.error("RabbitMQ 자원 정리 실패: {}", e.getMessage());
        }
    }
}
package com.example.codePicasso.global.exception.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // Common
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "C01", "1"),
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND,"C02",""),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"C03",""),
    NOT_FOUND_TOKEN(HttpStatus.NOT_FOUND, "", "토큰이 없습니다."),

    //Auth
    ID_ALREADY_EXISTS(HttpStatus.CONFLICT,"","이미 해당 아이디가 존재합니다."),
    NOT_FOUND_ID(HttpStatus.BAD_REQUEST,"", "해당 아이디가 존재하지 않습니다."),
    UNAUTHORIZED_ID(HttpStatus.UNAUTHORIZED,"","권한이 없습니다."),
    KAKAO_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR,"","카카오인증중 에러발생"),

    //User
    ID_ERROR(HttpStatus.UNAUTHORIZED,"1","아이디가 올바르지 않습니다."),
    PW_ERROR(HttpStatus.UNAUTHORIZED,"2","비밀번호가 올바르지 않습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "", "유저를 찾을 수 없습니다."),
    GAME_DELETED(HttpStatus.BAD_REQUEST, "", "삭제된 게임입니다."),

    // GameProposal
    PROPOSAL_NOT_FOUND(HttpStatus.NOT_FOUND, "", "요청을 찾을 수 없습니다."),
    PROPOSAL_ALREADY_REVIEWED(HttpStatus.BAD_REQUEST, "", "이미 관리자에 의해 처리된 요청입니다."),
    NOT_YOUR_PROPOSAL(HttpStatus.UNAUTHORIZED, "","귀하가 신청한 요청이 아닙니다."),

    //Game
    GAME_NOT_FOUND(HttpStatus.NOT_FOUND, "", "게임을 찾을 수 없습니다."),
    GAME_ALREADY_DELETED(HttpStatus.BAD_REQUEST, "", "이미 삭제된 게임입니다."),
    GAME_ALREADY_ACTIVATED(HttpStatus.BAD_REQUEST,"", "이미 활성화된 게임입니다.."),

    //Post
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "", "게시글을 찾을 수 없습니다."),

    //Comment
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "", "댓글을 찾을 수 없습니다."),
    CANNOT_WRITE_COMMENT(HttpStatus.BAD_REQUEST, "", "댓글을 작성할 수 없습니다."),

    //Category
    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "", "카테고리를 찾을 수 없습니다."),

    //Recommend
    RECOMMEND_NOT_FOUND(HttpStatus.NOT_FOUND, "", "존재하지 않는 추천입니다."),
    ALREADY_RECOMMENDED_POST(HttpStatus.BAD_REQUEST, "", "이미 추천한 게시글입니다."),

    //Chatting
    CHATTING_NOT_FOUND(HttpStatus.NOT_FOUND,"", "채팅 기록을 찾을 수 없습니다."),
    ROOM_NOT_FOUND(HttpStatus.NOT_FOUND,"", "채팅방을 찾을 수 없습니다."),
    NOTIFICATION_NOT_FOUND(HttpStatus.NOT_FOUND, "", "공지를 찾을수 없습니다."),
    UNAUTHORIZED_CHAT_ROOM(HttpStatus.UNAUTHORIZED,"","해당 채팅방은 비밀방입니다."),
    IMAGE_IOEXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "", "이미지 입출력작업중 문제가 발생했습니다."),
    REDIS_JACKSON_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR,"","레디스에서 메시지 발행하는도중 역직렬화에 실패했습니다."),
    RABBIT_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR,"","RabbitMQ의 연결 실패"),

    //Exchange
    EXCHANGE_NOT_FOUND(HttpStatus.NOT_FOUND, "", "거래내역을 찾을 수 없습니다."),

    //MyExchange
    MYEXCHANGE_NOT_FOUND(HttpStatus.NOT_FOUND, "", "내 거래내역을 찾을 수 없습니다."),
    ALREADY_IN_PROGRESS(HttpStatus.BAD_REQUEST, "", "거래 완료 처리가 이미 진행 중입니다."),
    ALREADY_IN_COMPLETED(HttpStatus.BAD_REQUEST, "", "이미 완료된 거래입니다."),
    DUPLICATE(HttpStatus.BAD_REQUEST, "", "중복된 요청입니다."),
    NOT_COMPLETED(HttpStatus.BAD_REQUEST, "", "완료되지 않은 거래입니다."),

    //Review
    REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND, "", "후기글을 찾을 수 없습니다."),
    TRANSACTION_FORBIDDEN(HttpStatus.BAD_REQUEST, "", "내 글에서 거래할 수 없습니다."),

    //Redis
    TRADE_RANKING_UPDATE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "", "거래 랭킹 업데이트 중 문제가 발생했습니다.");


    private final HttpStatus status;
    private final String code;
    private final String message;
}

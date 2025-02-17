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

    //User
    ID_ERROR(HttpStatus.UNAUTHORIZED,"1","아이디가 올바르지 않습니다."),
    PW_ERROR(HttpStatus.UNAUTHORIZED,"2","비밀번호가 올바르지 않습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "", "유저를 찾을 수 없습니다."),

    // GameProposal
    PROPOSAL_NOT_FOUND(HttpStatus.NOT_FOUND, "", "요청을 찾을 수 없습니다."),
    PROPOSAL_ALREADY_REVIEWED(HttpStatus.BAD_REQUEST, "", "이미 관리자에 의해 처리된 요청입니다."),
    NOT_YOUR_PROPOSAL(HttpStatus.UNAUTHORIZED, "","귀하가 신청한 요청이 아닙니다."),

    //Game
    GAME_NOT_FOUND(HttpStatus.NOT_FOUND, "", "게임을 찾을 수 없습니다."),
    GAME_ALREADY_DELETED(HttpStatus.BAD_REQUEST, "", "이미 삭제된 게임입니다."),
    GAME_ALREADY_ACTIVATED(HttpStatus.BAD_REQUEST,"", "이미 활성화된 게임입니다.."),

    //Post
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "", "커뮤니티를 찾을 수 없습니다."),

    //Category
    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "", "카테고리를 찾을 수 없습니다."),

    //Chatting
    CHATTING_NOT_FOUND(HttpStatus.NOT_FOUND,"", "채팅 기록을 찾을 수 없습니다."),

    //Exchange
    EXCHANGE_NOT_FOUND(HttpStatus.NOT_FOUND, "", "거래내역을 찾을 수 없습니다."),

    //MyExchange
    MYEXCHANGE_NOT_FOUND(HttpStatus.NOT_FOUND, "", "내 거래내역을 찾을 수 없습니다.");


    private final HttpStatus status;
    private final String code;
    private final String message;
}

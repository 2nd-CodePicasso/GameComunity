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

    //Auth
    ID_ALREADY_EXISTS(HttpStatus.CONFLICT,"","이미 해당 아이디가 존재합니다."),
    NOT_FOUND_ID(HttpStatus.BAD_REQUEST,"", "해당 아이디가 존재하지 않습니다."),
    UNAUTHORIZED_ID(HttpStatus.UNAUTHORIZED,"","권한이 없습니다. 관리자만 접근이 가능합니다."),

    //User
    USER_ID_ERROR(HttpStatus.UNAUTHORIZED,"1","아이디나 비밀번호가 올바르지 않습니다."),
    USER_PW_ERROR(HttpStatus.UNAUTHORIZED,"2","아이디나 비밀번호가 올바르지 않습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "", "유저를 찾을 수 없습니다."),


    //Game
    GAME_NOT_FOUND(HttpStatus.NOT_FOUND, "", "게임을 찾을 수 없습니다."),

    //Community
    COMMUNITY_NOT_FOUND(HttpStatus.NOT_FOUND, "", "커뮤니티를 찾을 수 없습니다."),

    //Chatting
    CHATTING_NOT_FOUND(HttpStatus.NOT_FOUND,"", "채팅 기록을 찾을 수 없습니다."),

    //ItemExchange
    EXCHANGE_NOT_FOUND(HttpStatus.NOT_FOUND, "", "거래내역을 찾을 수 없습니다.");


    private final HttpStatus status;
    private final String code;
    private final String message;
}

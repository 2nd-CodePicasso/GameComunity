package com.example.codePicasso.global.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public record ApiResponse<T>(boolean successOrFail, T data, String message) {

    public static <T> ResponseEntity<ApiResponse<T>> success(T data) {
        return ResponseEntity.ok(new ApiResponse<>(true, data, null));
    }

    public static <T> ResponseEntity<ApiResponse<T>> created(T data) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(new ApiResponse<>(true, data, null));
    }

    //수정이나 삭제 후 반환 데이터가 없을 시 사용 -> 204 No Content
    public static <T> ResponseEntity<ApiResponse<Void>> noContent() {
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
            .body(new ApiResponse<>(true, null, null));
    }

    public static <T> ResponseEntity<ApiResponse<Void>> noContentAndSendMessage(String message) {
        return ResponseEntity.ok(new ApiResponse<>(true, null, message));
    }

    public static <T> ResponseEntity<ApiResponse<T>> fail(HttpStatus status, String message) {
        return ResponseEntity.status(status)
            .body(new ApiResponse<>(false, null, message));
    }

}

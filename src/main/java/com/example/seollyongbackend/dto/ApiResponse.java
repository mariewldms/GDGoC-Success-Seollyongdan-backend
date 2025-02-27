package com.example.seollyongbackend.dto;

import org.springframework.http.HttpStatus;

public class ApiResponse<T> {
    private HttpStatus httpStatus;
    private boolean success;
    private T result;
    private String error;

    public ApiResponse(HttpStatus httpStatus, boolean success, T result, String error) {
        this.httpStatus = httpStatus;
        this.success = success;
        this.result = result;
        this.error = error;
    }

    public ApiResponse(String 댓글_작성_성공, CommentResponseDto commentResponseDto) {
    }

    public static <T> ApiResponse<T> success(HttpStatus status, T result) {
        return new ApiResponse<>(status, true, result, null);
    }

    public static <T> ApiResponse<T> error(HttpStatus status, String errorMessage) {
        return new ApiResponse<>(status, false, null, errorMessage);
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public boolean isSuccess() {
        return success;
    }

    public T getResult() {
        return result;
    }

    public String getError() {
        return error;
    }
}

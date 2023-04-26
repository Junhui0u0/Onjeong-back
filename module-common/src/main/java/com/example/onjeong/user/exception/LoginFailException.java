package com.example.onjeong.user.exception;

import com.example.onjeong.error.ErrorCode;
import lombok.Getter;

@Getter
public class LoginFailException extends RuntimeException{
    private final ErrorCode errorCode;

    public LoginFailException(String message, ErrorCode errorCode){
        super(message);
        this.errorCode = errorCode;
    }
}
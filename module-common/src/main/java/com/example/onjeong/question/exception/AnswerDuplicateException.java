package com.example.onjeong.question.exception;

import com.example.onjeong.error.ErrorCode;
import lombok.Getter;

@Getter
public class AnswerDuplicateException extends RuntimeException{

    private final ErrorCode errorCode;

    public AnswerDuplicateException(String message, ErrorCode errorCode){
        super(message);
        this.errorCode = errorCode;
    }
}
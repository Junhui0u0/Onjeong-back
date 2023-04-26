package com.example.onjeong.error;

import com.example.onjeong.S3.FileUploadException;
import com.example.onjeong.S3.UploadFileNotExistException;
import com.example.onjeong.anniversary.exception.AnniversaryNotExistException;
import com.example.onjeong.anniversary.exception.EmailDuplicateException;
import com.example.onjeong.board.exception.BoardNotExistException;
import com.example.onjeong.board.exception.BoardWriterNotSameException;
import com.example.onjeong.family.exception.FamilyNotExistException;
import com.example.onjeong.home.exception.RandCoinDuplicateException;
import com.example.onjeong.mail.exception.MailNotExistException;
import com.example.onjeong.mail.exception.ReceiveUserNotExistException;
import com.example.onjeong.profile.exception.ProfileNotExistException;
import com.example.onjeong.question.exception.AnswerDuplicateException;
import com.example.onjeong.user.exception.*;
import com.example.onjeong.question.exception.AnswerNotExistException;
import com.example.onjeong.question.exception.NullQuestionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailDuplicateException.class)
    public ResponseEntity<ErrorResponse> handleEmailDuplicateException(EmailDuplicateException ex){
        log.error("handleEmailDuplicateException",ex);
        final ErrorResponse response = new ErrorResponse(ex.getErrorCode());
        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
    }

    @ExceptionHandler(UserNotExistException.class)
    public ResponseEntity<ErrorResponse> handleUserNotExistException(UserNotExistException ex){
        log.error("handleUserNotExistException",ex);
        final ErrorResponse response = new ErrorResponse(ex.getErrorCode());
        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
    }

    @ExceptionHandler(ReceiveUserNotExistException.class)
    public ResponseEntity<ErrorResponse> handleReceiveUserNotExistException(ReceiveUserNotExistException ex){
        log.error("handleReceiveUserNotExistException",ex);
        final ErrorResponse response = new ErrorResponse(ex.getErrorCode());
        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
    }

    @ExceptionHandler(AnswerDuplicateException.class)
    public ResponseEntity<ErrorResponse> handleAnswerDuplicateException(AnswerDuplicateException ex){
        log.error("handleAnswerDuplicateException",ex);
        final ErrorResponse response = new ErrorResponse(ex.getErrorCode());
        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
    }

    @ExceptionHandler(MailNotExistException.class)
    public ResponseEntity<ErrorResponse> handleMailNotExistException(MailNotExistException ex){
        log.error("handleMailNotExistException",ex);
        final ErrorResponse response = new ErrorResponse(ex.getErrorCode());
        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
    }

    @ExceptionHandler(NullQuestionException.class)
    public ResponseEntity<ErrorResponse> handleNullQuestionException(NullQuestionException ex){
        log.error("handleNullQuestionException",ex);
        final ErrorResponse response = new ErrorResponse(ex.getErrorCode());
        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
    }

    @ExceptionHandler(AnswerNotExistException.class)
    public ResponseEntity<ErrorResponse> handleAnswerNotExistException(AnswerNotExistException ex){
        log.error("handleAnswerNotExistException",ex);
        final ErrorResponse response = new ErrorResponse(ex.getErrorCode());
        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex){
        log.error("handleException",ex);
        final ErrorResponse response = new ErrorResponse(ErrorCode.INTER_SERVER_ERROR);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex){
        log.error("handleHttpMessageNotReadableException",ex);
        final ErrorResponse response = new ErrorResponse(ErrorCode.DATETYPE_ERROR);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserNicknameDuplicationException.class)
    public ResponseEntity<ErrorResponse> handleUserNicknameDuplicationException(UserNicknameDuplicationException ex){
        log.error("handleUserNicknameDuplicationException",ex);
        final ErrorResponse response = new ErrorResponse(ex.getErrorCode());
        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
    }

    @ExceptionHandler(UserUnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handleUserUnauthorizedException(UserUnauthorizedException ex){
        log.error("handleUserUnauthorizedException",ex);
        final ErrorResponse response = new ErrorResponse(ex.getErrorCode());
        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
    }

    @ExceptionHandler(RefreshTokenExpiredException.class)
    public ResponseEntity<ErrorResponse> handleRefreshTokenExpiredException(RefreshTokenExpiredException ex){
        log.error("handleRefreshTokenExpiredException",ex);
        final ErrorResponse response = new ErrorResponse(ex.getErrorCode());
        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
    }

    @ExceptionHandler(TokenExpiredJwtException.class)
    public ResponseEntity<ErrorResponse> handleTokenExpiredJwtException(TokenExpiredJwtException ex){
        log.error("handleTokenExpiredJwtException",ex);
        final ErrorResponse response = new ErrorResponse(ex.getErrorCode());
        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
    }

    @ExceptionHandler(JoinedUserNotExistException.class)
    public ResponseEntity<ErrorResponse> handleJoinedUserNotExistException(JoinedUserNotExistException ex){
        log.error("handleJoinedUserNotExistException",ex);
        final ErrorResponse response = new ErrorResponse(ex.getErrorCode());
        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
    }

    @ExceptionHandler(FamilyNotExistException.class)
    public ResponseEntity<ErrorResponse> handleFamilyNotExistException(FamilyNotExistException ex){
        log.error("handleFamilyNotExistException",ex);
        final ErrorResponse response = new ErrorResponse(ex.getErrorCode());
        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
    }

    @ExceptionHandler(ProfileNotExistException.class)
    public ResponseEntity<ErrorResponse> handleProfileNotExistException(ProfileNotExistException ex){
        log.error("handleProfileNotExistException",ex);
        final ErrorResponse response = new ErrorResponse(ex.getErrorCode());
        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
    }

    @ExceptionHandler(AnniversaryNotExistException.class)
    public ResponseEntity<ErrorResponse> handleAnniversaryNotExistException(AnniversaryNotExistException ex){
        log.error("handleAnniversaryNotExistException",ex);
        final ErrorResponse response = new ErrorResponse(ex.getErrorCode());
        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
    }

    @ExceptionHandler(UploadFileNotExistException.class)
    public ResponseEntity<ErrorResponse> handleUploadFileNotExistException(UploadFileNotExistException ex){
        log.error("handleUploadFileNotExistException",ex);
        final ErrorResponse response = new ErrorResponse(ex.getErrorCode());
        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
    }

    @ExceptionHandler(FileUploadException.class)
    public ResponseEntity<ErrorResponse> handleFileUploadException(FileUploadException ex){
        log.error("handleFileUploadException",ex);
        final ErrorResponse response = new ErrorResponse(ex.getErrorCode());
        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
    }

    @ExceptionHandler(BoardWriterNotSameException.class)
    public ResponseEntity<ErrorResponse> handleBoardWriterNotSameException(BoardWriterNotSameException ex){
        log.error("handleBoardWriterNotSameException",ex);
        final ErrorResponse response = new ErrorResponse(ex.getErrorCode());
        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
    }

    @ExceptionHandler(BoardNotExistException.class)
    public ResponseEntity<ErrorResponse> handleBoardNotExistException(BoardNotExistException ex){
        log.error("handleBoardNotExistException",ex);
        final ErrorResponse response = new ErrorResponse(ex.getErrorCode());
        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
    }

    @ExceptionHandler(UserPasswordNotCorrectException.class)
    public ResponseEntity<ErrorResponse> handleUserPasswordNotCorrectException(UserPasswordNotCorrectException ex){
        log.error("handleUserPasswordNotCorrectException",ex);
        final ErrorResponse response = new ErrorResponse(ex.getErrorCode());
        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
    }

    @ExceptionHandler(RandCoinDuplicateException.class)
    public ResponseEntity<ErrorResponse> handleRandCoinDuplicateException(RandCoinDuplicateException ex){
        log.error("handleRandCoinDuplicateException",ex);
        final ErrorResponse response = new ErrorResponse(ex.getErrorCode());
        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
    }

    @ExceptionHandler(TokenNullException.class)
    public ResponseEntity<ErrorResponse> handleTokenNullException(TokenNullException ex){
        log.error("handleTokenNullException",ex);
        final ErrorResponse response = new ErrorResponse(ex.getErrorCode());
        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
    }

    @ExceptionHandler(AccessTokenNotSameException.class)
    public ResponseEntity<ErrorResponse> handleAccessTokenNotSameException(AccessTokenNotSameException ex){
        log.error("handleAccessTokenNotSameException",ex);
        final ErrorResponse response = new ErrorResponse(ex.getErrorCode());
        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
    }

    @ExceptionHandler(AccessTokenExpiredException.class)
    public ResponseEntity<ErrorResponse> handleAccessTokenExpiredException(AccessTokenExpiredException ex){
        log.error("handleAccessTokenExpiredException",ex);
        final ErrorResponse response = new ErrorResponse(ex.getErrorCode());
        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
    }

    @ExceptionHandler(LoginFailException.class)
    public ResponseEntity<ErrorResponse> handleLoginFailException(LoginFailException ex){
        log.error("handleLoginFailException",ex);
        final ErrorResponse response = new ErrorResponse(ex.getErrorCode());
        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
    }
}
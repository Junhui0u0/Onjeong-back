package com.example.onjeong.error;

import com.example.onjeong.user.exception.AccessTokenExpiredException;
import com.example.onjeong.user.exception.AccessTokenNotSameException;
import com.example.onjeong.user.exception.LoginFailException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


@RestController
public class ErrorController {

    @RequestMapping("/api/error/1")
    public void accessTokenExpiredError()  {
        throw new AccessTokenExpiredException("access token expired", ErrorCode.ACCESS_TOKEN_EXPIRED);
    }

    @RequestMapping("/api/error/2")
    public void accessTokenNotSameError()  {
        throw new AccessTokenNotSameException("access token not same", ErrorCode.ACCESS_TOKEN_NOT_SAME);
    }

    @RequestMapping("/api/error/3")
    public void LoginFailError(HttpServletRequest request)  {
        String message = (String) request.getAttribute("message");
        switch (message) {
            case "존재하지 않는 사용자입니다":
                throw new LoginFailException("login fail 1", ErrorCode.LOGIN_FAIL_1);
            case "아이디 또는 비밀번호가 틀립니다":
                throw new LoginFailException("login fail 2", ErrorCode.LOGIN_FAIL_2);
            case "잠긴 계정입니다":
                throw new LoginFailException("login fail 3", ErrorCode.LOGIN_FAIL_3);
            case "비활성화된 계정입니다":
                throw new LoginFailException("login fail 4", ErrorCode.LOGIN_FAIL_4);
            case "만료된 계정입니다":
                throw new LoginFailException("login fail 5", ErrorCode.LOGIN_FAIL_5);
            case "비밀번호가 만료되었습니다":
                throw new LoginFailException("login fail 6", ErrorCode.LOGIN_FAIL_6);
        }
    }
}
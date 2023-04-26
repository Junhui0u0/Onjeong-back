package com.example.onjeong.user.Auth;

import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class CustomLoginFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        String exceptionMsg = "";

        if (exception instanceof AuthenticationServiceException) {
            exceptionMsg="존재하지 않는 사용자입니다";

        } else if(exception instanceof BadCredentialsException) {
            exceptionMsg="아이디 또는 비밀번호가 틀립니다";

        } else if(exception instanceof LockedException) {
            exceptionMsg="잠긴 계정입니다";

        } else if(exception instanceof DisabledException) {
            exceptionMsg="비활성화된 계정입니다";

        } else if(exception instanceof AccountExpiredException) {
            exceptionMsg="만료된 계정입니다";
            
        } else if(exception instanceof CredentialsExpiredException) {
            exceptionMsg="비밀번호가 만료되었습니다";
        }

        request.setAttribute("message", exceptionMsg);
        request.getRequestDispatcher("/api/error/3").forward(request, response);
    }
}
package com.example.onjeong.user.Auth;

import com.example.onjeong.user.domain.MyUserDetails;
import com.example.onjeong.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Log4j2
@RequiredArgsConstructor
public class CustomLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(final HttpServletRequest request, final HttpServletResponse response,
                                                  final Authentication authentication) throws ServletException, IOException {

        final User user = ((MyUserDetails) authentication.getPrincipal()).getUser();
        request.getRequestDispatcher("/login/"+user.getUserId()).forward(request, response);

    }
}


package ru.sstu.library.config;

import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        if(e.getClass().equals(LockedException.class)){
            httpServletResponse.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
            httpServletResponse.setHeader("Location", "/?msg=denied");
            httpServletResponse.sendRedirect("/?msg=denied");
            return;
        }
        httpServletResponse.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
        httpServletResponse.setHeader("Location", "/?msg=failure");
        httpServletResponse.sendRedirect("/?msg=failure");
    }
}

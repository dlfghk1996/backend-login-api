package server.spring.auth.common.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import server.spring.auth.common.dto.ErrorResponse;
import server.spring.auth.common.dto.Response;
import server.spring.auth.common.enums.ResponseCode;

// 인증 예외처리
@Slf4j
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
        AuthenticationException authException) throws IOException {

        log.debug("** AuthenticationEntryPoint 인증 에러 **");
        log.error(authException.getMessage(), authException);

        ErrorResponse errorResponse = new ErrorResponse(ResponseCode.ERROR_AUTHENTICAION);
        response.setStatus(HttpStatus.OK.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new ObjectMapper().writeValueAsString(errorResponse));
    }
}

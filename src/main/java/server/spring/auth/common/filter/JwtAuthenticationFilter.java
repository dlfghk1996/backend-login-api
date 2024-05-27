package server.spring.auth.common.filter;

import static server.spring.auth.common.security.config.AuthProperties.AUTH_HEADER_NAME;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.*;
import server.spring.auth.common.enums.ResponseCode;
import server.spring.auth.common.exception.BizException;
import server.spring.auth.common.util.JwtTokenValidator;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenValidator jwtTokenValidator;


    @Bean
    public FilterRegistrationBean JwtRequestFilterRegistration(JwtAuthenticationFilter filter) {
        FilterRegistrationBean registration = new FilterRegistrationBean(filter);
        registration.setEnabled(false);

        return registration;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {


        try {
            if (!(checkUri(request.getRequestURI()))) {
                // 01. Header에서 Authorization를 꺼내온다.
                String authorization = request.getHeader(AUTH_HEADER_NAME);
                // header 가 null 경우 처리로직 추가 하기
                if(StringUtils.isBlank(authorization)){
                    throw new BizException(ResponseCode.ACCESS_TOKEN_NOT_FOUND);
                }
                if (!authorization.startsWith("Bearer")) {
                    throw new BizException(ResponseCode.INVALID_REQUEST);
                }

                // 02. Requestd Header에서 token 값 추출.
                String token = authorization.replace("Bearer", "").trim();
                if (jwtTokenValidator.validateToken(token)) {
                    // 03. 토큰이 유효하면 토큰으로부터 유저 정보를 받아옵니다.
                    Authentication authentication = jwtTokenValidator.getAuthentication(token);
                    // 04. 토큰의 유효성 검사가 완료되면 Spring이 관리하는 Security Context에 인증객체 설정
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }

        } catch (BizException e) {
            request.setAttribute("authFilterException", e.getResponseCode());
        }

        filterChain.doFilter(request, response);
    }

    // 필터 통과해야하는 url Checking
    public boolean checkUri(String requestUrl) {
        List<String> passUrls = new ArrayList<>();
        passUrls.add("/account");
        passUrls.add("/token");

        for (String passUrl : passUrls) {
            if (requestUrl.startsWith(passUrl) || requestUrl.matches("(.*)passUrl(.*)")) {
                return true;
            }
        }
        return false;
    }
}

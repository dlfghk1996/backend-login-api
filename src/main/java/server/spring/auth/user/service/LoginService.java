package server.spring.auth.user.service;


import static server.spring.auth.user.enums.MemberStatus.DELETED;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.spring.auth.common.enums.ResponseCode;
import server.spring.auth.common.exception.BizException;
import server.spring.auth.token.dto.TokenDTO;
import server.spring.auth.common.security.domain.PrincipalDetails;
import server.spring.auth.token.service.TokenService;
import server.spring.auth.user.domain.User;
import server.spring.auth.user.domain.dto.LoginDTO.*;

@Transactional
@RequiredArgsConstructor
@Service
public class LoginService {

    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;
    private final LoginHistoryService loginHistoryService;

    public TokenDTO signIn(LoginRequest request) {
        try {
            // 인증을 요청하는 사용자 값
            UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(request.getUserId(),
                    request.getPassword());

            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            // 로그인 성공한 시큐리티 인증 객체
            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            User user = principalDetails.getUser();

            // 탈퇴 회원 검증
            if (user.getStatus().equals(DELETED)) {
                throw new BizException(ResponseCode.MEMBER_STATUS_NOT_ACTIVE);
            }

            // 로그인 이력 저장
            loginHistoryService.add(user.getUserId(), true, null);

            return tokenService.createAccessToken(principalDetails.getRole(),
                principalDetails.getUser().getId());

        }catch (AuthenticationException e){
            // 로그인 이력 저장
            loginHistoryService.add(request.getUserId(), false, e.getMessage());

            throw new BizException(ResponseCode.MEMBER_INVALID_ACCOUNT);
        }
    }
}

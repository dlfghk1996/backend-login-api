package server.spring.auth.common.util;


import io.jsonwebtoken.*;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.service.spi.ServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import server.spring.auth.common.enums.ResponseCode;
import server.spring.auth.common.exception.BizException;
import server.spring.auth.common.security.domain.PrincipalDetails;
import server.spring.auth.token.dto.TokenDTO;
import server.spring.auth.token.enums.ClaimsKey;
import server.spring.auth.token.service.TokenService;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtTokenValidator {

    private final JwtTokenProvider jwtTokenProvider;

    private final TokenService tokenService;

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(jwtTokenProvider.getPublicKey())
                .build()
                .parseClaimsJws(token);

            return true;
        } catch (MalformedJwtException | UnsupportedJwtException e) {
            log.error("구조적인 문제 및 암호화되지 않은 JWT가 전달됬을 때 발생하는 오류", e.getLocalizedMessage());
            throw new BizException(ResponseCode.ACCESS_TOKEN_MISMATCH_PATTERN);
        } catch (ExpiredJwtException e) {
            log.error("만료된 JWT 토큰입니다.", e.getLocalizedMessage());
            throw new BizException(ResponseCode.TOKEN_EXPIRED);
        } catch (JwtException | IllegalArgumentException e) {
            log.error("잘못된 JWT 서명입니다.", e.getLocalizedMessage());
            throw new BizException(ResponseCode.ACCESS_TOKEN_INVALID_REQUEST);
        } catch (BizException e) {
            throw new BizException(ResponseCode.ACCESS_TOKEN_INVALID_REQUEST);
        } catch (Exception e) {
            throw new BizException(ResponseCode.ACCESS_TOKEN_INVALID_REQUEST);
        }
    }


    // JWT 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String reqToken) {
        log.info("****[토큰 인증 정보 검사] getAuthentication 호출 ******");

        Jws<Claims> jws = this.getJws(reqToken);
        Long userId = Long.valueOf(
            String.valueOf(jws.getBody().get(ClaimsKey.USERID_KEY.getName())));
        List<String> userRoleList = (List<String>) jws.getBody().get(ClaimsKey.ROLE_KEY.getName());

        TokenDTO tokenDTO = (TokenDTO) tokenService.getValue(userId);

        if(!tokenDTO.getAccessToken().equals(reqToken)){
            throw new BizException();
        }

        PrincipalDetails principalDetails = new PrincipalDetails();

        principalDetails.setUserWithId(userId);
        principalDetails.setRoleWithList(userRoleList);
        return new UsernamePasswordAuthenticationToken(principalDetails, null,
            principalDetails.getAuthorities());
    }

    // 토큰 복호화 : Claims 추출
    public Jws<Claims> getJws(String token) {
        try {
            // private key 로 public key 추출
            // jws 파싱
            Jws<Claims> jws =
                Jwts
                    .parserBuilder()
                    // jws 서명 증명을 위해 사용할 key
                    .setSigningKey(jwtTokenProvider.getPrivateKey())
                    .build()
                    .parseClaimsJws(token);
            return jws;
        } catch (Exception e) {
            throw new BizException(ResponseCode.ERROR);
        }
    }
}

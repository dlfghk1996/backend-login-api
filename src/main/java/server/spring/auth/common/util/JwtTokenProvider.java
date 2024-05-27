package server.spring.auth.common.util;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.security.Key;
import java.security.KeyPair;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.stereotype.Component;
import server.spring.auth.common.security.config.AuthProperties;
import server.spring.auth.token.enums.ClaimsKey;
import server.spring.auth.user.enums.RoleType;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    // access 토큰 생성
    public Map createAccessToken(List<RoleType> roleType, Long userId) {
        Map<String, Object> tokenMap = new HashMap<>(); // return map

        Date expirationTime =
                new Date(System.currentTimeMillis() + AuthProperties.ACCESS_TOKEN_EXPIRATION_TIME);

        String jws = this.generateJws(expirationTime,
                makeAccessTokenClaims(roleType, userId));

        tokenMap.put("accessTokenExpiredAt",
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(expirationTime));
        tokenMap.put("accessToken", jws);

        return tokenMap;
    }

    // refreah 토큰 생성
    public Map createRefreshToken(Long userId) {
        Map<String, Object> tokenMap = new HashMap<>(); // return map

        Date expirationTime =
                new Date(System.currentTimeMillis() + AuthProperties.REFRESH_TOKEN_EXPIRATION_TIME);

        // jws 생성
        String jws = this.generateJws(expirationTime, makeRefreshTokenClaims(userId));

        tokenMap.put("refreshTokenExpiredAt",
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(expirationTime));
        tokenMap.put("refreshToken", jws);

        return tokenMap;
    }

    // jws 생성
    private String generateJws(Date expirationTime, Map<String, Object> claims) {

        String jws =
                Jwts
                    .builder()
                    .setClaims(claims)
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(expirationTime) // 하루
                    .setId(String.valueOf(UUID.randomUUID()))
                    .signWith(this.getPrivateKey(), SignatureAlgorithm.RS256) // 해당 알고리즘 자동 설정 (S512와 차이)
                    .compact();
        return jws;
    }

    // access token claims 생성
    private Map<String, Object> makeAccessTokenClaims(List<RoleType> roleType, Long userId) {

        Map<String, Object> claims = new HashMap<>();
        claims.put(ClaimsKey.ROLE_KEY.getName(), roleType);
        claims.put(ClaimsKey.USERID_KEY.getName(), userId);
        return claims;
    }

    // access token claims 생성
    private Map<String, Object> makeRefreshTokenClaims(Long userId) {

        Map<String, Object> claims = new HashMap<>();
        claims.put(ClaimsKey.USERID_KEY.getName(), userId);
        return claims;
    }

    // keypair 방식의 private key 전달
    public Key getPrivateKey() {
        KeyStoreKeyFactory keyStoreKeyFactor =
                new KeyStoreKeyFactory(
                        new ClassPathResource(AuthProperties.KEY_PAIR_FILE_NAME),
                        AuthProperties.KEY_PAIR_SECRET.toCharArray());

        KeyPair keyPair =
                keyStoreKeyFactor.getKeyPair(
                        AuthProperties.KEY_PAIR_ALIAS, AuthProperties.KEY_PAIR_SECRET.toCharArray());

        return keyPair.getPrivate();
    }

    // keypair 방식의 public key 전달 (복호화시 사용)
    public Key getPublicKey() {
        KeyPair keyPair =
                new KeyStoreKeyFactory(
                        new ClassPathResource(AuthProperties.KEY_PAIR_FILE_NAME),
                        AuthProperties.KEY_PAIR_SECRET.toCharArray())
                        .getKeyPair(
                                AuthProperties.KEY_PAIR_ALIAS, AuthProperties.KEY_PAIR_SECRET.toCharArray());

        return keyPair.getPublic();
    }
}

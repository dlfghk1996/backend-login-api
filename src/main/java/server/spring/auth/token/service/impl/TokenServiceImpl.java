package server.spring.auth.token.service.impl;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import server.spring.auth.common.enums.ResponseCode;
import server.spring.auth.common.exception.BizException;
import server.spring.auth.common.util.JwtTokenProvider;
import server.spring.auth.token.dto.TokenDTO;
import server.spring.auth.token.service.TokenService;
import server.spring.auth.user.enums.RoleType;

@Slf4j
@RequiredArgsConstructor
@Service
public class TokenServiceImpl implements TokenService {
    private final JwtTokenProvider jwtTokenProvider;

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public TokenDTO createAccessToken(List<RoleType> roleType, Long userId) {
        Map<String, String> accessTokenMap = jwtTokenProvider.createAccessToken(roleType, userId);

        Map<String, String> refreshTokenMap = jwtTokenProvider.createRefreshToken(userId);

        accessTokenMap.putAll(refreshTokenMap);

        TokenDTO result = this.setterToken(accessTokenMap, new TokenDTO());

        // redis 저장
        save(userId, result);
        return result;
    }

    // Token Setter
    private TokenDTO setterToken(Map<String, String> map, TokenDTO token) {

        try {
            for (Map.Entry<String, String> entrySet : map.entrySet()) {
                Field[] fields = token.getClass().getDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true);  //private 변수를 public 변수로 변경

                    String fieldName = field.getName();

                    boolean isSameType = entrySet.getKey().equals(fieldName);
                    boolean isSameName = entrySet.getValue().getClass().equals(field.getType());

                    if (isSameName && isSameType) {
                        field.set(token, map.get(fieldName));
                    }
                }
            }

            return token;
        } catch (IllegalAccessException e) {
            log.info("Reflection 을 이용한 Token Setter 시도 중 에러", e.getLocalizedMessage());
            throw new BizException(ResponseCode.ERROR);
        }
    }

    @Override
    public void save(Long key, Object value) {
        redisTemplate.opsForValue().set(key.toString(), value);
    }

    @Override
    public TokenDTO getValue(Long key) {
        return (TokenDTO) redisTemplate.opsForValue().get(key.toString());
    }
}

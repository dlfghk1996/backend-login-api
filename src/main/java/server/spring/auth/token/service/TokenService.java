package server.spring.auth.token.service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import server.spring.auth.common.enums.ResponseCode;
import server.spring.auth.common.exception.BizException;
import server.spring.auth.token.dto.TokenDTO;
import server.spring.auth.common.util.JwtTokenProvider;
import server.spring.auth.user.enums.RoleType;



public interface TokenService {
    //Token 생성
    TokenDTO createAccessToken(List<RoleType> roleType, Long userId);

    //Token 저장
    void save(Long key, Object value);

    //Token 조회
    TokenDTO getValue(Long key);
}

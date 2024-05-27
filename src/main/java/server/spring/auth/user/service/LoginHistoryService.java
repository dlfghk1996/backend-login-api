package server.spring.auth.user.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.spring.auth.user.domain.LoginHistory;
import server.spring.auth.user.repository.LoginHistoryRepository;

@Transactional
@RequiredArgsConstructor
@Service
public class LoginHistoryService {

    @Autowired
    private HttpServletRequest request;

    private final LoginHistoryRepository repository;

    public void add(String userId, boolean loginResult, String failureReason) {

        // 로그인 이력 저장
        repository.save(LoginHistory.builder()
            .userId(userId)
            .loginResult(loginResult)
            .failureReason(failureReason)
            .ipAddress(request.getRemoteAddr())
            .userAgent(request.getHeader("User-Agent"))
            .loginTime(LocalDateTime.now())
            .build()
        );
    }
}

package server.spring.auth.common.security.service;


import jakarta.transaction.Transactional;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import server.spring.auth.common.enums.ResponseCode;
import server.spring.auth.common.exception.BizException;
import server.spring.auth.common.security.domain.PrincipalDetails;
import server.spring.auth.user.domain.User;
import server.spring.auth.user.enums.RoleType;
import server.spring.auth.user.repository.UserRepository;

@RequiredArgsConstructor
@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService {

     private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User user = userRepository.findByUserId(userId)
            .orElseThrow(() -> new BizException(ResponseCode.MEMBER_INVALID_ACCOUNT));

        return new PrincipalDetails(user, Arrays.asList(RoleType.USER));
    }
}

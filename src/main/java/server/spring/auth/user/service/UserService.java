package server.spring.auth.user.service;


import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.spring.auth.common.enums.ResponseCode;
import server.spring.auth.common.exception.BizException;
import server.spring.auth.user.domain.User;
import server.spring.auth.user.domain.dto.UserDTO;
import server.spring.auth.user.repository.UserRepository;

@Transactional
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository repository;

    private final ModelMapper mapper;

    public UserDTO get(Long id) {

        User user = repository.findById(id).orElseThrow(()->new BizException(ResponseCode.INVALID_REQUEST));
        return mapper.map(user, UserDTO.class);
    }
}

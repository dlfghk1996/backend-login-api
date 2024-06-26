package server.spring.auth.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.spring.auth.common.dto.Response;
import server.spring.auth.common.enums.ResponseCode;
import server.spring.auth.token.dto.TokenDTO;
import server.spring.auth.user.domain.dto.LoginDTO.*;
import server.spring.auth.user.service.LoginService;

@Slf4j
@RequestMapping("/account")
@RequiredArgsConstructor
@RestController
public class LoginController {
    private final LoginService service;

    // 로그인
    @PostMapping("/signin")
    public Response<TokenDTO> signIn(@Valid @RequestBody LoginRequest request) {
        return new Response<>(service.signIn(request), ResponseCode.OK);
    }
}
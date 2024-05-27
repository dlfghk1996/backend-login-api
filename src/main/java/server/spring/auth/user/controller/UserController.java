package server.spring.auth.user.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.spring.auth.common.dto.Response;
import server.spring.auth.common.enums.ResponseCode;
import server.spring.auth.common.security.domain.PrincipalDetails;
import server.spring.auth.user.domain.dto.UserDTO;
import server.spring.auth.user.service.UserService;



@RequiredArgsConstructor
@RequestMapping("/user")
@RestController
public class UserController {
    private final UserService service;

    @GetMapping
    public Response<UserDTO> get(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        return new Response<>(service.get(principalDetails.getUser().getId()), ResponseCode.OK);
    }
}

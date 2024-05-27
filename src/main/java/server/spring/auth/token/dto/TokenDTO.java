package server.spring.auth.token.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenDTO {

    private String accessToken;

    private String accessTokenExpiredAt;

    private String refreshToken;

    private String refreshTokenExpiredAt;
}

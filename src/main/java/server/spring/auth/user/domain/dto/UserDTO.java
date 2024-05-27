package server.spring.auth.user.domain.dto;


import java.time.LocalDateTime;
import lombok.Data;

@Data
public class UserDTO {
    private Long id;

    private String userId;

    private String name;

    private LocalDateTime createdAt;
}

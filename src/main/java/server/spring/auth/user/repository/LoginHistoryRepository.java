package server.spring.auth.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.spring.auth.user.domain.LoginHistory;

public interface LoginHistoryRepository extends JpaRepository<LoginHistory, Long> {

}

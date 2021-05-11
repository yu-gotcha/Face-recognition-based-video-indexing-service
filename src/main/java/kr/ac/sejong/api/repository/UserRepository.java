package kr.ac.sejong.api.repository;

import kr.ac.sejong.api.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByLoginId(String loginId);
    Boolean existsByLoginId(String loginId);
}

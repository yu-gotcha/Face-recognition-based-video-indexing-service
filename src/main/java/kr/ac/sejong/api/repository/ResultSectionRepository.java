package kr.ac.sejong.api.repository;

import kr.ac.sejong.api.domain.ResultSection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultSectionRepository extends JpaRepository<ResultSection, Long> {
}

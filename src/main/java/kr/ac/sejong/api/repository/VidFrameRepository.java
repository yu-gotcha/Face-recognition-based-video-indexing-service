package kr.ac.sejong.api.repository;

import kr.ac.sejong.api.domain.VidFrame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VidFrameRepository extends JpaRepository<VidFrame, Long> {
}

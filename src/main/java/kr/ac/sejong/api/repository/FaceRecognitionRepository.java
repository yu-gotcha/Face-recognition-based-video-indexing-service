package kr.ac.sejong.api.repository;

import kr.ac.sejong.api.domain.FaceRecognition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FaceRecognitionRepository extends JpaRepository<FaceRecognition, Long> {
}

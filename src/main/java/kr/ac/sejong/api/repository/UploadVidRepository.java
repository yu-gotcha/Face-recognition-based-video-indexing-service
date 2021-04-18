package kr.ac.sejong.api.repository;

import kr.ac.sejong.api.domain.UploadVid;
import kr.ac.sejong.api.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UploadVidRepository extends JpaRepository<UploadVid, Long> {
    List<UploadVid> findByVidUpUser(User user);
    UploadVid findTopByOrderByUpVidIdDesc();
}

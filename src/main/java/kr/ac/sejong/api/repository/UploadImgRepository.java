package kr.ac.sejong.api.repository;

import kr.ac.sejong.api.domain.UploadImg;
import kr.ac.sejong.api.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UploadImgRepository extends JpaRepository <UploadImg, Long> {
    List<UploadImg> findAll();
    List<UploadImg> findByImgUpUser(User user);
    UploadImg findTopByOrderByUpImgIdDesc();
    UploadImg findByUpImgId(long id);
}

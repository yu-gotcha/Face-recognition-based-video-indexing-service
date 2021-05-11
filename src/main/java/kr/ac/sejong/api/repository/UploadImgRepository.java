package kr.ac.sejong.api.repository;

import kr.ac.sejong.api.domain.UploadImg;
import kr.ac.sejong.api.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UploadImgRepository extends JpaRepository <UploadImg, Long> {
    List<UploadImg> findAll();
    List<UploadImg> findByImgUpUser(User user);
    UploadImg findTopByOrderByUpImgIdDesc();
    UploadImg findByUpImgId(long id);
}

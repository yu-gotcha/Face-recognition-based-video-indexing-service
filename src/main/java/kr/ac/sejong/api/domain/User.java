package kr.ac.sejong.api.domain;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "User")

@Getter
@Setter

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long userId;

    @Column(name = "user_name")
    @NotNull
    private String userName;

    @Column(name = "user_login_id")
    @NotNull
    private String loginId;

    @Column(name = "user_login_pw")
    @NotNull
    private String loginPw;

    /*
    @OneToMany(mappedBy = "imgUpUser")
    @Column( name = "up_img")
    private List<UploadImg> upImgList = new ArrayList<>();

    @OneToMany(mappedBy = "vidUpUser")
    @Column( name = "up_vid")
    private List<UploadVid> vidImgList = new ArrayList<>();
    */


    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @Column( name = "upload")
    private List<Upload> uploadList = new ArrayList<>();
}

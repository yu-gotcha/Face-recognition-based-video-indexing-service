package kr.ac.sejong.api.domain;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "UploadImg")

@Getter
@Setter


public class UploadImg {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "up_img_id")
    private long upImgId;

    @Column(name = "up_img_name")
    @NotNull
    private String upImgName;

    @Column(name = "up_img_saved_name")
    @NotNull
    private String upImgSavedName;

    @Column(name = "up_img_face_id")
    private long upImgFaceId;

    @Column(name = "up_img_path")
    @NotNull
    // 여기에 @NotNull 을 넣어야 할지 모르겠음. 업로드를 진행해봐야 알 듯
    private String upImgPath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "user_id"))
    private User imgUpUser;
    //User 테이블의 user_id를 외래키로 갖음

    @OneToOne
    @JoinColumn(name = "upload")
    private Upload uploadImg;
}
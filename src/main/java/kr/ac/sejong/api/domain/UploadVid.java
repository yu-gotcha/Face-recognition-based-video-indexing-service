package kr.ac.sejong.api.domain;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "UploadVid")

@Getter
@Setter

public class UploadVid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "up_vid_id")
    private long upVidId;

    @Column(name = "up_vid_name")
    @NotNull
    private String upVidName;

    @Column(name = "up_vid_saved_name")
    @NotNull
    private String upVidSavedName;

    @Column(name = "up_vid_path")
    // 여기에 @NotNull 을 넣어야 할지 모르겠음. 업로드를 진행해봐야 알 듯
    private String upVidPath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User vidUpUser;
    //User 테이블의 user_id를 외래키로 갖음

    @OneToOne
    @JoinColumn(name = "upload")
    private Upload uploadVid;

    @OneToMany(mappedBy = "upVid")
    @Column(name = "vid_frame")
    private List<VidFrame> vidFrameList = new ArrayList<>();
}

package kr.ac.sejong.api.domain;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "VidFrame")

@Getter
@Setter

public class VidFrame {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "frame_id")
    private long frameId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "up_vid_id"))
    private UploadVid upVid;
    //UploadVid 테이블의 up_vid_saved_name를 외래키로 갖음

    //face recognition 테이블에 가서 외래키 역할을 할 거임
    @Column(name = "frame_name")
    @NotNull
    private String frameName;

    //face recognition 테이블에 가서 외래키 역할을 할 거임
    @Column(name = "frame_no")
    @NotNull
    private long frameNo;

    @Column(name = "frame_path")
    @NotNull
    private long framePath;

    @OneToMany(mappedBy = "vidFrame")
    @Column(name = "face_recognition")
    private List<FaceRecognition> faceRecognitionList = new ArrayList<>();
}

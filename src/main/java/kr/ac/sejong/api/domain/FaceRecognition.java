package kr.ac.sejong.api.domain;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "FaceRecognition")

@Getter
@Setter


public class FaceRecognition {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "fr_img_id")
    private long frImgId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "frame_name"))
    private VidFrame vidFrame;
    //VidFrame 테이블의 frame_name를 외래키로 갖음

    @Column(name = "fr_img_name")
    @NotNull
    private String frImgName;
    //인식된 얼굴 크롭한 이미지

    @Column(name = "fr_img_path")
    @NotNull
    private String frImgPath;

    @Column(name = "cluster_id")
    private long clusterId;

    @Column(name = "flag")
    private int flag;

}

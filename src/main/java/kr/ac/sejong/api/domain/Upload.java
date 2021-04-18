package kr.ac.sejong.api.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "UploadManage")

@Getter
@Setter


public class Upload {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "up_id")
    private long upId;

    @OneToOne(mappedBy = "uploadImg")
    public UploadImg upImg;

    @OneToOne(mappedBy = "uploadVid")
    public UploadVid upVid;

    @Column( name = "result_cluster_id")
    private long resultClusterId;

    @OneToMany(mappedBy = "upload", fetch = FetchType.EAGER)
    @Column( name = "resule_section")
    private List<ResultSection> sectionList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "user"))
    private User user;

    @Column(name = "uploading")
    private int uploading;

    @Column(name = "processing")
    private int processing;

}

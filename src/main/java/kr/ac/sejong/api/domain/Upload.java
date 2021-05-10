package kr.ac.sejong.api.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Upload")

@Getter
@Setter


public class Upload {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "up_id")
    private long upId;

    @OneToOne
    public UploadImg uploadImg;

    @OneToOne
    public UploadVid uploadVid;

    @Column( name = "result_cluster_id")
    private long resultClusterId;

    @OneToMany(mappedBy = "upload", fetch = FetchType.EAGER)
    @Column( name = "resule_section")
    private List<ResultSection> sectionList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user")
    private User user;

    @Column(name = "uploading")
    private int uploading;

    @Column(name = "processing")
    private int processing;

}

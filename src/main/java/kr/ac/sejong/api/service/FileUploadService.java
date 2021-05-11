package kr.ac.sejong.api.service;

import kr.ac.sejong.api.domain.Upload;
import kr.ac.sejong.api.domain.UploadImg;
import kr.ac.sejong.api.domain.UploadVid;
import kr.ac.sejong.api.domain.User;
import kr.ac.sejong.api.repository.UploadImgRepository;
import kr.ac.sejong.api.repository.UploadRepository;
import kr.ac.sejong.api.repository.UploadVidRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileUploadService {
    private final UploadImgRepository UpImgRepository;
    private final UploadVidRepository UpVidRepository;
    private final UploadRepository uploadRepository;

    @Autowired
    public FileUploadService(UploadImgRepository UpImgRepository,UploadVidRepository UpVidRepository, UploadRepository uploadRepository) {
        this.UpImgRepository = UpImgRepository;
        this.UpVidRepository = UpVidRepository;
        this.uploadRepository = uploadRepository;
    }

    public Boolean saveImg(String fileName, String fileSavedName, String filePath, User user){
        UploadImg img = new UploadImg();

        img.setUpImgName(fileName);
        img.setUpImgSavedName(fileSavedName);
        img.setUpImgPath(filePath);
        img.setImgUpUser(user);

        try {
            UpImgRepository.save(img);
        }catch (Exception e){
            System.out.println(e);
            return false;
        }

        return true;
    }

    public Boolean saveVid(String fileName, String fileSavedName, String filePath, User user){
        UploadVid vid = new UploadVid();

        vid.setUpVidName(fileName);
        vid.setUpVidSavedName(fileSavedName);
        vid.setUpVidPath(filePath);
        vid.setVidUpUser(user);

        try {
            UpVidRepository.save(vid);
        }catch (Exception e){
            System.out.println(e);
            return false;
        }

        return true;
    }

    public Boolean saveUpload(String imgFileName, String imgSavedName, String imgPath, String vidFileName, String vidSavedName, String vidPath, UploadImg upImg, UploadVid upVid, User user){
        saveImg(imgFileName, imgSavedName, imgPath, user);
        saveVid(vidFileName, vidSavedName, vidPath, user);


        Upload upload = new Upload();

        upload.setUploadImg(upImg);
        upload.setUploadVid(upVid);
        upload.setUser(user);
        upload.setUploading(1);
        upload.setProcessing(0);

        try{
            uploadRepository.save(upload);
        }catch(Exception e){
            System.out.println(e);
            return false;
        }

        return true;
    }
}

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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.OutputStream;
import java.net.Socket;

@Service
public class FileUploadService {
    private final UploadImgRepository uploadImgRepository;
    private final UploadVidRepository uploadVidRepository;
    private final UploadRepository uploadRepository;
    public UploadImg uploadImg =new UploadImg();
    public UploadVid uploadVid = new UploadVid();

    @Autowired
    public FileUploadService(UploadImgRepository uploadImgRepository,UploadVidRepository uploadVidRepository, UploadRepository uploadRepository) {
        this.uploadImgRepository = uploadImgRepository;
        this.uploadVidRepository = uploadVidRepository;
        this.uploadRepository = uploadRepository;
    }

    public Boolean saveImg(String fileName, String fileSavedName, String filePath, User user){
        UploadImg img = new UploadImg();

        img.setUpImgName(fileName);
        img.setUpImgSavedName(fileSavedName);
        img.setUpImgPath(filePath);
        img.setImgUpUser(user);

        try {
            uploadImg = img;
            uploadImgRepository.save(img);
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
            uploadVid = vid;
            uploadVidRepository.save(vid);
        }catch (Exception e){
            System.out.println(e);
            return false;
        }

        return true;
    }

    public Boolean saveUpload(String imgFileName, String imgSavedName, String imgPath, String vidFileName, String vidSavedName, String vidPath, User user){
        saveImg(imgFileName, imgSavedName, imgPath, user);
        saveVid(vidFileName, vidSavedName, vidPath, user);
        System.out.println("Img:"+uploadImg);
        System.out.println("Vid:"+uploadVid);

        Upload upload = new Upload();

        upload.setUploadImg(uploadImg);
        upload.setUploadVid(uploadVid);
        upload.setUser(user);
        upload.setUploading(1);
        upload.setProcessing(0);
        System.out.println("upload:"+upload);

        try{
            uploadRepository.save(upload);
        }catch(Exception e){
            System.out.println(e);
            return false;
        }

        return true;
    }

    /*
    public String fileTransfer(@RequestPart MultipartFile imgFile, MultipartFile vidFile, HttpSession session) throws Exception{
        User user;
        byte[] vidBytes, imgBytes=imgFile.getBytes();

        user=(User)session.getAttribute("userdata");

        imgBytes=imgFile.getBytes();
        long imgLength = imgFile.getSize();
        long count=uploadImgRepository.findByImgUpUser(user).size();

        String originalFileName = imgFile.getOriginalFilename();
        String savedFileName=Long.toString(user.getUserId())+"_"+Long.toString(count)+"_"+originalFileName;

        Socket socket=null;
        OutputStream out;


        try{
            socket = new Socket("127.0.0.1", 9999);
            System.out.println("Server Start!");

            out=socket.getOutputStream();
            out.write(imgBytes);
            out.flush();

        }


        return "none";
    }
    */
}

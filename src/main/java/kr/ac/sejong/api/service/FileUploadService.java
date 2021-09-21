package kr.ac.sejong.api.service;

import kr.ac.sejong.api.domain.Upload;
import kr.ac.sejong.api.domain.UploadImg;
import kr.ac.sejong.api.domain.UploadVid;
import kr.ac.sejong.api.domain.User;
import kr.ac.sejong.api.repository.UploadImgRepository;
import kr.ac.sejong.api.repository.UploadRepository;
import kr.ac.sejong.api.repository.UploadVidRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.io.*;
import java.util.List;

@Service
public class FileUploadService {
    private final UploadImgRepository uploadImgRepository;
    private final UploadVidRepository uploadVidRepository;
    private final UploadRepository uploadRepository;
    public UploadImg uploadImg =new UploadImg();
    public UploadVid uploadVid = new UploadVid();

    private SocketService socketService;

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

    public long saveUpload(String imgFileName, String imgSavedName, String imgPath, String vidFileName, String vidSavedName, String vidPath, int faceCount, User user){
        saveImg(imgFileName, imgSavedName, imgPath, user);
        saveVid(vidFileName, vidSavedName, vidPath, user);
        System.out.println("Img:"+uploadImg);
        System.out.println("Vid:"+uploadVid);

        Upload upload = new Upload();

        upload.setUploadImg(uploadImg);
        upload.setUploadVid(uploadVid);
        upload.setFaceCount(faceCount);
        upload.setUser(user);
        upload.setUploading(1);
        upload.setProcessing(0);

        long upId;
        try{
            upId = uploadRepository.save(upload).getUpId();
        }catch(Exception e){
            System.out.println(e);
            return -1;
        }

        return upId;
    }

    @Async
    public void runCMD(){
        System.out.println("running....");
        Runtime runtime = Runtime.getRuntime();
        Process p;
        try{
            String cmd = "cmd /c cd C:/Users/MunsuYu/2021_Timeattack_Video-Processing && py main.py";
            System.out.println(cmd);
            p=runtime.exec(cmd);
            InputStream inputStream = p.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            System.out.println(inputStreamReader.getEncoding());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            while((line = bufferedReader.readLine())!= null){
                System.out.println(line);
            }
            inputStream.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }


    @Async
    public void sendUploadIdToRun(long uploadId) throws Exception {
        if(socketService.connect("127.0.0.1", 9999)) {
            if(socketService.sendMessage(String.valueOf(uploadId))) {
                long receiveId = Long.parseLong(socketService.receiveMessage());

                if(uploadId != receiveId) throw new Exception("Running id is not same. Please check it.");
            }

            else throw new Exception("Send Message Error");
        }

        else throw new Exception("Socket Connection Error");
    }
}

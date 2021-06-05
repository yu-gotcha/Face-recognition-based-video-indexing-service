package kr.ac.sejong.api.controller;


import kr.ac.sejong.api.domain.UploadImg;
import kr.ac.sejong.api.domain.UploadVid;
import kr.ac.sejong.api.domain.User;
import kr.ac.sejong.api.repository.UploadImgRepository;
import kr.ac.sejong.api.repository.UploadVidRepository;
import kr.ac.sejong.api.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;

@Controller
@RequestMapping(value = "")
public class FileUploadController {
    private final FileUploadService fileUploadService;
    private final UploadImgRepository uploadImgRepository;
    private final UploadVidRepository uploadVidRepository;

    @Autowired
    FileUploadController(FileUploadService fileUploadService, UploadImgRepository uploadImgRepository, UploadVidRepository uploadVidRepository) {
        this.fileUploadService = fileUploadService;
        this.uploadImgRepository = uploadImgRepository;
        this.uploadVidRepository = uploadVidRepository;
    }

    @GetMapping( value = "upload")
    public String upload() { return "upload"; }


    @PostMapping( value = "/upload" )
    public String upload(@RequestPart MultipartFile imgFile,MultipartFile vidFile, int faceCount, HttpSession session) throws Exception{
        String imgSavedFileName, vidSavedFileName, extension[];
        User user;
        long count;

        user=(User)session.getAttribute("userdata");

        //Image Upload
        count=uploadImgRepository.findByImgUpUser(user).size();

        String imgOriginalFileName = imgFile.getOriginalFilename();
        extension = imgOriginalFileName.split("\\.");
        imgSavedFileName=Long.toString(user.getUserId()) + "_" + Long.toString(count) + "." + extension[1];
        //File imgDest = new File("/usr/local/tomcat/file/image/"+imgSavedFileName);
        File imgDest = new File("C:/Users/MunsuYu/TimeAttack/TimeAttackFile/file/image/"+imgSavedFileName);
        imgFile.transferTo(imgDest);

        //Video Upload
        count=uploadVidRepository.findByVidUpUser(user).size();

        String vidOriginalFileName = vidFile.getOriginalFilename();
        extension = vidOriginalFileName.split("\\.");
        vidSavedFileName=Long.toString(user.getUserId())+"_"+Long.toString(count) + "." + extension[1];
        //File vidDest = new File("/usr/local/tomcat/file/video/"+vidSavedFileName);
        File vidDest = new File("C:/Users/MunsuYu/TimeAttack/TimeAttackFile/file/video/"+vidSavedFileName);
        vidFile.transferTo(vidDest);


        try{
            fileUploadService.saveUpload(imgOriginalFileName,imgSavedFileName, imgDest.toString(), vidOriginalFileName, vidSavedFileName, vidDest.toString(), faceCount, user);
            //fileUploadService.runCMD();
        }catch (Exception e){
            System.out.println(e);
            return "redirect:/upload";
        }

        return "redirect:/list";
    }




}

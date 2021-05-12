package kr.ac.sejong.api.controller;


import kr.ac.sejong.api.domain.UploadImg;
import kr.ac.sejong.api.domain.UploadVid;
import kr.ac.sejong.api.domain.User;
import kr.ac.sejong.api.repository.UploadImgRepository;
import kr.ac.sejong.api.repository.UploadVidRepository;
import kr.ac.sejong.api.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.OutputStream;
import java.net.Socket;

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
    /*
    public String upload(@RequestPart MultipartFile file, HttpSession session) throws Exception{
        String savedFileName;
        User user;
        long count;

        user=(User)session.getAttribute("userdata");
        count=uploadImgRepository.findByImgUpUser(user).size();

        String originalFileName = file.getOriginalFilename();
        savedFileName=Long.toString(user.getUserId())+"_"+Long.toString(count)+"_"+originalFileName;
        File dest = new File("C:/Image/"+savedFileName);
        file.transferTo(dest);


        fileUploadService.saveImg(file.getOriginalFilename(), savedFileName, dest.toString(), user);

        return "redirect:/upload";
    }
    */

    //transfer를 쓰지 않고 bytes로 소켓으로 던지는 것이 필요함함
   public String upload(@RequestPart MultipartFile imgFile,MultipartFile vidFile, HttpSession session) throws Exception{
        String imgSavedFileName, vidSavedFileName;
        User user;
        long count;
        byte vidBytes[], imgBytes[];

        user=(User)session.getAttribute("userdata");

        //Image Upload
        count=uploadImgRepository.findByImgUpUser(user).size();

        String imgOriginalFileName = imgFile.getOriginalFilename();
        imgSavedFileName=Long.toString(user.getUserId())+"_"+Long.toString(count)+"_"+imgOriginalFileName;
        File imgDest = new File("/usr/local/tomcat/file/image/"+imgSavedFileName);
        imgFile.transferTo(imgDest);

        //imgBytes=imgFile.getBytes();



        //Video Upload
        count=uploadVidRepository.findByVidUpUser(user).size();

        String vidOriginalFileName = vidFile.getOriginalFilename();
        vidSavedFileName=Long.toString(user.getUserId())+"_"+Long.toString(count)+"_"+vidOriginalFileName;
        File vidDest = new File("/usr/local/tomcat/file/video/"+vidSavedFileName);
        vidFile.transferTo(vidDest);

        //vidBytes=vidFile.getBytes();




        try{
            //fileUploadService.saveImg(imgFile.getOriginalFilename(), savedFileName, imgDest.toString(), user);
            //fileUploadService.saveVid(vidFile.getOriginalFilename(), savedFileName, vidDest.toString(), user);

            fileUploadService.saveUpload(imgOriginalFileName,imgSavedFileName, imgDest.toString(), vidOriginalFileName, vidSavedFileName, vidDest.toString(), user);
        }catch (Exception e){
            System.out.println(e);
            return "redirect:/upload";
        }

        return "redirect:/list";
    }




}

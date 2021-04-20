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
    @ResponseStatus(HttpStatus.CREATED)
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
    public String upload(@RequestPart MultipartFile imgFile, MultipartFile vidFile, HttpSession session) throws Exception{
        String savedFileName;
        User user;
        long count;

        user=(User)session.getAttribute("userdata");

        //Image Upload
        count=uploadImgRepository.findByImgUpUser(user).size();

        String originalFileName = imgFile.getOriginalFilename();
        savedFileName=Long.toString(user.getUserId())+"_"+Long.toString(count)+"_"+originalFileName;
        File dest = new File("C:/Image/"+savedFileName);
        imgFile.transferTo(dest);

        fileUploadService.saveImg(imgFile.getOriginalFilename(), savedFileName, dest.toString(), user);

        //Video Upload
        count=uploadVidRepository.findByVidUpUser(user).size();

        originalFileName = vidFile.getOriginalFilename();
        savedFileName=Long.toString(user.getUserId())+"_"+Long.toString(count)+"_"+originalFileName;
        dest = new File("C:/Image/"+savedFileName);
        vidFile.transferTo(dest);

        fileUploadService.saveVid(vidFile.getOriginalFilename(), savedFileName, dest.toString(), user);


        try{
            UploadImg upImg = uploadImgRepository.findTopByOrderByUpImgIdDesc();
            UploadVid upVid = uploadVidRepository.findTopByOrderByUpVidIdDesc();

            fileUploadService.saveUpload(upImg, upVid, user);
        }catch (Exception e){
            System.out.println(e);
            return "redirect:/";
        }

        return "redirect:/list";
    }


}

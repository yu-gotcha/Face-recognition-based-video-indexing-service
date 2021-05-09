package kr.ac.sejong.api.controller;


import kr.ac.sejong.api.domain.UploadImg;
import kr.ac.sejong.api.domain.UploadVid;
import kr.ac.sejong.api.domain.User;
import kr.ac.sejong.api.repository.UploadImgRepository;
import kr.ac.sejong.api.repository.UploadVidRepository;
import kr.ac.sejong.api.service.FileUploadService;
import kr.ac.sejong.api.service.SocketServiceImpl;
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
    private final SocketServiceImpl socketServiceImpl;

    @Autowired
    FileUploadController(FileUploadService fileUploadService, UploadImgRepository uploadImgRepository, UploadVidRepository uploadVidRepository, SocketServiceImpl socketServiceImpl) {
        this.fileUploadService = fileUploadService;
        this.uploadImgRepository = uploadImgRepository;
        this.uploadVidRepository = uploadVidRepository;
        this.socketServiceImpl = socketServiceImpl;
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

    //transfer를 쓰지 않고 bytes로 소켓으로 던지는 것이 필요함함
   public String upload(@RequestPart MultipartFile imgFile, MultipartFile vidFile, HttpSession session) throws Exception{
        String savedFileName;
        User user;
        long count;
        byte vidBytes[], imgBytes[];

        user=(User)session.getAttribute("userdata");

        //------------------------------이미지 업로드----------------------------------------------
        String savePath = System.getProperty("user.dir") + "\\files\\image";
        /* 파일이 저장되는 폴더가 없으면 폴더를 생성합니다. */
        if (!new File(savePath).exists()) {
            try{
                new File(savePath).mkdir();
            }
            catch(Exception e){
                e.getStackTrace();
            }
        }

        //Image Upload
        count=uploadImgRepository.findByImgUpUser(user).size();

        String originalFileName = imgFile.getOriginalFilename();
        savedFileName=Long.toString(user.getUserId())+"_"+Long.toString(count)+"_"+originalFileName;
        //File dest = new File("C:/Image/"+savedFileName);
        String filePath = savePath + "\\" + savedFileName;
        imgFile.transferTo(new File(filePath));

        //imgBytes=imgFile.getBytes();

        fileUploadService.saveImg(imgFile.getOriginalFilename(), savedFileName, filePath, user);


        //------------------------------비디오 업로드----------------------------------------------
        savePath = System.getProperty("user.dir") + "\\files\\video";
        /* 파일이 저장되는 폴더가 없으면 폴더를 생성합니다. */
        if (!new File(savePath).exists()) {
            try{
                new File(savePath).mkdir();
            }
            catch(Exception e){
                e.getStackTrace();
            }
        }
        //Video Upload
        count=uploadVidRepository.findByVidUpUser(user).size();

        originalFileName = vidFile.getOriginalFilename();
        savedFileName=Long.toString(user.getUserId())+"_"+Long.toString(count)+"_"+originalFileName;
        //dest = new File("C:/Image/"+savedFileName);
        filePath = savePath + "\\" + savedFileName;
        vidFile.transferTo(new File(filePath));

        //vidBytes=vidFile.getBytes();

        fileUploadService.saveVid(vidFile.getOriginalFilename(), savedFileName, filePath, user);


        try{
            UploadImg upImg = uploadImgRepository.findTopByOrderByUpImgIdDesc();
            UploadVid upVid = uploadVidRepository.findTopByOrderByUpVidIdDesc();

            fileUploadService.saveUpload(upImg, upVid, user);
        }catch (Exception e){
            System.out.println(e);
            return "redirect:/";
        }

        System.out.println(session.getServletContext().getRealPath("/"));
        System.out.println(savePath);
        System.out.println(filePath);

        return "redirect:/list";
    }


}

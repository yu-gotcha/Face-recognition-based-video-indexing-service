package kr.ac.sejong.api.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "")
public class FileUploadController {
    @GetMapping( value = "upload")
    public String upload() { return "upload"; }

    @PostMapping( value = "/upload" )
    public String uploadProcess(String user, String content){


    }
}

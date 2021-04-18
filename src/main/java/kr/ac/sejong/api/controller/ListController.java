package kr.ac.sejong.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "")
public class ListController {
    @GetMapping(value = "list")
    public String list() { return "list"; }
}

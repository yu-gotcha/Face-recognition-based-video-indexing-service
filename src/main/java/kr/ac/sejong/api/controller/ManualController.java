package kr.ac.sejong.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="")
public class ManualController {

    @GetMapping(value = "manual")
    public String manual () { return "manual"; }
}

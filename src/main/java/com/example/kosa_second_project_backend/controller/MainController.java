package com.example.kosa_second_project_backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class MainController {

    @GetMapping("/main")
    public String mainP() {
        System.out.println("main");
        return "Main Page";
    }
}

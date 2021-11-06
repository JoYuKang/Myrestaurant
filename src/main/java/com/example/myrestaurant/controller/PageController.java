package com.example.myrestaurant.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/pages")
public class PageController {

    @GetMapping(value = "/main")
    public ModelAndView main(){
                            // templates안에 폴더명/파일명으로 들어감
        return new ModelAndView("main");
    }
    @GetMapping(value = "/index")
    public String main1(){

        return "main";
    }

}

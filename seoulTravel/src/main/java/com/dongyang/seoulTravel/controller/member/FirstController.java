package com.example.firstproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FirstController {
    @GetMapping("/hi")
    public String niceToMeetYou(Model model){
        model.addAttribute("userName","김민석");
        return "greetings";
    }

    @GetMapping("/")
    public String home(){
        return "redirect:/hi";
    }

    @GetMapping("/bye")
    public String seeYouNext(Model model){
        model.addAttribute("nickName","김민석");
        return "goodbye";
    }

}

package com.trollmarket.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/landing-page")
public class LandingController {

    @GetMapping
    public String index(Model model){
        return "landing/landing-page";
    }
}

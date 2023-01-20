package com.trollmarket.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/error")
public class ErrorController {

    @GetMapping("/server")
    public String serverError(@RequestParam(required = false) String message,
                              Model model){
        model.addAttribute("errorMessage", message);
        return "error/error-server";
    }

    @GetMapping("/delete")
    public String failDelete(@RequestParam(required = false) String object,
                             @RequestParam(required = false) String dependencies,
                              Model model){
        model.addAttribute("object", object);
        model.addAttribute("dependencies", dependencies);
        return "error/error-delete";
    }

    @GetMapping("/edit")
    public String failEdit(@RequestParam(required = false) String object,
                             @RequestParam(required = false) String dependencies,
                             Model model){
        model.addAttribute("object", object);
        model.addAttribute("dependencies", dependencies);
        return "error/error-edit";
    }
}

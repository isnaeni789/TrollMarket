package com.trollmarket.controller;


import com.trollmarket.dto.account.RegisterAdminDTO;
import com.trollmarket.dto.account.RegisterDTO;
import com.trollmarket.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AccountService service;

    @GetMapping
    public String admin(Model model){
        RegisterAdminDTO dto = new RegisterAdminDTO();
        model.addAttribute("dto", dto);
        model.addAttribute("role", "Admin");
        return "account/register-admin";
    }

    @PostMapping("/save-account")
    public String register(@Valid @ModelAttribute("dto") RegisterAdminDTO dto,
                           BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()){
            model.addAttribute("role", "Admin");
            return "account/register-admin";
        }
        service.registerAdmin(dto);
        return "redirect:/";
    }
}

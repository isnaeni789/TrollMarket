package com.trollmarket.controller;

import com.trollmarket.dto.account.RegisterDTO;
import com.trollmarket.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService service;

    @GetMapping("/login-form")
    public String loginForm(Model model){
        return "account/login-form";
    }

    /*PostMapping untuk login, melalui spring security*/
    @GetMapping("/register-form")
    public String registerForm(@RequestParam("role") String role, Model model){
        RegisterDTO dto = new RegisterDTO();
        model.addAttribute("dto", dto);
        model.addAttribute("role", role);
        return "account/register-form";
    }

    @PostMapping("/save-account")
    public String register(@Valid @ModelAttribute("dto") RegisterDTO dto,
                           BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()){
            model.addAttribute("role", dto.getRole());
            return "account/register-form";
        }
        service.registerAccount(dto);
        return "redirect:/account/login-form";
    }

    @RequestMapping(value = "/access-denied", method = {RequestMethod.GET, RequestMethod.POST})
    public String accessDenied(Model model){
        return "account/access-denied";
    }
}

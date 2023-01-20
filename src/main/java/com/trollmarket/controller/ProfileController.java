package com.trollmarket.controller;

import com.trollmarket.dto.profile.ProfileTransactionHistory;
import com.trollmarket.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private ProfileService service;

    @GetMapping
    public String index(@RequestParam(defaultValue = "1")Integer page, Model model) {
        try {
            var transactionHistory = service.getTransactionHistory(page);
            var totalPages = transactionHistory.getTotalPages();
            model.addAttribute("profile", service.getProfileInfo());
            model.addAttribute("dataGrid", transactionHistory);
            model.addAttribute("totalPages", totalPages);
            model.addAttribute("currentPage", page);
            return "profile/profile-index";
        } catch (Exception exception){
            return "redirect:/error/server";
        }
    }
}

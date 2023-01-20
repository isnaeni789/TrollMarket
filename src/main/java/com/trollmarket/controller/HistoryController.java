package com.trollmarket.controller;

import com.trollmarket.dto.utility.DropdownDTO;
import com.trollmarket.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/history")
public class HistoryController {

    @Autowired
    private OrderService service;

    @GetMapping
    public String index(@RequestParam(defaultValue = "1")Integer page,
                        @RequestParam(defaultValue = "")String buyer,
                        @RequestParam(defaultValue = "")String seller,
                        Model model){
        try {
            var grid = service.getHistoryGrid(page, buyer, seller);
            var totalHalaman = grid.getTotalPages();
            model.addAttribute("dataGrid", grid);
            model.addAttribute("totalPages", totalHalaman);
            model.addAttribute("currentPage", page);
            model.addAttribute("buyer", buyer);
            model.addAttribute("seller", seller);
            List<DropdownDTO> buyerDropdown = service.getBuyerDropdown();
            model.addAttribute("buyerDropdown", buyerDropdown);
            List<DropdownDTO> sellerDropdown = service.getSellerDropdown();
            model.addAttribute("sellerDropdown", sellerDropdown);
            return "history/history-index";
        } catch (Exception exception){
            return "redirect:/error/server";
        }
    }
}

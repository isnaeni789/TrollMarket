package com.trollmarket.controller;

import com.trollmarket.dto.utility.DropdownDTO;
import com.trollmarket.service.ProductService;
import com.trollmarket.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/shop")
public class ShopController {

    @Autowired
    private ShopService service;

    @GetMapping
    public String index(@RequestParam(defaultValue = "1") Integer page,
                        @RequestParam(defaultValue = "") String name,
                        @RequestParam(defaultValue = "") String category,
                        @RequestParam(defaultValue = "") String description,
                        Model model){
        try {
            var grid = service.getProductShopGrid(page, name, category, description);
            var totalHalaman = grid.getTotalPages();
            model.addAttribute("dataGrid", grid);
            model.addAttribute("totalPages", totalHalaman);
            model.addAttribute("currentPage", page);
            model.addAttribute("name", name);
            model.addAttribute("category", category);
            model.addAttribute("description", description);
            List<DropdownDTO> shipperDropdown = service.getShipperDropdown();
            model.addAttribute("shipmentDropdown", shipperDropdown);
            Long buyerId = service.getBuyerId();
            model.addAttribute("buyerId", buyerId);
            return "shop/shop-index";
        } catch (Exception exception){
            return "redirect:/error/server";
        }
    }
}

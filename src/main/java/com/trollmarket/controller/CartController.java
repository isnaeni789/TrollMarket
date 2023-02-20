package com.trollmarket.controller;

import com.trollmarket.service.CartService;
import com.trollmarket.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService service;

    @GetMapping
    public String index(@RequestParam(defaultValue = "1")Integer page, Model model){
        try {
            var grid = service.getCartGrid();
            model.addAttribute("dataGrid", grid);
            model.addAttribute("totalPages", page);
            model.addAttribute("currentPage", page);
            return "cart/cart-index";
        } catch (Exception exception){
            return "redirect:/error/server";
        }
    }

    @GetMapping("/purchase-all")
    public String purchaseAll(Model model){
        try {
            service.purchaseAll();
            return "redirect:/cart";
        } catch (Exception exception){
            return "redirect:/error/server";
        }
    }

    @GetMapping("/delete")
    public String delete(@RequestParam(required = true) Long id, Model model){
        try {
            service.deleteCart(id);
            return "redirect:/cart";
        } catch (Exception exception){
            return "redirect:/error/server";
        }
    }

}

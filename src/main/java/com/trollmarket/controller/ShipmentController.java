package com.trollmarket.controller;

import com.trollmarket.service.ShipperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/shipment")
public class ShipmentController {

    @Autowired
    private ShipperService service;

    @GetMapping
    public String index(@RequestParam(defaultValue = "1")Integer page, Model model){
        try {
            var grid = service.getShipperGrid(page);
            var totalHalaman = grid.getTotalPages();
            model.addAttribute("dataGrid", grid);
            model.addAttribute("totalPages", totalHalaman);
            model.addAttribute("currentPage", page);
            return "shipment/shipment-index";
        } catch (Exception exception){
            return "redirect:/error/server";
        }
    }

    @GetMapping("/delete")
    public String delete(@RequestParam(required = true) Long id, Model model,
                         RedirectAttributes redirectAttributes){
        try {
            Long delete = service.delete(id);
            if (delete < 1){
                return "redirect:/shipment";
            }
            redirectAttributes.addAttribute("object", "shipper");
            redirectAttributes.addAttribute("dependencies", delete);
            return "redirect:/error/delete";
        } catch (Exception exception){
            var errorMessage = String.format("Jenis Exception: %s",
                    exception.getCause().getCause());
            redirectAttributes.addAttribute("message", errorMessage);
            return "redirect:/error/server";
        }
    }

    @GetMapping("/stop-service")
    public String stopService(@RequestParam(required = true) Long id, Model model,
                         RedirectAttributes redirectAttributes){
        try {
            service.stopService(id);
            return "redirect:/shipment";
        } catch (Exception exception){
            var errorMessage = String.format("Jenis Exception: %s",
                    exception.getCause().getCause());
            redirectAttributes.addAttribute("message", errorMessage);
            return "redirect:/error/server";
        }
    }
}

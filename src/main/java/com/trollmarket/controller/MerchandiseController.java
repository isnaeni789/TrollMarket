package com.trollmarket.controller;

import com.trollmarket.dto.product.UpsertProductDTO;
import com.trollmarket.dto.utility.DropdownDTO;
import com.trollmarket.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/merchandise")
public class MerchandiseController {

    @Autowired
    private ProductService service;

    @GetMapping
    public String index(@RequestParam(defaultValue = "1")Integer page, Model model){
        try {
            var grid = service.getProductMerchandiseGrid(page);
            var totalHalaman = grid.getTotalPages();
            model.addAttribute("dataGrid", grid);
            model.addAttribute("totalPages", totalHalaman);
            model.addAttribute("currentPage", page);
            return "merchandise/merchandise-index";
        } catch (Exception exception){
            return "redirect:/error/server";
        }
    }

    @GetMapping("/upsert-product")
    public String upsert(@RequestParam(required = false) Long id, Model model,
                         RedirectAttributes redirectAttributes){
        try {
            Long dependencies = service.getDependencies(id);
            if (dependencies < 1) {
                UpsertProductDTO dto = new UpsertProductDTO();
                model.addAttribute("actionType", "Insert New");
                if (id != null) {
                    dto = service.getUpdate(id);
                    model.addAttribute("actionType", "Update");
                }
                model.addAttribute("dto", dto);
                List<DropdownDTO> categoryDropdown = service.getCategoryDropdown();
                model.addAttribute("categoryDropdown", categoryDropdown);
                Long sellerId = service.getSellerId();
                model.addAttribute("sellerId", sellerId);
                return "merchandise/upsert-product";
            }
            redirectAttributes.addAttribute("object", "product");
            redirectAttributes.addAttribute("dependencies", dependencies);
            return "redirect:/error/edit";
        } catch (Exception exception){
            return "redirect:/error/server";
        }
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("dto") UpsertProductDTO dto,
                       BindingResult bindingResult, Model model){
        try {
            if (bindingResult.hasErrors()){
                var actionType = dto.getId() != null ? "Update" : "Insert New";
                model.addAttribute("actionType", actionType);
                List<DropdownDTO> categoryDropdown = service.getCategoryDropdown();
                model.addAttribute("categoryDropdown", categoryDropdown);
                Long sellerId = service.getSellerId();
                model.addAttribute("sellerId", sellerId);
                return "merchandise/upsert-product";
            }
            service.saveProduct(dto);
            return "redirect:/merchandise";
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
                return "redirect:/merchandise";
            }
            redirectAttributes.addAttribute("object", "product");
            redirectAttributes.addAttribute("dependencies", delete);
            return "redirect:/error/delete";
        } catch (Exception exception){
            var errorMessage = String.format("Jenis Exception: %s",
                    exception.getCause().getCause());
            redirectAttributes.addAttribute("message", errorMessage);
            return "redirect:/error/server";
        }
    }

    @GetMapping("/discontinue")
    public String discontinue(@RequestParam Long id, Model model){
        try {
            service.discontinue(id);
            return "redirect:/merchandise";
        } catch (Exception exception){
            return "redirect:/error/server";
        }
    }
}

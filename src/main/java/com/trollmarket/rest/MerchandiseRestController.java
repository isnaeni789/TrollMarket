package com.trollmarket.rest;

import com.trollmarket.dto.merchandise.ProductMerchandiseInfoDTO;
import com.trollmarket.dto.utility.ErrorDTO;
import com.trollmarket.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@CrossOrigin
@RestController
@RequestMapping("/api/merchandise")
public class MerchandiseRestController {
    @Autowired
    private ProductService service;

    @GetMapping("/product/{id}")
    public ResponseEntity<Object> get(@PathVariable Long id){
        try {
            var productInfo = service.getProductInfo(id);
            return ResponseEntity.status(200).body(productInfo);
        }catch (Exception exception){
            ErrorDTO errorDTO = new ErrorDTO(
                    exception.getMessage(),
                    exception.getMessage(),
                    LocalDateTime.now());
            return ResponseEntity.status(500).body(errorDTO);
        }
    }
}

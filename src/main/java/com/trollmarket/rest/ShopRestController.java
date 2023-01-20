package com.trollmarket.rest;

import com.trollmarket.dto.merchandise.ProductMerchandiseInfoDTO;
import com.trollmarket.dto.shipper.SingleShipperDTO;
import com.trollmarket.dto.shop.ProductShopInfoDTO;
import com.trollmarket.dto.utility.ErrorDTO;
import com.trollmarket.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@CrossOrigin
@RestController
@RequestMapping("/api/shop")
public class ShopRestController {

    @Autowired
    private ProductService service;

    @GetMapping("/product/{id}")
    public ResponseEntity<Object> get(@PathVariable Long id){
        try {
            var productShopInfo = service.getProductShopInfo(id);
            return ResponseEntity.status(200).body(productShopInfo);
        }catch (Exception exception){
            ErrorDTO errorDTO = new ErrorDTO(
                    exception.getMessage(),
                    exception.getMessage(),
                    LocalDateTime.now());
            return ResponseEntity.status(500).body(errorDTO);
        }
    }
}

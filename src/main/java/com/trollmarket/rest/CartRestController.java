package com.trollmarket.rest;

import com.trollmarket.dto.cart.UpsertCartDTO;
import com.trollmarket.dto.shipper.SingleShipperDTO;
import com.trollmarket.dto.shipper.UpsertShipperDTO;
import com.trollmarket.dto.utility.ErrorDTO;
import com.trollmarket.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/cart")
public class CartRestController {

    @Autowired
    private OrderService service;

    @PostMapping
    public ResponseEntity<Object> post(@Valid @RequestBody UpsertCartDTO dto,
                                       BindingResult bindingResult){
        try {
            if (!bindingResult.hasErrors()){
                service.addCart(dto);
                return ResponseEntity.status(201).body("Berhasil menambahkan cart");
            }
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            return ResponseEntity.status(422).body(allErrors);
        } catch (Exception exception){
            ErrorDTO errorDTO = new ErrorDTO(
                    exception.getCause().getCause().toString(),
                    exception.getMessage(),
                    LocalDateTime.now());
            return ResponseEntity.status(500).body(errorDTO);
        }
    }
}

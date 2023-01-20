package com.trollmarket.rest;

import com.trollmarket.dto.profile.TopupBuyerBalanceDTO;
import com.trollmarket.dto.shipper.SingleShipperDTO;
import com.trollmarket.dto.utility.ErrorDTO;
import com.trollmarket.service.ProfileService;
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
@RequestMapping("/api/profile")
public class ProfileRestController {

    @Autowired
    private ProfileService service;

    @PatchMapping("/topup-buyer")
    public ResponseEntity<Object> get(@Valid @RequestBody TopupBuyerBalanceDTO dto,
                                      BindingResult bindingResult){
        try {
            if (!bindingResult.hasErrors()){
                Double saldo = service.topupBuyer(dto);
                return ResponseEntity.status(200).body(saldo);
            }
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            return ResponseEntity.status(422).body(allErrors);
        }catch (Exception exception){
            ErrorDTO errorDTO = new ErrorDTO(
                    exception.getMessage(),
                    exception.getMessage(),
                    LocalDateTime.now());
            return ResponseEntity.status(500).body(errorDTO);
        }
    }
}

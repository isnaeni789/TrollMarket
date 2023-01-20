package com.trollmarket.rest;

import com.trollmarket.dto.shipper.SingleShipperDTO;
import com.trollmarket.dto.shipper.UpsertShipperDTO;
import com.trollmarket.dto.utility.ErrorDTO;
import com.trollmarket.service.ShipperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/shipment")
public class ShipperRestController {

    @Autowired
    private ShipperService service;

    @GetMapping
    public ResponseEntity<Object> get(@RequestParam(defaultValue = "1") Integer page){
        try {
            var grid = service.getShipperGrid(page);
            return ResponseEntity.status(HttpStatus.OK).body(grid);
        }catch (Exception exception){
            ErrorDTO errorDTO = new ErrorDTO(
                    exception.getCause().getCause().toString(),
                    exception.getMessage(),
                    LocalDateTime.now());
            return ResponseEntity.status(500).body(errorDTO);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> get(@PathVariable Long id){
        try {
            SingleShipperDTO oneShipper = service.getOneShipper(id);
            return ResponseEntity.status(200).body(oneShipper);
        }catch (Exception exception){
            ErrorDTO errorDTO = new ErrorDTO(
                    exception.getMessage(),
                    exception.getMessage(),
                    LocalDateTime.now());
            return ResponseEntity.status(500).body(errorDTO);
        }
    }

    @PostMapping
    public ResponseEntity<Object> post(@Valid @RequestBody UpsertShipperDTO dto,
                                       BindingResult bindingResult){
        try {
            if (!bindingResult.hasErrors()){
                SingleShipperDTO shipper = service.saveShipper(dto);
                return ResponseEntity.status(201).body(shipper);
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

    @PutMapping
    public ResponseEntity<Object> put(@Valid @RequestBody UpsertShipperDTO dto,
                                       BindingResult bindingResult){
        try {
            if (!bindingResult.hasErrors()){
                Long dependencies = service.getDependencies(dto.getId());
                if (dependencies < 1){
                    SingleShipperDTO shipper = service.saveShipper(dto);
                    return ResponseEntity.status(201).body(shipper);
                }
                return ResponseEntity.status(422).body("Tidak bisa edit");
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

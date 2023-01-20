package com.trollmarket.service;

import com.trollmarket.dao.ShipperRepository;
import com.trollmarket.dto.merchandise.ProductMerchandiseGridDTO;
import com.trollmarket.dto.product.SingleProductDTO;
import com.trollmarket.dto.product.UpsertProductDTO;
import com.trollmarket.dto.shipper.ShipperGridDTO;
import com.trollmarket.dto.shipper.SingleShipperDTO;
import com.trollmarket.dto.shipper.UpsertShipperDTO;
import com.trollmarket.entity.Product;
import com.trollmarket.entity.Shipper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ShipperService {

    @Autowired
    private ShipperRepository shipperRepository;

    public Page<ShipperGridDTO> getShipperGrid(Integer pageNumber){
        var pageable = PageRequest.of(pageNumber - 1, 10, Sort.by("id"));
        return shipperRepository.findAllShipper(pageable);
    }

    public SingleShipperDTO saveShipper(UpsertShipperDTO dto){
        Shipper entity = new Shipper(dto.getId(), dto.getName(), dto.getPrice(),
                dto.getIsActive());
        shipperRepository.save(entity);
        return SingleShipperDTO.builder().id(entity.getId())
                .name(entity.getName()).price(entity.getPrice())
                .isActive(entity.getIsActive()).build();
    }

    public SingleShipperDTO getOneShipper(Long id){
        Shipper entity = shipperRepository.findById(id).get();
        return SingleShipperDTO.builder().id(entity.getId())
                .name(entity.getName()).price(entity.getPrice())
                .isActive(entity.getIsActive()).build();
    }

    public UpsertShipperDTO getUpdate(Long id){
        var entity = shipperRepository.findById(id).get();
        return new UpsertShipperDTO(entity.getId(), entity.getName(), entity.getPrice(),
                entity.getIsActive());
    }

    public Long delete(Long id){
        Long dependencies = getDependencies(id);
        if (dependencies < 1){
            shipperRepository.deleteById(id);
        }
        return dependencies;
    }

    public Long getDependencies(Long id){
        return shipperRepository.countDependencies(id);
    }

    public void stopService(Long id){
        Shipper shipper = shipperRepository.findById(id).get();
        shipper.setIsActive(false);
        shipperRepository.save(shipper);
    }
}

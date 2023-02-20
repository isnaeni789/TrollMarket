package com.trollmarket.service;

import com.trollmarket.dao.*;
import com.trollmarket.dto.shop.ProductShopGridDTO;
import com.trollmarket.dto.shop.ProductShopInfoDTO;
import com.trollmarket.dto.utility.DropdownDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private SellerRepository sellerRepository;
    @Autowired
    private ShipperRepository shipperRepository;
    @Autowired
    private BuyerRepository buyerRepository;
    
    public Page<ProductShopGridDTO> getProductShopGrid(Integer pageNumber, String name, String category, String description){
        var pageable = PageRequest.of(pageNumber - 1, 10, Sort.by("id"));
        return productRepository.findAllShopProduct(name, category, description, pageable);
    }
    public ProductShopInfoDTO getProductShopInfo(Long id){
        return productRepository.findShopInfoProduct(id);
    }
    public List<DropdownDTO> getShipperDropdown(){ return shipperRepository.getShipmentDropdown();}
    
    /*----GET User Authentication-------*/
    public Long getSellerId() {
        String username = getUserLogin();
        return sellerRepository.getSellerId(username);
    }
    public Long getBuyerId() {
        String username = getUserLogin();
        return buyerRepository.getBuyerId(username);
    }
    private String getUserLogin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}

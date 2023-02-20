package com.trollmarket.service;

import com.trollmarket.dao.BuyerRepository;
import com.trollmarket.dao.OrderRepository;
import com.trollmarket.dao.ProductRepository;
import com.trollmarket.dao.SellerRepository;
import com.trollmarket.dto.history.HistoryGridDTO;
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
public class HistoryService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private BuyerRepository buyerRepository;
    @Autowired
    private SellerRepository sellerRepository;

    
    public Page<HistoryGridDTO> getHistoryGrid(Integer pageNumber, String buyer, String seller){
        var pageable = PageRequest.of(pageNumber - 1, 10, Sort.by("purchaseDate"));
        return orderRepository.findHistory(buyer, seller, pageable);
    }
    
    public List<DropdownDTO> getBuyerDropdown(){return buyerRepository.getBuyerDropdown();}
    public List<DropdownDTO> getSellerDropdown(){ return sellerRepository.getSellerDropdown();}
}

package com.trollmarket.service;

import com.trollmarket.dao.*;
import com.trollmarket.dto.cart.CartGridDTO;
import com.trollmarket.dto.cart.UpsertCartDTO;
import com.trollmarket.dto.history.HistoryGridDTO;
import com.trollmarket.dto.utility.DropdownDTO;
import com.trollmarket.entity.Buyer;
import com.trollmarket.entity.Order;
import com.trollmarket.entity.Product;
import com.trollmarket.entity.Seller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private BuyerRepository buyerRepository;
    @Autowired
    private SellerRepository sellerRepository;
    @Autowired
    private ProductRepository productRepository;
    

    /*Rest Controller Add Cart in menu Shop Buyer*/
    public void addCart(UpsertCartDTO dto){
        /*Get the entity product from click add cart*/
        Product product = productRepository.findById(dto.getProductId()).get();
        /*Get the product price*/
        Double price = product.getPrice();
        /*Check the cart from buyer is this already*/
        Long countCart = orderRepository.countCart(dto.getBuyerId(), dto.getProductId());
        /*If product id not yet in cart buyer create new order id*/
        if (countCart < 1){
            Order order = new Order(dto.getId(), dto.getBuyerId(), dto.getShipperId(),
                    dto.getProductId(), dto.getQuantity(), price);
            orderRepository.save(order);
        }
        /*If product id already in cart buyer and add the quantity*/
        else {
            /*Get the order id in cart not have purchase date*/
            Long orderId = orderRepository.orderId(dto.getBuyerId(), dto.getProductId());
            /*Get the entity order*/
            Order order = orderRepository.findById(orderId).get();
            /*Add the quantity*/
            Integer quantity = order.getQuantity();
            order.setQuantity(quantity + dto.getQuantity());
            /*Set new shipper from last input*/
            order.setShipperId(dto.getShipperId());
            /*Save the order*/
            orderRepository.save(order);
        }
    }
    

    /*------------------------GET User Authentication--------------------------------------------*/
    public Long getBuyerId() {
        String username = getUserLogin();
        return buyerRepository.getBuyerId(username);
    }
    private String getUserLogin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
    /*-------------------------------------------------------------------------------------------*/
}

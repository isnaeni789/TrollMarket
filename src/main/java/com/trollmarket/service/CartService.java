package com.trollmarket.service;

import com.trollmarket.dao.BuyerRepository;
import com.trollmarket.dao.OrderRepository;
import com.trollmarket.dao.ProductRepository;
import com.trollmarket.dao.SellerRepository;
import com.trollmarket.dto.cart.CartGridDTO;
import com.trollmarket.entity.Buyer;
import com.trollmarket.entity.Order;
import com.trollmarket.entity.Seller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class CartService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private BuyerRepository buyerRepository;
    @Autowired
    private SellerRepository sellerRepository;
    
    
    public List<CartGridDTO> getCartGrid(){
        String username = getUserLogin();
        return orderRepository.findByBuyer(username);
    }
    
    @Transactional
    public void purchaseAll(){
        /*Find Buyer who login*/
        Buyer buyer = buyerRepository.findById(getBuyerId()).get(); //buyer id match from uname
        Double buyerBalance = buyer.getBalance();
        /*Find Cart Buyer*/
        List<CartGridDTO> cart = orderRepository.findByBuyer(getUserLogin());
        /*SUM Total Price Cart*/
        Double totalPrice = 0.0;
        for (CartGridDTO dto : cart) {
            totalPrice += dto.getTotalPrice();
        }
        /*Check the buyer balance*/
        if (buyerBalance >= totalPrice){
            for (CartGridDTO dto : cart) {
                Order order = orderRepository.findById(dto.getId()).get();
                order.setPurchaseDate(LocalDate.now());
                order.setUnitPrice(dto.getUnitPrice()); // Set unit price from price product up to date
                Seller seller = sellerRepository.findById(dto.getSellerId()).get();
                var balance = dto.getUnitPrice() * dto.getQuantity();
                Double sellerBalance = seller.getBalance();
                seller.setBalance(sellerBalance + balance);
                orderRepository.save(order);
                sellerRepository.save(seller);
            }
            buyer.setBalance(buyerBalance - totalPrice);
            buyerRepository.save(buyer);
        }
    }
    public void deleteCart(Long id){
        orderRepository.deleteById(id);
    }
    
    private Long getBuyerId() {
        String username = getUserLogin();
        return buyerRepository.getBuyerId(username);
    }
    private String getUserLogin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}

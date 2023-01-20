package com.trollmarket.service;

import com.trollmarket.dao.AccountRepository;
import com.trollmarket.dao.BuyerRepository;
import com.trollmarket.dao.OrderRepository;
import com.trollmarket.dao.SellerRepository;
import com.trollmarket.dto.profile.ProfileInfoDTO;
import com.trollmarket.dto.profile.ProfileTransactionHistory;
import com.trollmarket.dto.profile.TopupBuyerBalanceDTO;
import com.trollmarket.entity.Account;
import com.trollmarket.entity.Buyer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

    @Autowired
    private BuyerRepository buyerRepository;
    @Autowired
    private SellerRepository sellerRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private OrderRepository orderRepository;

    public ProfileInfoDTO getProfileInfo(){
        Account account = accountRepository.findById(getUserLogin()).get();
        ProfileInfoDTO profil = new ProfileInfoDTO();
        if (account.getRole().equals("Buyer")) {
            profil = buyerRepository.getProfile(getUserLogin());
        } else if (account.getRole().equals("Seller")) {
            profil = sellerRepository.getProfile(getUserLogin());
        }
        profil.setRole(account.getRole());
        return profil;
    }

    public Page<ProfileTransactionHistory> getTransactionHistory(Integer page){
        var pageable = PageRequest.of(page - 1, 10, Sort.by("purchaseDate"));
        return orderRepository.findProfileTransaction(getUserLogin(), pageable);
    }

    public Double topupBuyer(TopupBuyerBalanceDTO dto){
        Buyer buyer = buyerRepository.findById(dto.getId()).get();
        Double balance = buyer.getBalance();
        var saldo = balance + dto.getBalance();
        buyer.setBalance(saldo);
        buyerRepository.save(buyer);
        return saldo;
    }

    private String getUserLogin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

}

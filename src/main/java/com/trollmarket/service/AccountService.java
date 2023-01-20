package com.trollmarket.service;

import com.trollmarket.ApplicationUserDetails;
import com.trollmarket.dao.AccountRepository;
import com.trollmarket.dao.BuyerRepository;
import com.trollmarket.dao.SellerRepository;
import com.trollmarket.dto.account.RegisterAdminDTO;
import com.trollmarket.dto.account.RegisterDTO;
import com.trollmarket.entity.Account;
import com.trollmarket.entity.Buyer;
import com.trollmarket.entity.Seller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private BuyerRepository buyerRepository;
    @Autowired
    private SellerRepository sellerRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public void registerAccount(RegisterDTO dto){
        String encode = passwordEncoder.encode(dto.getPassword());
        Account account = new Account(dto.getUsername(), encode, dto.getRole());
        accountRepository.save(account);
        if (dto.getRole().equals("Buyer")){
            Buyer buyer = new Buyer(dto.getName(), dto.getAddress(), 0.0, dto.getUsername());
            buyerRepository.save(buyer);
        } else if (dto.getRole().equals("Seller")) {
            Seller seller = new Seller(dto.getName(), dto.getAddress(), 0.0, dto.getUsername());
            sellerRepository.save(seller);
        }
    }

    public void registerAdmin(RegisterAdminDTO dto){
        String encode = passwordEncoder.encode(dto.getPassword());
        Account account = new Account(dto.getUsername(), encode, dto.getRole());
        accountRepository.save(account);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findById(username).get();
        ApplicationUserDetails userDetails = new ApplicationUserDetails(account);
        return userDetails;
    }

    public Boolean checkExistingUsername(String username){
        Long countUsername = accountRepository.countUsername(username);
        return countUsername < 1;
    }

}

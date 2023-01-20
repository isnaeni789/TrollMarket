package com.trollmarket;

import com.trollmarket.entity.Account;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/*Ini adalah semacam DTO yang digunakan untuk Login*/
public class ApplicationUserDetails implements UserDetails {

    private String username;
    private String password;
    private List<GrantedAuthority> authorities = new ArrayList<>();
    /*Roles*/

    public ApplicationUserDetails(Account account){
        this.username = account.getUsername();
        this.password = account.getPassword();
        authorities.add(new SimpleGrantedAuthority(account.getRole()));
        /*Convert String jadi SimpleGrantedAuthority*/
        /*GrantedAuthority adalah tipe data yang spring security gunakan untuk membaca role*/
        /*Notes: kenapa authorities dibuat collection, karena sebagian app ada yang user memiliki multiple roles*/
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

package com.trollmarket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class MvcSecurityConfiguration {

    @Bean
    @Order(2)
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
        .authorizeRequests()
                /*yang diperbolehkan tanpa login*/
                .antMatchers("/resources/**", "/account/**").permitAll()
                /*Request yang harus login*/
                .antMatchers("/").hasAnyAuthority("Admin", "Buyer", "Seller")
                .antMatchers("/profile/**").hasAnyAuthority("Buyer", "Seller")
                .antMatchers("/shipment/**", "/admin/**", "/history/**").hasAuthority("Admin")
                .antMatchers("/api/shipment").hasAuthority("Admin")
                .antMatchers("/merchandise/**").hasAuthority("Seller")
                .antMatchers("/shop/**", "/cart/**").hasAuthority("Buyer")
                .anyRequest().authenticated()
                /*Kalau belum authenticated, di redirect ke halaman login (controller)*/
                .and().formLogin().loginPage("/account/login-form")
                /*Memberikan routing untuk proses post login*/
                .loginProcessingUrl("/authenticating")
                /*Mengaktifkan request untuk logout*/
                .and().logout()
                /*Access denied untuk non-authorized*/
                .and().exceptionHandling().accessDeniedPage("/account/access-denied");
        return http.build();
    }
}

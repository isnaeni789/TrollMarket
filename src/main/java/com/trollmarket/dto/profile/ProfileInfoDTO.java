package com.trollmarket.dto.profile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileInfoDTO {
    private Long id;
    private String username;
    private String name;
    private String role;
    private String address;
    private Double balance;

    public ProfileInfoDTO(Long id, String username, String name,
                          String address, Double balance) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.address = address;
        this.balance = balance;
    }
}

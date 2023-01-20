package com.trollmarket.dto.account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestTokenDTO {
    private String username;
    private String password;
    private String subject;
    private String audience;
    private String secretKey;
}

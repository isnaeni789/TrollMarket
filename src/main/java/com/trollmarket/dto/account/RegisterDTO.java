package com.trollmarket.dto.account;

import com.trollmarket.validator.PasswordComparer;
import com.trollmarket.validator.UniqueUsername;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@PasswordComparer(password = "password", confirmPassword = "confirmPassword",
        message = "Confirm Password harus match")
public class RegisterDTO {

    @UniqueUsername(message = "Username sudah ada di database")
    @NotBlank(message = "Username tidak boleh kosong")
    @Size(max = 20, message = "Username tidak boleh lebih dari 20 karakter.")
    private String username;

    @NotBlank(message = "Password tidak boleh kosong")
    @Size(min = 8, message = "Password minimal 8 karakter")
    private String password;

    @NotBlank(message = "Password harus dikonfirmasi")
    @Size(min = 8, message = "Password minimal 8 karakter")
    private String confirmPassword;

    @NotBlank
    @Pattern(regexp = "Buyer|Seller", message = "Jangan inspect value role. Ulangi Langkah Awal.")
    private String role;

    @NotBlank(message = "Nama tidak boleh kosong")
    private String name;

    @NotBlank(message = "Address tidak boleh kosong")
    private String address;
}

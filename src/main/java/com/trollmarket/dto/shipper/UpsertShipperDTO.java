package com.trollmarket.dto.shipper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpsertShipperDTO {

    private Long id;

    @NotBlank(message = "Nama Tidak boleh kosong")
    @Size(max = 50, message = "Maksimal 50 karakter")
    private String name;

    @Min(value = 0)
    @NotNull(message = "Harga tidak boleh kosong")
    @Digits(integer = 12, fraction = 2, message = "Desimal dengan 2 fraction.")
    private Double price;

    @NotNull
    private Boolean isActive;
}

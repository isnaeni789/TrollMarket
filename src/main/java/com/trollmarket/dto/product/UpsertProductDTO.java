package com.trollmarket.dto.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpsertProductDTO {

    private Long id;

    @NotBlank(message = "Nama Tidak boleh kosong")
    @Size(max = 200, message = "Maksimal 200 karakter")
    private String name;

    @NotNull
    private Long sellerId;

    @NotNull(message = "Category tidak boleh kosong")
    private Long categoryId;

    @Size(max = 1000, message = "Maksimal 1000 karakter")
    private String description;

    @Min(value = 0)
    @NotNull(message = "Harga tidak boleh kosong")
    @Digits(integer = 12, fraction = 2, message = "Desimal dengan 2 fraction.")
    private Double price;

    @NotNull
    private Boolean discontinue;
}

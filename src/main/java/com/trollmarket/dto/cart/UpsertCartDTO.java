package com.trollmarket.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpsertCartDTO {
    private Long id;
    private Long buyerId;
    private Long productId;
    private Long shipperId;
    @Min(value = 1, message = "Minimal 1 quantity")
    private Integer quantity;
}

package com.trollmarket.dto.profile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class TopupBuyerBalanceDTO {
    private Long id;
    @Min(value = 0, message = "Tidak boleh minus")
    private Double balance;
}

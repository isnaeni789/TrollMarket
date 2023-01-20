package com.trollmarket.dto.utility;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDTO {
    private String jenisException;
    private String message;
    private LocalDateTime waktuError;
}

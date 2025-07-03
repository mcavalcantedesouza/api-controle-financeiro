package com.controlefinanceiro.api_controle_financeiro.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {

    private Integer id;

    private String name;

    private String email;

    private BigDecimal initialBalance;

    private LocalDateTime createdAt;
}

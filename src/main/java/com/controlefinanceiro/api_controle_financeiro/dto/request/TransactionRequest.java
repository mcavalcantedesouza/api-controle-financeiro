package com.controlefinanceiro.api_controle_financeiro.dto.request;

import com.controlefinanceiro.api_controle_financeiro.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionRequest {

    private String description ;

    private BigDecimal value;

    private TransactionType valueType;

    private LocalDate date;

    private Integer categoryId;
}

package com.controlefinanceiro.api_controle_financeiro.dto.response;

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
public class TransactionResponse {

    private Integer id;

    private String description ;

    private BigDecimal value;

    private TransactionType valueType;

    private LocalDate date;

    private Integer categoryId;

    private String categoryName;
}

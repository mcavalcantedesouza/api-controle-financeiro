package com.controlefinanceiro.api_controle_financeiro.dto.request;

import com.controlefinanceiro.api_controle_financeiro.enums.TransactionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryRequest {

    @NotBlank(message = "O nome da categoria é obrigatório.")
    private String name;

    @NotNull(message = "O tipo da transação é obrigatório.")
    private TransactionType valueType;
}

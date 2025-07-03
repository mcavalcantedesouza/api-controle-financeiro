package com.controlefinanceiro.api_controle_financeiro.dto.request;

import com.controlefinanceiro.api_controle_financeiro.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryRequest {

    private String name;

    private TransactionType valueType;
}

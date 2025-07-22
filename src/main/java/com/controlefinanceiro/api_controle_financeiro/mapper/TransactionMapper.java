package com.controlefinanceiro.api_controle_financeiro.mapper;

import com.controlefinanceiro.api_controle_financeiro.dto.request.TransactionRequest;
import com.controlefinanceiro.api_controle_financeiro.dto.response.TransactionResponse;
import com.controlefinanceiro.api_controle_financeiro.entity.TransactionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TransactionMapper {

    @Mapping(source = "categoryEntity.id", target = "categoryId")
    @Mapping(source = "categoryEntity.name", target = "categoryName")
    TransactionResponse toResponse(TransactionEntity entity);

    List<TransactionResponse> toResponseList(List<TransactionEntity> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userEntity", ignore = true)
    @Mapping(target = "categoryEntity", ignore = true)
    TransactionEntity toEntity(TransactionRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userEntity", ignore = true)
    @Mapping(target = "categoryEntity", ignore = true)
    void updateEntity(TransactionRequest request, @MappingTarget TransactionEntity entity);
}
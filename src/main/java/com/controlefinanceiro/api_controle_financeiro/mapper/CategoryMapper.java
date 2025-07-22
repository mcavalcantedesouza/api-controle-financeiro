package com.controlefinanceiro.api_controle_financeiro.mapper;

import com.controlefinanceiro.api_controle_financeiro.dto.request.CategoryRequest;
import com.controlefinanceiro.api_controle_financeiro.dto.response.CategoryResponse;
import com.controlefinanceiro.api_controle_financeiro.entity.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userEntity", ignore = true)
    @Mapping(target = "transactionEntities", ignore = true)
    CategoryEntity categoryRequestToCategoryEntity(CategoryRequest request);

    @Mapping(source = "userEntity.id", target = "userId")
    CategoryResponse categoryEntityToCategoryResponse(CategoryEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userEntity", ignore = true)
    void updateCategoryEntityFromCategoryRequest(CategoryRequest request, @MappingTarget CategoryEntity entity);

    List<CategoryResponse> categoryEntityListToCategoryResponseList(List<CategoryEntity> entities);

}

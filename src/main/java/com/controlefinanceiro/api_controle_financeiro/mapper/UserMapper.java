package com.controlefinanceiro.api_controle_financeiro.mapper;

import com.controlefinanceiro.api_controle_financeiro.dto.request.UserCreatedRequest;
import com.controlefinanceiro.api_controle_financeiro.dto.request.UserRequest;
import com.controlefinanceiro.api_controle_financeiro.dto.response.UserResponse;
import com.controlefinanceiro.api_controle_financeiro.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "hashPassword", ignore = true)
    @Mapping(target = "categories", ignore = true)
    @Mapping(target = "transactionEntities", ignore = true)
    UserEntity userRequestToUserEntity(UserRequest request);

    UserResponse userEntityToUserResponse(UserEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "hashPassword", ignore = true)
    void updateUserEntityFromUserRequest(UserCreatedRequest request, @MappingTarget UserEntity entity);

    List<UserResponse> userEntityListToUserResponseList(List<UserEntity> entities);
}

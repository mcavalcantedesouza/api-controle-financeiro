package com.controlefinanceiro.api_controle_financeiro.service;

import com.controlefinanceiro.api_controle_financeiro.dto.request.UserCreatedRequest;
import com.controlefinanceiro.api_controle_financeiro.dto.request.UserRequest;
import com.controlefinanceiro.api_controle_financeiro.dto.response.UserResponse;
import com.controlefinanceiro.api_controle_financeiro.entity.UserEntity;
import com.controlefinanceiro.api_controle_financeiro.mapper.UserMapper;
import com.controlefinanceiro.api_controle_financeiro.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper mapper;

    @Transactional
    public UserResponse createUser(UserRequest request) {

        if (repository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email já cadastrado.");
        }

        UserEntity userEntity = mapper.userRequestToUserEntity(request);

        String encodedPassword = passwordEncoder.encode(request.getPassword());
        userEntity.setHashPassword(encodedPassword);

        userEntity.setInitialBalance(BigDecimal.ZERO);

        UserEntity savedUser = repository.save(userEntity);

        return mapper.userEntityToUserResponse(savedUser);
    }

    @Transactional
    public UserResponse updateUser(Integer id, UserCreatedRequest request) {

        UserEntity userEntity = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));

        mapper.updateUserEntityFromUserRequest(request, userEntity);

        UserEntity savedUser = repository.save(userEntity);

        return mapper.userEntityToUserResponse(savedUser);
    }
}

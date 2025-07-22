package com.controlefinanceiro.api_controle_financeiro.service;

import com.controlefinanceiro.api_controle_financeiro.dto.request.UserRequest;
import com.controlefinanceiro.api_controle_financeiro.dto.response.UserResponse;
import com.controlefinanceiro.api_controle_financeiro.entity.UserEntity;
import com.controlefinanceiro.api_controle_financeiro.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

//    public UserResponse createUser(UserRequest request) {
//
//        if (repository.findByEmail(request.getEmail()).isPresent()) {
//
//            throw new RuntimeException("Email já cadastrado.");
//        }
//    }
}

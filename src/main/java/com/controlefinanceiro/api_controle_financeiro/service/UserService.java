package com.controlefinanceiro.api_controle_financeiro.service;

import com.controlefinanceiro.api_controle_financeiro.dto.request.UserCreatedRequest;
import com.controlefinanceiro.api_controle_financeiro.dto.request.UserRequest;
import com.controlefinanceiro.api_controle_financeiro.dto.response.UserResponse;
import com.controlefinanceiro.api_controle_financeiro.entity.RoleEntity;
import com.controlefinanceiro.api_controle_financeiro.entity.UserEntity;
import com.controlefinanceiro.api_controle_financeiro.enums.RoleName;
import com.controlefinanceiro.api_controle_financeiro.exception.EmailAlreadyExistsException;
import com.controlefinanceiro.api_controle_financeiro.mapper.UserMapper;
import com.controlefinanceiro.api_controle_financeiro.repository.RoleRepository;
import com.controlefinanceiro.api_controle_financeiro.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper mapper;

    @Transactional
    public UserResponse createUser(UserRequest request) {

        if (repository.findByEmail(request.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("O e-mail informado já está em uso");
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        RoleEntity userRole = roleRepository.findByRoleName(RoleName.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Role de usuário não encontrada"));

        UserEntity userEntity = UserEntity.builder()
                .name(request.getName())
                .email(request.getEmail())
                .hashPassword(encodedPassword)
                .roles(Collections.singleton(userRole))
                .build();

        UserEntity savedUser = repository.save(userEntity);
        return mapper.userEntityToUserResponse(savedUser);
    }

    @Transactional
    public UserResponse updateCurrentUser(UserCreatedRequest request) {
        UserEntity currentUser = getAuthenticatedUser();

        if (request.getEmail() != null && !request.getEmail().equals(currentUser.getEmail())) {
            if (repository.findByEmail(request.getEmail()).isPresent()) {
                throw new EmailAlreadyExistsException("O e-mail informado já está em uso.");
            }
        }

        mapper.updateUserEntityFromUserRequest(request, currentUser);
        UserEntity updatedUser = repository.save(currentUser);

        return mapper.userEntityToUserResponse(updatedUser);
    }

    @Transactional(readOnly = true)
    public UserResponse getCurrentUser() {
        UserEntity currentUser = getAuthenticatedUser();
        return mapper.userEntityToUserResponse(currentUser);
    }

    @Transactional
    public void deleteCurrentUser() {
        UserEntity currentUser = getAuthenticatedUser();
        repository.delete(currentUser);
    }

    UserEntity getAuthenticatedUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserEntity) {
            return (UserEntity) principal;
        }

        String username = (String) principal;
        return repository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado."));
    }

    /* Métodos para usuários com perfil de ADMIN */

    @Transactional
    public UserResponse getUserById(Integer id) {
        UserEntity userEntity = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));
        return mapper.userEntityToUserResponse(userEntity);
    }

    @Transactional
    public List<UserResponse> getUsers() {
        List<UserEntity> userEntities = repository.findAll();
        return mapper.userEntityListToUserResponseList(userEntities);
    }

    @Transactional
    public void deleteUserById(Integer id) {
        repository.deleteById(id);
    }

    /* Fim dos métodos para usuários com perfil de ADMIN */
}

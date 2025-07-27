package com.controlefinanceiro.api_controle_financeiro.controller;


import com.controlefinanceiro.api_controle_financeiro.dto.request.UserCreatedRequest;
import com.controlefinanceiro.api_controle_financeiro.dto.request.UserRequest;
import com.controlefinanceiro.api_controle_financeiro.dto.response.UserResponse;
import com.controlefinanceiro.api_controle_financeiro.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UserController {

    private final UserService service;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createUser(request));
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser() {
        return ResponseEntity.ok(service.getCurrentUser());
    }

    @PutMapping("/me")
    public ResponseEntity<UserResponse> updateCurrentUser(@RequestBody @Valid UserCreatedRequest request) {
        return ResponseEntity.ok(service.updateCurrentUser(request));
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteCurrentUser() {
        service.deleteCurrentUser();
        return ResponseEntity.noContent().build();
    }

    /*
     * ===================================================================
     *  Endpoints abaixo são para fins administrativos.
     *  Implementar a checagem de role futuramente para esses endpoints
     * ===================================================================
     */

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponse>> getUsers() {
        return ResponseEntity.ok(service.getUsers());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getUserById(id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        service.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

}

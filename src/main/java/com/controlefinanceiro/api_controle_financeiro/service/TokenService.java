package com.controlefinanceiro.api_controle_financeiro.service;

import com.controlefinanceiro.api_controle_financeiro.entity.UserEntity;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    private static final long EXPIRATION_TIME = 86400000; // 24 horas em milissegundos

    public String generateToken(UserEntity user) {
        return Jwts.builder()
                .setIssuer("API Controle Financeiro")
                .setSubject(user.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSigningKey())
                .compact();
    }

    public String getSubject(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();
        } catch (Exception e) {
            // Você pode querer logar a exceção ou tratá-la de forma mais específica
            throw new RuntimeException("Token JWT inválido ou expirado!", e);
        }
    }

    private SecretKey getSigningKey() {

        byte[] keyBytes = this.secret.getBytes(StandardCharsets.UTF_8);

        return Keys.hmacShaKeyFor(keyBytes);
    }
}
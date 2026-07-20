package br.com.ifba.prg04deskflow.infrastructure.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    // Gera o token com email, perfil, id e nome
    public String gerarToken(String email, String perfil, Long id, String nome) {
        return Jwts.builder()
                .subject(email)
                .claims(Map.of(
                        "perfil", perfil,
                        "id", id,
                        "nome", nome
                ))
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getChave())
                .compact();
    }

    public String extrairEmail(String token) {
        return getClaims(token).getSubject();
    }

    public String extrairPerfil(String token) {
        return (String) getClaims(token).get("perfil");
    }

    public Long extrairId(String token) {
        return ((Number) getClaims(token).get("id")).longValue();
    }

    public String extrairNome(String token) {
        return (String) getClaims(token).get("nome");
    }

    public boolean tokenValido(String token) {
        try {
            extrairEmail(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(getChave())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getChave() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }
}
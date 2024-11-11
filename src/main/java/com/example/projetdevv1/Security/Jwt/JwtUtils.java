package com.example.projetdevv1.Security.Jwt;

import com.example.projetdevv1.Security.Services.UserDetailsImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtUtils {
    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtExpirationMs}")
    private int jwtExpirationMs;

    public String generateJwtToken( Authentication authentication ) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        // Supposons que UserDetailsImpl ait une méthode getRoles() qui retourne une liste de rôles
        String roles = userPrincipal.getAuthorities().stream()
                .map(grantedAuthority -> grantedAuthority.getAuthority()) // Récupère le nom du rôle
                .collect(Collectors.joining(", ")); // Joindre les rôles avec une virgule

        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .claim("userId",userPrincipal.getId())
                .claim("role", roles) // Ajoutez le rôle ici
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (JwtException e) {
            System.err.println("Invalid JWT: " + e.getMessage());
        }
        return false;
    }
    public Long extractUserId(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        return claims.get("userId", Long.class); // Expect Long type
    }
    public String extractUserRole(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        return claims.get("role", String.class); // Récupère le rôle
    }

    
}

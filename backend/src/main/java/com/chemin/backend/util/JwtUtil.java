package com.chemin.backend.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Map;


@Component
public class JwtUtil {

    @Value("${jwt.secret_key}")
    private String SECRET_KEY;

    @Value("${jwt.expiration_time}")
    private long EXPIRATION_TIME;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public String generateToke(Map<String, Object> claims, String account) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);

        return Jwts.builder()
                .setClaims(claims)       // payload
                .setSubject(account)     // 一般存 username
                .setIssuedAt(now)        // 發行時間
                .setExpiration(expiryDate) // 過期時間
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // 解析 JWT，取得所有 claims
    public Claims extractToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException e) {
            return null; // token 無效或已過期
        }
    }

    // 檢查 token 是否過期
    public boolean isTokenExpired(String token) {
        Claims claims = extractToken(token);
        if (claims == null) return true;
        return claims.getExpiration().before(new Date());
    }

    // 驗證 token 是否有效（username 是否匹配且未過期）
    public boolean validateToken(String token, String account) {
        String tokenUsername = extractToken(token).getSubject();
        return (tokenUsername != null && tokenUsername.equals(account) && !isTokenExpired(token));
    }

}

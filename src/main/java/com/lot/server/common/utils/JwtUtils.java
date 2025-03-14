package com.lot.server.common.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Map;

public class JwtUtils {
    public static String createJWT(String secretKey, long ttlMillis, Map<String, Object> claims) {
        // signature algorithm
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

        // jwt generate/exp time
        long expMillis = System.currentTimeMillis() + ttlMillis;
        Date exp = new Date(expMillis);

        // jwt body
        JwtBuilder builder = Jwts.builder()
                .setClaims(claims)
                .setExpiration(exp)
                .signWith(key, signatureAlgorithm);

        return builder.compact();
    }

    public static Claims parseJWT(String secretKey, String token) {
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        Jws<Claims> jws = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);

        return jws.getBody(); // 获取 payload
    }
}

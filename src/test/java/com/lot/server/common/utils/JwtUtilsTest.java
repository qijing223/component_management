package com.lot.server.common.utils;

import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@Disabled
@SpringBootTest
class JwtUtilsTest {

    private final String secretKey =  "LoTBackEndSecurityKeyUsedForAuthorization";

    @Test
    void testCreateAndParseJWT() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", "1");

        long ttlMillis = 3600_000;

        String jwt = JwtUtils.createJWT(secretKey, ttlMillis, claims);
        assertNotNull(jwt);

        Claims parsedClaims = JwtUtils.parseJWT(secretKey, jwt);
        assertEquals("1", parsedClaims.get("userId"));

        Date expiration = parsedClaims.getExpiration();
        assertNotNull(expiration);
        assertTrue(expiration.after(new Date()));
    }

    @Test
    void testParseJWTWithInvalidKey_shouldThrowException() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", 123);

        String jwt = JwtUtils.createJWT(secretKey, 1000, claims);

        String wrongKey = "WrongLoTBackEndSecurityKeyUsedForAuthorization";

        assertThrows(io.jsonwebtoken.security.SignatureException.class, () -> {
            JwtUtils.parseJWT(wrongKey, jwt);
        });
    }
}

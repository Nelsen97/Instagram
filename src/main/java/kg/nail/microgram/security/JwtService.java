package kg.nail.microgram.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import kg.nail.microgram.config.JwtProperties;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class JwtService {
    final JwtProperties jwtProperties;
    Key key;

    @PostConstruct //запускается один раз после запуска приложения
    public void init() {
        this.key = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());
    }

    public String generateAccessToken(JwtEntity jwtEntity) {
        return buildToken(jwtEntity, jwtProperties.getAccess());
    }

    public String generateRefreshToken(JwtEntity jwtEntity) {
        return buildToken(jwtEntity, jwtProperties.getRefresh());
    }

    public String extractSubject(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith((SecretKey) key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private String buildToken(JwtEntity jwtEntity, Long expirationTime) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", jwtEntity.getId());
        claims.put("sub", jwtEntity.getEmail());
        claims.put("role", jwtEntity.getAuthorities());
        claims.put("fullName", jwtEntity.getFullName());
        Instant expirationDate = Instant.now().plus(expirationTime, ChronoUnit.DAYS);
        return Jwts.builder()
                .claims(claims)
                .expiration(Date.from(expirationDate))
                .signWith(key)
                .compact();
    }

}

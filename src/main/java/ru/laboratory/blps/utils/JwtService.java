package ru.laboratory.blps.utils;

import ru.laboratory.blps.model.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${jwt.access-token-lifetime}")
    private long ACCESS_TOKEN_LIFETIME;

    @Value("${jwt.refresh-token-lifetime}")
    private long REFRESH_TOKEN_LIFETIME;

    @Value("${jwt.secret-word}")
    private String SECRET_WORD;

    public String extractUserIdFromToken(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    public Date extractExpirationFromToken(String token) {
        return extractClaims(token, Claims::getExpiration);
    }

    public <T> T extractClaims(String token, Function<Claims, T> claimsTFunction) {
        final Claims claims = extractAllClaims(token);
        return claimsTFunction.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_WORD).parseClaimsJws(token).getBody();
    }

    public Boolean isTokenExpired(String token) {
        return extractExpirationFromToken(token).before(new Date());
    }

    public String generateToken(User user, TokenType tokenType) {
        Map<String, Object> claims = new HashMap<>();
        switch (tokenType) {
            case access:
                return createToken(claims, user, ACCESS_TOKEN_LIFETIME);
            case refresh:
                return createToken(claims, user, REFRESH_TOKEN_LIFETIME);
            default:
                throw new IllegalArgumentException();
        }
    }

    private String createToken(Map<String, Object> claims, User user, long tokenLifeTime) {
        claims.put("role", user.getRole().getAuthority());
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(String.valueOf(user.getId()))
                .setIssuer(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + tokenLifeTime))
                .signWith(SignatureAlgorithm.HS512, SECRET_WORD)
                .compact();
    }

    public Boolean validateToken(String token, User user) {
        final long userId = Long.parseLong(extractUserIdFromToken(token));
        return (userId == user.getId() && !isTokenExpired(token));
    }
}

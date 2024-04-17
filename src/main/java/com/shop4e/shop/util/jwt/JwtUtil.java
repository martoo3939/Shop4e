package com.shop4e.shop.util.jwt;

import com.shop4e.shop.exception.CustomException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

  private final String SECRET_KEY;
  private final Long TOKEN_DURATION;

  public JwtUtil(
      @Value("app.jwt.secret-key") String secretKey,
      @Value("#{new Long('${app.jwt.token-duration}')}") Long tokenDuration) {
    SECRET_KEY = secretKey;
    TOKEN_DURATION = tokenDuration;
  }

  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  public Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  private Claims extractAllClaims(String token) {
    try {
      return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }  catch (Exception ex) {
      throw new CustomException("Invalid token");
    }
  }

  private Boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  public Token generateToken(UserDetails userDetails) {
    Map<String, Object> claims = new HashMap<>();
    String token = createToken(claims, userDetails);
    String refreshToken = createRefreshToken(claims, userDetails);

    return new Token(token, refreshToken);
  }

  private String createToken(Map<String, Object> claims, UserDetails userDetails) {
    return Jwts.builder()
        .setClaims(claims)
        .setSubject(userDetails.getUsername())
        .claim("authorities", userDetails.getAuthorities())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(
            new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(TOKEN_DURATION)))
        .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
        .compact();
  }

  private String createRefreshToken(Map<String, Object> claims, UserDetails userDetails) {
    return Jwts.builder()
        .setClaims(claims)
        .setSubject(userDetails.getUsername())
        .claim("refresh", true)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(TOKEN_DURATION * 2)))
        .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
        .compact();
  }

  public Boolean isTokenValid(String token, UserDetails userDetails) {
    final String username = extractUsername(token);
    return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
  }

  public static class Token {
    private String token;
    private String refreshToken;

    public Token(String token, String refreshToken) {
      this.token = token;
      this.refreshToken = refreshToken;
    }

    public String getToken() {
      return token;
    }

    public Token setToken(String token) {
      this.token = token;
      return this;
    }

    public String getRefreshToken() {
      return refreshToken;
    }

    public Token setRefreshToken(String refreshToken) {
      this.refreshToken = refreshToken;
      return this;
    }
  }
}

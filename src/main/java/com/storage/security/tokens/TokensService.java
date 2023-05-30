package com.storage.security.tokens;

import com.storage.exceptions.NotFoundException;
import com.storage.repositories.UserRepository;
import com.storage.security.dto.RefreshTokenDto;
import com.storage.security.tokens.dto.TokensDto;
import com.storage.security.tokens.exception.AppTokensServiceException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TokensService {

    @Value("${tokens.access-token.expiration-time-ms}")
    private Long accessTokenExpirationTimeMs;

    @Value("${tokens.refresh-token.expiration-time-ms}")
    private Long refreshTokenExpirationTimeMs;

    @Value("${tokens.refresh-token.access-token-expiration-time-ms-property}")
    private String accessTokenExpirationTimeFieldName;

    @Value("${tokens.prefix}")
    private String tokensPrefix;

    private final UserRepository userRepository;
    private final SecretKey secretKey;


    public TokensDto generateTokens(Authentication authentication) {
        String email = authentication.getName();
        String userUuid = userRepository.getUuidByEmail(email)
                .orElseThrow(() -> new NotFoundException("Cannot find user by email: " + email));

        Date currentDate = new Date();
        Date accessTokenExpirationDate = new Date(currentDate.getTime() + accessTokenExpirationTimeMs);
        Date refreshTokenExpirationDate = new Date(currentDate.getTime() + refreshTokenExpirationTimeMs);

        String accessToken = generateAccessToken(userUuid, currentDate, accessTokenExpirationDate);
        String refreshToken = generateRefreshToken(userUuid, currentDate, accessTokenExpirationDate, refreshTokenExpirationDate);

        return TokensDto
                .builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public UsernamePasswordAuthenticationToken parseAccessToken(String header) {
        if (header == null)
            throw new AppTokensServiceException("Token Header is null");

        if (!header.startsWith(tokensPrefix))
            throw new AppTokensServiceException("Token has invalid format");

        String token = header.replaceAll(tokensPrefix, "");
        String userUuid = getUuid(token);

        return userRepository
                .findByUuid(userUuid)
                .map(user -> new UsernamePasswordAuthenticationToken(
                        user.getEmail(),
                        null,
                        List.of(new SimpleGrantedAuthority(user.getRole().toString()))
                ))
                .orElseThrow(() -> new AppTokensServiceException("Authorization failed!"));
    }

    public TokensDto refreshTokens(RefreshTokenDto refreshTokenDto) {
        if (refreshTokenDto == null)
            throw new AppTokensServiceException("Refresh token object is null");

        String refreshToken = refreshTokenDto.token();
        if (refreshToken == null)
            throw new AppTokensServiceException("Token is null");

        if (isRefreshTokenExpired(refreshToken))
            throw new AppTokensServiceException("Refresh token has been expired");

        if (!isAccessTokenStillValid(refreshToken))
            throw new AppTokensServiceException("Cannot refresh tokens because of access token expiration");

        String userUuid = getUuid(refreshToken);
        Date currentDate = new Date();
        Date accessTokenExpirationDate = new Date(currentDate.getTime() + accessTokenExpirationTimeMs);
        Date refreshTokenExpirationDate = getExpirationDate(refreshToken);

        String accessToken = generateAccessToken(userUuid, currentDate, accessTokenExpirationDate);

        String newRefreshToken = generateRefreshToken(userUuid, currentDate, accessTokenExpirationDate, refreshTokenExpirationDate);

        return TokensDto
                .builder()
                .accessToken(accessToken)
                .refreshToken(newRefreshToken)
                .build();
    }

    private String generateRefreshToken(String userUuid, Date currentDate, Date accessTokenExpirationDate, Date refreshTokenExpirationDate) {
        return Jwts
                .builder()
                .setSubject(userUuid)
                .setExpiration(refreshTokenExpirationDate)
                .setIssuedAt(currentDate)
                .claim(accessTokenExpirationTimeFieldName, accessTokenExpirationDate.getTime())
                .signWith(secretKey)
                .compact();
    }

    private String generateAccessToken(String userUuid, Date currentDate, Date accessTokenExpirationDate) {
        return Jwts
                .builder()
                .setSubject(userUuid)
                .setExpiration(accessTokenExpirationDate)
                .setIssuedAt(currentDate)
                .signWith(secretKey)
                .compact();
    }

    private boolean isAccessTokenStillValid(String refreshToken) {
        long accessTokenExpirationTimeMs = getAccessTokenExpirationTimeMs(refreshToken);
        return accessTokenExpirationTimeMs < System.currentTimeMillis();
    }

    private Claims claims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private String getUuid(String token) {
        return claims(token).getSubject();
    }

    private Date getExpirationDate(String token) {
        return claims(token).getExpiration();
    }

    private boolean isRefreshTokenExpired(String token) {
        return getExpirationDate(token).before(new Date());
    }

    private long getAccessTokenExpirationTimeMs(String token) {
        return claims(token).get(accessTokenExpirationTimeFieldName, Long.class);
    }
}

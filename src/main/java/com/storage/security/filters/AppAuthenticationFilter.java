package com.storage.security.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.storage.security.dto.AuthenticationDto;
import com.storage.security.exceptions.AppSecurityException;
import com.storage.security.tokens.TokensService;
import com.storage.security.tokens.dto.TokensDto;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Collections;

@RequiredArgsConstructor
public class AppAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final TokensService appTokensService;

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response) throws AuthenticationException {
        try {
            AuthenticationDto userToAuthenticate =
                    new ObjectMapper().readValue(request.getInputStream(), AuthenticationDto.class);

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userToAuthenticate.getUsername(),
                            userToAuthenticate.getPassword(),
                            Collections.emptyList()
                    ));
        } catch (Exception e) {
            throw new AppSecurityException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult) throws IOException {
        // Authentication store info about authenticated user
        TokensDto tokens = appTokensService.generateTokens(authResult);
        // return tokens
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(new ObjectMapper().writeValueAsString(tokens));
        response.getWriter().flush();
        response.getWriter().close();
    }
}

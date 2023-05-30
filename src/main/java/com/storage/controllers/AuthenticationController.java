package com.storage.controllers;

import com.storage.security.dto.RefreshTokenDto;
import com.storage.security.tokens.TokensService;
import com.storage.security.tokens.dto.TokensDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final TokensService tokensService;
    @PostMapping("/refresh-token")
    public ResponseEntity<TokensDto> refreshToken(@RequestBody @Validated RefreshTokenDto refreshTokenDto) {
        TokensDto tokensDto = tokensService.refreshTokens(refreshTokenDto);
        return new ResponseEntity<>(tokensDto, HttpStatus.OK);
    }
}

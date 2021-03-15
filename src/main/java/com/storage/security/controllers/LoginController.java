package com.storage.security.controllers;

import com.storage.security.models.LoginCredentials;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    /**
     * Endpoint needed by Swagger only. /login endpoint provided by Spring Security
     *
     * @author Pawel Konarzewski
     * @since 14/03/2021
     */

    @PostMapping("/login")
    public void login(@RequestBody LoginCredentials credentials) {

    }
}

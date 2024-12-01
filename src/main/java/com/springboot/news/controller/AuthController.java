package com.springboot.news.controller;

import com.springboot.news.payload.JwtAuthResponse;
import com.springboot.news.payload.LoginDto;
import com.springboot.news.payload.RegisterDto;
import com.springboot.news.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
@Tag(name = "CRUD REST APIs for Authentication")
public class AuthController {
    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(
            summary = "Login REST API",
            description = "Login REST API is used to login into an account"
    )
    @ApiResponse(responseCode = "200", description = "Http Status 201 SUCCESS")
    @PostMapping(value = {"/login", "/signin"})
    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDto loginDto) {
        String token = authService.login(loginDto);
        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setAccessToken(token);
        return ResponseEntity.ok(jwtAuthResponse);
    }

    @Operation(
            summary = "Register REST API",
            description = "Register REST API is used to register an account"
    )
    @ApiResponse(responseCode = "201", description = "Http Status 201 CREATED")
    @PostMapping(value = {"/register", "/signup"})
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
        String response = authService.register(registerDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}

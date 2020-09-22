package com.zvkvc.eksperti.controller;

import com.zvkvc.eksperti.dto.AuthResponse;
import com.zvkvc.eksperti.dto.LoginRequest;
import com.zvkvc.eksperti.dto.RefreshTokenRequest;
import com.zvkvc.eksperti.dto.RegisterRequest;
import com.zvkvc.eksperti.service.AuthService;
import com.zvkvc.eksperti.service.RefreshTokenService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/auth") // parent mapping
@AllArgsConstructor
public class AuthController {

    @Autowired
    private AuthService authService;

    private final RefreshTokenService refreshTokenService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest) {
        System.out.println("Register payload received...OK");
        System.out.println("Processing payload...");
        authService.signup(registerRequest);
        return new ResponseEntity<>("User Registration Successful", HttpStatus.OK);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);

    }

    @PostMapping("refresh/token")
    public AuthResponse refreshTokens(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        return authService.refreshToken(refreshTokenRequest);

    }

    @PostMapping("logout")
    public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
        return ResponseEntity.status(HttpStatus.OK).body("Refresh Token Deleted Successfully!");
    }





}

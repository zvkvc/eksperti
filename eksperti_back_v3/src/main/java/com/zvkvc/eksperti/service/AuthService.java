package com.zvkvc.eksperti.service;

import com.zvkvc.eksperti.dao.UserRepository;

import com.zvkvc.eksperti.dto.AuthResponse;
import com.zvkvc.eksperti.dto.LoginRequest;
import com.zvkvc.eksperti.dto.RefreshTokenRequest;
import com.zvkvc.eksperti.dto.RegisterRequest;
import com.zvkvc.eksperti.model.User;
import com.zvkvc.eksperti.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;


@Service
public class AuthService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationManager authenticationManager; // there are multiple impls of this, so make sure to create
    // ... specific bean in Security Config
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private RefreshTokenService refreshTokenService;

    @Transactional
    public void signup(RegisterRequest registerRequest) {
        User user = new User();
        // fill in the user info from request body
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setCreated(Instant.now());
        user.setEnabled(true); // set to false for Verification purposes

        userRepository.save(user);
        System.out.println("New user created and saved!");
    }

    @Transactional
    User getCurrentUser() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(principal.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User name not found - " + principal.getUsername()));
    }

    public AuthResponse login(LoginRequest loginRequest) {
        // map credentials from loginRequest to UsernamePasswordAuthToken
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword());
        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        // authenticationManager has been provided by custom UserDetailsService that provides UserDetails to be authenticated against
        // --first we store authentication object in security context
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        /* -- -- -- */
        // so if you want to check if a person is logged in or not
        // you can lookup Security Context for authentication object
        // and if you find an object then user is logged in and if not user is not logged in
        String token = jwtProvider.generateToken(authenticate); // generate token based on authenticate object
        // to send this token we'll use DTO called AuthResponse

        return AuthResponse.builder()
                .authenticationToken(token)
                .refreshToken("") // empty token
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .username(loginRequest.getUsername())
                .build();
    }

    public AuthResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken());
        String token = jwtProvider.generateTokenWithUserName(refreshTokenRequest.getUsername());
        return AuthResponse.builder()
                .authenticationToken(token)
                .refreshToken(refreshTokenRequest.getRefreshToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .username(refreshTokenRequest.getUsername())
                .build();
    }

    public boolean isLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
    }

}

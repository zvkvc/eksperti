package com.zvkvc.eksperti.security;

import com.zvkvc.eksperti.exceptions.GeneralException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;
import java.time.Instant;

import static io.jsonwebtoken.Jwts.parser;
import static java.util.Date.from;

@Service
public class JwtProvider {

    private KeyStore keyStore;

    @Value("${jwt.expiration.time}")
    private Long jwtExpirationInMillis;

    @PostConstruct
    public void init() {
        try {
            keyStore = KeyStore.getInstance("JKS"); // we provide keyStore instance of type JKS
            InputStream resourceAsStream = getClass().getResourceAsStream("/springkeystore.jks"); // keyStore file
            keyStore.load(resourceAsStream, "secret".toCharArray()); // pass="secret"

        } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e) {
            throw new GeneralException("Exception occured while loading keystore.");
        }
    }

    public String generateToken(Authentication authentication) {
        // IMPORTANT: Make sure to import core.userdetails.User  NOT  model.User
        // ...since authentication.getPrincipal() object won't correspond to model.User
        User principal = (User) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(principal.getUsername())
                .signWith(getPrivateKey()) // we should provide a private key from keystore to sign a token with
                // we'll use asymmetric java keystore so our key stays consistent
                .setExpiration(from(Instant.now().plusMillis(jwtExpirationInMillis)))
                .compact();
    }

    private PrivateKey getPrivateKey() {
        try {
            return (PrivateKey) keyStore.getKey("springkeystore", "secret".toCharArray());
        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
            throw new GeneralException("Exception occured while retrieving public key from keystore.");
        }
    }

    public Boolean validateToken(String jwt) {
        parser().setSigningKey(getPublicKey()).parseClaimsJws(jwt); // validate public key against incoming jwt(private key)
        // why is parser() depracated btw?
        return true;
    }

    // custom keystore can be created in windows cmd
    private PublicKey getPublicKey() {
        try {
            return keyStore.getCertificate("springkeystore").getPublicKey();
        } catch (KeyStoreException e) {
            throw new GeneralException("Exception occured while "+ "retrieving public key from keystore");
        }
    }

    public String getUsernameFromJwt(String token) {
        Claims claims = parser()
                .setSigningKey(getPublicKey())
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject(); // string
    }


    public String generateTokenWithUserName(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(from(Instant.now()))
                .signWith(getPrivateKey())
                .setExpiration(from(Instant.now().plusMillis(jwtExpirationInMillis)))
                .compact();
    }

    public Long getJwtExpirationInMillis() {
        return jwtExpirationInMillis;
    }


}

/*
----- Token Invalidation Options
1) Delete Token from the Browser - but there is a possibility that hacker already accessed JWT
    that is a reason for invalidating token also on the backend
2) Introduce Expiration Times for JWT (and make them as short as possible) - 15mins?
    The downside is that once the token expires we must ask the user to log in again (bad user experience)
((3)) Use Refresh Tokens - idea is to provide an user an additional token at the time of authentication and use
    this token to generate new JWT whenever JWT is expired or about to be expired. When user logs out we just delete this token.
4) Token Blacklisting - when user wants to log out we store the token in DB then we check each token (on each request)
    weather it's blacklisted in DB. If yes throw an error(invalid).
    However, this defeats the purpose of using a token, because we're using state and doing DB lookup for every request.
    By using Redis(in-memory db) we can hugely improve performance of this issue.
 */

package br.ce.christian.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.ce.christian.dtos.LoginRequest;
import br.ce.christian.dtos.LoginResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtEncoder jwtEncoder;

    public AuthController(AuthenticationManager authenticationManager, JwtEncoder jwtEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtEncoder = jwtEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        try {
            // 1. Autentica o usuário
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.username(),
                            loginRequest.password()
                    )
            );

            // 2. Define o tempo de expiração do token (ex: 1 hora)
            Instant now = Instant.now();
            long expiresIn = 3600L; // segundos

            // 3. Monta as claims do JWT
            JwtClaimsSet claims = JwtClaimsSet.builder()
                    .issuer("spring-boot")
                    .issuedAt(now)
                    .expiresAt(now.plusSeconds(expiresIn))
                    .subject(authentication.getName())
                    .claim("roles", authentication.getAuthorities().stream()
                            .map(a -> a.getAuthority())
                            .toList())
                    .build();

            // 4. Gera o token
            String token = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

            // 5. Retorna o token e o tempo de expiração
            return ResponseEntity.ok(new LoginResponse(token, expiresIn));

        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).build();
        }
    }


}

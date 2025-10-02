/*package br.ce.christian.controller;

import br.ce.christian.dtos.LoginRequest;
import br.ce.christian.dtos.LoginResponse;
import br.ce.christian.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.stream.Collectors;

@RestController

public class TokenController {

    private final JwtEncoder jwtEncoder;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public TokenController(JwtEncoder jwtEncoder, UserRepository userRepository, BCryptPasswordEncoder passwordEncoder){
        this.jwtEncoder = jwtEncoder;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){
        var user = userRepository.findByUsername(loginRequest.username());

        if (user.isEmpty() || !user.get().isLoginCorrect(loginRequest, passwordEncoder)){
            throw new BadCredentialsException("user or password is invalid!");
        }

        var now = Instant.now();
        var expiresIn = 300L;

        var scopes = user.get().getRoles()
                .stream()
                .map(role -> role.getName().name()) // pega o nome do enum
                .collect(Collectors.joining(" "));

        // configurar os atributos do jwt (claims)
        var claims = JwtClaimsSet.builder()
                .issuer("mybackend") // quem ta gerando o token
                .subject(user.get().getUserId().toString()) // qual é o usuario
                .issuedAt(now) // data de emissao do token
                .expiresAt(now.plusSeconds(expiresIn)) // tempo de expiracao
                .claim("scope", scopes)
                .build();

        var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue(); // token jwt usando os claims definidos acima
        return ResponseEntity.ok(new LoginResponse(jwtValue, expiresIn));
    }
}*/

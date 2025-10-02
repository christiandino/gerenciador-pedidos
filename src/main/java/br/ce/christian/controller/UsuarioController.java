package br.ce.christian.controller;


import br.ce.christian.dtos.CriarUsuarioDTO;
import br.ce.christian.enums.AtividadeExercida;
import br.ce.christian.model.Role;
import br.ce.christian.model.User;
import br.ce.christian.repository.RoleRepository;
import br.ce.christian.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioController(UserRepository userRepository,
                          RoleRepository roleRepository,
                          PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Void> criarUsuario(@RequestBody CriarUsuarioDTO dto){
        if (userRepository.findByUsername(dto.username()).isPresent()){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Usuário já existe");
        }

        Role role = roleRepository.findByName(dto.role())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Role não encontrada no banco"));

        var user = new User();
        user.setUsername(dto.username());
        user.setPassword(passwordEncoder.encode(dto.password()));
        user.setRoles(Set.of(role));

        userRepository.save(user);

        return ResponseEntity.ok().build();
    }
}


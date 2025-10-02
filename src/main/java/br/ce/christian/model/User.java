package br.ce.christian.model;

import br.ce.christian.dtos.LoginRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Table(name = "tb_users")
public class User implements UserDetails{

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    //@Column(name = "user_id")
    private UUID userId;

    @Setter
    @Column(unique = true, nullable = false)
    private String username;

    @Setter
    @Column(nullable = false)
    private String password;

    @Setter
    @Getter
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "tb_users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    @OneToOne(mappedBy = "user")
    private Cliente cliente;

    @OneToOne(mappedBy = "user")
    private Funcionario funcionario;

    // ------------------- UserDetails ------------------- //

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Converte as roles do banco em GrantedAuthority para Spring Security
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName().name()))
                .toList();
    }

    @Override
    public String getPassword() { return password; }

    @Override
    public String getUsername() { return username; }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }

    public boolean isLoginCorrect(LoginRequest loginRequest, PasswordEncoder passwordEncoder) {
        // compara a senha que veio sem criptografia com a senha criptografada
        // loginRequest.password() que veio do request, this.password senha do banco de dados
        return passwordEncoder.matches(loginRequest.password(), this.password);
    }
}
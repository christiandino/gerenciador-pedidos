package br.ce.christian.repository;

import br.ce.christian.model.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
    boolean existsByCpf(String cpf);

    boolean existsByEmail(String emailNormalizado);
}

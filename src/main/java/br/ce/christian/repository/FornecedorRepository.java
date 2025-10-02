package br.ce.christian.repository;


import br.ce.christian.model.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface FornecedorRepository extends JpaRepository<Fornecedor, Long> {

    boolean existsByNome(String nome);

    Optional<Fornecedor> findByNome(String nome);
}

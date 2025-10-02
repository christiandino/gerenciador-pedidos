package br.ce.christian.repository;

import br.ce.christian.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    @Query("SELECT c FROM Categoria c JOIN c.produtos p GROUP BY c HAVING COUNT(p) > :numero")
    List<Categoria> categoriasComMaisDe (@Param("numero") Long numero);

    Optional<Categoria> findByNome(String nome);

    boolean existsByNome(String nome);
}

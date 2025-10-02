package br.ce.christian.repository;

import br.ce.christian.enums.AtividadeExercida;
import br.ce.christian.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(AtividadeExercida name);
}
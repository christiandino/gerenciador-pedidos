package br.ce.christian.dtos;

import br.ce.christian.enums.AtividadeExercida;

public record CriarUsuarioDTO(
        String username,
        String password,
        AtividadeExercida role // ADMIN, GERENTE, VENDEDOR, CLIENTE
) {}

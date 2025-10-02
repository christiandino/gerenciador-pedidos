package br.ce.christian.dtos;

import br.ce.christian.model.Categoria;

public class CategoriaResponseDTO {

    private Long id;
    private String nome;

    public CategoriaResponseDTO(Categoria categoria) {
        this.id = categoria.getId();
        this.nome = categoria.getNome();
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }
}

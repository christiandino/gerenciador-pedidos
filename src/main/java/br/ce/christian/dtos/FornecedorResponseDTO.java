package br.ce.christian.dtos;

import br.ce.christian.model.Fornecedor;

public class FornecedorResponseDTO {
    private Long id;
    private String nome;

    public FornecedorResponseDTO(Fornecedor fornecedor) {
        this.id = fornecedor.getId();
        this.nome = fornecedor.getNome();
    }

    public Long getId() { return id; }
    public String getNome() { return nome; }
}
package br.ce.christian.dtos;

import br.ce.christian.model.Produto;

public class ProdutoResponseDTO {

    private Long id;
    private String nome;
    private Double preco;
    private Long categoriaId;
    private String nomeCategoria;
    private FornecedorResponseDTO fornecedor;

    public ProdutoResponseDTO(Produto produto) {
        this.id = produto.getId();
        this.nome = produto.getNome();
        this.preco = produto.getPreco();
        this.categoriaId = produto.getCategoria() != null ? produto.getCategoria().getId() : null;
        this.nomeCategoria = produto.getCategoria() != null ? produto.getCategoria().getNome() : null;
        this.fornecedor = produto.getFornecedor() != null ? new FornecedorResponseDTO(produto.getFornecedor()) : null;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Double getPreco() {
        return preco;
    }

    public Long getCategoriaId() {
        return categoriaId;
    }

    public String getNomeCategoria() {
        return nomeCategoria;
    }

    public FornecedorResponseDTO getFornecedor() {
        return fornecedor;
    }
}

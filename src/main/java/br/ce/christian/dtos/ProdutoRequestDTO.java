package br.ce.christian.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class ProdutoRequestDTO {

    @NotBlank(message = "O nome do produto é obrigatório")
    @Size(min = 2, max = 100, message = "O nome do produto deve ter entre 2 e 100 caracteres")
    private String nome;

    @NotNull(message = "O preço é obrigatório")
    @Positive(message = "O preço deve ser maior que zero")
    private Double preco;

    @NotNull(message = "A categoria é obrigatória")
    private Long categoriaId;

    @NotNull(message = "O fornecedor é obrigatório")
    private Long fornecedorId;

    public String getNome() {
        return nome;
    }

    public Double getPreco() {
        return preco;
    }

    public Long getCategoriaId() {
        return categoriaId;
    }

    public Long getFornecedorId() {
        return fornecedorId;
    }
}

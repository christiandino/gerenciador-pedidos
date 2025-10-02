package br.ce.christian.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public class FornecedorRequestDTO {

    @NotBlank(message = "O nome do fornecedor é obrigatório")
    @Size(min = 2, max = 100, message = "O nome do fornecedor deve ter entre 2 e 100 caracteres")
    private String nome;

    private List<Long> produtosIds; // novos produtos a associar

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public List<Long> getProdutosIds() { return produtosIds; }
    public void setProdutosIds(List<Long> produtosIds) { this.produtosIds = produtosIds; }
}
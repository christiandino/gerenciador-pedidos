package br.ce.christian.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CategoriaRequestDTO {

    @NotBlank(message = "O nome da categoria é obrigatório")
    @Size(min= 2, max = 50, message = "O nome da categoria deve ter entre 2 e 50 caracteres")
    private String nome;

    public String getNome(){
        return nome;
    }
}

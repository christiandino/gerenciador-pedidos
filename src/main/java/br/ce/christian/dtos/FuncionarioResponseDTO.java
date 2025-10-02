package br.ce.christian.dtos;

import br.ce.christian.enums.AtividadeExercida;
import br.ce.christian.model.Funcionario;

import java.math.BigDecimal;
import java.time.LocalDate;

public class FuncionarioResponseDTO {

    private Long id;
    private String nome;
    private String cpf;
    private String email;
    private AtividadeExercida cargo;
    private BigDecimal salario;
    private LocalDate dataContratacao;
    private String endereco;

    public FuncionarioResponseDTO(Funcionario funcionario) {
        this.id = funcionario.getId();
        this.nome = funcionario.getNome();
        this.cpf = funcionario.getCpf();
        this.email = funcionario.getEmail();
        this.cargo = funcionario.getCargo();
        this.salario = funcionario.getSalario();
        this.dataContratacao = funcionario.getDataContratacao();
        this.endereco = funcionario.getEndereco();
    }

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public String getCpf() { return cpf; }
    public String getEmail() { return email; }
    public AtividadeExercida getCargo() { return cargo; }
    public BigDecimal getSalario() { return salario; }
    public LocalDate getDataContratacao() { return dataContratacao; }
    public String getEndereco() { return endereco; }
}

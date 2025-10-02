package br.ce.christian.dtos;

import br.ce.christian.model.Cliente;

import java.util.ArrayList;
import java.util.List;

public class ClienteResponseDTO {

    private Long id;
    private String nome;
    private String cpf;
    private String email;
    private String endereco;
    private List<PedidoResponseDTO> pedidos;

    public ClienteResponseDTO(Cliente cliente) {
        this.id = cliente.getId();
        this.nome = cliente.getNome();
        this.cpf = cliente.getCpf();
        this.email = cliente.getEmail();
        this.endereco = cliente.getEndereco();
        this.pedidos = new ArrayList<>();
    }


    public ClienteResponseDTO(Long id, String nome, String cpf, String email, String endereco, List<PedidoResponseDTO> pedidos) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.endereco = endereco;
        this.pedidos = pedidos;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getCpf() {
        return cpf;
    }

    public String getEndereco() {
        return endereco;
    }

    public List<PedidoResponseDTO> getPedidos() {
        return pedidos;
    }
}

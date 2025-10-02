package br.ce.christian.dtos;

import br.ce.christian.model.Pedido;
import br.ce.christian.model.Produto;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class PedidoResponseDTO {

    private Long id;
    private LocalDate data;
    private List<Long> produtosIds;
    private Long clienteId;
    private String status;

    public PedidoResponseDTO(Pedido pedido) {
        this.id = pedido.getId();
        this.data = pedido.getData();
        this.produtosIds = pedido.getProdutos().stream()
                .map(Produto::getId)
                .collect(Collectors.toList());
        this.clienteId = (pedido.getCliente() != null) ? pedido.getCliente().getId() : null;
        this.status = pedido.getStatus().name();
    }

    // Getters
    public Long getId() {
        return id;
    }

    public LocalDate getData() {
        return data;
    }

    public List<Long> getProdutosIds() {
        return produtosIds;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public String getStatus(){
        return status;
    }
}

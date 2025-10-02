package br.ce.christian.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

public class PedidoRequestDTO {

    @NotNull(message = "A data do pedido é obrigatória")
    private LocalDate data;

    @NotNull(message = "O pedido precisa ter ao menos um produto")
    @NotEmpty(message = "O pedido precisa ter ao menos um produto")
    private List<Long> produtosIds;

    @NotNull(message = "O cliente é obrigatório")
    private Long clienteId;


    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public List<Long> getProdutosIds() {
        return produtosIds;
    }

    public void setProdutosIds(List<Long> produtosIds) {
        this.produtosIds = produtosIds;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }
}

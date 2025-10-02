package br.ce.christian.exceptions;

public class PedidoNotFoundException extends RuntimeException {
    public PedidoNotFoundException(Long id) {
        super("Pedido não encontrado com ID: " + id);
    }
}

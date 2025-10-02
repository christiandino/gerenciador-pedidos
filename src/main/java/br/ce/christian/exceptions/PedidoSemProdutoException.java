package br.ce.christian.exceptions;

public class PedidoSemProdutoException extends RuntimeException {
    public PedidoSemProdutoException(){
        super("Um pedido deve conter ao menos um produto.");
    }
}

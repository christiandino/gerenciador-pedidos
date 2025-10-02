package br.ce.christian.exceptions;

public class ProdutoNotFoundException extends RuntimeException {
    public ProdutoNotFoundException(Long id) {
        super("Produto não encontrado com ID: " + id);
    }
}
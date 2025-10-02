package br.ce.christian.exceptions;


public class ProdutoNotFoundStringException extends RuntimeException {
    public ProdutoNotFoundStringException(String palavra) {
        super("Produto não encontrado com a palavra: " + palavra);
    }
}

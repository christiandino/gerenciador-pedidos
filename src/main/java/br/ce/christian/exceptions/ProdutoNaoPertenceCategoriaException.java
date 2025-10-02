package br.ce.christian.exceptions;

public class ProdutoNaoPertenceCategoriaException extends RuntimeException {
    public ProdutoNaoPertenceCategoriaException(Long produtoId, Long categoriaId) {
        super("O produto com ID " + produtoId + " não pertence à categoria com ID " + categoriaId);
    }
}

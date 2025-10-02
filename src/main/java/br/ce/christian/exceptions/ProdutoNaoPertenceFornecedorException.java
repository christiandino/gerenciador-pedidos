package br.ce.christian.exceptions;

public class ProdutoNaoPertenceFornecedorException extends RuntimeException {
    public ProdutoNaoPertenceFornecedorException(Long produtoId, Long fornecedorId) {
        super("Produto " + produtoId + " não pertence ao fornecedor " + fornecedorId);
    }
}
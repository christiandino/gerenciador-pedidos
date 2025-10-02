package br.ce.christian.exceptions;

public class ProdutoJaPertenceAOutroFornecedorException extends RuntimeException {
    public ProdutoJaPertenceAOutroFornecedorException(Long produtoId, String fornecedorNome) {
        super("Produto " + produtoId + " já pertence ao fornecedor: " + fornecedorNome);
    }
}

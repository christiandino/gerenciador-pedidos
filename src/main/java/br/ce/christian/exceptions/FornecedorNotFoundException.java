package br.ce.christian.exceptions;

public class FornecedorNotFoundException extends RuntimeException {
    public FornecedorNotFoundException(Long id) {
        super("Fornecedor não encontrado com ID: " + id);
    }
}

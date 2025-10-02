package br.ce.christian.exceptions;

public class FornecedorJaExistenteException extends RuntimeException {
    public FornecedorJaExistenteException(String nome) {
        super("Fornecedor já existente: " + nome);
    }
}
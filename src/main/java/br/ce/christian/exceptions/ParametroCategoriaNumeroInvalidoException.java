package br.ce.christian.exceptions;

public class ParametroCategoriaNumeroInvalidoException extends RuntimeException {
    public ParametroCategoriaNumeroInvalidoException(Long id) {
        super("O número deve ser maior que zero");
    }
}

package br.ce.christian.exceptions;

public class FuncionarioSemPermissaoException extends RuntimeException {
    public FuncionarioSemPermissaoException(String mensagem) {
        super(mensagem);
    }
}

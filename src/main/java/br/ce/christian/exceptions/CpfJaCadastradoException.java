package br.ce.christian.exceptions;

public class CpfJaCadastradoException extends RuntimeException {
    public CpfJaCadastradoException(String cpf) {
        super("CPF já cadastrado para outro cliente: " + cpf);
    }
}
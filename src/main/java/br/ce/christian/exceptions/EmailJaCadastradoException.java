package br.ce.christian.exceptions;

public class EmailJaCadastradoException extends RuntimeException {
    public EmailJaCadastradoException(String email){
        super("Email já cadastrado para outro usuario: " + email);
    }
}

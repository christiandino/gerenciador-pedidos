package br.ce.christian.exceptions;

public class ClienteNotFoundException extends RuntimeException{

    public ClienteNotFoundException(Long id){
        super("Cliente não encontrado com ID: " + id);
    }

}

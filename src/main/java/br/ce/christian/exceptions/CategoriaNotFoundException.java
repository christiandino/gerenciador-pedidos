package br.ce.christian.exceptions;

public class CategoriaNotFoundException extends RuntimeException{
    public CategoriaNotFoundException(Long id){
        super("Categoria não encontrada com ID: " + id);
    }
}

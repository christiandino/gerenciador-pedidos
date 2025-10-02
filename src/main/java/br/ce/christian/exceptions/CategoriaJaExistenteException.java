package br.ce.christian.exceptions;

public class CategoriaJaExistenteException extends RuntimeException {
    public CategoriaJaExistenteException(String nome) {
        super("Categoria " + nome + " já existente no sistema.");
    }
}

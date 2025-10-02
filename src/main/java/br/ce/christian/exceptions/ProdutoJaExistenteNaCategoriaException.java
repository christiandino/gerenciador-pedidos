package br.ce.christian.exceptions;

public class ProdutoJaExistenteNaCategoriaException extends RuntimeException{
    public ProdutoJaExistenteNaCategoriaException(String nomeProduto, String nomeCategoria) {
        super("Produto '" + nomeProduto + "' já existe na categoria '" + nomeCategoria + "'");
    }
}

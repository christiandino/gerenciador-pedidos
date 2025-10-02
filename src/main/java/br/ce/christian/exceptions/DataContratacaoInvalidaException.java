package br.ce.christian.exceptions;

import java.time.LocalDate;

public class DataContratacaoInvalidaException extends RuntimeException {
    public DataContratacaoInvalidaException(LocalDate data) {
        super("Data de contratação inválida: " + data + ". Não pode ser no futuro.");
    }
}


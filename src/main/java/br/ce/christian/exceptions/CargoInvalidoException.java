package br.ce.christian.exceptions;

public class CargoInvalidoException extends RuntimeException {
    public CargoInvalidoException(String cargo){
        super("Cargo inválido: " + cargo + ". Os cargos permitidos são: GERENTE ou VENDEDOR.");
    }
}

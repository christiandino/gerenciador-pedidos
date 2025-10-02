package br.ce.christian.utils;

public class CpfUtils {

    private CpfUtils(){}

    public static String normalizar(String cpf){
        return cpf != null ? cpf.replaceAll("[^0-9]", "") : null;
    }
}

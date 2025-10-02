package br.ce.christian.utils;

public class EmailUtils {

    private EmailUtils(){}

    public static String normalizar(String email){
        return email == null ? null: email.trim().toLowerCase();
    }
}

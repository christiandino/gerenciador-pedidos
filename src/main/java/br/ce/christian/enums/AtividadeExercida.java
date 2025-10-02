package br.ce.christian.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum AtividadeExercida {

    ADMIN(1L),
    GERENTE(2L),
    VENDEDOR(3L),
    CLIENTE(4L);

    long roleId;

    AtividadeExercida(long roleId) {this.roleId = roleId; }

    public long getRoleId(){
        return roleId;
    }

    @JsonCreator
    public static AtividadeExercida fromString(String value) {
        return AtividadeExercida.valueOf(value.toUpperCase());
    }
}

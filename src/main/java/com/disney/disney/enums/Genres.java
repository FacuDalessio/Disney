package com.disney.disney.enums;

public enum Genres {
    ACTION(1, "Accion"), BIOPIC(2, "Biografica"), COMEDY(3, "Comedia"), DRAMA(4, "Drama"),EDUCATION(5, "Educacion")
    ,FICTION(6, "Ficcion"), SCARY(7, "Terror"), THRILLER(8, "Suspenso"), MUSICAL(9, "Musical");
    
    private Integer code;
    private String valor;

    private Genres(Integer code, String valor) {
        this.code = code;
        this.valor = valor;
    }

    public Integer getCode() {
        return code;
    }

    public String getValor() {
        return valor;
    }
    
    
}

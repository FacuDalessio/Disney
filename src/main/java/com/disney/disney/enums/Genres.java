package com.disney.disney.enums;

public enum Genres {
    ACTION(1, "Action"), BIOPIC(2, "Biopic"), COMEDY(3, "Comedy"), DRAMA(4, "Drama"),EDUCATION(5, "Education")
    ,FICTION(6, "Fiction"), SCARY(7, "Scary"), THRILLER(8, "Thriller"), MUSICAL(9, "Musical");
    
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

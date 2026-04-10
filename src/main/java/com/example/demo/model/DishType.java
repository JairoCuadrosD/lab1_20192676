package com.example.demo.model;

public enum DishType {
    ENTRADA,
    PLATO_PRINCIPAL,
    BEBIDA;

    public String getEtiqueta() {
        return switch (this) {
            case ENTRADA -> "Entradas";
            case PLATO_PRINCIPAL -> "Platos principales";
            case BEBIDA -> "Bebidas";
        };
    }
}

package com.example.demo.model;

/**
 * Flujo: CREADO → EN_PREPARACION → LISTO → ENTREGADO
 */
public enum OrderStatus {
    CREADO,
    EN_PREPARACION,
    LISTO,
    ENTREGADO;

    public String getEtiqueta() {
        return switch (this) {
            case CREADO -> "Creado";
            case EN_PREPARACION -> "En preparación";
            case LISTO -> "Listo";
            case ENTREGADO -> "Entregado";
        };
    }
}

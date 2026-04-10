package com.example.demo.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.ArrayList;
import java.util.List;

/**
 * Objeto para data binding del formulario de nuevo pedido.
 */
public class PedidoForm {

    @NotBlank(message = "El nombre del cliente es obligatorio")
    private String nombreCliente;

    @NotBlank(message = "Debe elegir tipo de servicio")
    private String tipoServicio;

    @NotEmpty(message = "Debe elegir al menos un plato")
    private List<Long> platosIds = new ArrayList<>();

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(String tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    public List<Long> getPlatosIds() {
        return platosIds;
    }

    public void setPlatosIds(List<Long> platosIds) {
        this.platosIds = platosIds;
    }
}

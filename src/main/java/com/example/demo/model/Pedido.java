package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Pedido del restaurante (equivale a "Order" del enunciado).
 */
public class Pedido {

    private Long id;
    private String nombreCliente;
    /** PARA_LLEVAR o EN_MESA */
    private String tipoServicio;
    private List<Dish> platos = new ArrayList<>();
    private OrderStatus estado = OrderStatus.CREADO;
    private int tiempoTotalMinutos;

    public Pedido() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public List<Dish> getPlatos() {
        return platos;
    }

    public void setPlatos(List<Dish> platos) {
        this.platos = platos;
    }

    public OrderStatus getEstado() {
        return estado;
    }

    public void setEstado(OrderStatus estado) {
        this.estado = estado;
    }

    public int getTiempoTotalMinutos() {
        return tiempoTotalMinutos;
    }

    public void setTiempoTotalMinutos(int tiempoTotalMinutos) {
        this.tiempoTotalMinutos = tiempoTotalMinutos;
    }

    public double getPrecioTotal() {
        return platos.stream().mapToDouble(Dish::getPrecio).sum();
    }
}

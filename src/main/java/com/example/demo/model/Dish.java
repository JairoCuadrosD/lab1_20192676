package com.example.demo.model;

import java.util.Objects;

public class Dish {

    private Long id;
    private String nombre;
    private double precio;
    private int minutosPreparacion;
    private DishType tipo;

    public Dish() {
    }

    public Dish(Long id, String nombre, double precio, int minutosPreparacion, DishType tipo) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.minutosPreparacion = minutosPreparacion;
        this.tipo = tipo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getMinutosPreparacion() {
        return minutosPreparacion;
    }

    public void setMinutosPreparacion(int minutosPreparacion) {
        this.minutosPreparacion = minutosPreparacion;
    }

    public DishType getTipo() {
        return tipo;
    }

    public void setTipo(DishType tipo) {
        this.tipo = tipo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Dish dish = (Dish) o;
        return Objects.equals(id, dish.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}

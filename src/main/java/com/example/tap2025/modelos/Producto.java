package com.example.tap2025.modelos;

import java.util.Objects;

public class Producto {
    private String nombre;
    private double precio;
    private int cantidad;
    private String categoria;
    private String imagen;

    public Producto(String nombre, double precio, String categoria, String imagen) {
        this.nombre = nombre;
        this.precio = Math.max(0, precio);
        this.categoria = categoria;
        this.imagen = imagen;
        this.cantidad = 1;
    }

    public Producto(String nombre, double precio, int cantidad, String categoria, String imagen) {
        this.nombre = nombre;
        this.precio = Math.max(0, precio);
        this.cantidad = Math.max(1, cantidad);
        this.categoria = categoria;
        this.imagen = imagen;
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
        this.precio = Math.max(0, precio);
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = Math.max(1, cantidad);
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public double getTotal() {
        return precio * cantidad;
    }

    public void incrementarCant() {
        this.cantidad++;
    }

    @Override
    public String toString() {
        return nombre + " x" + cantidad + " = $" + getTotal();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Producto)) return false;
        Producto producto = (Producto) o;
        return Objects.equals(nombre, producto.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre);
    }
}


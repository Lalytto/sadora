package com.example.lalytto.sadora.Models;

/**
 * Created by Lalytto on 13/7/2016.
 */
public class Categorias {

    private int categoria_id;
    private String categoria_nombre;
    private String categoria_imagen;
    private String categoria_descripcion;

    public Categorias() {

    }

    public int getCategoria_id() {
        return categoria_id;
    }

    public void setCategoria_id(int categoria_id) {
        this.categoria_id = categoria_id;
    }

    public String getCategoria_nombre() {
        return categoria_nombre;
    }

    public void setCategoria_nombre(String categoria_nombre) {
        this.categoria_nombre = categoria_nombre;
    }

    public String getCategoria_imagen() {
        return categoria_imagen;
    }

    public void setCategoria_imagen(String categoria_imagen) {
        this.categoria_imagen = categoria_imagen;
    }

    public String getCategoria_descripcion() {
        return categoria_descripcion;
    }

    public void setCategoria_descripcion(String categoria_descripcion) {
        this.categoria_descripcion = categoria_descripcion;
    }
}

package com.example.dss.object;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.List;
import java.util.Objects;

public class Carrito {

    private String id;
    private Usuario usuario;
    private List<Camiseta> camisetas;

    public Carrito() {
    }

    public Carrito(String id, Usuario usuario, List<Camiseta> camisetas) {
        this.id = id;
        this.usuario = usuario;
        this.camisetas = camisetas;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Camiseta> getCamisetas() {
        return camisetas;
    }

    public void setCamisetas(List<Camiseta> camisetas) {
        this.camisetas = camisetas;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Carrito carrito = (Carrito) o;
        return Objects.equals(id, carrito.id) &&
                Objects.equals(usuario, carrito.usuario) &&
                Objects.equals(camisetas, carrito.camisetas);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(id, usuario, camisetas);
    }

    @Override
    public String toString() {
        return "Carrito{" +
                "id='" + id + '\'' +
                ", usuario=" + usuario +
                ", camisetas=" + camisetas +
                '}';
    }
}

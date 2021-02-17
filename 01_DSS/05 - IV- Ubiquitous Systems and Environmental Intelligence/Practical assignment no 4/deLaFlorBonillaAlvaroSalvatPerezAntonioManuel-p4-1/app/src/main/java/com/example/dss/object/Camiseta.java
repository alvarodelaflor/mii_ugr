package com.example.dss.object;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.Serializable;
import java.util.Objects;

public class Camiseta implements Serializable {
    private String id;
    private String modelo;
    private String talla;
    private Double precio;
    private Boolean vendida;
    private String image;
    private String imageShop;
    private String location;

    public Camiseta(String id, String modelo, String talla, Double precio, Boolean vendida, String image, String imageShop, String location) {
        this.id = id;
        this.modelo = modelo;
        this.talla = talla;
        this.precio = precio;
        this.vendida = vendida;
        this.image = image;
        this.imageShop = imageShop;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getTalla() {
        return talla;
    }

    public void setTalla(String talla) {
        this.talla = talla;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Boolean getVendida() {
        return vendida;
    }

    public void setVendida(Boolean vendida) {
        this.vendida = vendida;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImageShop() {
        return imageShop;
    }

    public void setImageShop(String imageShop) {
        this.imageShop = imageShop;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Camiseta camiseta = (Camiseta) o;
        return Objects.equals(id, camiseta.id) &&
                Objects.equals(modelo, camiseta.modelo) &&
                Objects.equals(talla, camiseta.talla) &&
                Objects.equals(precio, camiseta.precio) &&
                Objects.equals(vendida, camiseta.vendida) &&
                Objects.equals(image, camiseta.image) &&
                Objects.equals(imageShop, camiseta.imageShop) &&
                Objects.equals(location, camiseta.location);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(id, modelo, talla, precio, vendida, image, imageShop, location);
    }

    @Override
    public String toString() {
        return "Camiseta{" +
                "id='" + id + '\'' +
                ", modelo='" + modelo + '\'' +
                ", talla='" + talla + '\'' +
                ", precio=" + precio +
                ", vendida=" + vendida +
                ", image='" + image + '\'' +
                ", imageShop='" + imageShop + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}

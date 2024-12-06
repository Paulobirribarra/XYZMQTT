package com.example.xyz;

public class DataClase {
    private String key;
    private String Nombre;
    private String imgUrl;
    private String Tipo;
    private String Ubicación;

    public DataClase() {
    }

    public DataClase(String key, String nombre, String imgUrl, String tipo, String ubicación) {
        this.key = key;
        Nombre = nombre;
        this.imgUrl = imgUrl;
        Tipo = tipo;
        Ubicación = ubicación;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String tipo) {
        Tipo = tipo;
    }

    public String getUbicación() {
        return Ubicación;
    }

    public void setUbicación(String ubicación) {
        Ubicación = ubicación;
    }
}

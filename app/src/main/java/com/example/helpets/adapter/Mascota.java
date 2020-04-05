package com.example.helpets.adapter;

public class Mascota {

    private String nombre;
    private String edad;
    private String vacunas;
    private String idMascota;
    private String imagenMascota;

    public Mascota(String nombre, String edad, String vacunas, String idMascota, String imagenMascota) {
        this.nombre = nombre;
        this.edad = edad;
        this.vacunas = vacunas;
        this.idMascota = idMascota;
        this.imagenMascota = imagenMascota;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getVacunas() {
        return vacunas;
    }

    public void setVacunas(String vacunas) {
        this.vacunas = vacunas;
    }

    public String getIdMascota() {
        return idMascota;
    }

    public void setIdMascota(String idMascota) {
        this.idMascota = idMascota;
    }

    public String getImagenMascota() {
        return imagenMascota;
    }

    public void setImagenMascota(String imagenMascota) {
        this.imagenMascota = imagenMascota;
    }
}

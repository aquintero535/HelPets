package com.example.helpets.adapter;

public class Veterinario {

    private String nombreVeterinario;
    private String clientes;
    private String fotoPerfil;
    private String idVeterinario;

    public Veterinario(String nombreVeterinario, String clientes, String fotoPerfil, String idVeterinario) {
        this.nombreVeterinario = nombreVeterinario;
        this.clientes = clientes;
        this.fotoPerfil = fotoPerfil;
        this.idVeterinario = idVeterinario;
    }

    public String getNombreVeterinario() {
        return nombreVeterinario;
    }

    public void setNombreVeterinario(String nombreVeterinario) {
        this.nombreVeterinario = nombreVeterinario;
    }

    public String getClientes() {
        return clientes;
    }

    public void setClientes(String clientes) {
        this.clientes = clientes;
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public String getIdVeterinario() {
        return idVeterinario;
    }

    public void setIdVeterinario(String idVeterinario) {
        this.idVeterinario = idVeterinario;
    }
}

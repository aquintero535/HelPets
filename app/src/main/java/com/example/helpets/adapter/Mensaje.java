package com.example.helpets.adapter;

public class Mensaje {

    private String nombreUsuario;
    private String mensaje;
    private String fotoPerfil;
    private String tipoMensaje;
    private String hora;

    public Mensaje(String nombreUsuario, String mensaje, String fotoPerfil, String tipoMensaje, String hora) {
        this.nombreUsuario = nombreUsuario;
        this.mensaje = mensaje;
        this.fotoPerfil = fotoPerfil;
        this.tipoMensaje = tipoMensaje;
        this.hora = hora;
    }

    public Mensaje() {
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public String getTipoMensaje() {
        return tipoMensaje;
    }

    public void setTipoMensaje(String tipoMensaje) {
        this.tipoMensaje = tipoMensaje;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }
}

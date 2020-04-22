package com.example.helpets;

public interface Callback {

    /* Interface para un método asíncrono */
    void datosObtenidos();
    void datosNoObtenidos(Exception e);
}

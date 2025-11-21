package com.mycompany.proyecto;
import modelo.UsuarioData;
import vista.InicioSesion;
import controlador.InicioControlador;

public class Staykonnect {
    public static void main(String[] args) {
        UsuarioData dao = new UsuarioData();
        InicioSesion vista = new InicioSesion();
        new InicioControlador(vista, dao);
        vista.setVisible(true);
    }
}
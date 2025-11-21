package com.mycompany.proyecto;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import vista.InicioSesion;

/**
 * Clase principal de la aplicación StayKonnect
 * Inicia la interfaz gráfica de inicio de sesión
 */
public class Staykonnect {

    public static void main(String[] args) {
        // Configurar look and feel del sistema para mejor apariencia
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("No se pudo configurar el Look and Feel: " + e.getMessage());
        }

        // Iniciar la aplicación en el hilo de eventos de Swing
        SwingUtilities.invokeLater(() -> {
            InicioSesion login = new InicioSesion();
            login.setVisible(true);
        });
    }
}
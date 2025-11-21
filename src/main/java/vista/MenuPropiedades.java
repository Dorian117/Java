package vista;

import javax.swing.*;
import java.awt.*;
import modelo.Usuario;
import modelo.UsuarioData;

/**
 * Menú de gestión de propiedades
 * Redirige según el rol del usuario
 */
public class MenuPropiedades extends JFrame {

    private Usuario usuarioActual;

    public MenuPropiedades() {
        super("Gestión de Propiedades - StayKonnect");
        this.usuarioActual = UsuarioData.getUsuarioActual();
        initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(450, 350);
        setLayout(null);
        setLocationRelativeTo(null);

        // Título
        JLabel lblTitulo = new JLabel("Gestión de Propiedades");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setBounds(120, 20, 250, 30);
        add(lblTitulo);

        // Información del usuario
        String nombreUsuario = usuarioActual != null ? usuarioActual.getNombre() : "Usuario";
        String rol = usuarioActual != null ? usuarioActual.getRol() : "";
        JLabel lblUsuario = new JLabel("Usuario: " + nombreUsuario + " (" + rol + ")");
        lblUsuario.setBounds(50, 60, 350, 20);
        add(lblUsuario);

        // Botón: Ver Propiedades Disponibles
        JButton btnBuscar = new JButton("Ver Propiedades Disponibles");
        btnBuscar.setBounds(100, 100, 250, 40);
        btnBuscar.setBackground(new Color(33, 150, 243));
        btnBuscar.setForeground(Color.WHITE);
        btnBuscar.setFont(new Font("Arial", Font.BOLD, 12));
        btnBuscar.addActionListener(e -> abrirBusqueda());
        add(btnBuscar);

        // Botón: Gestionar Mis Propiedades (solo para anfitriones)
        if (usuarioActual != null && usuarioActual.esAnfitrion()) {
            JButton btnMisPropiedades = new JButton("Gestionar Mis Propiedades");
            btnMisPropiedades.setBounds(100, 160, 250, 40);
            btnMisPropiedades.setBackground(new Color(76, 175, 80));
            btnMisPropiedades.setForeground(Color.WHITE);
            btnMisPropiedades.setFont(new Font("Arial", Font.BOLD, 12));
            btnMisPropiedades.addActionListener(e -> abrirMenuAnfitrion());
            add(btnMisPropiedades);
        }

        // Botón: Volver
        JButton btnVolver = new JButton("Volver al Menú Principal");
        btnVolver.setBounds(100, 220, 250, 40);
        btnVolver.addActionListener(e -> volver());
        add(btnVolver);
    }

    /**
     * Abre la ventana de búsqueda de propiedades
     */
    private void abrirBusqueda() {
        BusquedaPropiedades busqueda = new BusquedaPropiedades();
        busqueda.setVisible(true);
        this.dispose();
    }

    /**
     * Abre el menú de anfitrión
     */
    private void abrirMenuAnfitrion() {
        MenuAnfitrion menu = new MenuAnfitrion();
        menu.setVisible(true);
        this.dispose();
    }

    /**
     * Vuelve al menú principal
     */
    private void volver() {
        MenuPrincipal menu = new MenuPrincipal();
        menu.setVisible(true);
        this.dispose();
    }
}

package vista;

import javax.swing.*;

public class MenuAnfitrion extends JFrame {

    public MenuAnfitrion() {
        super("Menú Anfitrión - StayKonnect");
        initComponents();
    }

    private void initComponents() {
        setSize(450, 350);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel lblTitulo = new JLabel("Panel de Anfitrión");
        lblTitulo.setBounds(160, 30, 300, 30);
        add(lblTitulo);

        JButton btnPropiedades = new JButton("Gestionar Propiedades");
        btnPropiedades.setBounds(130, 120, 200, 35);
        add(btnPropiedades);

        JButton btnReservas = new JButton("Ver Reservas");
        btnReservas.setBounds(130, 170, 200, 35);
        add(btnReservas);

        JButton btnSalir = new JButton("Salir");
        btnSalir.setBounds(130, 220, 200, 35);
        btnSalir.addActionListener(e -> dispose());
        add(btnSalir);
    }
}

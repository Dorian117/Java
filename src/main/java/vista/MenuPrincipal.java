package vista;

import javax.swing.*;
import java.awt.*;

public class MenuPrincipal extends JFrame {

    private JButton btnGestionPropiedades;

    public MenuPrincipal() {
        super("Menú principal");
        initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLayout(null);
        setLocationRelativeTo(null);

        JLabel lbl = new JLabel("Bienvenido a StayKonnect", SwingConstants.CENTER);
        lbl.setFont(new Font("Arial", Font.BOLD, 18));
        lbl.setBounds(50, 30, 300, 40);
        add(lbl);

        btnGestionPropiedades = new JButton("Gestión de Propiedades");
        btnGestionPropiedades.setBounds(125, 120, 150, 35);
        btnGestionPropiedades.setActionCommand("GESTION_PROPIEDADES");

        btnGestionPropiedades.setBackground(Color.BLACK);
        btnGestionPropiedades.setForeground(Color.WHITE);

        btnGestionPropiedades.setFocusPainted(false);

        add(btnGestionPropiedades);
    }

    public void addGestionPropiedadesListener(java.awt.event.ActionListener l) {
        btnGestionPropiedades.addActionListener(l);
    }
}

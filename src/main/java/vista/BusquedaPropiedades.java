package vista;

import javax.swing.*;

public class BusquedaPropiedades extends JFrame {

    public BusquedaPropiedades() {
        super("Búsqueda de Propiedades - StayKonnect");
        initComponents();
    }

    private void initComponents() {
        setSize(500, 400);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel lblTitulo = new JLabel("Búsqueda de Propiedades");
        lblTitulo.setBounds(150, 30, 300, 30);
        add(lblTitulo);

        JLabel lblInfo = new JLabel("Aquí aparecerá la lista de alojamientos.");
        lblInfo.setBounds(120, 120, 300, 30);
        add(lblInfo);
    }
}

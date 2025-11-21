package vista;

import javax.swing.*;

public class MenuPropiedades extends JFrame {

    public MenuPropiedades() {
        super("Gestión de Propiedades");
        initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLayout(null);

        JLabel lbl = new JLabel("Menú de Propiedades");
        lbl.setBounds(120, 30, 200, 25);
        add(lbl);

        JButton btnRegistrar = new JButton("Registrar Propiedad");
        btnRegistrar.setBounds(100, 90, 200, 40);
        add(btnRegistrar);

        JButton btnBuscar = new JButton("Buscar Propiedad");
        btnBuscar.setBounds(100, 150, 200, 40);
        add(btnBuscar);
    }
}

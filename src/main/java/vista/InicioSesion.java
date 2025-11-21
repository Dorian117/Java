package vista;

import java.awt.event.ActionListener;
import javax.swing.*;

public class InicioSesion extends JFrame {
    
    private JTextField txtCorreo;
    private JPasswordField txtContrasena;
    private JButton btnIngresar;
    private JButton btnRegistrar;

    public InicioSesion() {
        super("Inicio de sesión");
        initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(350, 200);
        setLayout(null);

        JLabel lblCorreo = new JLabel("Correo:");
        lblCorreo.setBounds(20, 20, 80, 25);
        add(lblCorreo);

        txtCorreo = new JTextField();
        txtCorreo.setBounds(110, 20, 200, 25);
        add(txtCorreo);

        JLabel lblContrasena = new JLabel("Contraseña:");
        lblContrasena.setBounds(20, 60, 80, 25);
        add(lblContrasena);

        txtContrasena = new JPasswordField();
        txtContrasena.setBounds(110, 60, 200, 25);
        add(txtContrasena);

        btnIngresar = new JButton("Ingresar");
        btnIngresar.setActionCommand("INGRESAR");
        btnIngresar.setBounds(50, 110, 100, 30);
        add(btnIngresar);

        btnRegistrar = new JButton("Registrar");
        btnRegistrar.setActionCommand("REGISTRAR");
        btnRegistrar.setBounds(180, 110, 100, 30);
        add(btnRegistrar);
    }

    public void addIngresarListener(ActionListener l) { btnIngresar.addActionListener(l); }
    public void addRegistrarListener(ActionListener l) { btnRegistrar.addActionListener(l); }

    public String getCorreo() { return txtCorreo.getText().trim(); }
    public String getContrasena() { return new String(txtContrasena.getPassword()); }
}

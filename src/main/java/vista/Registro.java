package vista;

import javax.swing.*;
import modelo.Usuario;
import modelo.UsuarioData;
import controlador.InicioControlador;

public class Registro extends JFrame {

    private JTextField txtNombre;
    private JTextField txtCorreo;
    private JPasswordField txtContrasena;
    private JComboBox<String> cmbRol;
    private JButton btnRegistrar;
    private JButton btnVolver;

    private UsuarioData dao;

    public Registro(UsuarioData dao) {
        super("Registro");
        this.dao = dao;
        initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(360, 260);
        setLayout(null);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(20, 20, 80, 25);
        add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(110, 20, 200, 25);
        add(txtNombre);

        JLabel lblCorreo = new JLabel("Correo:");
        lblCorreo.setBounds(20, 60, 80, 25);
        add(lblCorreo);

        txtCorreo = new JTextField();
        txtCorreo.setBounds(110, 60, 200, 25);
        add(txtCorreo);

        JLabel lblContrasena = new JLabel("Contraseña:");
        lblContrasena.setBounds(20, 100, 80, 25);
        add(lblContrasena);

        txtContrasena = new JPasswordField();
        txtContrasena.setBounds(110, 100, 200, 25);
        add(txtContrasena);

        JLabel lblRol = new JLabel("Rol:");
        lblRol.setBounds(20, 140, 80, 25);
        add(lblRol);

        cmbRol = new JComboBox<>(new String[]{"Viajero", "Anfitrion"});
        cmbRol.setBounds(110, 140, 200, 25);
        add(cmbRol);

        btnRegistrar = new JButton("Registrar");
        btnRegistrar.setBounds(60, 190, 100, 30);
        add(btnRegistrar);

        btnVolver = new JButton("Volver");
        btnVolver.setBounds(200, 190, 100, 30);
        add(btnVolver);

        btnRegistrar.addActionListener(e -> {
            String nombre = txtNombre.getText().trim();
            String correo = txtCorreo.getText().trim();
            String contrasena = new String(txtContrasena.getPassword());
            String rol = (String) cmbRol.getSelectedItem();

            if (nombre.isEmpty() || correo.isEmpty() || contrasena.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Complete todos los campos");
                return;
            }

            Usuario u = new Usuario(nombre, correo, contrasena, rol);
            dao.registrarUsuario(u);
            JOptionPane.showMessageDialog(this, "Usuario registrado");

            InicioSesion inicio = new InicioSesion();
            new InicioControlador(inicio, dao);
            inicio.setVisible(true);

            dispose();
        });

        btnVolver.addActionListener(e -> {
            InicioSesion inicio = new InicioSesion();
            new InicioControlador(inicio, dao);
            inicio.setVisible(true);
            dispose();
        });
    }
}

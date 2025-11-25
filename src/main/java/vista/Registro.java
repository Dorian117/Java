package vista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import modelo.Usuario;
import modelo.UsuarioData;

public class Registro extends JFrame {
    private JTextField txtNombre;
    private JTextField txtCorreo;
    private JTextField txtTelefono;  // Nuevo campo para teléfono
    private JPasswordField txtContrasena;
    private JPasswordField txtConfirmarContrasena;  // Nuevo campo para confirmar contraseña
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
        setSize(360, 320);  // Aumentamos el tamaño para los nuevos campos
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

        JLabel lblTelefono = new JLabel("Teléfono:");
        lblTelefono.setBounds(20, 100, 80, 25);
        add(lblTelefono);
        txtTelefono = new JTextField();
        txtTelefono.setBounds(110, 100, 200, 25);
        add(txtTelefono);

        JLabel lblContrasena = new JLabel("Contraseña:");
        lblContrasena.setBounds(20, 140, 80, 25);
        add(lblContrasena);
        txtContrasena = new JPasswordField();
        txtContrasena.setBounds(110, 140, 200, 25);
        add(txtContrasena);

        JLabel lblConfirmarContrasena = new JLabel("Confirmar:");
        lblConfirmarContrasena.setBounds(20, 180, 80, 25);
        add(lblConfirmarContrasena);
        txtConfirmarContrasena = new JPasswordField();
        txtConfirmarContrasena.setBounds(110, 180, 200, 25);
        add(txtConfirmarContrasena);

        JLabel lblRol = new JLabel("Rol:");
        lblRol.setBounds(20, 220, 80, 25);
        add(lblRol);
        cmbRol = new JComboBox<>(new String[] { "Viajero", "Anfitrion" });
        cmbRol.setBounds(110, 220, 200, 25);
        add(cmbRol);

        btnRegistrar = new JButton("Registrar");
        btnRegistrar.setBounds(60, 260, 100, 30);
        add(btnRegistrar);

        btnVolver = new JButton("Volver");
        btnVolver.setBounds(200, 260, 100, 30);
        add(btnVolver);

        btnRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = txtNombre.getText().trim();
                String correo = txtCorreo.getText().trim();
                String telefono = txtTelefono.getText().trim();  // Obtener teléfono
                String contrasena = new String(txtContrasena.getPassword());
                String confirmarContrasena = new String(txtConfirmarContrasena.getPassword());  // Obtener confirmación de contraseña
                String rol = (String) cmbRol.getSelectedItem();

                // Validación de campos vacíos
                if (nombre.isEmpty() || correo.isEmpty() || telefono.isEmpty() || contrasena.isEmpty() || confirmarContrasena.isEmpty()) {
                    JOptionPane.showMessageDialog(Registro.this, "Complete todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Validar que las contraseñas coincidan
                if (!contrasena.equals(confirmarContrasena)) {
                    JOptionPane.showMessageDialog(Registro.this, "Las contraseñas no coinciden", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Verificar si el correo ya está registrado
                if (dao.existeEmail(correo)) {
                    JOptionPane.showMessageDialog(Registro.this, "El correo ya está registrado", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Crear usuario
                Usuario u = new Usuario(nombre, correo, telefono, contrasena, rol);

                // Registrar usuario
                boolean exito = dao.registrarUsuario(u);
                if (exito) {
                    JOptionPane.showMessageDialog(Registro.this, "Usuario registrado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    // Redirigir al inicio de sesión
                    InicioSesion inicio = new InicioSesion(dao);
                    inicio.setLocationRelativeTo(Registro.this);
                    inicio.setVisible(true);
                    Registro.this.dispose();
                } else {
                    JOptionPane.showMessageDialog(Registro.this, "Error al registrar usuario", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnVolver.addActionListener(e -> {
            // Volver a la ventana de inicio de sesión
            InicioSesion inicio = new InicioSesion(dao);
            inicio.setLocationRelativeTo(Registro.this);
            inicio.setVisible(true);
            Registro.this.dispose();
        });
    }
}

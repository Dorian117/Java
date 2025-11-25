package vista;

import javax.swing.*;
import java.awt.event.ActionListener;

import modelo.Usuario;
import modelo.UsuarioData;
import controlador.ControladorLogin;
import controlador.ControladorLogin.ResultadoLogin;

/**
 * Ventana de inicio de sesión
 * Permite autenticar usuarios y redirigir según su rol
 */
public class InicioSesion extends JFrame {

    // ========== COMPONENTES DE LA INTERFAZ ==========
    private JTextField txtCorreo;
    private JPasswordField txtContrasena;
    private JButton btnIngresar;
    private JButton btnRegistrar;
    private JLabel lblMensaje;

    // ========== CONTROLADOR Y DAO ==========
    private ControladorLogin controlador;
    private UsuarioData dao;

    // ========== CONSTRUCTOR ==========

    /**
     * Constructor por defecto (crea nuevo DAO)
     */
    public InicioSesion() {
        this(new UsuarioData());
    }

    /**
     * Constructor con DAO existente
     * @param dao Objeto DAO para gestión de usuarios
     */
    public InicioSesion(UsuarioData dao) {
        super("Inicio de Sesión - StayKonnect");
        this.dao = dao;
        this.controlador = new ControladorLogin(dao);
        initComponents();
    }

    // ========== INICIALIZACIÓN DE COMPONENTES ==========

    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 350);
        setLayout(null);
        setLocationRelativeTo(null);
        setResizable(false);

        // ===== TÍTULO =====
        JLabel lblTitulo = new JLabel("StayKonnect");
        lblTitulo.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 24));
        lblTitulo.setBounds(125, 20, 200, 35);
        add(lblTitulo);

        JLabel lblSubtitulo = new JLabel("Plataforma de Alojamiento");
        lblSubtitulo.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 12));
        lblSubtitulo.setBounds(120, 55, 200, 20);
        add(lblSubtitulo);

        // ===== CAMPO: CORREO =====
        JLabel lblCorreo = new JLabel("Correo electrónico:");
        lblCorreo.setBounds(50, 100, 130, 25);
        add(lblCorreo);

        txtCorreo = new JTextField();
        txtCorreo.setBounds(50, 125, 300, 30);
        txtCorreo.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 14));
        add(txtCorreo);

        // ===== CAMPO: CONTRASEÑA =====
        JLabel lblContrasena = new JLabel("Contraseña:");
        lblContrasena.setBounds(50, 170, 130, 25);
        add(lblContrasena);

        txtContrasena = new JPasswordField();
        txtContrasena.setBounds(50, 195, 300, 30);
        txtContrasena.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 14));
        add(txtContrasena);

        // ===== MENSAJE =====
        lblMensaje = new JLabel("");
        lblMensaje.setBounds(50, 230, 300, 20);
        lblMensaje.setFont(new java.awt.Font("Arial", java.awt.Font.ITALIC, 11));
        lblMensaje.setForeground(java.awt.Color.BLUE);
        add(lblMensaje);

        // ===== BOTÓN INGRESAR =====
        btnIngresar = new JButton("Ingresar");
        btnIngresar.setBounds(50, 260, 140, 35);
        btnIngresar.setBackground(new java.awt.Color(33, 150, 243));
        btnIngresar.setForeground(java.awt.Color.WHITE);
        btnIngresar.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 14));
        btnIngresar.setFocusPainted(false);
        btnIngresar.addActionListener(e -> iniciarSesion());
        add(btnIngresar);

        // ===== BOTÓN REGISTRARSE =====
        btnRegistrar = new JButton("Registrarse");
        btnRegistrar.setBounds(210, 260, 140, 35);
        btnRegistrar.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 14));
        btnRegistrar.setFocusPainted(false);
        btnRegistrar.addActionListener(e -> abrirRegistro());
        add(btnRegistrar);

        getRootPane().setDefaultButton(btnIngresar);

        mostrarInfoPrueba();
    }

    // ========== GETTERS QUE EL CONTROLADOR NECESITA ==========

    public String getCorreo() {
        return txtCorreo.getText().trim();
    }

    public String getContrasena() {
        return new String(txtContrasena.getPassword());
    }

    public void addIngresarListener(ActionListener l) {
        btnIngresar.addActionListener(l);
    }

    public void addRegistrarListener(ActionListener l) {
        btnRegistrar.addActionListener(l);
    }

    // ========== MÉTODOS DE ACCIÓN ==========

    private void iniciarSesion() {
        String correo = getCorreo();
        String contrasena = getContrasena();

        btnIngresar.setEnabled(false);
        btnIngresar.setText("Ingresando...");
        lblMensaje.setText("");

        ResultadoLogin resultado = controlador.autenticar(correo, contrasena);

        btnIngresar.setEnabled(true);
        btnIngresar.setText("Ingresar");

        if (resultado.isExitoso()) {
            Usuario usuario = resultado.getUsuario();

            JOptionPane.showMessageDialog(
                this,
                resultado.getMensaje(),
                "Bienvenido",
                JOptionPane.INFORMATION_MESSAGE
            );

            txtContrasena.setText("");

            if (usuario.esViajero()) {
                BusquedaPropiedades busqueda = new BusquedaPropiedades();
                busqueda.setVisible(true);
                this.dispose();

            } else if (usuario.esAnfitrion()) {
                MenuAnfitrion menu = new MenuAnfitrion();
                menu.setVisible(true);
                this.dispose();

            } else {
                MenuPrincipal menu = new MenuPrincipal();
                menu.setVisible(true);
                this.dispose();
            }
        } else {
            JOptionPane.showMessageDialog(
                this,
                resultado.getMensaje(),
                "Error de Autenticación",
                JOptionPane.ERROR_MESSAGE
            );
            txtContrasena.setText("");
            txtContrasena.requestFocus();
        }
    }

    private void abrirRegistro() {
        Registro registro = new Registro(dao);
        registro.setVisible(true);
        this.dispose();
    }

    private void mostrarInfoPrueba() {
        lblMensaje.setText("Prueba: admin@admin.com / 1234");
    }

    // ========== MAIN ==========

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> {
            InicioSesion login = new InicioSesion();
            login.setVisible(true);
        });
    }
}

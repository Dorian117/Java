package vista;

import javax.swing.*;
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
    
    /**
     * Configura todos los componentes de la interfaz
     */
    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 350);
        setLayout(null);
        setLocationRelativeTo(null); // Centrar en pantalla
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
        
        // ===== MENSAJE DE INFORMACIÓN =====
        lblMensaje = new JLabel("");
        lblMensaje.setBounds(50, 230, 300, 20);
        lblMensaje.setFont(new java.awt.Font("Arial", java.awt.Font.ITALIC, 11));
        lblMensaje.setForeground(java.awt.Color.BLUE);
        add(lblMensaje);
        
        // ===== BOTÓN: INGRESAR =====
        btnIngresar = new JButton("Ingresar");
        btnIngresar.setBounds(50, 260, 140, 35);
        btnIngresar.setBackground(new java.awt.Color(33, 150, 243));
        btnIngresar.setForeground(java.awt.Color.WHITE);
        btnIngresar.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 14));
        btnIngresar.setFocusPainted(false);
        btnIngresar.addActionListener(e -> iniciarSesion());
        add(btnIngresar);
        
        // ===== BOTÓN: REGISTRAR =====
        btnRegistrar = new JButton("Registrarse");
        btnRegistrar.setBounds(210, 260, 140, 35);
        btnRegistrar.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 14));
        btnRegistrar.setFocusPainted(false);
        btnRegistrar.addActionListener(e -> abrirRegistro());
        add(btnRegistrar);
        
        // ===== TECLA ENTER PARA INGRESAR =====
        getRootPane().setDefaultButton(btnIngresar);
        
        // ===== MOSTRAR USUARIOS DE PRUEBA =====
        mostrarInfoPrueba();
    }
    
    // ========== MÉTODOS DE ACCIÓN ==========
    
    /**
     * Maneja el evento de inicio de sesión
     * Captura datos, llama al controlador y redirige según rol
     */
    private void iniciarSesion() {
        // Capturar datos
        String correo = txtCorreo.getText().trim();
        String contrasena = new String(txtContrasena.getPassword());
        
        // Deshabilitar botón mientras procesa
        btnIngresar.setEnabled(false);
        btnIngresar.setText("Ingresando...");
        lblMensaje.setText("");
        
        // Llamar al controlador para autenticar
        ResultadoLogin resultado = controlador.autenticar(correo, contrasena);
        
        // Habilitar botón nuevamente
        btnIngresar.setEnabled(true);
        btnIngresar.setText("Ingresar");
        
        // Procesar resultado
        if (resultado.isExitoso()) {
            // ÉXITO: Obtener usuario y redirigir según rol
            Usuario usuario = resultado.getUsuario();
            
            JOptionPane.showMessageDialog(
                this,
                resultado.getMensaje(),
                "Bienvenido",
                JOptionPane.INFORMATION_MESSAGE
            );
            
            // Limpiar campos
            txtContrasena.setText("");
            
            // Redirigir según rol
            if (usuario.esViajero()) {
                // VIAJERO: Ir a búsqueda de propiedades
                BusquedaPropiedades busqueda = new BusquedaPropiedades();
                busqueda.setVisible(true);
                this.dispose();
                
            } else if (usuario.esAnfitrion()) {
                // ANFITRIÓN: Ir a menú de anfitrión
                MenuAnfitrion menuAnfitrion = new MenuAnfitrion();
                menuAnfitrion.setVisible(true);
                this.dispose();
                
            } else {
                // ADMIN u otro: Ir a menú principal genérico
                MenuPrincipal menu = new MenuPrincipal();
                menu.setVisible(true);
                this.dispose();
            }
            
        } else {
            // ERROR: Mostrar mensaje
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
    
    /**
     * Abre la ventana de registro
     */
    private void abrirRegistro() {
        Registro registro = new Registro(dao);
        registro.setVisible(true);
        this.dispose();
    }
    
    /**
     * Muestra información de usuarios de prueba
     */
    private void mostrarInfoPrueba() {
        lblMensaje.setText("Prueba: admin@admin.com / 1234");
    }
    
    // ========== MÉTODO MAIN PARA EJECUTAR ==========
    
    /**
     * Punto de entrada de la aplicación
     */
    public static void main(String[] args) {
        // Configurar look and feel del sistema
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Crear y mostrar ventana
        SwingUtilities.invokeLater(() -> {
            InicioSesion login = new InicioSesion();
            login.setVisible(true);
        });
    }
}
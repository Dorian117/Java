package vista;

import javax.swing.*;
import modelo.UsuarioData;
import controlador.ControladorRegistro;
import controlador.ControladorRegistro.ResultadoRegistro;

/**
 * Ventana de registro de nuevos usuarios
 * Permite registrar Viajeros y Anfitriones
 */
public class Registro extends JFrame {
    
    //    COMPONENTES DE LA INTERFAZ   
    private JTextField txtNombre;
    private JTextField txtCorreo;
    private JTextField txtTelefono;
    private JPasswordField txtContrasena;
    private JPasswordField txtConfirmacion;
    private JComboBox<String> cmbRol;
    private JButton btnRegistrar;
    private JButton btnVolver;
    
    //    CONTROLADOR Y DAO   
    private ControladorRegistro controlador;
    private UsuarioData dao;
    
    //    CONSTRUCTOR   
    
    /**
     * Constructor
     * @param dao Objeto DAO para gestión de usuarios
     */
    public Registro(UsuarioData dao) {
        super("Registro - StayKonnect");
        this.dao = dao;
        this.controlador = new ControladorRegistro(dao);
        initComponents();
    }
    
    //    INICIALIZACIÓN DE COMPONENTES   
    
    /**
     * Configura todos los componentes de la interfaz
     */
    private void initComponents() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 450);
        setLayout(null);
        setLocationRelativeTo(null); // Centrar en pantalla
        setResizable(false);
        
        //   TÍTULO  
        JLabel lblTitulo = new JLabel("Registro de Usuario");
        lblTitulo.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 18));
        lblTitulo.setBounds(110, 10, 200, 30);
        add(lblTitulo);
        
        //   CAMPO: NOMBRE  
        JLabel lblNombre = new JLabel("Nombre completo:");
        lblNombre.setBounds(30, 60, 120, 25);
        add(lblNombre);
        
        txtNombre = new JTextField();
        txtNombre.setBounds(160, 60, 200, 25);
        add(txtNombre);
        
        //   CAMPO: CORREO  
        JLabel lblCorreo = new JLabel("Correo electrónico:");
        lblCorreo.setBounds(30, 100, 120, 25);
        add(lblCorreo);
        
        txtCorreo = new JTextField();
        txtCorreo.setBounds(160, 100, 200, 25);
        add(txtCorreo);
        
        //   CAMPO: TELÉFONO  
        JLabel lblTelefono = new JLabel("Teléfono:");
        lblTelefono.setBounds(30, 140, 120, 25);
        add(lblTelefono);
        
        txtTelefono = new JTextField();
        txtTelefono.setBounds(160, 140, 200, 25);
        add(txtTelefono);
        
        //   CAMPO: CONTRASEÑA  
        JLabel lblContrasena = new JLabel("Contraseña:");
        lblContrasena.setBounds(30, 180, 120, 25);
        add(lblContrasena);
        
        txtContrasena = new JPasswordField();
        txtContrasena.setBounds(160, 180, 200, 25);
        add(txtContrasena);
        
        //   CAMPO: CONFIRMAR CONTRASEÑA  
        JLabel lblConfirmacion = new JLabel("Confirmar contraseña:");
        lblConfirmacion.setBounds(30, 220, 130, 25);
        add(lblConfirmacion);
        
        txtConfirmacion = new JPasswordField();
        txtConfirmacion.setBounds(160, 220, 200, 25);
        add(txtConfirmacion);
        
        //   CAMPO: ROL  
        JLabel lblRol = new JLabel("Tipo de usuario:");
        lblRol.setBounds(30, 260, 120, 25);
        add(lblRol);
        
        cmbRol = new JComboBox<>(new String[]{"Viajero", "Anfitrion"});
        cmbRol.setBounds(160, 260, 200, 25);
        add(cmbRol);
        
        //   NOTA INFORMATIVA  
        JLabel lblNota = new JLabel("<html><i>* Todos los campos son obligatorios</i></html>");
        lblNota.setBounds(30, 295, 340, 30);
        lblNota.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 11));
        add(lblNota);
        
        //   BOTÓN: REGISTRAR  
        btnRegistrar = new JButton("Registrar");
        btnRegistrar.setBounds(80, 340, 120, 35);
        btnRegistrar.setBackground(new java.awt.Color(76, 175, 80));
        btnRegistrar.setForeground(java.awt.Color.WHITE);
        btnRegistrar.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 14));
        btnRegistrar.setFocusPainted(false);
        btnRegistrar.addActionListener(e -> registrar());
        add(btnRegistrar);
        
        //   BOTÓN: VOLVER  
        btnVolver = new JButton("Volver");
        btnVolver.setBounds(220, 340, 120, 35);
        btnVolver.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 14));
        btnVolver.setFocusPainted(false);
        btnVolver.addActionListener(e -> volver());
        add(btnVolver);
        
        //   TECLA ENTER PARA REGISTRAR  
        getRootPane().setDefaultButton(btnRegistrar);
    }
    
    //    MÉTODOS DE ACCIÓN   
    
    /**
     * Maneja el evento de registro
     * Captura datos, llama al controlador y muestra resultado
     */
    private void registrar() {
        // Capturar datos de los campos
        String nombre = txtNombre.getText().trim();
        String correo = txtCorreo.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String contrasena = new String(txtContrasena.getPassword());
        String confirmacion = new String(txtConfirmacion.getPassword());
        String rol = (String) cmbRol.getSelectedItem();
        
        // Deshabilitar botón mientras procesa
        btnRegistrar.setEnabled(false);
        btnRegistrar.setText("Registrando...");
        
        // Llamar al controlador para registrar
        ResultadoRegistro resultado = controlador.registrarUsuario(
            nombre, correo, telefono, contrasena, confirmacion, rol
        );
        
        // Habilitar botón nuevamente
        btnRegistrar.setEnabled(true);
        btnRegistrar.setText("Registrar");
        
        // Mostrar resultado
        if (resultado.isExitoso()) {
            // ÉXITO: Mostrar mensaje y redirigir a login
            JOptionPane.showMessageDialog(
                this,
                resultado.getMensaje(),
                "Registro Exitoso",
                JOptionPane.INFORMATION_MESSAGE
            );
            
            // Limpiar campos
            limpiarCampos();
            
            // Redirigir a inicio de sesión
            InicioSesion login = new InicioSesion(dao);
            login.setVisible(true);
            this.dispose();
            
        } else {
            // ERROR: Mostrar mensaje de error
            JOptionPane.showMessageDialog(
                this,
                resultado.getMensaje(),
                "Error en el Registro",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
    
    /**
     * Maneja el evento de volver
     * Regresa a la pantalla de inicio de sesión
     */
    private void volver() {
        InicioSesion login = new InicioSesion(dao);
        login.setVisible(true);
        this.dispose();
    }
    
    /**
     * Limpia todos los campos del formulario
     */
    private void limpiarCampos() {
        txtNombre.setText("");
        txtCorreo.setText("");
        txtTelefono.setText("");
        txtContrasena.setText("");
        txtConfirmacion.setText("");
        cmbRol.setSelectedIndex(0);
        txtNombre.requestFocus();
    }
}
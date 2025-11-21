package controlador;

import modelo.Usuario;
import modelo.UsuarioData;

/**
 * Controlador para manejar la lógica de inicio de sesión
 * Separa la lógica de negocio de la vista
 */
public class ControladorLogin {
    
    private UsuarioData dao;
    
    /**
     * Constructor
     * @param dao Objeto DAO para acceder a datos de usuarios
     */
    public ControladorLogin(UsuarioData dao) {
        this.dao = dao;
    }
    
    /**
     * Autentica un usuario con validaciones
     * @param email Correo electrónico
     * @param contrasena Contraseña
     * @return Resultado de la operación
     */
    public ResultadoLogin autenticar(String email, String contrasena) {
        
        // ===== VALIDACIÓN 1: Campos vacíos =====
        if (email == null || email.trim().isEmpty()) {
            return new ResultadoLogin(false, "El email es obligatorio", null);
        }
        
        if (contrasena == null || contrasena.isEmpty()) {
            return new ResultadoLogin(false, "La contraseña es obligatoria", null);
        }
        
        // ===== AUTENTICACIÓN: Buscar usuario =====
        Usuario usuario = dao.buscarUsuario(email.trim(), contrasena);
        
        if (usuario == null) {
            return new ResultadoLogin(false, "Email o contraseña incorrectos", null);
        }
        
        // ===== ÉXITO: Guardar sesión =====
        UsuarioData.setUsuarioActual(usuario);
        
        String mensajeBienvenida = "¡Bienvenido " + usuario.getNombre() + "!";
        return new ResultadoLogin(true, mensajeBienvenida, usuario);
    }
    
    /**
     * Cierra la sesión del usuario actual
     */
    public void cerrarSesion() {
        UsuarioData.cerrarSesion();
    }
    
    /**
     * Obtiene el usuario actualmente logueado
     * @return Usuario actual o null
     */
    public Usuario obtenerUsuarioActual() {
        return UsuarioData.getUsuarioActual();
    }
    
    /**
     * Verifica si hay una sesión activa
     * @return true si hay usuario logueado
     */
    public boolean haySesionActiva() {
        return UsuarioData.haySesionActiva();
    }
    
    /**
     * Clase interna para retornar resultados de login
     */
    public static class ResultadoLogin {
        private boolean exitoso;
        private String mensaje;
        private Usuario usuario;
        
        public ResultadoLogin(boolean exitoso, String mensaje, Usuario usuario) {
            this.exitoso = exitoso;
            this.mensaje = mensaje;
            this.usuario = usuario;
        }
        
        public boolean isExitoso() {
            return exitoso;
        }
        
        public String getMensaje() {
            return mensaje;
        }
        
        public Usuario getUsuario() {
            return usuario;
        }
    }
}

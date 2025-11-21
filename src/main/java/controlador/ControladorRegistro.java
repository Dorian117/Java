package controlador;

import modelo.Usuario;
import modelo.UsuarioData;

/**
 * Controlador para manejar la lógica de registro de usuarios
 * Separa la lógica de negocio de la vista
 */
public class ControladorRegistro {
    
    private UsuarioData dao;
    
    /**
     * Constructor
     * @param dao Objeto DAO para acceder a datos de usuarios
     */
    public ControladorRegistro(UsuarioData dao) {
        this.dao = dao;
    }
    
    /**
     * Registra un nuevo usuario con validaciones
     * @param nombre Nombre completo
     * @param email Correo electrónico
     * @param telefono Teléfono de contacto
     * @param contrasena Contraseña
     * @param contrasenaConfirmacion Confirmación de contraseña
     * @param rol "Viajero" o "Anfitrion"
     * @return Resultado de la operación
     */
    public ResultadoRegistro registrarUsuario(String nombre, String email, String telefono,
                                              String contrasena, String contrasenaConfirmacion, 
                                              String rol) {
        
        //   VALIDACIÓN 1: Campos vacíos  
        if (nombre == null || nombre.trim().isEmpty()) {
            return new ResultadoRegistro(false, "El nombre es obligatorio");
        }
        
        if (email == null || email.trim().isEmpty()) {
            return new ResultadoRegistro(false, "El email es obligatorio");
        }
        
        if (telefono == null || telefono.trim().isEmpty()) {
            return new ResultadoRegistro(false, "El teléfono es obligatorio");
        }
        
        if (contrasena == null || contrasena.isEmpty()) {
            return new ResultadoRegistro(false, "La contraseña es obligatoria");
        }
        
        if (rol == null || rol.isEmpty()) {
            return new ResultadoRegistro(false, "Debe seleccionar un rol");
        }
        
        //   VALIDACIÓN 2: Formato de email  
        if (!validarEmail(email)) {
            return new ResultadoRegistro(false, "El email no tiene un formato válido");
        }
        
        //   VALIDACIÓN 3: Email duplicado  
        if (dao.existeEmail(email)) {
            return new ResultadoRegistro(false, "El email ya está registrado. Use otro email o inicie sesión.");
        }
        
        //   VALIDACIÓN 4: Longitud de contraseña  
        if (contrasena.length() < 6) {
            return new ResultadoRegistro(false, "La contraseña debe tener mínimo 6 caracteres");
        }
        
        //   VALIDACIÓN 5: Confirmación de contraseña  
        if (!contrasena.equals(contrasenaConfirmacion)) {
            return new ResultadoRegistro(false, "Las contraseñas no coinciden");
        }
        
        //   VALIDACIÓN 6: Formato de teléfono (opcional pero recomendado)  
        if (!validarTelefono(telefono)) {
            return new ResultadoRegistro(false, "El teléfono debe tener 10 dígitos");
        }
        
        //   REGISTRO: Crear usuario con contraseña hasheada
        String contrasenaHasheada = modelo.HashUtil.hashPassword(contrasena);
        Usuario nuevoUsuario = new Usuario(nombre.trim(), email.trim(),
                                          telefono.trim(), contrasenaHasheada, rol);

        boolean registrado = dao.registrarUsuario(nuevoUsuario);
        
        if (registrado) {
            return new ResultadoRegistro(true, "Usuario registrado exitosamente. Ahora puede iniciar sesión.");
        } else {
            return new ResultadoRegistro(false, "Error al registrar usuario. Intente nuevamente.");
        }
    }
    
    /**
     * Valida formato básico de email
     * @param email Email a validar
     * @return true si el formato es válido
     */
    private boolean validarEmail(String email) {
        // Validación simple: debe contener @ y punto después del @
        if (!email.contains("@")) {
            return false;
        }
        
        int posicionArroba = email.indexOf("@");
        int posicionPunto = email.lastIndexOf(".");
        
        // Debe haber al menos un carácter antes del @
        // Debe haber al menos un punto después del @
        // Debe haber al menos 2 caracteres después del último punto
        return posicionArroba > 0 && 
               posicionPunto > posicionArroba && 
               email.length() - posicionPunto > 2;
    }
    
    /**
     * Valida formato de teléfono (10 dígitos)
     * @param telefono Teléfono a validar
     * @return true si tiene 10 dígitos
     */
    private boolean validarTelefono(String telefono) {
        // Remover espacios y guiones
        String telefonoLimpio = telefono.replaceAll("[\\s-]", "");
        
        // Verificar que tenga 10 dígitos
        return telefonoLimpio.matches("\\d{10}");
    }
    
    /**
     * Clase interna para retornar resultados de registro
     */
    public static class ResultadoRegistro {
        private boolean exitoso;
        private String mensaje;
        
        public ResultadoRegistro(boolean exitoso, String mensaje) {
            this.exitoso = exitoso;
            this.mensaje = mensaje;
        }
        
        public boolean isExitoso() {
            return exitoso;
        }
        
        public String getMensaje() {
            return mensaje;
        }
    }
}

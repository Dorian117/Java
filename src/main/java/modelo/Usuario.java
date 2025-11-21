package modelo;

import java.util.UUID;
import java.time.LocalDate;

/**
 * Clase que representa un usuario del sistema y hereda a otras, como viajero o anfitrión.
 */
public class Usuario {
    
    //  ATRIBUTOS
    
    private String usuarioId;
    private String nombre;
    private String email;
    private String telefono;
    private String contrasena;
    private String rol;     // "Viajero" o "Anfitrion"
    private String fechaRegistro;
    
    //  CONSTRUCTORES
    
    
    public Usuario() {
        this.fechaRegistro = LocalDate.now().toString();
    }
    
    /**
     * Constructor completo
     */
    public Usuario(String usuarioId, String nombre, String email, String telefono, String contrasena, String rol) {
        this.usuarioId = usuarioId;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.contrasena = contrasena;
        this.rol = rol;
        this.fechaRegistro = LocalDate.now().toString();
    }
    
    public Usuario(String nombre, String email, String telefono, String contrasena, String rol) {
        this.usuarioId = UUID.randomUUID().toString();
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.contrasena = contrasena;
        this.rol = rol;
        this.fechaRegistro = LocalDate.now().toString();
    }
    
    //GETTERS Y SETTERS
    
    public String getUsuarioId() {
        return usuarioId;
    }
    
    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getTelefono() {
        return telefono;
    }
    
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    public String getContrasena() {
        return contrasena;
    }
    
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
    
    public String getRol() {
        return rol;
    }
    
    public void setRol(String rol) {
        this.rol = rol;
    }
    
    public String getFechaRegistro() {
        return fechaRegistro;
    }
    
    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
    
    //MÉTODOS DE UTILIDAD
    
    /**
     * Verifica si el usuario es un Viajero
     * @return true si el rol es Viajero
     */
    public boolean esViajero() {
        return "Viajero".equalsIgnoreCase(this.rol);
    }
    
    /**
     * Verifica si el usuario es un Anfitrión
     * @return true si el rol es Anfitrión
     */
    public boolean esAnfitrion() {
        return "Anfitrion".equalsIgnoreCase(this.rol);
    }
    
    /**
     * Representación en texto del usuario
     */
    @Override
    public String toString() {
        return "Usuario{" +
                "id='" + usuarioId + '\'' +
                ", nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", telefono='" + telefono + '\'' +
                ", rol='" + rol + '\'' +
                ", fechaRegistro='" + fechaRegistro + '\'' +
                '}';
    }
}

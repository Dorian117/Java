package modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Clase DAO (Data Access Object) para gestionar usuarios
 * Almacena usuarios en memoria (ArrayList estático)
 * En el futuro migrará a base de datos
 */
public class UsuarioData {
    
    //   ALMACENAMIENTO EN MEMORIA  
    
    private static List<Usuario> listaUsuarios = new ArrayList<>();
    
    private static Usuario usuarioActual = null;
    
    //   CONSTRUCTOR  
    
    public UsuarioData() {
        if (listaUsuarios.isEmpty()) {
            cargarDatosPrueba();
        }
    }
    
    //   MÉTODOS CRUD  
    
    /**
     * Registra un nuevo usuario en el sistema
     * @param usuario Usuario a registrar
     * @return true si se registró exitosamente, false si el email ya existe
     */
    public boolean registrarUsuario(Usuario usuario) {
        // Validar que el email no esté duplicado
        if (existeEmail(usuario.getEmail())) {
            return false;
        }
        
        // Generar ID único si no tiene
        if (usuario.getUsuarioId() == null || usuario.getUsuarioId().isEmpty()) {
            usuario.setUsuarioId(UUID.randomUUID().toString());
        }
        
        listaUsuarios.add(usuario);
        System.out.println("✅ Usuario registrado: " + usuario.getEmail());
        return true;
    }
    
    /**
     * Busca un usuario por email y contraseña
     * @param email Email del usuario
     * @param contrasena Contraseña del usuario
     * @return Usuario si los datos son correctos, null si no
     */
    public Usuario buscarUsuario(String email, String contrasena) {
        for (Usuario u : listaUsuarios) {
            if (u.getEmail().equalsIgnoreCase(email) && u.getContrasena().equals(contrasena)) {
                return u;
            }
        }
        return null;
    }
    
    /**
     * Busca un usuario solo por email
     * @param email Email a buscar
     * @return Usuario si existe, null si no
     */
    public Usuario buscarPorEmail(String email) {
        for (Usuario u : listaUsuarios) {
            if (u.getEmail().equalsIgnoreCase(email)) {
                return u;
            }
        }
        return null;
    }
    
    /**
     * Verifica si un email ya está registrado
     * @param email Email a verificar
     * @return true si el email existe, false si no
     */
    public boolean existeEmail(String email) {
        return buscarPorEmail(email) != null;
    }
    
    /**
     * Obtiene todos los usuarios registrados
     * @return Lista de todos los usuarios
     */
    public List<Usuario> obtenerTodosUsuarios() {
        return new ArrayList<>(listaUsuarios);
    }
    
    /**
     * Cuenta cuántos usuarios tienen un rol específico
     * @param rol "Viajero" o "Anfitrion"
     * @return Cantidad de usuarios con ese rol
     */
    public int contarPorRol(String rol) {
        int count = 0;
        for (Usuario u : listaUsuarios) {
            if (u.getRol().equalsIgnoreCase(rol)) {
                count++;
            }
        }
        return count;
    }
    
    //   GESTIÓN DE SESIÓN  
    
    /**
     * Establece el usuario actualmente logueado
     * @param usuario Usuario que inició sesión
     */
    public static void setUsuarioActual(Usuario usuario) {
        usuarioActual = usuario;
        if (usuario != null) {
            System.out.println("✅ Sesión iniciada: " + usuario.getNombre() + " (" + usuario.getRol() + ")");
        }
    }
    
    /**
     * Obtiene el usuario actualmente logueado
     * @return Usuario actual o null si nadie está logueado
     */
    public static Usuario getUsuarioActual() {
        return usuarioActual;
    }
    
    /**
     * Cierra la sesión del usuario actual
     */
    public static void cerrarSesion() {
        if (usuarioActual != null) {
            System.out.println("👋 Sesión cerrada: " + usuarioActual.getNombre());
        }
        usuarioActual = null;
    }
    
    /**
     * Verifica si hay un usuario logueado actualmente
     * @return true si hay sesión activa, false si no
     */
    public static boolean haySesionActiva() {
        return usuarioActual != null;
    }
    
    //   DATOS DE PRUEBA  
    
    /**
     * Carga usuarios de ejemplo para pruebas
     * Solo se ejecuta una vez al inicio
     */
    private void cargarDatosPrueba() {
        // Usuario Admin
        Usuario admin = new Usuario(
            UUID.randomUUID().toString(),
            "Administrador",
            "admin@admin.com",
            "3001234567",
            "1234",
            "Admin"
        );
        listaUsuarios.add(admin);
        
        // Viajero de prueba 1
        Usuario viajero1 = new Usuario(
            UUID.randomUUID().toString(),
            "Carlos Pérez",
            "carlos.perez@gmail.com",
            "3101234567",
            "viajero123",
            "Viajero"
        );
        listaUsuarios.add(viajero1);
        
        // Viajero de prueba 2
        Usuario viajero2 = new Usuario(
            UUID.randomUUID().toString(),
            "Ana Martínez",
            "ana.martinez@gmail.com",
            "3159876543",
            "viajero123",
            "Viajero"
        );
        listaUsuarios.add(viajero2);
        
        // Anfitrión de prueba 1
        Usuario anfitrion1 = new Usuario(
            UUID.randomUUID().toString(),
            "María González",
            "maria.gonzalez@gmail.com",
            "3201234567",
            "anfitrion123",
            "Anfitrion"
        );
        listaUsuarios.add(anfitrion1);
        
        // Anfitrión de prueba 2
        Usuario anfitrion2 = new Usuario(
            UUID.randomUUID().toString(),
            "Pedro Rodríguez",
            "pedro.rodriguez@gmail.com",
            "3184567890",
            "anfitrion123",
            "Anfitrion"
        );
        listaUsuarios.add(anfitrion2);
        
        System.out.println("✅ Datos de prueba cargados: " + listaUsuarios.size() + " usuarios");
        System.out.println("   - Admin: admin@admin.com / 1234");
        System.out.println("   - Viajeros: 2");
        System.out.println("   - Anfitriones: 2");
    }
    
    //   MÉTODOS DE UTILIDAD  
    
    /**
     * Imprime todos los usuarios registrados en consola
     */
    public void imprimirUsuarios() {
        System.out.println("\n  USUARIOS REGISTRADOS  ");
        if (listaUsuarios.isEmpty()) {
            System.out.println("No hay usuarios registrados");
        } else {
            for (int i = 0; i < listaUsuarios.size(); i++) {
                Usuario u = listaUsuarios.get(i);
                System.out.println((i + 1) + ". " + u.getNombre() + " (" + u.getEmail() + ") - " + u.getRol());
            }
        }
    }
    
    /**
     * Obtiene estadísticas de usuarios
     */
    public void imprimirEstadisticas() {
        System.out.println("\n  ESTADÍSTICAS  ");
        System.out.println("Total usuarios: " + listaUsuarios.size());
        System.out.println("Viajeros: " + contarPorRol("Viajero"));
        System.out.println("Anfitriones: " + contarPorRol("Anfitrion"));
        System.out.println("Otros: " + contarPorRol("Admin"));
    }
}

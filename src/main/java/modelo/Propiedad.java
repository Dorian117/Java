package modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Clase que representa una propiedad en el sistema StayKonnect
 * Puede ser publicada por un Anfitrión para ser alquilada por Viajeros
 */
public class Propiedad {
    
    //   ATRIBUTOS  
    
    // Identificación
    private String propiedadId;
    private String anfitrionId;
    
    // Información básica
    private String titulo;
    private String descripcion;
    private String ciudad;
    private String direccion;
    
    // Características
    private String tipo;
    private int capacidad;
    private int habitaciones;
    private int banos;
    
    // Precio
    private double precioPorNoche;
    
    // Servicios disponibles
    private List<String> servicios;
    
    // Estado
    private boolean disponible;// true = disponible para reservar
    
    //   CONSTRUCTORES
    
    /**
     * Constructor vacío
     */
    public Propiedad() {
        this.servicios = new ArrayList<>();
        this.disponible = true;
    }
    
    /**
     * Constructor completo
     */
    public Propiedad(String propiedadId, String anfitrionId, String titulo,
                     String descripcion, String ciudad, String direccion,
                     String tipo, int capacidad, int habitaciones, int banos,
                     double precioPorNoche) {
        this.propiedadId = propiedadId;
        this.anfitrionId = anfitrionId;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.ciudad = ciudad;
        this.direccion = direccion;
        this.tipo = tipo;
        this.capacidad = capacidad;
        this.habitaciones = habitaciones;
        this.banos = banos;
        this.precioPorNoche = precioPorNoche;
        this.servicios = new ArrayList<>();
        this.disponible = true;
    }
    
    /**
     * Constructor sin ID (se genera automáticamente)
     */
    public Propiedad(String anfitrionId, String titulo, String descripcion, 
                     String ciudad, String direccion, String tipo, int capacidad, 
                     int habitaciones, int banos, double precioPorNoche) {
        this.propiedadId = UUID.randomUUID().toString();
        this.anfitrionId = anfitrionId;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.ciudad = ciudad;
        this.direccion = direccion;
        this.tipo = tipo;
        this.capacidad = capacidad;
        this.habitaciones = habitaciones;
        this.banos = banos;
        this.precioPorNoche = precioPorNoche;
        this.servicios = new ArrayList<>();
        this.disponible = true;
    }
    
    //   GETTERS Y SETTERS
    
    public String getPropiedadId() {
        return propiedadId;
    }
    
    public void setPropiedadId(String propiedadId) {
        this.propiedadId = propiedadId;
    }
    
    public String getAnfitrionId() {
        return anfitrionId;
    }
    
    public void setAnfitrionId(String anfitrionId) {
        this.anfitrionId = anfitrionId;
    }
    
    public String getTitulo() {
        return titulo;
    }
    
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public String getCiudad() {
        return ciudad;
    }
    
    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }
    
    public String getDireccion() {
        return direccion;
    }
    
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    
    public String getTipo() {
        return tipo;
    }
    
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    public int getCapacidad() {
        return capacidad;
    }
    
    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }
    
    public int getHabitaciones() {
        return habitaciones;
    }
    
    public void setHabitaciones(int habitaciones) {
        this.habitaciones = habitaciones;
    }
    
    public int getBanos() {
        return banos;
    }
    
    public void setBanos(int banos) {
        this.banos = banos;
    }
    
    public double getPrecioPorNoche() {
        return precioPorNoche;
    }
    
    public void setPrecioPorNoche(double precioPorNoche) {
        this.precioPorNoche = precioPorNoche;
    }
    
    public List<String> getServicios() {
        return servicios;
    }
    
    public void setServicios(List<String> servicios) {
        this.servicios = servicios;
    }
    
    public boolean isDisponible() {
        return disponible;
    }
    
    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }
    
    //   MÉTODOS DE UTILIDAD  
    
    /**
     * Agrega un servicio a la propiedad (si no existe ya)
     * @param servicio Nombre del servicio (ej: "WiFi", "Cocina")
     */
    public void agregarServicio(String servicio) {
        if (!this.servicios.contains(servicio)) {
            this.servicios.add(servicio);
        }
    }
    
    /**
     * Remueve un servicio de la propiedad
     * @param servicio Nombre del servicio a remover
     */
    public void removerServicio(String servicio) {
        this.servicios.remove(servicio);
    }
    
    /**
     * Verifica si la propiedad tiene un servicio específico
     * @param servicio Nombre del servicio a verificar
     * @return true si la propiedad tiene ese servicio
     */
    public boolean tieneServicio(String servicio) {
        return this.servicios.contains(servicio);
    }
    
    /**
     * Obtiene una cadena con los servicios
     * @return String con servicios (ej: "WiFi, Cocina, TV")
     */
    public String getServiciosTexto() {
        if (servicios.isEmpty()) {
            return "Sin servicios";
        }
        return String.join(", ", servicios);
    }
    
    /**
     * Representación en texto de la propiedad
     */
    @Override
    public String toString() {
        return "Propiedad{" +
                "id='" + propiedadId + '\'' +
                ", titulo='" + titulo + '\'' +
                ", ciudad='" + ciudad + '\'' +
                ", tipo='" + tipo + '\'' +
                ", precio=" + precioPorNoche +
                ", capacidad=" + capacidad +
                ", disponible=" + disponible +
                '}';
    }
}

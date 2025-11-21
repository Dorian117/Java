package controlador;

import modelo.Propiedad;
import modelo.PropiedadData;
import java.util.List;

/**
 * Controlador para manejar la lógica de búsqueda de propiedades
 * Separa la lógica de negocio de la vista
 */
public class ControladorBusqueda {
    
    private PropiedadData dao;
    
    /**
     * Constructor
     * @param dao Objeto DAO para acceder a datos de propiedades
     */
    public ControladorBusqueda(PropiedadData dao) {
        this.dao = dao;
    }
    
    /**
     * Busca propiedades aplicando filtros
     * @param ciudad Ciudad seleccionada (puede ser "Todas")
     * @param precioMinTexto Precio mínimo como String
     * @param precioMaxTexto Precio máximo como String
     * @param serviciosSeleccionados Lista de servicios requeridos
     * @return Resultado de la búsqueda
     */
    public ResultadoBusqueda buscarPropiedades(String ciudad, String precioMinTexto, 
                                               String precioMaxTexto, 
                                               List<String> serviciosSeleccionados) {
        
        //   CONVERSIÓN Y VALIDACIÓN DE PRECIOS  
        double precioMin = 0;
        double precioMax = 0;
        
        // Convertir precio mínimo
        if (precioMinTexto != null && !precioMinTexto.trim().isEmpty()) {
            try {
                precioMin = Double.parseDouble(precioMinTexto.trim());
                if (precioMin < 0) {
                    return new ResultadoBusqueda(false, 
                        "El precio mínimo no puede ser negativo", null);
                }
            } catch (NumberFormatException e) {
                return new ResultadoBusqueda(false, 
                    "El precio mínimo debe ser un número válido", null);
            }
        }
        
        // Convertir precio máximo
        if (precioMaxTexto != null && !precioMaxTexto.trim().isEmpty()) {
            try {
                precioMax = Double.parseDouble(precioMaxTexto.trim());
                if (precioMax < 0) {
                    return new ResultadoBusqueda(false, 
                        "El precio máximo no puede ser negativo", null);
                }
            } catch (NumberFormatException e) {
                return new ResultadoBusqueda(false, 
                    "El precio máximo debe ser un número válido", null);
            }
        }
        
        // Validar coherencia de precios
        if (precioMin > 0 && precioMax > 0 && precioMin > precioMax) {
            return new ResultadoBusqueda(false, 
                "El precio mínimo no puede ser mayor que el precio máximo", null);
        }
        
        //   BÚSQUEDA: Aplicar filtros  
        List<Propiedad> resultados = dao.buscarConFiltros(
            ciudad, precioMin, precioMax, serviciosSeleccionados
        );
        
        //   MENSAJE DE RESULTADO  
        String mensaje;
        if (resultados.isEmpty()) {
            mensaje = "No se encontraron propiedades con los criterios seleccionados. " +
                     "Intente modificar los filtros.";
        } else {
            mensaje = "Se encontraron " + resultados.size() + " propiedad(es)";
        }
        
        return new ResultadoBusqueda(true, mensaje, resultados);
    }
    
    /**
     * Obtiene todas las propiedades disponibles (sin filtros)
     * @return Resultado con todas las propiedades
     */
    public ResultadoBusqueda obtenerTodasPropiedades() {
        List<Propiedad> resultados = dao.obtenerPropiedadesDisponibles();
        
        String mensaje = "Mostrando todas las propiedades disponibles (" + 
                        resultados.size() + " total)";
        
        return new ResultadoBusqueda(true, mensaje, resultados);
    }
    
    /**
     * Obtiene lista de ciudades disponibles para el ComboBox
     * @return Lista de nombres de ciudades
     */
    public List<String> obtenerCiudades() {
        return dao.obtenerCiudades();
    }
    
    /**
     * Obtiene lista de servicios disponibles para los checkboxes
     * @return Lista de nombres de servicios
     */
    public List<String> obtenerServiciosDisponibles() {
        return dao.obtenerServiciosDisponibles();
    }
    
    /**
     * Busca propiedades de un anfitrión específico
     * @param anfitrionId ID del anfitrión
     * @return Resultado con propiedades del anfitrión
     */
    public ResultadoBusqueda obtenerPropiedadesPorAnfitrion(String anfitrionId) {
        List<Propiedad> resultados = dao.obtenerPropiedadesPorAnfitrion(anfitrionId);
        
        String mensaje;
        if (resultados.isEmpty()) {
            mensaje = "No tiene propiedades registradas aún";
        } else {
            mensaje = "Tiene " + resultados.size() + " propiedad(es) registrada(s)";
        }
        
        return new ResultadoBusqueda(true, mensaje, resultados);
    }
    
    /**
     * Clase interna para retornar resultados de búsqueda
     */
    public static class ResultadoBusqueda {
        private boolean exitoso;
        private String mensaje;
        private List<Propiedad> propiedades;
        
        public ResultadoBusqueda(boolean exitoso, String mensaje, List<Propiedad> propiedades) {
            this.exitoso = exitoso;
            this.mensaje = mensaje;
            this.propiedades = propiedades;
        }
        
        public boolean isExitoso() {
            return exitoso;
        }
        
        public String getMensaje() {
            return mensaje;
        }
        
        public List<Propiedad> getPropiedades() {
            return propiedades;
        }
        
        public int getCantidad() {
            return propiedades != null ? propiedades.size() : 0;
        }
    }
}
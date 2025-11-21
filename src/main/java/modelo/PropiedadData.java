package modelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Clase DAO (Data Access Object) para gestionar propiedades
 * Utiliza múltiples estructuras de datos para optimizar operaciones:
 * - ArrayList: Para iteración y mantenimiento del orden de inserción
 * - HashMap: Para búsqueda rápida O(1) por ID
 * - TreeMap: Para ordenamiento automático por precio (estructura de árbol balanceado)
 */
public class PropiedadData {

    //   ALMACENAMIENTO EN MEMORIA

    // Lista principal (mantiene orden de inserción)
    private static List<Propiedad> listaPropiedades = new ArrayList<>();

    // HashMap para búsqueda rápida por ID - Complejidad: O(1)
    private static HashMap<String, Propiedad> propiedadesPorId = new HashMap<>();

    // TreeMap para ordenamiento automático por precio - Árbol Red-Black
    // Permite obtener propiedades ordenadas por precio en O(log n)
    private static TreeMap<Double, List<Propiedad>> propiedadesPorPrecio = new TreeMap<>();
    
    //   CONSTRUCTOR  
    
    /**
     * Constructor: carga datos de prueba si la lista está vacía
     */
    public PropiedadData() {
        if (listaPropiedades.isEmpty()) {
            cargarDatosPrueba();
        }
    }
    
    //   MÉTODOS CRUD  
    
    /**
     * Registra una nueva propiedad en el sistema
     * Actualiza todas las estructuras de datos (ArrayList, HashMap y TreeMap)
     * @param propiedad Propiedad a registrar
     * @return true si se registró exitosamente
     */
    public boolean registrarPropiedad(Propiedad propiedad) {
        // Generar ID único si no tiene
        if (propiedad.getPropiedadId() == null || propiedad.getPropiedadId().isEmpty()) {
            propiedad.setPropiedadId(UUID.randomUUID().toString());
        }

        // Agregar a ArrayList
        listaPropiedades.add(propiedad);

        // Agregar a HashMap por ID
        propiedadesPorId.put(propiedad.getPropiedadId(), propiedad);

        // Agregar a TreeMap por precio (permite múltiples propiedades con mismo precio)
        double precio = propiedad.getPrecioPorNoche();
        if (!propiedadesPorPrecio.containsKey(precio)) {
            propiedadesPorPrecio.put(precio, new ArrayList<>());
        }
        propiedadesPorPrecio.get(precio).add(propiedad);

        System.out.println("✅ Propiedad registrada: " + propiedad.getTitulo() +
                          " (Precio: $" + String.format("%,.0f", precio) + ")");
        return true;
    }
    
    /**
     * Busca una propiedad por su ID usando HashMap
     * Complejidad: O(1) - Búsqueda instantánea
     * @param id ID de la propiedad
     * @return Propiedad si existe, null si no
     */
    public Propiedad buscarPorId(String id) {
        return propiedadesPorId.get(id);
    }
    
    /**
     * Obtiene todas las propiedades disponibles
     * @return Lista de propiedades con disponible=true
     */
    public List<Propiedad> obtenerPropiedadesDisponibles() {
        List<Propiedad> disponibles = new ArrayList<>();
        for (Propiedad p : listaPropiedades) {
            if (p.isDisponible()) {
                disponibles.add(p);
            }
        }
        return disponibles;
    }
    
    /**
     * Obtiene todas las propiedades (disponibles y no disponibles)
     * @return Lista completa de propiedades
     */
    public List<Propiedad> obtenerTodasPropiedades() {
        return new ArrayList<>(listaPropiedades);
    }
    
    /**
     * Obtiene propiedades de un anfitrión específico
     * @param anfitrionId ID del anfitrión
     * @return Lista de propiedades del anfitrión
     */
    public List<Propiedad> obtenerPropiedadesPorAnfitrion(String anfitrionId) {
        List<Propiedad> propiedades = new ArrayList<>();
        for (Propiedad p : listaPropiedades) {
            if (p.getAnfitrionId().equals(anfitrionId)) {
                propiedades.add(p);
            }
        }
        return propiedades;
    }
    
    //   MÉTODOS DE BÚSQUEDA CON FILTROS  
    
    /**
     * Busca propiedades aplicando filtros
     * @param ciudad Ciudad a buscar 
     * @param precioMin Precio mínimo 
     * @param precioMax Precio máximo 
     * @param serviciosRequeridos Lista de servicios que debe tener 
     * @return Lista de propiedades que cumplen los filtros
     */
    public List<Propiedad> buscarConFiltros(String ciudad, double precioMin, 
                                            double precioMax, List<String> serviciosRequeridos) {
        // Empezar con todas las propiedades disponibles
        List<Propiedad> resultados = obtenerPropiedadesDisponibles();
        
        // Filtro por ciudad
        if (ciudad != null && !ciudad.trim().isEmpty() && 
            !ciudad.equalsIgnoreCase("Todas") && !ciudad.equalsIgnoreCase("Seleccione")) {
            List<Propiedad> filtradas = new ArrayList<>();
            for (Propiedad p : resultados) {
                if (p.getCiudad().equalsIgnoreCase(ciudad)) {
                    filtradas.add(p);
                }
            }
            resultados = filtradas;
        }
        
        // Filtro por precio mínimo
        if (precioMin > 0) {
            List<Propiedad> filtradas = new ArrayList<>();
            for (Propiedad p : resultados) {
                if (p.getPrecioPorNoche() >= precioMin) {
                    filtradas.add(p);
                }
            }
            resultados = filtradas;
        }
        
        // Filtro por precio máximo
        if (precioMax > 0 && precioMax >= precioMin) {
            List<Propiedad> filtradas = new ArrayList<>();
            for (Propiedad p : resultados) {
                if (p.getPrecioPorNoche() <= precioMax) {
                    filtradas.add(p);
                }
            }
            resultados = filtradas;
        }
        
        // Filtro por servicios
        if (serviciosRequeridos != null && !serviciosRequeridos.isEmpty()) {
            List<Propiedad> filtradas = new ArrayList<>();
            for (Propiedad p : resultados) {
                // La propiedad debe tener TODOS los servicios requeridos
                if (p.getServicios().containsAll(serviciosRequeridos)) {
                    filtradas.add(p);
                }
            }
            resultados = filtradas;
        }
        
        System.out.println("Búsqueda completada: " + resultados.size() + " propiedades encontradas");
        return resultados;
    }
    
    /**
     * Obtiene lista de ciudades únicas (para llenar ComboBox)
     * @return Lista de nombres de ciudades ordenadas
     */
    public List<String> obtenerCiudades() {
        List<String> ciudades = new ArrayList<>();
        for (Propiedad p : listaPropiedades) {
            if (!ciudades.contains(p.getCiudad())) {
                ciudades.add(p.getCiudad());
            }
        }
        // Ordenar alfabéticamente
        ciudades.sort(String.CASE_INSENSITIVE_ORDER);
        return ciudades;
    }
    
    /**
     * Obtiene todos los servicios únicos disponibles
     * @return Lista de servicios disponibles en todas las propiedades
     */
    public List<String> obtenerServiciosDisponibles() {
        List<String> servicios = new ArrayList<>();
        for (Propiedad p : listaPropiedades) {
            for (String servicio : p.getServicios()) {
                if (!servicios.contains(servicio)) {
                    servicios.add(servicio);
                }
            }
        }
        servicios.sort(String.CASE_INSENSITIVE_ORDER);
        return servicios;
    }
    
    //   DATOS DE PRUEBA  
    
    private void cargarDatosPrueba() {
        // ID genérico de anfitrión (en producción sería del usuario real)
        String anfitrionDemo = "anfitrion-demo";
        
        //   PROPIEDAD 1: Bogotá  
        Propiedad p1 = new Propiedad(
            UUID.randomUUID().toString(),
            anfitrionDemo,
            "Apartamento moderno en Chapinero",
            "Hermoso apartamento de 2 habitaciones en el corazón de Chapinero. Completamente amoblado con vista a la ciudad.",
            "Bogotá",
            "Calle 63 # 10-20",
            "Apartamento",
            4, 2, 2, 150000
        );
        p1.agregarServicio("WiFi");
        p1.agregarServicio("Cocina");
        p1.agregarServicio("TV");
        registrarPropiedad(p1);
        
        //   PROPIEDAD 2: Bogotá  
        Propiedad p2 = new Propiedad(
            UUID.randomUUID().toString(),
            anfitrionDemo,
            "Casa acogedora en Usaquén",
            "Casa de 3 habitaciones con jardín. Perfecta para familias. Zona tranquila y segura.",
            "Bogotá",
            "Carrera 7 # 125-30",
            "Casa",
            6, 3, 2, 250000
        );
        p2.agregarServicio("WiFi");
        p2.agregarServicio("Cocina");
        p2.agregarServicio("Parqueadero");
        p2.agregarServicio("Lavadora");
        registrarPropiedad(p2);
        
        //   PROPIEDAD 3: Bogotá  
        Propiedad p3 = new Propiedad(
            UUID.randomUUID().toString(),
            anfitrionDemo,
            "Habitación privada en Teusaquillo",
            "Habitación cómoda en apartamento compartido. Baño privado. Ideal para estudiantes.",
            "Bogotá",
            "Calle 32 # 20-15",
            "Habitación",
            2, 1, 1, 80000
        );
        p3.agregarServicio("WiFi");
        p3.agregarServicio("TV");
        registrarPropiedad(p3);
        
        //   PROPIEDAD 4: Bogotá  
        Propiedad p4 = new Propiedad(
            UUID.randomUUID().toString(),
            anfitrionDemo,
            "Loft de lujo en Parque 93",
            "Loft moderno con acabados de primera. Vista panorámica. Gimnasio en el edificio.",
            "Bogotá",
            "Calle 93 # 15-30",
            "Apartamento",
            2, 1, 1, 300000
        );
        p4.agregarServicio("WiFi");
        p4.agregarServicio("Cocina");
        p4.agregarServicio("TV");
        p4.agregarServicio("Aire Acondicionado");
        p4.agregarServicio("Parqueadero");
        registrarPropiedad(p4);
        
        //   PROPIEDAD 5: Bogotá  
        Propiedad p5 = new Propiedad(
            UUID.randomUUID().toString(),
            anfitrionDemo,
            "Apartamento económico en Suba",
            "Apartamento sencillo pero cómodo. Cerca al transporte público. Ideal para viajeros de presupuesto.",
            "Bogotá",
            "Transversal 91 # 145-20",
            "Apartamento",
            3, 2, 1, 90000
        );
        p5.agregarServicio("WiFi");
        p5.agregarServicio("Cocina");
        registrarPropiedad(p5);
        
        //   PROPIEDAD 6: Medellín  
        Propiedad p6 = new Propiedad(
            UUID.randomUUID().toString(),
            anfitrionDemo,
            "Apartamento con vista en El Poblado",
            "Apartamento de lujo en la zona más exclusiva de Medellín. Vista espectacular.",
            "Medellín",
            "Carrera 43A # 5-100",
            "Apartamento",
            4, 2, 2, 280000
        );
        p6.agregarServicio("WiFi");
        p6.agregarServicio("Cocina");
        p6.agregarServicio("TV");
        p6.agregarServicio("Parqueadero");
        p6.agregarServicio("Piscina");
        registrarPropiedad(p6);
        
        //   PROPIEDAD 7: Cartagena  
        Propiedad p7 = new Propiedad(
            UUID.randomUUID().toString(),
            anfitrionDemo,
            "Casa de playa en Bocagrande",
            "Casa frente al mar. Perfecta para vacaciones familiares. Acceso directo a la playa.",
            "Cartagena",
            "Avenida San Martín # 8-50",
            "Casa",
            8, 4, 3, 400000
        );
        p7.agregarServicio("WiFi");
        p7.agregarServicio("Cocina");
        p7.agregarServicio("TV");
        p7.agregarServicio("Aire Acondicionado");
        p7.agregarServicio("Parqueadero");
        registrarPropiedad(p7);
        
        //   PROPIEDAD 8: Cali  
        Propiedad p8 = new Propiedad(
            UUID.randomUUID().toString(),
            anfitrionDemo,
            "Apartamento cerca a la Terminal",
            "Ideal para viajeros de paso. Cómodo y accesible. Cerca a centros comerciales.",
            "Cali",
            "Calle 30N # 2AN-30",
            "Apartamento",
            3, 2, 1, 110000
        );
        p8.agregarServicio("WiFi");
        p8.agregarServicio("Cocina");
        p8.agregarServicio("TV");
        registrarPropiedad(p8);
        
        //   PROPIEDAD 9: Bogotá  
        Propiedad p9 = new Propiedad(
            UUID.randomUUID().toString(),
            anfitrionDemo,
            "Studio para estudiantes - Salitre",
            "Studio compacto ideal para una persona. Incluye servicios. Zona universitaria.",
            "Bogotá",
            "Avenida 68 # 22B-50",
            "Apartamento",
            1, 1, 1, 70000
        );
        p9.agregarServicio("WiFi");
        p9.agregarServicio("Cocina");
        registrarPropiedad(p9);
        
        //   PROPIEDAD 10: Medellín  
        Propiedad p10 = new Propiedad(
            UUID.randomUUID().toString(),
            anfitrionDemo,
            "Casa campestre en Rionegro",
            "Casa de descanso con hermosa vista a las montañas. Ideal para escapadas de fin de semana.",
            "Medellín",
            "Vereda El Retiro Km 5",
            "Casa",
            10, 5, 3, 350000
        );
        p10.agregarServicio("WiFi");
        p10.agregarServicio("Cocina");
        p10.agregarServicio("Parqueadero");
        p10.agregarServicio("Piscina");
        p10.agregarServicio("Chimenea");
        registrarPropiedad(p10);
        
        System.out.println("✅ Datos de prueba cargados: " + listaPropiedades.size() + " propiedades");
        System.out.println("   - Bogotá: 6 propiedades");
        System.out.println("   - Medellín: 2 propiedades");
        System.out.println("   - Cartagena: 1 propiedad");
        System.out.println("   - Cali: 1 propiedad");
    }
    
    //   MÉTODOS DE UTILIDAD
    
    /**
     * Imprime todas las propiedades (para debug)
     */
    public void imprimirPropiedades() {
        System.out.println("   PROPIEDADES REGISTRADAS  ");
        if (listaPropiedades.isEmpty()) {
            System.out.println("No hay propiedades registradas");
        } else {
            for (int i = 0; i < listaPropiedades.size(); i++) {
                Propiedad p = listaPropiedades.get(i);
                System.out.println((i + 1) + ". " + p.getTitulo() + 
                                   " - " + p.getCiudad() + 
                                   " - $" + String.format("%,.0f", p.getPrecioPorNoche()));
            }
        }
    }
    
    /**
     * Obtiene estadísticas básicas
     */
    public void imprimirEstadisticas() {
        System.out.println("   ESTADÍSTICAS  ");
        System.out.println("Total propiedades: " + listaPropiedades.size());
        System.out.println("Propiedades disponibles: " + obtenerPropiedadesDisponibles().size());
        System.out.println("Ciudades: " + obtenerCiudades());
        
        // Precio promedio
        double sumaPrecios = 0;
        for (Propiedad p : listaPropiedades) {
            sumaPrecios += p.getPrecioPorNoche();
        }
        double promedio = sumaPrecios / listaPropiedades.size();
        System.out.println("Precio promedio: $" + String.format("%,.0f", promedio));
    }

    //   MÉTODOS CON ESTRUCTURAS DE DATOS AVANZADAS

    /**
     * Obtiene propiedades ordenadas por precio (ascendente)
     * Utiliza TreeMap (árbol Red-Black) - Complejidad: O(n)
     * @return Lista de propiedades ordenadas de menor a mayor precio
     */
    public List<Propiedad> obtenerPropiedadesOrdenadasPorPrecio() {
        List<Propiedad> ordenadas = new ArrayList<>();
        // TreeMap mantiene las claves ordenadas automáticamente
        for (List<Propiedad> props : propiedadesPorPrecio.values()) {
            ordenadas.addAll(props);
        }
        return ordenadas;
    }

    /**
     * Obtiene propiedades ordenadas por precio (descendente)
     * Utiliza TreeMap en orden inverso - Complejidad: O(n)
     * @return Lista de propiedades ordenadas de mayor a menor precio
     */
    public List<Propiedad> obtenerPropiedadesOrdenadasPorPrecioDesc() {
        List<Propiedad> ordenadas = new ArrayList<>();
        // descendingMap() invierte el orden del árbol
        for (List<Propiedad> props : propiedadesPorPrecio.descendingMap().values()) {
            ordenadas.addAll(props);
        }
        return ordenadas;
    }

    /**
     * Obtiene las N propiedades más baratas
     * Utiliza TreeMap - Complejidad: O(n) donde n es el límite
     * @param limite Número de propiedades a obtener
     * @return Lista con las propiedades más baratas
     */
    public List<Propiedad> obtenerPropiedadesMasBaratas(int limite) {
        List<Propiedad> baratas = new ArrayList<>();
        int contador = 0;

        // Iterar desde el precio más bajo
        for (List<Propiedad> props : propiedadesPorPrecio.values()) {
            for (Propiedad p : props) {
                if (contador >= limite) {
                    return baratas;
                }
                baratas.add(p);
                contador++;
            }
        }
        return baratas;
    }

    /**
     * Obtiene las N propiedades más caras
     * Utiliza TreeMap en orden inverso - Complejidad: O(n) donde n es el límite
     * @param limite Número de propiedades a obtener
     * @return Lista con las propiedades más caras
     */
    public List<Propiedad> obtenerPropiedadesMasCaras(int limite) {
        List<Propiedad> caras = new ArrayList<>();
        int contador = 0;

        // Iterar desde el precio más alto
        for (List<Propiedad> props : propiedadesPorPrecio.descendingMap().values()) {
            for (Propiedad p : props) {
                if (contador >= limite) {
                    return caras;
                }
                caras.add(p);
                contador++;
            }
        }
        return caras;
    }

    /**
     * Obtiene propiedades en un rango de precios
     * Utiliza TreeMap.subMap() - Complejidad: O(log n + m) donde m es el resultado
     * @param precioMin Precio mínimo (inclusive)
     * @param precioMax Precio máximo (inclusive)
     * @return Lista de propiedades en el rango de precio
     */
    public List<Propiedad> obtenerPropiedadesEnRangoPrecio(double precioMin, double precioMax) {
        List<Propiedad> enRango = new ArrayList<>();

        // subMap() usa la estructura de árbol para encontrar el rango eficientemente
        for (List<Propiedad> props : propiedadesPorPrecio.subMap(precioMin, true, precioMax, true).values()) {
            enRango.addAll(props);
        }

        return enRango;
    }

    /**
     * Obtiene el precio más bajo disponible
     * Utiliza TreeMap.firstKey() - Complejidad: O(1)
     * @return Precio mínimo, o 0 si no hay propiedades
     */
    public double obtenerPrecioMinimo() {
        return propiedadesPorPrecio.isEmpty() ? 0 : propiedadesPorPrecio.firstKey();
    }

    /**
     * Obtiene el precio más alto disponible
     * Utiliza TreeMap.lastKey() - Complejidad: O(1)
     * @return Precio máximo, o 0 si no hay propiedades
     */
    public double obtenerPrecioMaximo() {
        return propiedadesPorPrecio.isEmpty() ? 0 : propiedadesPorPrecio.lastKey();
    }

    /**
     * Demuestra el uso de las estructuras de datos
     */
    public void demostrarEstructurasDatos() {
        System.out.println("\n═══════════════════════════════════════════════════");
        System.out.println("   DEMOSTRACIÓN DE ESTRUCTURAS DE DATOS");
        System.out.println("═══════════════════════════════════════════════════\n");

        // HashMap - Búsqueda O(1)
        System.out.println("1️⃣  HASHMAP - Búsqueda por ID en O(1)");
        if (!listaPropiedades.isEmpty()) {
            String idPrueba = listaPropiedades.get(0).getPropiedadId();
            Propiedad encontrada = buscarPorId(idPrueba);
            System.out.println("   Buscando ID: " + idPrueba.substring(0, 8) + "...");
            System.out.println("   ✅ Encontrada: " + encontrada.getTitulo());
        }

        // TreeMap - Ordenamiento automático
        System.out.println("\n2️⃣  TREEMAP - Árbol Red-Black (ordenado por precio)");
        System.out.println("   Precios en el árbol: " + propiedadesPorPrecio.keySet());
        System.out.println("   Precio mínimo: $" + String.format("%,.0f", obtenerPrecioMinimo()));
        System.out.println("   Precio máximo: $" + String.format("%,.0f", obtenerPrecioMaximo()));

        // Propiedades más baratas
        System.out.println("\n3️⃣  TOP 3 PROPIEDADES MÁS BARATAS:");
        List<Propiedad> baratas = obtenerPropiedadesMasBaratas(3);
        for (int i = 0; i < baratas.size(); i++) {
            Propiedad p = baratas.get(i);
            System.out.println("   " + (i + 1) + ". " + p.getTitulo() +
                             " - $" + String.format("%,.0f", p.getPrecioPorNoche()));
        }

        // Propiedades más caras
        System.out.println("\n4️⃣  TOP 3 PROPIEDADES MÁS CARAS:");
        List<Propiedad> caras = obtenerPropiedadesMasCaras(3);
        for (int i = 0; i < caras.size(); i++) {
            Propiedad p = caras.get(i);
            System.out.println("   " + (i + 1) + ". " + p.getTitulo() +
                             " - $" + String.format("%,.0f", p.getPrecioPorNoche()));
        }

        // Rango de precios
        System.out.println("\n5️⃣  PROPIEDADES ENTRE $100,000 Y $200,000:");
        List<Propiedad> enRango = obtenerPropiedadesEnRangoPrecio(100000, 200000);
        System.out.println("   Encontradas: " + enRango.size() + " propiedades");
        for (Propiedad p : enRango) {
            System.out.println("   - " + p.getTitulo() +
                             " ($" + String.format("%,.0f", p.getPrecioPorNoche()) + ")");
        }

        System.out.println("\n═══════════════════════════════════════════════════\n");
    }
}

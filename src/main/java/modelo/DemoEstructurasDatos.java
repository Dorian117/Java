package modelo;

/**
 * Clase para demostrar el uso de estructuras de datos avanzadas
 * HashMap y TreeMap en el proyecto StayKonnect
 */
public class DemoEstructurasDatos {

    public static void main(String[] args) {
        System.out.println("╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║     DEMOSTRACIÓN: HashMap y TreeMap en StayKonnect        ║");
        System.out.println("╚═══════════════════════════════════════════════════════════╝\n");

        // Inicializar datos
        PropiedadData propiedadDao = new PropiedadData();
        UsuarioData usuarioDao = new UsuarioData();

        System.out.println("📚 Datos cargados correctamente\n");

        // ═══════════════════════════════════════════════════════════
        // DEMOSTRACIÓN DE HASHMAP EN USUARIODATA
        // ═══════════════════════════════════════════════════════════

        System.out.println("┌──────────────────────────────────────────────────────────┐");
        System.out.println("│  1. HASHMAP - Búsqueda de Usuarios O(1)                │");
        System.out.println("└──────────────────────────────────────────────────────────┘\n");

        // Buscar por email (HashMap)
        System.out.println("🔍 Búsqueda por Email (HashMap):");
        Usuario usuario1 = usuarioDao.buscarPorEmail("admin@admin.com");
        if (usuario1 != null) {
            System.out.println("   ✅ Encontrado: " + usuario1.getNombre());
            System.out.println("   📧 Email: " + usuario1.getEmail());
            System.out.println("   👤 Rol: " + usuario1.getRol());
            System.out.println("   🆔 ID: " + usuario1.getUsuarioId().substring(0, 8) + "...");

            // Buscar por ID (HashMap)
            System.out.println("\n🔍 Búsqueda por ID (HashMap):");
            Usuario usuario2 = usuarioDao.buscarPorId(usuario1.getUsuarioId());
            System.out.println("   ✅ Encontrado: " + usuario2.getNombre());
            System.out.println("   ⏱️  Complejidad: O(1) - Instantáneo");
        }

        // ═══════════════════════════════════════════════════════════
        // DEMOSTRACIÓN DE HASHMAP Y TREEMAP EN PROPIEDADDATA
        // ═══════════════════════════════════════════════════════════

        System.out.println("\n\n┌──────────────────────────────────────────────────────────┐");
        System.out.println("│  2. HASHMAP - Búsqueda de Propiedades O(1)             │");
        System.out.println("└──────────────────────────────────────────────────────────┘\n");

        // Obtener todas las propiedades
        var propiedades = propiedadDao.obtenerTodasPropiedades();
        if (!propiedades.isEmpty()) {
            Propiedad prop = propiedades.get(0);
            System.out.println("🔍 Búsqueda por ID (HashMap):");
            System.out.println("   Buscando: " + prop.getPropiedadId().substring(0, 8) + "...");

            Propiedad encontrada = propiedadDao.buscarPorId(prop.getPropiedadId());
            System.out.println("   ✅ Encontrada: " + encontrada.getTitulo());
            System.out.println("   📍 Ciudad: " + encontrada.getCiudad());
            System.out.println("   💰 Precio: $" + String.format("%,.0f", encontrada.getPrecioPorNoche()));
            System.out.println("   ⏱️  Complejidad: O(1) - Instantáneo");
        }

        // ═══════════════════════════════════════════════════════════
        // DEMOSTRACIÓN DE TREEMAP - ÁRBOL RED-BLACK
        // ═══════════════════════════════════════════════════════════

        System.out.println("\n\n┌──────────────────────────────────────────────────────────┐");
        System.out.println("│  3. TREEMAP - Árbol Red-Black (Ordenamiento Automático)│");
        System.out.println("└──────────────────────────────────────────────────────────┘\n");

        System.out.println("🌳 Características del TreeMap:");
        System.out.println("   • Estructura: Árbol Red-Black balanceado");
        System.out.println("   • Inserción: O(log n)");
        System.out.println("   • Búsqueda: O(log n)");
        System.out.println("   • Orden: Automático por clave (precio)\n");

        System.out.println("📊 Estadísticas de Precios:");
        System.out.println("   💵 Precio Mínimo: $" + String.format("%,.0f", propiedadDao.obtenerPrecioMinimo()));
        System.out.println("   💰 Precio Máximo: $" + String.format("%,.0f", propiedadDao.obtenerPrecioMaximo()));

        // ═══════════════════════════════════════════════════════════
        // TOP PROPIEDADES MÁS BARATAS Y MÁS CARAS
        // ═══════════════════════════════════════════════════════════

        System.out.println("\n\n┌──────────────────────────────────────────────────────────┐");
        System.out.println("│  4. TOP 3 PROPIEDADES MÁS BARATAS (TreeMap)            │");
        System.out.println("└──────────────────────────────────────────────────────────┘\n");

        var baratas = propiedadDao.obtenerPropiedadesMasBaratas(3);
        for (int i = 0; i < baratas.size(); i++) {
            Propiedad p = baratas.get(i);
            System.out.println("   " + (i + 1) + ". " + p.getTitulo());
            System.out.println("      📍 " + p.getCiudad());
            System.out.println("      💵 $" + String.format("%,.0f", p.getPrecioPorNoche()) + "/noche");
            System.out.println();
        }

        System.out.println("┌──────────────────────────────────────────────────────────┐");
        System.out.println("│  5. TOP 3 PROPIEDADES MÁS CARAS (TreeMap)              │");
        System.out.println("└──────────────────────────────────────────────────────────┘\n");

        var caras = propiedadDao.obtenerPropiedadesMasCaras(3);
        for (int i = 0; i < caras.size(); i++) {
            Propiedad p = caras.get(i);
            System.out.println("   " + (i + 1) + ". " + p.getTitulo());
            System.out.println("      📍 " + p.getCiudad());
            System.out.println("      💰 $" + String.format("%,.0f", p.getPrecioPorNoche()) + "/noche");
            System.out.println();
        }

        // ═══════════════════════════════════════════════════════════
        // BÚSQUEDA POR RANGO DE PRECIOS
        // ═══════════════════════════════════════════════════════════

        System.out.println("┌──────────────────────────────────────────────────────────┐");
        System.out.println("│  6. BÚSQUEDA POR RANGO DE PRECIOS (TreeMap.subMap)     │");
        System.out.println("└──────────────────────────────────────────────────────────┘\n");

        System.out.println("🔍 Buscando propiedades entre $100,000 y $200,000...");
        var enRango = propiedadDao.obtenerPropiedadesEnRangoPrecio(100000, 200000);
        System.out.println("   ✅ Encontradas: " + enRango.size() + " propiedades\n");

        for (Propiedad p : enRango) {
            System.out.println("   • " + p.getTitulo());
            System.out.println("     💵 $" + String.format("%,.0f", p.getPrecioPorNoche()) +
                             " | 📍 " + p.getCiudad());
        }

        // ═══════════════════════════════════════════════════════════
        // ORDEN ASCENDENTE Y DESCENDENTE
        // ═══════════════════════════════════════════════════════════

        System.out.println("\n\n┌──────────────────────────────────────────────────────────┐");
        System.out.println("│  7. TODAS LAS PROPIEDADES ORDENADAS POR PRECIO         │");
        System.out.println("└──────────────────────────────────────────────────────────┘\n");

        System.out.println("📈 ORDEN ASCENDENTE (Menor a Mayor):");
        var ordenadas = propiedadDao.obtenerPropiedadesOrdenadasPorPrecio();
        for (int i = 0; i < Math.min(5, ordenadas.size()); i++) {
            Propiedad p = ordenadas.get(i);
            System.out.println("   " + (i + 1) + ". $" + String.format("%,10.0f", p.getPrecioPorNoche()) +
                             " - " + p.getTitulo());
        }

        System.out.println("\n📉 ORDEN DESCENDENTE (Mayor a Menor):");
        var ordenadas_desc = propiedadDao.obtenerPropiedadesOrdenadasPorPrecioDesc();
        for (int i = 0; i < Math.min(5, ordenadas_desc.size()); i++) {
            Propiedad p = ordenadas_desc.get(i);
            System.out.println("   " + (i + 1) + ". $" + String.format("%,10.0f", p.getPrecioPorNoche()) +
                             " - " + p.getTitulo());
        }

        // ═══════════════════════════════════════════════════════════
        // RESUMEN DE COMPLEJIDADES
        // ═══════════════════════════════════════════════════════════

        System.out.println("\n\n╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║              RESUMEN DE COMPLEJIDADES                     ║");
        System.out.println("╚═══════════════════════════════════════════════════════════╝\n");

        System.out.println("📊 HASHMAP (Tabla de Hash):");
        System.out.println("   • Búsqueda por clave:     O(1)");
        System.out.println("   • Inserción:              O(1)");
        System.out.println("   • Eliminación:            O(1)");
        System.out.println("   • Orden:                  No garantizado\n");

        System.out.println("🌳 TREEMAP (Árbol Red-Black):");
        System.out.println("   • Búsqueda por clave:     O(log n)");
        System.out.println("   • Inserción:              O(log n)");
        System.out.println("   • Eliminación:            O(log n)");
        System.out.println("   • Orden:                  Automático (ordenado)");
        System.out.println("   • firstKey()/lastKey():   O(1)");
        System.out.println("   • subMap():               O(log n + m)\n");

        System.out.println("✨ VENTAJAS DE USAR AMBAS ESTRUCTURAS:");
        System.out.println("   • HashMap: Búsquedas ultrarrápidas por ID");
        System.out.println("   • TreeMap: Ordenamiento automático y consultas por rango");
        System.out.println("   • ArrayList: Mantiene el orden de inserción");
        System.out.println("   • Combinación: Aprovecha lo mejor de cada estructura\n");

        System.out.println("╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║                  FIN DE LA DEMOSTRACIÓN                   ║");
        System.out.println("╚═══════════════════════════════════════════════════════════╝\n");
    }
}

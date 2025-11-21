package vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import modelo.Propiedad;
import modelo.PropiedadData;
import modelo.UsuarioData;
import controlador.ControladorBusqueda;
import controlador.ControladorBusqueda.ResultadoBusqueda;

/**
 * Ventana de búsqueda de propiedades para viajeros
 * Permite filtrar propiedades por ciudad, precio y servicios
 */
public class BusquedaPropiedades extends JFrame {

    //   COMPONENTES DE LA INTERFAZ
    private JComboBox<String> cmbCiudad;
    private JTextField txtPrecioMin;
    private JTextField txtPrecioMax;
    private JCheckBox chkWifi;
    private JCheckBox chkCocina;
    private JCheckBox chkTV;
    private JCheckBox chkParqueadero;
    private JCheckBox chkPiscina;
    private JCheckBox chkAireAcondicionado;
    private JButton btnBuscar;
    private JButton btnLimpiar;
    private JButton btnCerrarSesion;
    private JTable tablaPropiedades;
    private DefaultTableModel modeloTabla;
    private JLabel lblResultados;

    //   CONTROLADOR Y DAO
    private ControladorBusqueda controlador;
    private PropiedadData dao;

    //   CONSTRUCTOR

    /**
     * Constructor por defecto
     */
    public BusquedaPropiedades() {
        super("Búsqueda de Propiedades - StayKonnect");
        this.dao = new PropiedadData();
        this.controlador = new ControladorBusqueda(dao);
        initComponents();
        cargarTodasPropiedades(); // Cargar todas al inicio
    }

    //   INICIALIZACIÓN DE COMPONENTES

    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 650);
        setLayout(null);
        setLocationRelativeTo(null);
        setResizable(false);

        //   TÍTULO
        JLabel lblTitulo = new JLabel("Buscar Alojamiento");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setBounds(20, 10, 300, 30);
        add(lblTitulo);

        //   INFORMACIÓN DE USUARIO
        String nombreUsuario = UsuarioData.getUsuarioActual() != null
            ? UsuarioData.getUsuarioActual().getNombre()
            : "Usuario";
        JLabel lblUsuario = new JLabel("Bienvenido: " + nombreUsuario);
        lblUsuario.setBounds(650, 15, 150, 20);
        add(lblUsuario);

        //   BOTÓN CERRAR SESIÓN
        btnCerrarSesion = new JButton("Cerrar Sesión");
        btnCerrarSesion.setBounds(770, 10, 110, 30);
        btnCerrarSesion.addActionListener(e -> cerrarSesion());
        add(btnCerrarSesion);

        //   PANEL DE FILTROS
        JPanel panelFiltros = new JPanel();
        panelFiltros.setBorder(BorderFactory.createTitledBorder("Filtros de Búsqueda"));
        panelFiltros.setBounds(20, 50, 860, 150);
        panelFiltros.setLayout(null);
        add(panelFiltros);

        // Ciudad
        JLabel lblCiudad = new JLabel("Ciudad:");
        lblCiudad.setBounds(20, 30, 80, 25);
        panelFiltros.add(lblCiudad);

        cmbCiudad = new JComboBox<>();
        cmbCiudad.addItem("Todas");
        for (String ciudad : controlador.obtenerCiudades()) {
            cmbCiudad.addItem(ciudad);
        }
        cmbCiudad.setBounds(100, 30, 150, 25);
        panelFiltros.add(cmbCiudad);

        // Precio Mínimo
        JLabel lblPrecioMin = new JLabel("Precio min:");
        lblPrecioMin.setBounds(270, 30, 80, 25);
        panelFiltros.add(lblPrecioMin);

        txtPrecioMin = new JTextField();
        txtPrecioMin.setBounds(350, 30, 100, 25);
        panelFiltros.add(txtPrecioMin);

        // Precio Máximo
        JLabel lblPrecioMax = new JLabel("Precio máx:");
        lblPrecioMax.setBounds(470, 30, 80, 25);
        panelFiltros.add(lblPrecioMax);

        txtPrecioMax = new JTextField();
        txtPrecioMax.setBounds(550, 30, 100, 25);
        panelFiltros.add(txtPrecioMax);

        // Servicios
        JLabel lblServicios = new JLabel("Servicios:");
        lblServicios.setBounds(20, 70, 100, 25);
        lblServicios.setFont(new Font("Arial", Font.BOLD, 12));
        panelFiltros.add(lblServicios);

        chkWifi = new JCheckBox("WiFi");
        chkWifi.setBounds(20, 95, 100, 25);
        panelFiltros.add(chkWifi);

        chkCocina = new JCheckBox("Cocina");
        chkCocina.setBounds(130, 95, 100, 25);
        panelFiltros.add(chkCocina);

        chkTV = new JCheckBox("TV");
        chkTV.setBounds(240, 95, 100, 25);
        panelFiltros.add(chkTV);

        chkParqueadero = new JCheckBox("Parqueadero");
        chkParqueadero.setBounds(350, 95, 120, 25);
        panelFiltros.add(chkParqueadero);

        chkPiscina = new JCheckBox("Piscina");
        chkPiscina.setBounds(480, 95, 100, 25);
        panelFiltros.add(chkPiscina);

        chkAireAcondicionado = new JCheckBox("Aire Acondicionado");
        chkAireAcondicionado.setBounds(590, 95, 150, 25);
        panelFiltros.add(chkAireAcondicionado);

        // Botones de acción
        btnBuscar = new JButton("Buscar");
        btnBuscar.setBounds(680, 30, 100, 35);
        btnBuscar.setBackground(new Color(33, 150, 243));
        btnBuscar.setForeground(Color.WHITE);
        btnBuscar.setFont(new Font("Arial", Font.BOLD, 12));
        btnBuscar.addActionListener(e -> buscarPropiedades());
        panelFiltros.add(btnBuscar);

        btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setBounds(680, 75, 100, 30);
        btnLimpiar.addActionListener(e -> limpiarFiltros());
        panelFiltros.add(btnLimpiar);

        //   ETIQUETA DE RESULTADOS
        lblResultados = new JLabel("Mostrando todas las propiedades");
        lblResultados.setBounds(20, 210, 400, 20);
        lblResultados.setFont(new Font("Arial", Font.ITALIC, 12));
        lblResultados.setForeground(new Color(76, 175, 80));
        add(lblResultados);

        //   TABLA DE RESULTADOS
        String[] columnas = {"Ciudad", "Título", "Tipo", "Capacidad", "Habitaciones", "Precio/Noche", "Servicios"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // No editable
            }
        };

        tablaPropiedades = new JTable(modeloTabla);
        tablaPropiedades.setRowHeight(25);
        tablaPropiedades.getTableHeader().setReorderingAllowed(false);

        // Configurar anchos de columnas
        tablaPropiedades.getColumnModel().getColumn(0).setPreferredWidth(80);  // Ciudad
        tablaPropiedades.getColumnModel().getColumn(1).setPreferredWidth(200); // Título
        tablaPropiedades.getColumnModel().getColumn(2).setPreferredWidth(80);  // Tipo
        tablaPropiedades.getColumnModel().getColumn(3).setPreferredWidth(70);  // Capacidad
        tablaPropiedades.getColumnModel().getColumn(4).setPreferredWidth(80);  // Habitaciones
        tablaPropiedades.getColumnModel().getColumn(5).setPreferredWidth(100); // Precio
        tablaPropiedades.getColumnModel().getColumn(6).setPreferredWidth(200); // Servicios

        JScrollPane scrollPane = new JScrollPane(tablaPropiedades);
        scrollPane.setBounds(20, 240, 860, 350);
        add(scrollPane);
    }

    //   MÉTODOS DE ACCIÓN

    /**
     * Busca propiedades aplicando los filtros seleccionados
     */
    private void buscarPropiedades() {
        // Capturar filtros
        String ciudad = (String) cmbCiudad.getSelectedItem();
        String precioMin = txtPrecioMin.getText().trim();
        String precioMax = txtPrecioMax.getText().trim();

        // Capturar servicios seleccionados
        List<String> servicios = new ArrayList<>();
        if (chkWifi.isSelected()) servicios.add("WiFi");
        if (chkCocina.isSelected()) servicios.add("Cocina");
        if (chkTV.isSelected()) servicios.add("TV");
        if (chkParqueadero.isSelected()) servicios.add("Parqueadero");
        if (chkPiscina.isSelected()) servicios.add("Piscina");
        if (chkAireAcondicionado.isSelected()) servicios.add("Aire Acondicionado");

        // Llamar al controlador
        ResultadoBusqueda resultado = controlador.buscarPropiedades(
            ciudad, precioMin, precioMax, servicios
        );

        // Verificar resultado
        if (!resultado.isExitoso()) {
            // Error en validación
            JOptionPane.showMessageDialog(
                this,
                resultado.getMensaje(),
                "Error en Búsqueda",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        // Mostrar resultados
        lblResultados.setText(resultado.getMensaje());
        mostrarPropiedades(resultado.getPropiedades());

        // Mostrar mensaje si no hay resultados
        if (resultado.getCantidad() == 0) {
            lblResultados.setForeground(Color.RED);
        } else {
            lblResultados.setForeground(new Color(76, 175, 80));
        }
    }

    /**
     * Carga todas las propiedades disponibles
     */
    private void cargarTodasPropiedades() {
        ResultadoBusqueda resultado = controlador.obtenerTodasPropiedades();
        lblResultados.setText(resultado.getMensaje());
        mostrarPropiedades(resultado.getPropiedades());
    }

    /**
     * Muestra las propiedades en la tabla
     */
    private void mostrarPropiedades(List<Propiedad> propiedades) {
        // Limpiar tabla
        modeloTabla.setRowCount(0);

        // Agregar propiedades
        if (propiedades != null) {
            for (Propiedad p : propiedades) {
                Object[] fila = {
                    p.getCiudad(),
                    p.getTitulo(),
                    p.getTipo(),
                    p.getCapacidad() + " pers.",
                    p.getHabitaciones(),
                    String.format("$%,.0f", p.getPrecioPorNoche()),
                    p.getServiciosTexto()
                };
                modeloTabla.addRow(fila);
            }
        }
    }

    /**
     * Limpia todos los filtros y recarga todas las propiedades
     */
    private void limpiarFiltros() {
        cmbCiudad.setSelectedIndex(0);
        txtPrecioMin.setText("");
        txtPrecioMax.setText("");
        chkWifi.setSelected(false);
        chkCocina.setSelected(false);
        chkTV.setSelected(false);
        chkParqueadero.setSelected(false);
        chkPiscina.setSelected(false);
        chkAireAcondicionado.setSelected(false);

        cargarTodasPropiedades();
    }

    /**
     * Cierra la sesión del usuario y vuelve al login
     */
    private void cerrarSesion() {
        int confirmacion = JOptionPane.showConfirmDialog(
            this,
            "¿Está seguro que desea cerrar sesión?",
            "Confirmar Cierre de Sesión",
            JOptionPane.YES_NO_OPTION
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            UsuarioData.cerrarSesion();
            InicioSesion login = new InicioSesion();
            login.setVisible(true);
            this.dispose();
        }
    }
}

package vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import modelo.Propiedad;
import modelo.PropiedadData;
import modelo.Usuario;
import modelo.UsuarioData;
import controlador.ControladorBusqueda;
import controlador.ControladorBusqueda.ResultadoBusqueda;

/**
 * Ventana principal para anfitriones
 * Permite gestionar propiedades: ver, registrar, editar
 */
public class MenuAnfitrion extends JFrame {

    //   COMPONENTES DE LA INTERFAZ
    private JLabel lblBienvenida;
    private JTable tablaPropiedades;
    private DefaultTableModel modeloTabla;
    private JButton btnRegistrarPropiedad;
    private JButton btnActualizar;
    private JButton btnCerrarSesion;
    private JLabel lblEstadisticas;

    //   CONTROLADOR Y DAO
    private ControladorBusqueda controlador;
    private PropiedadData dao;
    private Usuario usuarioActual;

    //   CONSTRUCTOR

    /**
     * Constructor por defecto
     */
    public MenuAnfitrion() {
        super("Menú de Anfitrión - StayKonnect");
        this.dao = new PropiedadData();
        this.controlador = new ControladorBusqueda(dao);
        this.usuarioActual = UsuarioData.getUsuarioActual();
        initComponents();
        cargarPropiedadesAnfitrion();
    }

    //   INICIALIZACIÓN DE COMPONENTES

    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLayout(null);
        setLocationRelativeTo(null);
        setResizable(false);

        //   TÍTULO Y BIENVENIDA
        JLabel lblTitulo = new JLabel("Panel de Anfitrión");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setBounds(20, 10, 300, 30);
        add(lblTitulo);

        String nombreAnfitrion = usuarioActual != null ? usuarioActual.getNombre() : "Anfitrión";
        lblBienvenida = new JLabel("Bienvenido: " + nombreAnfitrion);
        lblBienvenida.setFont(new Font("Arial", Font.PLAIN, 14));
        lblBienvenida.setBounds(20, 45, 400, 25);
        add(lblBienvenida);

        //   BOTÓN CERRAR SESIÓN
        btnCerrarSesion = new JButton("Cerrar Sesión");
        btnCerrarSesion.setBounds(770, 15, 110, 30);
        btnCerrarSesion.addActionListener(e -> cerrarSesion());
        add(btnCerrarSesion);

        //   PANEL DE ACCIONES
        JPanel panelAcciones = new JPanel();
        panelAcciones.setBorder(BorderFactory.createTitledBorder("Acciones"));
        panelAcciones.setBounds(20, 80, 860, 70);
        panelAcciones.setLayout(null);
        add(panelAcciones);

        btnRegistrarPropiedad = new JButton("Registrar Nueva Propiedad");
        btnRegistrarPropiedad.setBounds(20, 25, 200, 35);
        btnRegistrarPropiedad.setBackground(new Color(76, 175, 80));
        btnRegistrarPropiedad.setForeground(Color.WHITE);
        btnRegistrarPropiedad.setFont(new Font("Arial", Font.BOLD, 12));
        btnRegistrarPropiedad.addActionListener(e -> registrarPropiedad());
        panelAcciones.add(btnRegistrarPropiedad);

        btnActualizar = new JButton("Actualizar Lista");
        btnActualizar.setBounds(240, 25, 150, 35);
        btnActualizar.setBackground(new Color(33, 150, 243));
        btnActualizar.setForeground(Color.WHITE);
        btnActualizar.setFont(new Font("Arial", Font.BOLD, 12));
        btnActualizar.addActionListener(e -> cargarPropiedadesAnfitrion());
        panelAcciones.add(btnActualizar);

        JButton btnVerTodasPropiedades = new JButton("Ver Todas las Propiedades");
        btnVerTodasPropiedades.setBounds(410, 25, 200, 35);
        btnVerTodasPropiedades.addActionListener(e -> verTodasPropiedades());
        panelAcciones.add(btnVerTodasPropiedades);

        //   ESTADÍSTICAS
        lblEstadisticas = new JLabel("Propiedades registradas: 0");
        lblEstadisticas.setBounds(20, 160, 400, 20);
        lblEstadisticas.setFont(new Font("Arial", Font.ITALIC, 12));
        lblEstadisticas.setForeground(new Color(76, 175, 80));
        add(lblEstadisticas);

        //   TABLA DE PROPIEDADES DEL ANFITRIÓN
        JLabel lblMisPropiedades = new JLabel("Mis Propiedades:");
        lblMisPropiedades.setFont(new Font("Arial", Font.BOLD, 14));
        lblMisPropiedades.setBounds(20, 185, 200, 20);
        add(lblMisPropiedades);

        String[] columnas = {"Ciudad", "Título", "Tipo", "Capacidad", "Habitaciones", "Precio/Noche", "Disponible"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaPropiedades = new JTable(modeloTabla);
        tablaPropiedades.setRowHeight(25);
        tablaPropiedades.getTableHeader().setReorderingAllowed(false);

        // Configurar anchos
        tablaPropiedades.getColumnModel().getColumn(0).setPreferredWidth(80);
        tablaPropiedades.getColumnModel().getColumn(1).setPreferredWidth(250);
        tablaPropiedades.getColumnModel().getColumn(2).setPreferredWidth(80);
        tablaPropiedades.getColumnModel().getColumn(3).setPreferredWidth(70);
        tablaPropiedades.getColumnModel().getColumn(4).setPreferredWidth(80);
        tablaPropiedades.getColumnModel().getColumn(5).setPreferredWidth(100);
        tablaPropiedades.getColumnModel().getColumn(6).setPreferredWidth(80);

        JScrollPane scrollPane = new JScrollPane(tablaPropiedades);
        scrollPane.setBounds(20, 210, 860, 330);
        add(scrollPane);
    }

    //   MÉTODOS DE ACCIÓN

    /**
     * Carga las propiedades del anfitrión actual
     */
    private void cargarPropiedadesAnfitrion() {
        if (usuarioActual == null) {
            JOptionPane.showMessageDialog(
                this,
                "No hay usuario logueado",
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        // Obtener propiedades del anfitrión
        ResultadoBusqueda resultado = controlador.obtenerPropiedadesPorAnfitrion(
            usuarioActual.getUsuarioId()
        );

        // Actualizar estadísticas
        lblEstadisticas.setText(resultado.getMensaje());

        // Mostrar en tabla
        mostrarPropiedades(resultado.getPropiedades());
    }

    /**
     * Muestra propiedades en la tabla
     */
    private void mostrarPropiedades(List<Propiedad> propiedades) {
        modeloTabla.setRowCount(0);

        if (propiedades != null) {
            for (Propiedad p : propiedades) {
                Object[] fila = {
                    p.getCiudad(),
                    p.getTitulo(),
                    p.getTipo(),
                    p.getCapacidad() + " pers.",
                    p.getHabitaciones(),
                    String.format("$%,.0f", p.getPrecioPorNoche()),
                    p.isDisponible() ? "Sí" : "No"
                };
                modeloTabla.addRow(fila);
            }
        }
    }

    /**
     * Abre el formulario para registrar una nueva propiedad
     */
    private void registrarPropiedad() {
        if (usuarioActual == null) {
            JOptionPane.showMessageDialog(
                this,
                "Debe iniciar sesión para registrar propiedades",
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        // Crear formulario de registro
        JDialog dialogo = new JDialog(this, "Registrar Nueva Propiedad", true);
        dialogo.setSize(500, 550);
        dialogo.setLayout(null);
        dialogo.setLocationRelativeTo(this);

        // Campos del formulario
        JLabel lblTitulo = new JLabel("Título:");
        lblTitulo.setBounds(30, 20, 100, 25);
        dialogo.add(lblTitulo);
        JTextField txtTitulo = new JTextField();
        txtTitulo.setBounds(150, 20, 320, 25);
        dialogo.add(txtTitulo);

        JLabel lblDescripcion = new JLabel("Descripción:");
        lblDescripcion.setBounds(30, 60, 100, 25);
        dialogo.add(lblDescripcion);
        JTextArea txtDescripcion = new JTextArea();
        txtDescripcion.setLineWrap(true);
        JScrollPane scrollDesc = new JScrollPane(txtDescripcion);
        scrollDesc.setBounds(150, 60, 320, 60);
        dialogo.add(scrollDesc);

        JLabel lblCiudad = new JLabel("Ciudad:");
        lblCiudad.setBounds(30, 135, 100, 25);
        dialogo.add(lblCiudad);
        JTextField txtCiudad = new JTextField();
        txtCiudad.setBounds(150, 135, 320, 25);
        dialogo.add(txtCiudad);

        JLabel lblDireccion = new JLabel("Dirección:");
        lblDireccion.setBounds(30, 175, 100, 25);
        dialogo.add(lblDireccion);
        JTextField txtDireccion = new JTextField();
        txtDireccion.setBounds(150, 175, 320, 25);
        dialogo.add(txtDireccion);

        JLabel lblTipo = new JLabel("Tipo:");
        lblTipo.setBounds(30, 215, 100, 25);
        dialogo.add(lblTipo);
        JComboBox<String> cmbTipo = new JComboBox<>(new String[]{"Apartamento", "Casa", "Habitación", "Loft", "Studio"});
        cmbTipo.setBounds(150, 215, 150, 25);
        dialogo.add(cmbTipo);

        JLabel lblCapacidad = new JLabel("Capacidad:");
        lblCapacidad.setBounds(30, 255, 100, 25);
        dialogo.add(lblCapacidad);
        JSpinner spnCapacidad = new JSpinner(new SpinnerNumberModel(2, 1, 20, 1));
        spnCapacidad.setBounds(150, 255, 60, 25);
        dialogo.add(spnCapacidad);

        JLabel lblHabitaciones = new JLabel("Habitaciones:");
        lblHabitaciones.setBounds(30, 295, 100, 25);
        dialogo.add(lblHabitaciones);
        JSpinner spnHabitaciones = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
        spnHabitaciones.setBounds(150, 295, 60, 25);
        dialogo.add(spnHabitaciones);

        JLabel lblBanos = new JLabel("Baños:");
        lblBanos.setBounds(250, 295, 50, 25);
        dialogo.add(lblBanos);
        JSpinner spnBanos = new JSpinner(new SpinnerNumberModel(1, 1, 5, 1));
        spnBanos.setBounds(310, 295, 60, 25);
        dialogo.add(spnBanos);

        JLabel lblPrecio = new JLabel("Precio/Noche:");
        lblPrecio.setBounds(30, 335, 100, 25);
        dialogo.add(lblPrecio);
        JTextField txtPrecio = new JTextField();
        txtPrecio.setBounds(150, 335, 150, 25);
        dialogo.add(txtPrecio);

        // Servicios
        JLabel lblServicios = new JLabel("Servicios:");
        lblServicios.setBounds(30, 375, 100, 25);
        dialogo.add(lblServicios);

        JCheckBox chkWifi = new JCheckBox("WiFi");
        chkWifi.setBounds(150, 375, 80, 25);
        dialogo.add(chkWifi);

        JCheckBox chkCocina = new JCheckBox("Cocina");
        chkCocina.setBounds(240, 375, 100, 25);
        dialogo.add(chkCocina);

        JCheckBox chkTV = new JCheckBox("TV");
        chkTV.setBounds(350, 375, 70, 25);
        dialogo.add(chkTV);

        JCheckBox chkParqueadero = new JCheckBox("Parqueadero");
        chkParqueadero.setBounds(150, 405, 120, 25);
        dialogo.add(chkParqueadero);

        JCheckBox chkPiscina = new JCheckBox("Piscina");
        chkPiscina.setBounds(280, 405, 100, 25);
        dialogo.add(chkPiscina);

        // Botones
        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(150, 460, 120, 35);
        btnGuardar.setBackground(new Color(76, 175, 80));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.addActionListener(e -> {
            // Validar y guardar
            try {
                String titulo = txtTitulo.getText().trim();
                String descripcion = txtDescripcion.getText().trim();
                String ciudad = txtCiudad.getText().trim();
                String direccion = txtDireccion.getText().trim();
                String tipo = (String) cmbTipo.getSelectedItem();
                int capacidad = (int) spnCapacidad.getValue();
                int habitaciones = (int) spnHabitaciones.getValue();
                int banos = (int) spnBanos.getValue();
                double precio = Double.parseDouble(txtPrecio.getText().trim());

                if (titulo.isEmpty() || ciudad.isEmpty()) {
                    JOptionPane.showMessageDialog(dialogo, "Complete los campos obligatorios");
                    return;
                }

                // Crear propiedad
                Propiedad nuevaPropiedad = new Propiedad(
                    usuarioActual.getUsuarioId(),
                    titulo, descripcion, ciudad, direccion,
                    tipo, capacidad, habitaciones, banos, precio
                );

                // Agregar servicios
                if (chkWifi.isSelected()) nuevaPropiedad.agregarServicio("WiFi");
                if (chkCocina.isSelected()) nuevaPropiedad.agregarServicio("Cocina");
                if (chkTV.isSelected()) nuevaPropiedad.agregarServicio("TV");
                if (chkParqueadero.isSelected()) nuevaPropiedad.agregarServicio("Parqueadero");
                if (chkPiscina.isSelected()) nuevaPropiedad.agregarServicio("Piscina");

                // Registrar
                dao.registrarPropiedad(nuevaPropiedad);

                JOptionPane.showMessageDialog(dialogo, "Propiedad registrada exitosamente");
                dialogo.dispose();
                cargarPropiedadesAnfitrion();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialogo, "El precio debe ser un número válido");
            }
        });
        dialogo.add(btnGuardar);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(290, 460, 120, 35);
        btnCancelar.addActionListener(e -> dialogo.dispose());
        dialogo.add(btnCancelar);

        dialogo.setVisible(true);
    }

    /**
     * Ver todas las propiedades del sistema
     */
    private void verTodasPropiedades() {
        BusquedaPropiedades busqueda = new BusquedaPropiedades();
        busqueda.setVisible(true);
        this.dispose();
    }

    /**
     * Cierra sesión y vuelve al login
     */
    private void cerrarSesion() {
        int confirmacion = JOptionPane.showConfirmDialog(
            this,
            "¿Está seguro que desea cerrar sesión?",
            "Confirmar",
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

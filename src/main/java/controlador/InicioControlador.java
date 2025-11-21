package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import modelo.Usuario;
import modelo.UsuarioData;
import vista.InicioSesion;
import vista.MenuPrincipal;
import vista.Registro;

public class InicioControlador implements ActionListener {

    private InicioSesion vista;
    private UsuarioData dao;

    public InicioControlador(InicioSesion vista, UsuarioData dao) {
        this.vista = vista;
        this.dao = dao;

        vista.addIngresarListener(this);
        vista.addRegistrarListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        switch (e.getActionCommand()) {

            case "INGRESAR":
                String correo = vista.getCorreo();
                String contrasena = vista.getContrasena();
                Usuario usuario = dao.buscarUsuario(correo, contrasena);

                if (usuario != null) {
                    JOptionPane.showMessageDialog(vista, "Bienvenido " + usuario.getNombre());
                    MenuPrincipal menu = new MenuPrincipal();
                    menu.setVisible(true);
                    vista.dispose();
                } else {
                    JOptionPane.showMessageDialog(vista, "Correo o contraseña incorrectos");
                }
                break;

            case "REGISTRAR":
                Registro reg = new Registro(dao);
                reg.setVisible(true);
                vista.dispose();
                break;
        }
    }
}

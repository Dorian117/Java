package controlador;

import vista.MenuPrincipal;
import vista.MenuPropiedades;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPrincipalControlador implements ActionListener {

    private MenuPrincipal vista;

    public MenuPrincipalControlador(MenuPrincipal vista) {
        this.vista = vista;
        this.vista.addGestionPropiedadesListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("GESTION_PROPIEDADES".equals(e.getActionCommand())) {

            MenuPropiedades mp = new MenuPropiedades();
            mp.setLocationRelativeTo(vista);
            mp.setVisible(true);

            vista.dispose();
        }
    }
}


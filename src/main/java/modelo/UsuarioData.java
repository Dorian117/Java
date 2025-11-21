package modelo;

import java.util.ArrayList;
import java.util.List;

public class UsuarioData {

    private static List<Usuario> listaUsuarios = new ArrayList<>();

    public UsuarioData() {
        if (listaUsuarios.isEmpty()) {
            listaUsuarios.add(new Usuario("Admin", "admin@admin.com", "1234", "admin"));
        }
    }

    public void registrarUsuario(Usuario usuario) {
        listaUsuarios.add(usuario);
    }

    public Usuario buscarUsuario(String correo, String contrasena) {
        for (Usuario u : listaUsuarios) {
            if (u.getCorreo().equals(correo) && u.getContrasena().equals(contrasena)) {
                return u;
            }
        }
        return null;
    }
}

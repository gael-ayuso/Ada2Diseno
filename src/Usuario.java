import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Usuario {
    private final String usuario;
    private final String contrasena;

    public Usuario(String usuario, String contrasena) {
        this.usuario = usuario;
        this.contrasena = contrasena;
    }

    public boolean login(String usuario, String contrasena) {
        return this.usuario.equals(usuario) && this.contrasena.equals(contrasena);
    }

    public String getUsuario() {
        return usuario;
    }


}

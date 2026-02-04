package disenio.ada2;

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
    public String getContrasena() {
        return contrasena;
    }

    @Override
    public String toString() {
        return usuario + "," + contrasena;
    }
}

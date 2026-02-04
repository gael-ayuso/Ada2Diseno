import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class LoginFrame extends JFrame {

    private JTextField txtUsuario;
    private JPasswordField txtPassword;

    public LoginFrame() {
        setTitle("Login - Sistema de Calificaciones");
        setSize(350, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 2, 10, 10));

        add(new JLabel("Usuario:"));
        txtUsuario = new JTextField();
        add(txtUsuario);

        add(new JLabel("Contraseña:"));
        txtPassword = new JPasswordField();
        add(txtPassword);

        JButton btnLogin = new JButton("Iniciar sesión");
        add(new JLabel()); // espacio vacío
        add(btnLogin);

        btnLogin.addActionListener(e -> autenticar());

        setVisible(true);
    }

    private void autenticar() {
        String usuario = txtUsuario.getText();
        String password = new String(txtPassword.getPassword());

        try {
            UsersReader reader = new UsersReader("Usuarios.csv");
            ArrayList<Usuario> usuarios = reader.getUsers();

            for (Usuario u : usuarios) {
                if (usuario.equals(u.getUsuario()) &&
                        Encriptador.verificarConSalt(password, u.getContrasena())) {

                    JOptionPane.showMessageDialog(this,
                            "Bienvenido " + usuario);

                    dispose(); // cerrar login
                    new MenuPrincipal(); // siguiente ventana
                    return;
                }
            }

            JOptionPane.showMessageDialog(this,
                    "Usuario o contraseña incorrectos",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);

        } catch (FileNotFoundException | NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
    }
}
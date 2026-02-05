package disenio.ada2.ui;

import disenio.ada2.Encriptador;
import disenio.ada2.UsersReader;
import disenio.ada2.Usuario;

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
        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 10));

        panel.add(new JLabel("Usuario:"));
        txtUsuario = new JTextField();
        panel.add(txtUsuario);

        panel.add(new JLabel("Contraseña:"));
        txtPassword = new JPasswordField();
        panel.add(txtPassword);

        JButton btnLogin = new JButton("Iniciar sesión");
        panel.add(new JLabel()); // espacio vacío
        panel.add(btnLogin);

        btnLogin.addActionListener(e -> autenticar());
        add(panel);
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
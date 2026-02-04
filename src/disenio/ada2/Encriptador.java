package disenio.ada2;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Scanner;

public class Encriptador {

    private static String generarPepper(){
        return "holamundo12345";
    }

    public static byte[] generarSalt(){
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    public static String generarHashConSalt(String password, byte[] salt) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(salt);
        md.update(generarPepper().getBytes(StandardCharsets.UTF_8));
        byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(salt)+"$"+
                Base64.getEncoder().encodeToString(hashedPassword);
    }

    //Salt y hashedPassword estara en la base de datos
    public static boolean verificarConSalt(String password, String hashedPassword) throws NoSuchAlgorithmException {
        String[] hashes = hashedPassword.split("\\$");
        byte [] salt = Base64.getDecoder().decode(hashes[0]);
        String hash = generarHashConSalt(password, salt);
        return hash.equals(hashedPassword);
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Usuario> usuarios = new ArrayList<>();
        while(true){
            byte[] salt = generarSalt();
            System.out.println("Nombre del usuario: ");
            String username = scanner.nextLine();
            if(username.equals("salir")){
                break;
            }
            System.out.println("Contrasenia: ");
            String password = scanner.nextLine();
            usuarios.add(new Usuario(username, generarHashConSalt(password, salt)));
            if(password.equals("salir")){
                break;
            }
        }
        for(Usuario usuario : usuarios){
            System.out.println(usuario);
        }
    }

}
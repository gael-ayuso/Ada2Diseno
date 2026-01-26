import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Scanner;

public class UsersReader {
    private final ArrayList<Usuario> users;

    public UsersReader(String path) throws FileNotFoundException {
        this.users = usersReader(path);
    }

    public UsersReader() {
        this.users = new ArrayList<>();
    }

    private ArrayList<Usuario> usersReader(String filepath) {
        String line;
        ArrayList<Usuario> users = new ArrayList<>();
        try(Scanner scanner = new Scanner(new File(filepath))) {
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                String[] split = line.split(",");
                if(split.length == 2) {
                    users.add(new Usuario(split[0], split[1]));
                }
            }
        }catch (FileNotFoundException e){
            throw new RuntimeException(e);
        }
        return users; 
    }

    public static void writeUsers(String fileName, List<Usuario> usuarios) {
        try {
            Scanner scanner = new Scanner(new File(fileName));
            FileWriter fileWriter;
            fileWriter = new FileWriter(fileName, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            if(!scanner.hasNextLine()) {
                bufferedWriter.write("Usuarios,Contrasenas,");
                bufferedWriter.newLine();
            }
            scanner.close();
            for (Usuario usuario : usuarios) {
                bufferedWriter.write(usuario.getUsuario() + ","
                        + usuario.getContrasena() + ",");
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
            scanner.close();
            System.out.println("Archivo guardado exitosamente");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Usuario> getUsers() {
        return users;
    }

    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            UsersReader usersReader = new UsersReader();
            while(true){
                byte[] salt = Encriptador.generarSalt();
                System.out.print("Nombre del usuario (o 'salir'): ");
                String username = scanner.nextLine();
                if(username.equals("salir")){
                    break;
                }
                System.out.print("Contrasenia: ");
                String password = scanner.nextLine();

                usersReader.getUsers().add(new Usuario(
                        username,
                        Encriptador.generarHashConSalt(password, salt)
                ));
            }
            scanner.close();
            for (Usuario usuario : usersReader.getUsers()) {
                if(usuario.getUsuario().equals("Pepe")){
                    System.out.println(Encriptador.verificarConSalt("12345", usuario.getContrasena()));
                }

            }

            usersReader.writeUsers("Usuarios.csv", usersReader.getUsers());


        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }



    }


}

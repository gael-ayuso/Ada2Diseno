import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class UsersReader {
    private final ArrayList<Usuario> users;

    public UsersReader(String path) throws FileNotFoundException {
        this.users = usersReader(path);
    }

    private ArrayList<Usuario> usersReader(String filepath) {
        String line;
        ArrayList<Usuario> users = new ArrayList<>();
        try(Scanner scanner = new Scanner(new File(filepath))) {
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

    public ArrayList<Usuario> getUsers() {
        return users;
    }

    public static void main(String[] args) {

        try{
            UsersReader usersReader = new UsersReader("Usuarios.csv");
            ArrayList<Usuario> users = usersReader.getUsers();
            for (Usuario usuario : users) {
                if(usuario.login("Juan", "12345")) {
                    System.out.println(usuario);
                }else {
                    System.out.println("No se encuentra el usuario o la contrasenia es incorrecta");
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


}

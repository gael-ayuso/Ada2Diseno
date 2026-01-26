import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try{
            UsersReader usersReader = new UsersReader("Usuarios.csv");
            ArrayList<Usuario> usuarios = usersReader.getUsers();
            while(true){
                System.out.println("Ingrese su usuario:");
                String usuario = scanner.nextLine();
                System.out.println("Ingrese su password:");
                String password = scanner.nextLine();
                for (Usuario usuario1 : usuarios) {
                    if(usuario.equals(usuario1.getUsuario()) && Encriptador.verificarConSalt(password, usuario1.getContrasena())){
                        List<Alumno> alumnos = CsvReader.read("src/Libro2.csv"); // <==== Aca se agrega la direccion del archivo de la lista
                        for (Alumno alumno : alumnos) {

                            System.out.println("Asignar calificacion a " + alumno.getMatricula() + " en Diseño de Software: ");
                            while(true){
                                int calificacionAsignatura = scanner.nextInt();
                                if (calificacionAsignatura < 0 || calificacionAsignatura > 100) {
                                    System.out.println("Calificacion invalida. Debe estar entre 0 y 100.");
                                }else{
                                    alumno.setCalificacion(calificacionAsignatura);
                                    break;
                                }
                            }
                        }
                        scanner.close();
                        System.out.println("\n=== ALUMNOS CON CALIFICACIONES ===");
                        for (Alumno alumno : alumnos) {
                            System.out.println(alumno);
                        }

                        CsvReader.writeToFile("Calificaciones.csv", alumnos);

                        ReportePDF.generarReporte("ReporteCalificaciones.pdf", alumnos);

                        break;
                    }else{
                        System.out.println("Usuario o contrasena invalidas");
                        break;
                    }
                }
                System.out.println("Desea salir (exit)");
                String line = scanner.nextLine();
                if(line.equals("exit")){
                    break;
                }
            }
        }catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }


    }
}
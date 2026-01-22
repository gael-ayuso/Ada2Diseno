import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
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
    }
}
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        List<Alumno> alumnos = CsvReader.read("src/Libro2.csv");
        Scanner scanner = new Scanner(System.in);

        for(Alumno alumno : alumnos) {
            System.out.println(alumno);
            String materia;

            while(true) {
                System.out.print("Ingrese la materia: ");
                materia = scanner.nextLine().trim();
                if(materia.equalsIgnoreCase("continuar")) {
                    break;
                }
                if(!materia.isEmpty()) {
                    alumno.addAsignatura(new Asignatura(materia));
                }
            }

            for(Asignatura asignatura : alumno.getAsignaturas()) {
                int calificacion = -1;

                while(calificacion < 0 || calificacion > 100) {
                    System.out.println("Ingrese la calificacion para " + asignatura.getNombreAsignatura() + ": ");

                    if(scanner.hasNextInt()) {
                        calificacion = scanner.nextInt();
                        scanner.nextLine();

                        if(calificacion < 0 || calificacion > 100) {
                            System.out.println("Calificacion invalida. Debe estar entre 0 y 100.");
                        }
                    } else {
                        System.out.println("Por favor ingrese un numero valido.");
                        scanner.nextLine();
                    }
                }

                asignatura.setCalificacion(calificacion);
            }
        }

        scanner.close();

        System.out.println("\n=== ALUMNOS CON CALIFICACIONES ===");
        for(Alumno alumno : alumnos) {
            System.out.println(alumno);
        }

        CsvReader.writeToFile("Calificaciones.csv", alumnos);

    }
}
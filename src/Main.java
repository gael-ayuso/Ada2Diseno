import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese la direccion de la lista de alumnos:");
        String filepath = scanner.nextLine();
        List<Alumno> alumnos = CsvReader.read(filepath); // <==== Aca se agrega la direccion del archivo de la lista
        for (Alumno alumno : alumnos) {
            System.out.println(alumno);
            System.out.println("Si desea dejar de agregar materias escriba \"continuar\"");
            while (true) {
                System.out.print("Ingrese la materia: ");
                String materia = scanner.nextLine().trim();
                if (materia.equalsIgnoreCase("continuar")) {
                    break;
                }
                if (!materia.isEmpty()) {
                    alumno.addAsignatura(new Asignatura(materia));
                }
            }
            for (Asignatura asignatura : alumno.getAsignaturas()) {
                int calificacion = -1;
                while (calificacion < 0 || calificacion > 100) {
                    System.out.println("Ingrese la calificacion para " + asignatura.getNombreAsignatura() + ": ");
                    if (scanner.hasNextInt()) {
                        calificacion = scanner.nextInt();
                        scanner.nextLine();
                        if (calificacion < 0 || calificacion > 100) {
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
        for (Alumno alumno : alumnos) {
            System.out.println(alumno);
        }

        CsvReader.writeToFile("Calificaciones.csv", alumnos);

    }
}
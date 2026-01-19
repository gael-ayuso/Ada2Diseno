import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CsvReader {

    public static List<Alumno> read(String filePath) {
        String line;
        List<Alumno> alumnos = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(filePath))) {
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                String[] tokens = line.split(",");
                if (tokens.length == 4) {
                    alumnos.add(new Alumno(Integer.parseInt(tokens[0]), tokens[1], tokens[2], tokens[3]));
                }
            }
        } catch (NumberFormatException | FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return alumnos;
    }

    public static void writeToFile(String fileName, List<Alumno> alumnos) {
        try {
            int maxAsignaturas = 0;
            FileWriter fileWriter;
            fileWriter = new FileWriter(fileName);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (Alumno alumno : alumnos) {
                if (alumno.getAsignaturas() != null) {
                    maxAsignaturas = Math.max(maxAsignaturas, alumno.getAsignaturas().size());
                }
            }
            for (Alumno alumno : alumnos) {
                bufferedWriter.write(alumno.getMatricula() + ",");
                int asignaturasActuales = 0;
                if (alumno.getAsignaturas() != null) {
                    for (Asignatura asignatura : alumno.getAsignaturas()) {
                        bufferedWriter.write(asignatura.getNombreAsignatura() + "," + asignatura.getCalificacion() + ",");
                        asignaturasActuales++;
                    }
                    for (int i = asignaturasActuales; i < maxAsignaturas; i++) {
                        bufferedWriter.write(",,"); // Rellenar las columnas vacias de los otros que no tienen tantas materias
                    }
                    bufferedWriter.newLine();
                }
            }
            bufferedWriter.close();
            System.out.println("Archivo guardado exitosamente");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}

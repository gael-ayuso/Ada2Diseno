import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CsvReader {

    public static List<Alumno> read(String filePath) throws IOException {
        String line;
        List<Alumno> alumnos = new ArrayList<>();
        try(Scanner scanner = new Scanner(new File(filePath))) {
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                Alumno alumno;
                line = scanner.nextLine();
                String[] tokens = line.split(",");
                if(tokens.length == 4) {
                    alumnos.add(new Alumno(Integer.parseInt(tokens[0]),tokens[1],tokens[2],tokens[3]));
                }
            }
        }catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return alumnos;
    }

    public static void write(List<Alumno> alumnos, String filePath) throws IOException {

    }


}

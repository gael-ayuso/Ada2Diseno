import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.FileOutputStream;
import java.util.List;

public class ReportePDF {

    public static void generarReporte(String ruta, List<Alumno> alumnos) {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(ruta));
            document.open();

            // Título
            Font tituloFont = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
            Paragraph titulo = new Paragraph("Reporte de Calificaciones", tituloFont);
            titulo.setAlignment(Element.ALIGN_CENTER);
            titulo.setSpacingAfter(20);
            document.add(titulo);

            // Tabla con 3 columnas
            PdfPTable table = new PdfPTable(3);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{3, 6, 3});

            // Encabezados
            agregarCeldaEncabezado(table, "Matrícula");
            agregarCeldaEncabezado(table, "Nombre Completo");
            agregarCeldaEncabezado(table, "Calificación");

            // Datos
            for (Alumno alumno : alumnos) {
                table.addCell(String.valueOf(alumno.getMatricula()));
                table.addCell(extraerNombre(alumno.toString()));
                table.addCell(String.valueOf(alumno.getCalificacion()));
            }

            document.add(table);
            document.close();

            System.out.println("PDF generado correctamente ✅");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void agregarCeldaEncabezado(PdfPTable table, String texto) {
        Font font = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
        PdfPCell cell = new PdfPCell(new Phrase(texto, font));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        table.addCell(cell);
    }

    private static String extraerNombre(String texto) {
        // Ejemplo del toString:
        // matricula= 123, nombre=Juan Pérez López, calificación= 90
        int inicio = texto.indexOf("nombre=") + 7;
        int fin = texto.indexOf(", calificación=");
        return texto.substring(inicio, fin);
    }
}

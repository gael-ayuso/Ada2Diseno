package disenio.ada2.ui;

import disenio.ada2.Alumno;
import disenio.ada2.CsvReader;
import disenio.ada2.ReportePDF;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Vector;

public class MenuPrincipal extends JFrame {
    private JTable table;
    private Vector<Alumno> alumnos;

    public MenuPrincipal() {
        setTitle("Menú Principal");
        setSize(700, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        JPanel panel = new JPanel(new GridLayout(3,1));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 10));
        panel.add(new JLabel("Calificaciones alumnos", SwingConstants.CENTER), BorderLayout.NORTH);

        alumnos = new Vector<>(CsvReader.read("src/disenio/ada2/Libro2.csv"));

        aplicarCalificacionesDesdeArchivo();

        JScrollPane scrollPane = getJScrollPane(alumnos);
        panel.add(scrollPane, BorderLayout.CENTER);

        refrescarTablaDesdeAlumnos();
        JPanel panelBotones = new JPanel(new FlowLayout());
        JButton btnSalir = new JButton("Salir");
        JButton btnGenerarPDF = new JButton("Generar Reporte PDF");
        JButton btnAnadirAlumno = new JButton("Agregar Alumno");
        JButton btnGuardar = new JButton("Guardar Calificaciones");
        panelBotones.add(btnSalir);
        panelBotones.add(btnGenerarPDF);
        panelBotones.add(btnAnadirAlumno);
        panelBotones.add(btnGuardar);
        panel.add(panelBotones, BorderLayout.SOUTH);

        btnSalir.addActionListener(e -> System.exit(0));
        btnGuardar.addActionListener(e -> guardarCambios());
        btnGenerarPDF.addActionListener(e -> generarPDF());
        btnAnadirAlumno.addActionListener(e -> anadirAlumno());


        add(panel);
        setVisible(true);
    }

    private JScrollPane getJScrollPane(Vector<Alumno> alumnos) {
        Vector<String> columnas = new Vector<>();
        columnas.add("Matricula");
        columnas.add("Nombre");
        columnas.add("Primer Apellido");
        columnas.add("Segundo Apellido");
        columnas.add("Calificacion");

        Vector<Vector<Object>> datos = new Vector<>();
        for (Alumno alumno : alumnos) {
            Vector<Object> fila = new Vector<>();
            fila.add(alumno.getMatricula());
            fila.add(alumno.getNombre());
            fila.add(alumno.getPrimer_Apellido());
            fila.add(alumno.getSegundo_Apellido());
            fila.add(alumno.getCalificacion());
            datos.add(fila);
        }
        DefaultTableModel modelo = new DefaultTableModel(datos, columnas){
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4;
            }
        };

        this.table = new JTable(datos, columnas);
        return new JScrollPane(table);
    }

    private void refrescarTablaDesdeAlumnos() {
        if (table == null) return;

        for (int i = 0; i < alumnos.size(); i++) {
            table.setValueAt(alumnos.get(i).getCalificacion(), i, 4);
        }
    }

    private void aplicarCalificacionesDesdeArchivo() {
        Map<Integer, Integer> calificacionesPorMatricula = cargarCalificacionesDesdeCSV("Calificaciones.csv");
        if (calificacionesPorMatricula.isEmpty()) return;

        for (Alumno alumno : alumnos) {
            Integer cal = calificacionesPorMatricula.get(alumno.getMatricula());
            if (cal != null) {
                alumno.setCalificacion(cal);
            }
        }
    }

    private Map<Integer, Integer> cargarCalificacionesDesdeCSV(String filePath) {
        Map<Integer, Integer> mapa = new HashMap<>();

        File file = new File(filePath);
        if (!file.exists()) {
            return mapa;
        }

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) continue;

                String[] tokens = line.split(",");

                if (tokens.length > 0 && tokens[0].equalsIgnoreCase("Matricula")) {
                    continue;
                }

                if (tokens.length >= 3) {
                    try {
                        int matricula = Integer.parseInt(tokens[0].trim());
                        int calificacion = Integer.parseInt(tokens[2].trim());
                        mapa.put(matricula, calificacion);
                    } catch (NumberFormatException ignored) {

                    }
                }
            }
        } catch (FileNotFoundException e) {
            return mapa;
        }

        return mapa;
    }

    private void guardarCambios() {
        if (table.isEditing()) {
            table.getCellEditor().stopCellEditing();
        }

        for (int i = 0; i < alumnos.size(); i++) {
            Object valor = table.getValueAt(i, 4);
            int calificacion;
            try {
                if (valor == null) {
                    calificacion = -1;
                } else {
                    String texto = valor.toString().trim();
                    if (texto.isEmpty() || texto.equalsIgnoreCase("S/C")) {
                        calificacion = -1;
                    } else {
                        calificacion = Integer.parseInt(texto);
                        if (calificacion != -1 && (calificacion < 0 || calificacion > 100)) {
                            throw new NumberFormatException("Fuera de rango");
                        }
                    }
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(
                        this,
                        "Calificación inválida en la fila " + (i + 1) + ".\n" +
                                "Usa un número entre 0 y 100, o -1 (o S/C) para sin calificación.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            alumnos.get(i).setCalificacion(calificacion);
        }

        CsvReader.writeToFile("Calificaciones.csv", alumnos);
        JOptionPane.showMessageDialog(this, "Calificaciones guardadas exitosamente");
    }

    public void anadirAlumno() {
        JTextField txtMatricula = new JTextField();
        JTextField txtNombre = new JTextField();
        JTextField txtPrimerApellido = new JTextField();
        JTextField txtSegundoApellido = new JTextField();
        JTextField txtCalificacion = new JTextField();

        JPanel form = new JPanel(new GridLayout(6, 2));
        form.add(new JLabel("Matrícula:"));
        form.add(txtMatricula);
        form.add(new JLabel("Nombre(s):"));
        form.add(txtNombre);
        form.add(new JLabel("Primer apellido:"));
        form.add(txtPrimerApellido);
        form.add(new JLabel("Segundo apellido:"));
        form.add(txtSegundoApellido);
        form.add(new JLabel("Calificaciones:"));
        form.add(txtCalificacion);


        int option = JOptionPane.showConfirmDialog(
                this,
                form,
                "Agregar Alumno",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (option != JOptionPane.OK_OPTION) return;

        int matricula;
        try {
            matricula = Integer.parseInt(txtMatricula.getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "La matrícula debe ser un número.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String nombre = txtNombre.getText().trim();
        String primerApellido = txtPrimerApellido.getText().trim();
        String segundoApellido = txtSegundoApellido.getText().trim();

        if (nombre.isEmpty() || primerApellido.isEmpty() || segundoApellido.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nombre y apellidos no pueden estar vacíos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        for (Alumno a : alumnos) {
            if (a.getMatricula() == matricula) {
                JOptionPane.showMessageDialog(this, "Ya existe un alumno con esa matrícula.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        Alumno nuevo = new Alumno(matricula, primerApellido, segundoApellido, nombre);
        nuevo.setCalificacion(-1);

        CsvReader.agregarAlumnoArchivo("src/disenio/ada2/Libro2.csv", nuevo);

        alumnos.add(nuevo);

        DefaultTableModel model = (DefaultTableModel) table.getModel();
        Vector<Object> fila = new Vector<>();
        fila.add(nuevo.getMatricula());
        fila.add(nuevo.getNombre());
        fila.add(nuevo.getPrimer_Apellido());
        fila.add(nuevo.getSegundo_Apellido());
        fila.add(nuevo.getCalificacion());
        model.addRow(fila);

        JOptionPane.showMessageDialog(this, "Alumno agregado y guardado en Libro2.csv");
    }

    public void generarPDF(){
        ReportePDF.generarReporte("ReporteCalificaciones.pdf", alumnos);
        JOptionPane.showMessageDialog(this, "Reporte de califaciones generado exitosamente");
    }
}
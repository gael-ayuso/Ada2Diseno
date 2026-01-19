import java.util.ArrayList;
import java.util.List;

public class Alumno {
    private String nombre;
    private String primer_Apellido;
    private String segundo_Apellido;
    private int matricula;
    private int calificacion;
    //private List<Asignatura> asignaturas;

    public Alumno(int matricula, String primer_Apellido, String segundo_Apellido, String nombre) {
        this.nombre = nombre;
        this.primer_Apellido = primer_Apellido;
        this.segundo_Apellido = segundo_Apellido;
        this.matricula = matricula;
        //this.asignaturas = new ArrayList<>();
    }

//    public void addAsignatura(Asignatura asignatura) {
//        //this.asignaturas.add(asignatura);
//    }

//    public List<Asignatura> getAsignaturas() {
//        return asignaturas;
//    }

    public String getNombre() {
        return nombre + " " + primer_Apellido + " " + segundo_Apellido;
    }

    public int getMatricula() {
        return matricula;
    }

    public int getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(int calificacion) {
        this.calificacion = calificacion;
    }

    @Override
    public String toString() {
        return "matricula= " + matricula + ", nombre=" +
                nombre + " " + primer_Apellido + " " + segundo_Apellido +
                ", calificación= " + calificacion;
    }
}
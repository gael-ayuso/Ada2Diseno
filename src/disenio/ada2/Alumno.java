package disenio.ada2;

public class Alumno {
    private final String nombre;
    private final String primer_Apellido;
    private final String segundo_Apellido;
    private final int matricula;
    private int calificacion;
    //private List<Asignatura> asignaturas;

    public Alumno(int matricula, String primer_Apellido, String segundo_Apellido, String nombre) {
        this.nombre = nombre;
        this.primer_Apellido = primer_Apellido;
        this.segundo_Apellido = segundo_Apellido;
        this.matricula = matricula;
        //this.asignaturas = new ArrayList<>();
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
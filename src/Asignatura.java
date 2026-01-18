public class Asignatura {
    private String nombreAsignatura;
    private Integer calificacion;

    public Asignatura(String nombreAsignatura) {
        this.nombreAsignatura = nombreAsignatura;
        this.calificacion = null;
    }

    public void setCalificacion(Integer calificacion) {
        if (calificacion != null && (calificacion < 0 || calificacion > 100)) {
            throw new IllegalArgumentException("Calificación debe estar entre 0 y 100");
        }
        this.calificacion = calificacion;
    }

    public Integer getCalificacion() {
        return calificacion;
    }

    public String getNombreAsignatura() {
        return nombreAsignatura;
    }

    @Override
    public String toString() {
        return nombreAsignatura + " - " + (calificacion != null ? calificacion : "Sin calificación");
    }
}
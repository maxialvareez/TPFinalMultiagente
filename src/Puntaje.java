public class Puntaje implements Comparable<Puntaje>{

    private String comida;
    private double puntaje;
    private boolean activo;


    public Puntaje(String comida, double puntaje) {
        this.comida = comida;
        this.puntaje = puntaje;
        this.activo = true;

    }

    public String getComida() {
        return comida;
    }

    public Puntaje setComida(String comida) {
        this.comida = comida;
        return this;
    }

    public double getPuntaje() {
        return puntaje;
    }

    public Puntaje setPuntaje(double puntaje) {
        this.puntaje = puntaje;
        return this;
    }

    public boolean isActivo() {
        return activo;
    }

    public Puntaje setActivo(boolean activo) {
        this.activo = activo;
        return this;
    }

    @Override
    public int compareTo(Puntaje p) {
        return Double.compare(this.puntaje,p.getPuntaje());
    }
}

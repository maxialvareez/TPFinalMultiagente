public class Puntaje implements Comparable<Puntaje>{

    private String comida;
    private double puntaje;


    public Puntaje(String comida, double puntaje) {
        this.comida = comida;
        this.puntaje = puntaje;
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

    @Override
    public int compareTo(Puntaje p) {
        return Double.compare(this.puntaje,p.getPuntaje());
    }
}

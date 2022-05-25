public class Puntaje implements Comparable<Puntaje>{

    private String comida;
    private float puntaje;
    private boolean activo;


    public Puntaje(String comida, float puntaje) {
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

    public float getPuntaje() {
        return puntaje;
    }

    public Puntaje setPuntaje(float puntaje) {
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
        return Float.compare(this.puntaje,p.getPuntaje());
    }
}

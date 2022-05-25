import jade.core.Agent;



import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class AgentNegociador extends Agent {

    private List<Puntaje> puntajes;
    private int pActual = 0;


    protected void setup() {

        this.puntajes = new ArrayList<>();

    }


    public List<Puntaje> getPuntajes() {
        return puntajes;
    }

    public void agregarPuntaje(Puntaje p){
        puntajes.add(p);
    }

    public void ordenarPuntajes(){
        Collections.sort(this.puntajes, Collections.reverseOrder());

    }

    public int getpActual() {
        return pActual;
    }

    public String getPropuestaActual(){
        return this.puntajes.get(this.pActual).getComida();
    }

    public double getPuntajePropuestaActual(){
        return this.puntajes.get(this.pActual).getPuntaje();
    }

    public double getPuntaje(String comida){
        for (Puntaje p : this.puntajes){
            if(p.getComida().equals(comida))
                return p.getPuntaje();
        }
        return 0;
    }

    public double getPeorPuntaje(){
        if (puntajes.size() > 0)
            return (puntajes.get(puntajes.size()-1).getPuntaje());

        return -1;
    }


    public void cederComida(){
        this.pActual ++;
    }

    public boolean existeComida(){
        if (this.pActual <= puntajes.size()){
            return true;
        }
        return false;
    }

    public double calcularZeuthen(double uPropuestaRecibida){
        if (this.getPuntajePropuestaActual() == 0)
            return 1;
        else{
            return ((this.getPuntajePropuestaActual() - uPropuestaRecibida) / this.getPuntajePropuestaActual());
        }
    }


}

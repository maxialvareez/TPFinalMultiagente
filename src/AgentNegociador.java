import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


public class AgentNegociador extends Agent {

    private List<Puntaje> puntajes;
    private int pActual;


    protected void setup() {

        this.puntajes = new ArrayList<>();

        // Crea una descripción del agente para el DF
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());

        // Crea una descripción del servicio
        ServiceDescription sd = new ServiceDescription();
        sd.setType("negociacion");
        sd.setName("comidas");
        dfd.addServices(sd);
        try {
            DFService.register(this, dfd);
        }
        catch (FIPAException fe) {
            fe.printStackTrace();
        }

        addBehaviour(new EsperarPropuestaInicial());
    }

    protected void takeDown() {
        try {
            DFService.deregister(this);
        }
        catch (FIPAException fe) {
            fe.printStackTrace();
        }

    }

    public List<Puntaje> getPuntajes() {
        return puntajes;
    }

    public void agregarPuntaje(Puntaje p){
        puntajes.add(p);
    }

    public void ordenarPuntajes(){
        Collections.sort(this.puntajes);
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
        this.puntajes.remove(0);
    }

    public boolean existeComida(){
        if (puntajes.isEmpty())
            return false;

        return true;
    }

    public double calcularZeuthen(){
        if (this.getPuntajePropuestaActual() == 0)
            return 1;
        else{
            return ((this.getPuntajePropuestaActual() - this.getPeorPuntaje()) / this.getPuntajePropuestaActual());
        }
    }


}

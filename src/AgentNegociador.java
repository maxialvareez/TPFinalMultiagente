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
        puntajes.add(new Puntaje("Milanesas", Math.random()));
        puntajes.add(new Puntaje("Papas", Math.random()));
        puntajes.add(new Puntaje("Pasta", Math.random()));
        puntajes.add(new Puntaje("Asado", Math.random()));
        puntajes.add(new Puntaje("Arroz", Math.random()));
        Collections.sort(puntajes);


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

}
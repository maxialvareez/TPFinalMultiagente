import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;


public class AgentResponder extends AgentNegociador{

    protected void setup() {
        super.setup();

        agregarPuntaje(new Puntaje("Milanesas", 2));
        agregarPuntaje(new Puntaje("Papas",3));
        agregarPuntaje(new Puntaje("Pasta",3.5f));
        agregarPuntaje(new Puntaje("Asado", 1));
        agregarPuntaje(new Puntaje("Arroz", 5));
        ordenarPuntajes();

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

}

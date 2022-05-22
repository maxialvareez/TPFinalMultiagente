import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.proto.SubscriptionInitiator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AgentInitiator extends AgentNegociador {

    protected void setup() {
        super.setup();

        agregarPuntaje(new Puntaje("Milanesas", 5));
        agregarPuntaje(new Puntaje("Papas",4.5));
        agregarPuntaje(new Puntaje("Pasta", 4));
        agregarPuntaje(new Puntaje("Asado", 2));
        agregarPuntaje(new Puntaje("Arroz", 1));
        ordenarPuntajes();


        DFAgentDescription template = new DFAgentDescription();
        ServiceDescription sd = new ServiceDescription();
        sd.setType("negociacion");
        sd.setName("comidas");
        template.addServices(sd);
        try {
            DFAgentDescription[] result = DFService.search(this, template);
            if (result.length > 0)
                addBehaviour(new FSMProtocolo(result[0].getName()));
            else {
                addBehaviour( new SubscriptionInitiator( this,
                        DFService.createSubscriptionMessage( this, getDefaultDF(),
                                template, null))
                {
                    protected void handleInform(ACLMessage inform) {
                        try {
                            DFAgentDescription[] result =
                                    DFService.decodeNotification(inform.getContent());
                            if (result[0].getAllServices().hasNext())
                                addBehaviour(new FSMProtocolo(result[0].getName()));


                        }
                        catch (FIPAException fe) {fe.printStackTrace(); }
                    }
                });
            }

        }
        catch (FIPAException fe) { fe.printStackTrace(); }
    }
}

import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.proto.SubscriptionInitiator;

public class Iniciator extends Agent {
    protected void setup() {
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

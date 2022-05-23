import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

// Comportamiento cíclico que recibe propuestas iniciales e inicia la FSM
public class EsperarPropuestaInicial extends CyclicBehaviour {

	@Override
	public void action() {


		 //Solo recibimos mensajes PROPOSE iniciales
		ACLMessage prop_ini = myAgent.receive(MessageTemplate.and(
						MessageTemplate.MatchPerformative(ACLMessage.PROPOSE),MessageTemplate.MatchInReplyTo("")));      //chequear si va con comillas vacías o null

		if (prop_ini != null) {
			System.out.println("Se inicia la maquina de estados del resp");
			myAgent.addBehaviour(new FSMProtocolo(prop_ini));
		}
		else
			block();

	}

}

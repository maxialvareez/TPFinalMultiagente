import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.tools.introspector.gui.MyDialog;

public class RecibirZeuthen extends Behaviour {

	int event = -1;
	boolean termino = false;
	@Override
	public void action() {

		//TODO Filtrado mensaje
		ACLMessage recibido = myAgent.receive(MessageTemplate.MatchPerformative(ACLMessage.INFORM));

		System.out.println("Entra al recibir zeuthen");
		if (recibido != null) {
			System.out.println("Recibe el inform del zeuthen");
			termino = true;
			double miZeuthen = ((AgentNegociador) myAgent).calcularZeuthen();
			double otroZeuthen = Double.parseDouble(recibido.getContent());

			//TODO que hacer para el caso de que sean iguales? Porque asi ambos concederian, o ambos no concederian
			if (miZeuthen <= otroZeuthen) {
				((AgentNegociador)myAgent).cederComida();
				event = 1;
			}
			else {
				event = 0;
			}
		}
		else block();
	}

	@Override
	public boolean done() {
		return termino;
	}


	@Override
	public void reset() {
		event = -1;
		termino = false;
	}

	@Override
	public int onEnd() {
		return event;
	}
}

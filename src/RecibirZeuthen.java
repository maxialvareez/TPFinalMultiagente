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
		ACLMessage comida = (ACLMessage) getDataStore().get(FSMProtocolo.ULTIMOMSJ);
		String comida_propuesta = comida.getContent();

		double uPropRecibida = ((AgentNegociador)myAgent).getPuntaje(comida_propuesta);


		if (recibido != null) {
			termino = true;
			double miZeuthen = ((AgentNegociador) myAgent).calcularZeuthen(uPropRecibida);
			double otroZeuthen = Double.parseDouble(recibido.getContent());

			System.out.println(myAgent.getName()+  ", Prop Propia: " + ((AgentNegociador) myAgent).getPropuestaActual() + "---------> Puntaje:"+((AgentNegociador) myAgent).getPuntajePropuestaActual()+ ",  Prop Recibida: " + comida_propuesta + "---------> Puntaje: " + uPropRecibida+ ", MiZeuthen: "+ miZeuthen+ ", OtroZeuthen" + otroZeuthen);

			//TODO que hacer para el caso de que sean iguales? Porque asi ambos concederian, o ambos no concederian
			if (miZeuthen <= otroZeuthen) {
				((AgentNegociador)myAgent).cederComida();
				event = 1;
				System.out.println("Va a proponer " + myAgent.getName());
			}
			else {
				event = 0;
				System.out.println("Espera nueva propuesta " + myAgent.getName());
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

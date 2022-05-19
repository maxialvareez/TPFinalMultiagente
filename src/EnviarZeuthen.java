import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;

public class EnviarZeuthen extends Behaviour {

	@Override
	public void action() {
		String comida_propuesta = "";   //TODO Extraer del DS

		double uPropActual = ((AgentNegociador)myAgent).getPuntajePropuestaActual();
		double uPropRecibida = ((AgentNegociador)myAgent).getPuntaje(comida_propuesta);


		ACLMessage ult_msg = (ACLMessage)getDataStore().get(FSMProtocolo.PROPOSE_INITIAL);


	}

	@Override
	public boolean done() {

		return false;
	}

}

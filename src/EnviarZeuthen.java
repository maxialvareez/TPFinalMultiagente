import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;

public class EnviarZeuthen extends Behaviour {

	@Override
	public void action() {
		ACLMessage ultimoRecibido = (ACLMessage) getDataStore().get(FSMProtocolo.ULTIMOMSJ);

		String comida_propuesta = ultimoRecibido.getContent();


		double uPropActual = ((AgentNegociador)myAgent).getPuntajePropuestaActual();  //Utilidad de propuesta actual
		double uPropRecibida = ((AgentNegociador)myAgent).getPuntaje(comida_propuesta); //Utilidad de propuesta recibida


		ACLMessage ult_msg = (ACLMessage)getDataStore().get(FSMProtocolo.ULTIMOMSJ);

		double miZeuthen = ((AgentNegociador)myAgent).calcularZeuthen();

		//TODO hay que setear mas cosas del mensaje
		ACLMessage mensaje=  (ACLMessage) this.getDataStore().get(FSMProtocolo.ULTIMOMSJ);
		mensaje.createReply();
		mensaje.setPerformative(ACLMessage.INFORM);
		//Todo No se puede enviar un double, con la ontologia creo que se permitiria, pero fijarse como hacerlo ahora.
		mensaje.setContent(Double.toString(miZeuthen));

		myAgent.send(mensaje);

	}



	@Override
	public boolean done() {

		return false;
	}

}

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;

import javax.swing.plaf.synth.SynthOptionPaneUI;

public class EnviarZeuthen extends Behaviour {

	@Override
	public void action() {
		ACLMessage ultimoRecibido = (ACLMessage) getDataStore().get(FSMProtocolo.ULTIMOMSJ);

		String comida_propuesta = ultimoRecibido.getContent();

		double uPropActual = ((AgentNegociador)myAgent).getPuntajePropuestaActual();  //Utilidad de propuesta actual
		double uPropRecibida = ((AgentNegociador)myAgent).getPuntaje(comida_propuesta); //Utilidad de propuesta recibida

		ACLMessage ult_msg = (ACLMessage)getDataStore().get(FSMProtocolo.ULTIMOMSJ);

		double miZeuthen = ((AgentNegociador)myAgent).calcularZeuthen(uPropRecibida);

		ACLMessage mensaje=  (ACLMessage) this.getDataStore().get(FSMProtocolo.ULTIMOMSJ);

		ACLMessage respuesta = mensaje.createReply();
		respuesta.setPerformative(ACLMessage.INFORM);

		System.out.println("---ENVIAR ZEUTHEN---"+ myAgent.getName()+  ", Prop Recibida: " + comida_propuesta);
		//Todo No se puede enviar un double, con la ontologia creo que se permitiria, pero fijarse como hacerlo ahora.
		respuesta.setContent(Double.toString(miZeuthen));

		myAgent.send(respuesta);

	}



	@Override
	public boolean done() {
		return true;
	}

}

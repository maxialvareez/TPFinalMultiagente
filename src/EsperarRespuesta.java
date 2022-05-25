
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.Iterator;


public class EsperarRespuesta extends Behaviour {
	private boolean termino = false;
	private int event = -1;
	@Override
	public void action() {
		ACLMessage msj_propose= (ACLMessage) this.getDataStore().get(FSMProtocolo.MSJ_ENVIADO);


		//Se filtra ConversationID, y ReplyTo (que el el mensaje anterior es el replywith)
		//todo filtrar por reject o accept
		ACLMessage msj_actual = myAgent.receive(MessageTemplate.and(
				MessageTemplate.MatchConversationId(msj_propose.getConversationId()),MessageTemplate.MatchInReplyTo(msj_propose.getReplyWith())));

		System.out.println("En estado esperar respuesta");
		if (msj_actual != null) {
			this.getDataStore().put(FSMProtocolo.ULTIMOMSJ,msj_actual);

			if (msj_actual.getPerformative() == ACLMessage.REJECT_PROPOSAL){
				this.event = 0;
			}
			else {
				if (msj_actual.getPerformative() == ACLMessage.ACCEPT_PROPOSAL) {
					System.out.println(this.myAgent.getName() + "  Accede al acuerdo");
					this.event = 1;
				}
			}
			this.termino = true;
		}
		else{
			block();
		}
	}

	@Override
	public boolean done() {
		return this.termino;
	}

	@Override
	public int onEnd() {
		return this.event;
	}

	@Override
	public void reset() {
		this.termino = false;
		this.event = -1;
	}

}

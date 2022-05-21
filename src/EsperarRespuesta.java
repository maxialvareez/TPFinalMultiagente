
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
		ACLMessage msj_propose= (ACLMessage) this.getDataStore().get(FSMProtocolo.PROPOSE_INITIAL);

		AID oponente =  msj_propose.getSender();

		//Se filtra ConversationID, y ReplyTo (que el el mensaje anterior es el replywith)
		ACLMessage msj_actual = myAgent.receive(MessageTemplate.and(
				MessageTemplate.MatchConversationId(msj_propose.getConversationId()),MessageTemplate.MatchInReplyTo(msj_propose.getReplyWith())));


		if (msj_actual != null) {
			getDataStore().put(FSMProtocolo.ULTIMOMSJ,msj_actual);

			if (msj_actual.getPerformative() == ACLMessage.REJECT_PROPOSAL){
				getDataStore().put(FSMProtocolo.ULTIMOMSJ, msj_actual);
				event = 0;
			}
			else {
				if (msj_actual.getPerformative() == ACLMessage.ACCEPT_PROPOSAL) {
					this.getDataStore().put(FSMProtocolo.ULTIMOMSJ, msj_actual);
					event = 1;
				}
			}
			termino = true;
		}
		else{
			block();
		}
	}

	@Override
	public boolean done() {
		return termino;
	}

	@Override
	public int onEnd() {
		return this.event;
	}
	@Override
	public void reset() {
		termino = false;
		event = -1;
	}
	}


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
		ACLMessage req = (ACLMessage) this.getDataStore().get("mensaje propose");
		AID responder = (AID) this.getDataStore().get("responder");

		ACLMessage msg = myAgent.receive();


		if (msg != null) {
			getDataStore().put(FSMProtocolo.ULTIMOMSG,msg);

			if (msg.getPerformative() == ACLMessage.REJECT_PROPOSAL){
				event = 0;
			}
			else {
				if (msg.getPerformative() == ACLMessage.ACCEPT_PROPOSAL) {
					this.getDataStore().put("mensaje accept", msg);
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

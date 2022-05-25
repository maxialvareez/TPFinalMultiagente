import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class EsperarPropuesta extends Behaviour {
	int event = -1;
	boolean termino = false;

	@Override
	public void action() {

		//Todo Mas filtros
		ACLMessage msj_recibido = myAgent.receive(MessageTemplate.or(MessageTemplate.MatchPerformative(ACLMessage.PROPOSE),MessageTemplate.MatchPerformative(ACLMessage.CANCEL)));

		if (msj_recibido != null){
			this.termino = true;
			if (msj_recibido.getPerformative() == ACLMessage.PROPOSE) {
				//Todo fijarse si usar ULTIMOMSJ o PROPOSE_INITIAL
				getDataStore().put(FSMProtocolo.ULTIMOMSJ, msj_recibido);
				this.event = 0;

			} else{

				this.event = 1;
			}

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
		this.event = -1;
		this.termino = false;
	}
}

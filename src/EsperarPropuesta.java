import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class EsperarPropuesta extends Behaviour {

	boolean termino;

	@Override
	public void action() {

		//Todo Mas filtros
		ACLMessage msj_propose = myAgent.receive(MessageTemplate.MatchPerformative(ACLMessage.PROPOSE));

		if (msj_propose != null){
			termino = true;
			//Todo fijarse si usar ULTIMOMSJ o PROPOSE_INITIAL
			getDataStore().put(FSMProtocolo.PROPOSE_INITIAL, msj_propose);
		}
		else{
			block();
		}
	}

	@Override
	public boolean done() {
		// TODO Auto-generated method stub
		return termino;
	}

}

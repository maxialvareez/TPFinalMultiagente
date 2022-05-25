import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;

	public class EvaluarPropuesta extends Behaviour {
		private int event = -1;
		@Override
		public void action() {

			ACLMessage mensaje = (ACLMessage) this.getDataStore().get(FSMProtocolo.ULTIMOMSJ);
			ACLMessage respuesta = mensaje.createReply();

			//Comparacion entre la utilizad de la propuesta propia y la recibida. Si U(PropPropia) <= U(PropuestaRecibida) entonces acepta
			float uMiPropuesta = ((AgentNegociador)myAgent).getPuntajePropuestaActual();
			float uOtraPropuesta = ((AgentNegociador)myAgent).getPuntaje(mensaje.getContent());


			if (uMiPropuesta <= uOtraPropuesta){
				respuesta.setContent("Me parece una muy buena elección");
				respuesta.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
				this.event = 1;
			}
			else{
				respuesta.setContent(((AgentNegociador)myAgent).getPropuestaActual());
				respuesta.setPerformative(ACLMessage.REJECT_PROPOSAL);
				this.event = 0;
			}
			getDataStore().put(FSMProtocolo.MSJ_ENVIADO, respuesta);
			this.myAgent.send(respuesta);
		}


		@Override
		public boolean done() {
			return true;
		}

		@Override
		public int onEnd() {
			return this.event;
		}

		@Override
		public void reset(){
			this.event = -1;
		}
	}

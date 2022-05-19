import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;

	public class EvaluarPropuesta extends Behaviour {
		private int event = -1;
		private boolean recibido = false;
		@Override
		public void action() {
			//System.out.println("En Estado Intermedio");

			ACLMessage mensaje = (ACLMessage) this.getDataStore().get(FSMProtocolo.PROPOSE_INITIAL);

			if (mensaje != null) {
				recibido = true;
				ACLMessage respuesta = mensaje.createReply();

				double valor = Math.random();
				if (valor > 0.2) {
					respuesta.setContent("Esa comida no me gusta");
					respuesta.setPerformative(ACLMessage.REJECT_PROPOSAL);
					event = 0;
				} else {
					respuesta.setContent("Me parece una muy buena elección");
					respuesta.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
					event = 1;
				}
				this.myAgent.send(respuesta);
			}
			else
				block();
		}

		@Override
		public boolean done() {
			return this.recibido;
		}

		@Override
		public int onEnd() {
			return this.event;
		}

		@Override
		public void reset(){
			this.event = -1;
			this.recibido = false;
		}
	}

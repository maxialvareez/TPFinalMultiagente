import jade.core.behaviours.Behaviour;

import jade.core.AID;
import jade.lang.acl.ACLMessage;
import java.util.Arrays;
import java.util.List;

	public class EnviarPropuesta extends Behaviour {

		int event = -1;

		@Override
		public void action() {

			ACLMessage mensaje = null;

			if (((AgentNegociador) myAgent).existeComida()) {
				if (this.getDataStore().get(FSMProtocolo.ULTIMOMSJ) == null) {
					AID nombreOponente = (AID) this.getDataStore().get(FSMProtocolo.AID_OPONENTE);

					//Armado del mensaje a enviar
					ACLMessage msj_propose = new ACLMessage(ACLMessage.PROPOSE);
					msj_propose.setReplyWith(myAgent.getName() + "-" + Math.random());
					msj_propose.setConversationId(myAgent.getName() + nombreOponente + "-" + Math.random());
					msj_propose.setInReplyTo("");
					msj_propose.setContent(((AgentNegociador) myAgent).getPropuestaActual());
					msj_propose.addReceiver(nombreOponente);

					//Datos para el filtrado de mensajes del proximo estado (va a filtrar por ConversationID y ReplyTo(el replyTo que espera es el replyWith de este mensaje guardado)
					this.getDataStore().put(FSMProtocolo.MSJ_ENVIADO, msj_propose);

					this.event = 0;
					this.myAgent.send(msj_propose);
				} else {

					mensaje = (ACLMessage) getDataStore().get(FSMProtocolo.ULTIMOMSJ);
					ACLMessage respuesta = mensaje.createReply();
					respuesta.setContent(((AgentNegociador) myAgent).getPropuestaActual());
					respuesta.setPerformative(ACLMessage.PROPOSE);

					this.getDataStore().put(FSMProtocolo.MSJ_ENVIADO, respuesta);

					this.event = 0;
					this.myAgent.send(respuesta);
				}
			} else {
				this.event = 1;
			}
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

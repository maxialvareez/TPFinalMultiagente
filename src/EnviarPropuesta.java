import jade.core.behaviours.Behaviour;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import java.util.Arrays;
import java.util.List;

	public class EnviarPropuesta extends Behaviour {
		private boolean termino = false;
		List<String> comidas = Arrays.asList("Milanesas","Papas","Asado","Pasta");
		int posicion = 0;

		@Override
		public void action() {
			ACLMessage req = new ACLMessage(ACLMessage.PROPOSE);
			req.setContent("Te gustaría comer "+ this.comidas.get(posicion));
			req.addReceiver(new AID("AgenteResponder", AID.ISLOCALNAME));

			this.getDataStore().put("mensaje request", req);
			System.out.println("Te gustaría comer "+ this.comidas.get(posicion)+ "?");
			if (posicion < this.comidas.size() -1)
				posicion += 1;
			else
				posicion = 0;
			this.myAgent.send(req);
			this.termino = true;

		}

		@Override
		public boolean done() {
			return  termino;
		}

		public void reset(){
			this.termino= false;
		}

	}

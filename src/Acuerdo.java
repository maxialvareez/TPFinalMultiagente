import jade.core.behaviours.Behaviour;

public class Acuerdo extends Behaviour {

	@Override
	public void action() {
		System.out.println("Acuerdo --------" +this.myAgent.getName() );
	}

	@Override
	public boolean done() {

		return true;
	}

}

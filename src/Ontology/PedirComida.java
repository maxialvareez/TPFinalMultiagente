package Ontology;
import jade.content.AgentAction;

public class PedirComida implements AgentAction {
    private Comida comida;
    private String lugar;

    public PedirComida(Comida comida, String lugar) {
        this.comida = comida;
        this.lugar = lugar;
    }

    public PedirComida(Comida comida) {
        this.comida = comida;
    }

    public Comida getComida() {
        return comida;
    }

    public PedirComida setComida(Comida comida) {
        this.comida = comida;
        return this;
    }

    public String getLugar() {
        return lugar;
    }

    public PedirComida setLugar(String lugar) {
        this.lugar = lugar;
        return this;
    }
}

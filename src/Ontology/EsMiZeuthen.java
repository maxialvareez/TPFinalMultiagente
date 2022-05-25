package Ontology;
import jade.content.Predicate;

public class EsMiZeuthen implements Predicate{
    private float valor;

    public EsMiZeuthen(float valor) {
        this.valor = valor;
    }

    public float getValor() {
        return valor;
    }

    public EsMiZeuthen setValor(float valor) {
        this.valor = valor;
        return this;
    }
}

import Ontology.*;
import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.core.Agent;



import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class AgentNegociador extends Agent {

    private Codec codec = new SLCodec();
    private Ontology ontology = MCPOntology.getInstance();

    private List<Puntaje> puntajes;
    private int pActual = 0;


    protected void setup() {

        getContentManager().registerLanguage(this.codec);
        getContentManager().registerOntology(this.ontology);
        this.puntajes = new ArrayList<>();

    }


    public List<Puntaje> getPuntajes() {
        return puntajes;
    }

    public void agregarPuntaje(Puntaje p){
        puntajes.add(p);
    }

    public void ordenarPuntajes(){
        Collections.sort(this.puntajes, Collections.reverseOrder());

    }

    public int getpActual() {
        return pActual;
    }

    public String getPropuestaActual(){
        return this.puntajes.get(this.pActual).getComida();
    }

    public float getPuntajePropuestaActual(){
        return this.puntajes.get(this.pActual).getPuntaje();
    }

    public float getPuntaje(String comida){
        for (Puntaje p : this.puntajes){
            if(p.getComida().equals(comida))
                return p.getPuntaje();
        }
        return 0;
    }

    public float getPeorPuntaje(){
        if (puntajes.size() > 0)
            return (puntajes.get(puntajes.size()-1).getPuntaje());

        return -1;
    }


    public void cederComida(){
        this.pActual ++;
    }

    public boolean existeComida(){
        if (this.pActual <= puntajes.size()){
            return true;
        }
        return false;
    }

    public float calcularZeuthen(float uPropuestaRecibida){
        if (this.getPuntajePropuestaActual() == 0)
            return 1;
        else{
            return ((this.getPuntajePropuestaActual() - uPropuestaRecibida) / this.getPuntajePropuestaActual());
        }
    }

    public Codec getCodec() {
        return codec;
    }

    public Ontology getOntology() {
        return ontology;
    }
}

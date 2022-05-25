package Ontology;
import jade.content.Concept;

public class Comida implements Concept{
    private String nombre;
    private String tipoComida;  //DATE????
    private String ingredientes;

    public Comida(String nombre) {
        this.nombre = nombre;
    }

    public Comida(String nombre, String tipoComida, String ingredientes) {
        this.nombre = nombre;
        this.tipoComida = tipoComida;
        this.ingredientes = ingredientes;
    }

    public String getNombre() {
        return nombre;
    }

    public Comida setNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public String getTipoComida() {
        return tipoComida;
    }

    public Comida setTipoComida(String tipoComida) {
        this.tipoComida = tipoComida;
        return this;
    }

    public String getIngredientes() {
        return ingredientes;
    }

    public Comida setIngredientes(String ingredientes) {
        this.ingredientes = ingredientes;
        return this;
    }
}

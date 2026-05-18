package MomentoEvaluativo3;

import java.util.LinkedList; // Para el historial de materias

public class Materia {

    private String codigo; //Codigo unico de la materia
    private String nombreMate; 
    private int cupoMax; //Cupo maximo de estudiantes para la materia
    private LinkedList<String> preRequisitos; //La lista de pre-requisitos que tiene que contar
    private LinkedList<String> inscritos; 

    public Materia(String codigo, String nombreMate, int cupoMax){
        
        this.codigo = codigo;
        this.nombreMate = nombreMate;
        this.cupoMax = cupoMax;
        this.preRequisitos = new LinkedList<>();
        this.inscritos = new LinkedList<>();

    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombreMate() {
        return nombreMate;
    }

    public void setNombreMate(String nombreMate) {
        this.nombreMate = nombreMate;
    }

    public int getCupoMax() {
        return cupoMax;
    }

    public void setCupoMax(int cupoMax) {
        this.cupoMax = cupoMax;
    }

    public LinkedList<String> getPreRequisitos() {
        return preRequisitos;
    }

    public void setPreRequisitos(LinkedList<String> preRequisitos) {
        this.preRequisitos = preRequisitos;
    }

    public LinkedList<String> getInscritos() {
        return inscritos;
    }

    public void setInscritos(LinkedList<String> inscritos) {
        this.inscritos = inscritos;
    }

    // Agregar un pre-requisito a la materia
    public void agregarRequisito(String codigoReque){
        this.preRequisitos.add(codigoReque); // Agrega el código del pre-requisito a la lista de pre-requisitos de la materia
    }

    //Comprueba que aun haya cupo en la materia seleccionada
    public boolean verificarCupo(){
        return this.inscritos.size() < this.cupoMax; // Retorna true si el número de inscritos es menor que el cupo máximo, indicando que aún hay cupo disponible
    }
    
}

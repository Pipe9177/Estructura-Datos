package MomentoEvaluativo3;

import java.util.LinkedList; //Para el historial de las materias

public class Estudiante extends Persona {
    private int semestreAct; //Semestre actual del estudiante
    private double[][] notas; //Matriz para almacenar notas por semestre del estudiante
    private LinkedList<String> historial; //Lista para almacenar el historial de materias cursadas del estudiante
    
    public Estudiante(String nombre, String id, String email, int semestreAct){
        super(nombre, id, email); //Llama al constructor de la clase padre (Persona) para inicializar los atributos heredados
        this.semestreAct = semestreAct; 
        this.notas = new double[10][20]; //Matriz de notas con un tamaño de 10 semestres y 20 materias por semestre
        this.historial = new LinkedList<>(); 
    }

    public int getSemestreAct() {
        return semestreAct;
    }

    public void setSemestreAct(int semestreAct) {
        this.semestreAct = semestreAct;
    }

    public double[][] getNotas() {
        return notas;
    }

    public void setNotas(double[][] notas) {
        this.notas = notas;
    }

    public LinkedList<String> getHistorial() {
        return historial;
    }

    public void setHistorial(LinkedList<String> historial) {
        this.historial = historial;
    }

    @Override
    public void mostrarInformacion(){
        System.out.println("\nNombre: " + nombre);
        System.out.println("ID: " + id);
        System.out.println("Email: " + email);
        System.out.println("Semestre Actual: " + semestreAct);
        System.out.println("Historial de Materias Cursadas: " + historial);
    }
}

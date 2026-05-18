package MomentoEvaluativo3;

import java.util.LinkedList; //Para el historial de las materias

public class Estudiante extends Persona {
    private int semestreAct; // Semestre actual del estudiante
    private double[][] notas; // Matriz para almacenar notas por semestre del estudiante
    private LinkedList<String> historial; // Lista para almacenar el historial de materias cursadas del estudiante

    public Estudiante(String nombre, String id, String email, int semestreAct) {
        super(nombre, id, email); // Llama al constructor de la clase padre (Persona) para inicializar los
                                  // atributos heredados
        this.semestreAct = semestreAct;
        this.notas = new double[10][20]; // Matriz de notas con un tamaño de 10 semestres y 20 materias por semestre
        this.historial = new LinkedList<>();
    }

    // Método para guardar una calificación específica en la matriz
    public void registrarNota(int semestre, int materiaIndice, double nota) {
        this.notas[semestre][materiaIndice] = nota; // Almacena la nota en la posición correspondiente de la matriz
    }

    // Calcular el promedio de notas del estudiante en un semestre
    public double calcularPromedio(int semestre) {
        double suma = 0; // Acumula las calificaciones
        int contador = 0;
        for (int i = 0; i < 20; i++) { // 20 debido a que son las materias max del semestre
            if (notas[semestre][i] != null) { // Comprueba si en la matriz hay una nota
                suma += notas[semestre][i];
                contador++;
            }

        }
        if (contador == 0) {
            return 0.0; // Evita división por cero si no hay notas registradas
        } else {
            return suma / contador; // Retorna el promedio calculado
        }
    }

    // Materia aprobada para los pre-requisitos
    public void aprobarMateria(String materia) {
        this.historial.add(materia); // Agrega la materia aprobada al historial del estudiante
    }

    // Corrobora que la materia este aprobada
    public boolean verificarAprobacion(String materia) {
        return this.historial.contains(materia); // Retorna true si el historial del estudiante contiene la materia,
                                                 // indicando que ha sido aprobada
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
    public void mostrarInformacion() {
        System.out.println("\nNombre: " + nombre);
        System.out.println("ID: " + id);
        System.out.println("Email: " + email);
        System.out.println("Semestre Actual: " + semestreAct);
        System.out.println("Historial de Materias Cursadas: " + historial);
    }
}

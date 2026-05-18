package MomentoEvaluativo2;

import java.util.Stack;

public class Buque {

    private Stack<Contenedor> buque = new Stack<>(); // Pila para almacenar los contenedores en el buque

    public void cargar(Contenedor co) {
        if (buque.isEmpty() || co.getPeso() <= buque.peek().getPeso()) { // Si el buque está vacío o el peso del
            // nuevo contenedor es menor o igual al del último contenedor agregado, se puede
            // agregar al buque.
            buque.push(co); // Agrega el contenedor al buque
            System.out.println("\nContenedor " + co.getId() + " anexado correctamente en el buque.");
        } else {
            System.out.println("-----------\nPELIGRO: RIEGOS DE INESTABILIDAD---------");
            System.out.println("Peso del contenedor: " + co.getPeso() + " > Tope permitido: " + buque.peek().getPeso());
        }
    }

    // Retirar usando Pila Auxiliar
    public void retirar() {
        if (buque.isEmpty())
            return; // Si el buque está vacío, no hay contenedores para retirar

        Stack<Contenedor> aux = new Stack<>(); // Pila auxiliar para almacenar temporalmente los contenedores retirados
        while (buque.size() > 1) {
            aux.push(buque.pop()); // Retira el contenedor superior del buque y lo guarda en la pila auxiliar
        }

        System.out.println("\nOperacion Critica: Retirando contenedor del fondo: " + buque.pop().getId());

        while (!aux.isEmpty()) {
            buque.push(aux.pop()); // Devuelve los contenedores de la pila auxiliar al buque
        }
    }
}

import java.util.LinkedList;
import java.util.Queue;

public class Punto3 {
    public static void main(String[] args) {
        // Estructuras principales
        Queue<Integer> carritosDisponibles = new LinkedList<>();
        Queue<Integer> esperaPorCarrito = new LinkedList<>();
        
        // Arreglo de colas para las 3 cajas
        Queue<Integer>[] cajas = new LinkedList[3];
        for (int i = 0; i < cajas.length; i++) {
            cajas[i] = new LinkedList<>();
        }

        // 1. Inicializar los 25 carritos
        for (int i = 1; i <= 25; i++) {
            carritosDisponibles.add(i);
        }

        System.out.println("--- Inicio de Simulación ---");

        // Simulemos la llegada de 30 clientes
        for (int clienteID = 1; clienteID <= 30; clienteID++) {
            
            // Intentar tomar un carrito
            if (!carritosDisponibles.isEmpty()) {
                int carrito = carritosDisponibles.poll();
                System.out.println("Cliente " + clienteID + " tomó el carrito " + carrito);
                
                // Ir a la caja con menos gente
                int mejorCaja = seleccionarMejorCaja(cajas);
                cajas[mejorCaja].add(carrito); // El cliente entra a la fila con su carrito
                System.out.println("Cliente " + clienteID + " entró a la fila de la Caja " + (mejorCaja + 1));
            } else {
                esperaPorCarrito.add(clienteID);
                System.out.println("Cliente " + clienteID + " está esperando un carrito.");
            }
        }

        // 2. Simular el proceso de pago y liberación de carritos
        System.out.println("\n--- Procesando Pagos ---");
        for (int i = 0; i < cajas.length; i++) {
            while (!cajas[i].isEmpty()) {
                int carritoLiberado = cajas[i].poll();
                System.out.println("Pago realizado. Carrito " + carritoLiberado + " liberado.");
                
                // El carrito vuelve a estar disponible
                carritosDisponibles.add(carritoLiberado);

                // Si había alguien esperando carrito, lo toma de inmediato
                if (!esperaPorCarrito.isEmpty()) {
                    int clienteEspera = esperaPorCarrito.poll();
                    int nuevoCarrito = carritosDisponibles.poll();
                    System.out.println(">> Cliente " + clienteEspera + " tomó el carrito liberado " + nuevoCarrito);
                    
                    int mejor = seleccionarMejorCaja(cajas);
                    cajas[mejor].add(nuevoCarrito);
                }
            }
        }
    }

    // Método para encontrar la cola más corta
    public static int seleccionarMejorCaja(Queue<Integer>[] cajas) {
        int indiceMenor = 0;
        int minGente = cajas[0].size();

        for (int i = 1; i < cajas.length; i++) {
            if (cajas[i].size() < minGente) {
                minGente = cajas[i].size();
                indiceMenor = i;
            }
        }
        return indiceMenor;
    }
}

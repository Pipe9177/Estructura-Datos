import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Punto4 {
    public static void main(String[] args) {
        Random rnd = new Random();
        Queue<Integer> colaUnica = new LinkedList<>();
        
        // Tiempos en los que cada caja terminará de atender (en minutos)
        double[] tiempoFinAtencion = new double[5]; 
        int totalAtendidos = 0;
        int maxFila = 0;
        int sumaFila = 0;
        int tiempoCaja4Abierta = 0;
        boolean caja4Activa = false;

        // Simulamos minuto a minuto por 7 horas (420 minutos)
        for (int minuto = 0; minuto < 420; minuto++) {
            
            // 1. Llegada de clientes: Intervalo medio de 1 min.
            // Para la simulación, asumimos que llega un cliente cada minuto.
            colaUnica.add(minuto); 

            // 2. Control de la cuarta caja
            if (colaUnica.size() > 20) {
                caja4Activa = true;
            }
            if (caja4Activa) {
                tiempoCaja4Abierta++;
                if (colaUnica.isEmpty()) caja4Activa = false;
            }

            // 3. Revisar cada caja
            int numCajas = caja4Activa ? 4 : 3;
            for (int n = 1; n <= numCajas; n++) {
                // Si la caja está libre (su tiempo de fin es <= al minuto actual)
                if (tiempoFinAtencion[n] <= minuto && !colaUnica.isEmpty()) {
                    colaUnica.poll(); // Sale de la fila
                    totalAtendidos++;
                    
                    // Asignar tiempo según el rango del libro
                    double demora = 0;
                    if (n == 1) demora = 1.5 + (2.5 - 1.5) * rnd.nextDouble();
                    else if (n == 2) demora = 2 + (5 - 2) * rnd.nextDouble();
                    else if (n == 3) demora = 2 + (4 - 2) * rnd.nextDouble();
                    else if (n == 4) demora = 2 + (4.5 - 2) * rnd.nextDouble();
                    
                    tiempoFinAtencion[n] = minuto + demora;
                }
            }

            // 4. Estadísticas
            if (colaUnica.size() > maxFila) maxFila = colaUnica.size();
            sumaFila += colaUnica.size();
        }

        System.out.println("--- RESULTADOS SIMULACIÓN ---");
        System.out.println("Clientes atendidos: " + totalAtendidos);
        System.out.println("Tamaño máximo de la fila: " + maxFila);
        System.out.println("Tamaño medio de la fila: " + (sumaFila / 420.0));
        System.out.println("Minutos que la caja 4 estuvo abierta: " + tiempoCaja4Abierta);
    }
}

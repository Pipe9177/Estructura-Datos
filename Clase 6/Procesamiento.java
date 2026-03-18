
import java.util.Random;

public class Procesamiento {

    private Random numeros = new Random();
    public int energiaContenedores[] = new int[12];
    int cuentas = 0;
    int[] conteo;

    public int[] Procesar() {
        for (int i = 0; i < 12; i++) {
            energiaContenedores[i] = numeros.nextInt(101) + 50; // Genera numeros aleatorios entre 50 y 150
            if (energiaContenedores[i] % 10 == 0) {
                cuentas++; // Contamos cuántos números son múltiplos de 10
            }

        }

        conteo = new int[cuentas]; // Creamos un nuevo arreglo para almacenar solo los múltiplos de 10
        int indice = 0; // Índice para el nuevo arreglo
        for (int c : energiaContenedores) {
            if (c % 10 == 0) {
                conteo[indice] = c; // Almacenamos el número múltiplo de 10 en el nuevo arreglo
                indice++;
            }

        }
        return conteo;
    }

    public int[][] Bahias(int conteo[]) { // Recibimos el arreglo de múltiplos de 10 y lo organizamos en una matriz de
                                          // 3x3

        int mapaCarga[][] = new int[3][3];
        int filtro = 0; // Índice para recorrer el arreglo de múltiplos de 10

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (filtro < conteo.length) {
                    mapaCarga[i][j] = conteo[filtro]; // Asignamos el valor del arreglo de múltiplos de 10 a la matriz
                    filtro++;
                } else {
                    mapaCarga[i][j] = -1; // Indica que no hay más contenedores para mostrar en esta posición
                }
            }
        }
        return mapaCarga;
    }

    public Modelo[] Vuelo(int[][] mapaCarga) { // Recibimos la matriz de 3x3 y creamos un arreglo de objetos Modelo con
                                               // la
        // información de cada contenedor
        Modelo[] suministro = new Modelo[9]; // Creamos un arreglo de objetos Modelo para almacenar la información de
                                             // cada
        // contenedor
        int posicion = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int energia = mapaCarga[i][j];
                if (energia != -1) {
                    String prioridad = (energia > 100) ? "Alta" : "Estandar"; // Asignamos la prioridad según el nivel
                                                                              // de
                    // energía
                    String id = "Contenedor - " + i + " - " + j; // Generamos un ID único para cada contenedor basado en su
                    // posición en la matriz

                    suministro[posicion] = new Modelo(id, energia, prioridad); // Creamos un nuevo objeto Modelo con la
                    // información del contenedor y lo almacenamos en el arreglo
                } else {
                    suministro[posicion] = null; // No hay contenedor en esta posición
                }
                posicion++;
            }
        }
        return suministro;
    }
}

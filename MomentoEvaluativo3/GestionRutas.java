package MomentoEvaluativo3;

//Clase diseñada para calcular las distancias entre edificion y manejar las conexiones
public class GestionRutas {
    private String[] edificios = { "Ingenierias", "Biblioteca", "Cafeteria", "Rectoria", "Laboratorios" };
    private int[][] distancias = new int[5][5];

    public GestionRutas() {
        for (int i = 0; i < edificios.length; i++) {
            for (int j = 0; j < edificios.length; j++) {
                distancias[i][j] = (int) (Math.random() * 500) + 1; // Asignar una distancia aleatoria entre 1 y 500
            }
        }
    }

    // Mapa para que el usuario pueda visualizar los edificios y seleccione
    public void mostrarMapa() {
        System.out.println("Mapa de edificios: ");
        for (int i = 0; i < edificios.length; i++) {
            System.out.println(i + ": " + edificios[i]); // Muestra el mapa de edificios con su respectivo indice para
                                                         // que el usuario pueda seleccionar
        }
    }

    // Registrar conexiones entre edificios, verificando que no se registren
    // conexiones entre el mismo edificio
    public void registrarConexion(int origen, int destino, int distancia) {
        if (origen == destino) {
            System.out.println("Error: No se pueden registrar conexiones en el mismo edificio.");
            return;
        } else {
            distancias[origen][destino] = distancia; // Conserva la distancia de ida
            distancias[destino][origen] = distancia; // Ahora conserva la distancia de vuelta
        }
    }

    // Mediante el algoritmo de Dijkstra se calcula la ruta mas corta
    public void rutaCorta(int origen, int destino) {
        int n = edificios.length; //Tamaño del arreglo
        int [] distanciaMin = new int[n]; //Almacenar las distancias minimas
        boolean [] visitado = new boolean[n]; //Lugares ya visitados
        int [] optimizar = new int[n]; // Reformula la ruta mas corta

        //Distancia inicial y la distancia de origen
        for (int i = 0; i < n; i++){
            distanciaMin[i] = Integer.MAX_VALUE; // Esta linea de codigo define la distancia inicial como infinito; para que siempre la siguiente opcion sea menor y las mas corta.
            optimizar[i] = -1; // No hay otro camino mas corto
        }

        distanciaMin[origen] = 0; //La distancia inicial siempre sera 0

        //Proceso de Dijkstra
        for (int i = 0; i < n; i++){
            int edi = -1; //Variable para detectar la ruta mas corta mediante edificios

            //Empieza la busqueda en edificios no visitados 
            for (int j = 0; j < n; j++){
                if (!visitado[j] && (edi == -1 || distanciaMin[j] < distanciaMin[edi])){
                    edi = j; //Detecta el edificio con la distancia mas corta
                }
            }

            if (distanciaMin[edi] == Integer.MAX_VALUE){
                break; // Si no hay mas edificios alcanzables, se detiene el proceso
                visitados[edi] = true; //Registra el edificio como ya visitado
            
            //ACTUALIZA LAS DISTANCIAS 
            for (int d = 0; d < n; d++){
                //Procede a verificar que no haya conexiones juntamente con que sea menor la distancia 
            if (distancias[edi][d] > 0 && !visitados[d] && distanciaMin[edi] + distancias[edi][d] < distanciaMin[d]){
                distanciaMin[d] = distanciaMin[edi] + distancias[edi][d]; //Actualiza la distancia minima
                optimizar[d] = edi; //Actualiza y registra
            }
            
            }
            
            }
        }


       //Resultado presentado en consola
    System.out.println("\n------DISTANCIA DE RUTA MAS CORTA------");
    System.out.println("Distancia de " + edificios[origen] + " a " + edificios[destino] + ": " + distanciaMin[destino] + " metros.");

    }

}

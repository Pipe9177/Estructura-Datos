import java.util.Scanner;
import java.util.Random;

public class Puerto {

    private Contenedor[][] patio = new Contenedor[10][10]; // Matriz que representa la cantidad de contenedores que
    private Buque[] muelles = new Buque[5]; // Cantidad de buques que estaran en el puerto
    private Scanner sc = new Scanner(System.in); // Scanner para leer la entrada del usuario con 10 contenedores
    private Random m = new Random(); // Random para generar 90 contenedores aleatorios
    private int buqueActivos = -1; // Variable para llevar la cuenta de los buques registrados en el puerto

    public void aleatorios() {
        int contador = 0; // Contador para llevar la cuenta de los contenedores generados
        System.err.println("\nGenerando 90 contenedores aleatorios");

        while (contador < 90) {
            int columna = m.nextInt(10); // Generar una columna aleatoria entre 0 y 9
            for (int fila = 0; fila < patio.length; fila++) { // Analisa la fila mas baja disponible por la gravedad
                if (patio[fila][columna] == null) { // Si el espacio esta vacio, agregar un nuevo contenedor
                    int idAle = 1000 + contador; // Generar un Id aleatorio para el contenedor
                    patio[fila][columna] = new Contenedor(idAle, 500.0, "Origen Automatico", "Destino Automatico");
                    // Crear un nuevo contenedor con el Id generado y agregarlo al patio
                    contador++; // Incrementar el contador de contenedores generados
                    break; // Salir del bucle para generar el siguiente contenedor
                }
            }
        }
        System.out.println("\nContenedores aleatorios generados exitosamente");
    }

    public void registrarBuque() {
        for (int i = 0; i < muelles.length; i++) {
            if (muelles[i] == null) { // Si el muelle esta vacio, agregar el buque
                System.out.println("\n----Registro de Buques----( Muelle " + i + ")----");
                System.out.print("Ingrese el Id del buque: ");
                int id = sc.nextInt(); // Leer el Id del buque
                System.out.print("Ingrese la capacidad del buque en toneladas: ");
                double capacidad = sc.nextDouble(); // Leer la capacidad del buque
                System.out.print("Ingrese el origen del buque: ");
                String origen = sc.next(); // Leer el origen del buque

                muelles[i] = new Buque(id, capacidad, origen); // Crear un nuevo buque y agregarlo al muelle
                System.out.println("\nBuque: " + id + "registrado exitosamente en el Muelle " + i);
                return; // Salir del método después de registrar el buque
            } else {
                System.out
                        .println("\nEl Muelle:  " + i + " ya esta ocupado por el buque con Id: " + muelles[i].getId());
            }
        }
    }

    public void seleccionar() {
        boolean estado = false; //Aqui se verifica si hay un buque o no

        for(int i = 0; i < muelles.length; i++){
            if(muelles[i] != null){
                System.out.println("\nBuque ubicado en el Muelle " + i + ": " + muelles[i].getId());
                estado = true; // Si hay un buque registrado, cambiar el estado a true
            }
        }
        if(!estado){ //Si no hay buques registrados, informar al usuario y retomar
            System.out.println("\nActualmente no hay buques registrados, porfavor registrar uno");
            return; // Salir del método si no hay buques registrados
        }
        System.out.print("\nIngrese el muelle que desea trabajar (0-4):");
        int sel = sc.nextInt(); // Leer el muelle seleccionado por el usuario
        if (sel >= 0 && sel < 10 && muelles[sel] != null){ //Verificar si el muelle seleccionado es valido
            buqueActivos = sel; // Establecer el muelle activo para trabajar con el buque seleccionado
            System.out.println("\nBuque " + muelles[sel].getId() + "seleccionado");
        } else {
            System.out.println("\nEl muelle que usted esta seleccionando se encuentra vacio");
        }
    }

    public void mostrarPatios() {
        System.out.println("\n----Patios del Puerto----");
        for (int i = 0; i < patio.length; i++) {
            for (int j = 0; j < patio[i].length; j++) {
                if (patio[i][j] != null) { // Si el espacio no esta vacio, mostrar el Id del contenedor
                    System.out.print("[" + patio[i][j].getId() + "] ");
                } else {
                    System.out.print("[Vacío] "); // Si el espacio esta vacio, mostrar "Vacío"
                }
            }
            System.out.println(); // Salto de linea para mostrar la siguiente fila del patio
        }
    }

    public void ubicarContenedor() {
        mostrarPatios(); // Muestra en pantalla patios para que el usuario pueda elegir donde ubicar el
                         // contenedor
        System.out.print("\nEscoge fila (0-9) para ubicar el contenedor: ");
        int fi = sc.nextInt(); // Leer la fila donde se ubicara el contenedor
        System.out.print("\nEscoge columna (0-9) para ubicar el contenedor: ");
        int co = sc.nextInt(); // Leer la columna donde se ubicara el contenedor

        // Comprobar si el espacio esta ocupado
        if (fi < 0 || fi > 9 || co < 0 || co > 9 || patio[fi][co] != null) {
            System.out.println("\nEl espacio seleccionado esta ocupado");
            return; // Salir del método si el espacio esta ocupado o si las coordenadas son
                    // invalidas
        }

        // Comprobar si el hay algun contenedor debajo, en caso de que no salta una
        // alerta
        if (fi < 9 && patio[fi + 1][co] == null) {
            System.out.println("\nNo se puede ubicar el contenedor en el aire");
            return;
        }

        System.out.print("\nIngrese el Id del contenedor: ");
        int id = sc.nextInt(); // Leer el Id del contenedor
        System.out.print("Ingrese el peso del contenedor en toneladas: ");
        double peso = sc.nextDouble(); // Leer el peso del contenedor
        System.out.print("Ingrese el origen del contenedor: ");
        String origen = sc.next(); // Leer el origen del contenedor
        System.out.print("Ingrese el destino del contenedor: ");
        String destino = sc.next(); // Leer el destino del contenedor

        patio[fi][co] = new Contenedor(id, peso, origen, destino); // Crear un nuevo contenedor y agregarlo al patio
        System.out.println("\nContenedor " + id + " ubicado exitosamente en la fila " + fi + " y columna " + co);
    }

    public void desembarcar(String destino) {
        // En este caso se necesita que el contenedor sea null para considerarlo
        // desembarcado, juntamente con el destino en el cual desembarcara

        System.out.print("\nIngrese la fila del contenedor que desea desembarcar (0-9): ");
        int fi = sc.nextInt(); // Leer la fila del contenedor a desembarcar
        System.out.print("\nIngrese la columna del contenedor que desea desembarcar (0-9):");
        int co = sc.nextInt();

        // Verificar si los lugares estan llenos
        if (fi < 0 || fi > 9 || co < 0 || co > 9) {
            System.out.println("\nCoordenadas incorrectas");
            return;
        }

        // Verificar si el espacio esta vacio
        if (patio[fi][co] == null) {
            System.out.println("\nNo hay contenedor en el espacio seleccionado");
            return;
        }
        patio[fi][co] = null; // Desembarcar el contenedor seleccionado al establecer su posición en null
        System.out.println("\nContenedor desembarcado exitosamente de la fila " + fi + " y columna " + co
                + " con destino a " + destino);
    }
}

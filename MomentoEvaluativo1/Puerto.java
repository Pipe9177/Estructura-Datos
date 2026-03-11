import java.util.Scanner;
import java.util.Random;

public class Puerto {

    private Buque[] muelles = new Buque[5]; // Cantidad de buques que estaran en el puerto
    private Scanner sc = new Scanner(System.in); // Scanner para leer la entrada del usuario con 10 contenedores
    private Random m = new Random(); // Random para generar 90 contenedores aleatorios
    int buqueActivos = -1; // Variable para llevar la cuenta de los buques registrados en el puerto

    public void aleatorios() {

        if (buqueActivos == -1) {
            System.out.println("\nNo ha seleccionado o esta registrado un buque");
            return; // Salir del método si no hay buques registrados o seleccionados
        }

        Contenedor[][] patio = muelles[buqueActivos].getPatioUnico();

        int creados = 0; // Contador para llevar la cuenta de los contenedores generados
        System.err.println("\nGenerando 90 contenedores aleatorios");

        while (creados < 90) {
            int columna = m.nextInt(10); // Generar una columna aleatoria entre 0 y 9
            for (int fila = 9; fila >= 0; fila--) { // Analisa la fila mas baja disponible por la gravedad
                if (patio[fila][columna] == null) { // Si el espacio esta vacio, agregar un nuevo contenedor
                    int idAle = m.nextInt(1000); // Generar un Id aleatorio para el contenedor
                    patio[fila][columna] = new Contenedor(idAle, 500.0, "Origen Automatico", "Destino Automatico");
                    // Crear un nuevo contenedor con el Id generado y agregarlo al patio
                    creados++; // Incrementar el contador de contenedores generados
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
                System.out.println("\nEl muelle cuenta con un registro de 5 lugares");
                System.out.print("\nIngrese el Id del buque: ");
                int id = sc.nextInt(); // Leer el Id del buque
                System.out.print("Ingrese la capacidad del buque en toneladas: ");
                double capacidad = sc.nextDouble(); // Leer la capacidad del buque
                System.out.print("Ingrese el origen del buque: ");
                String origen = sc.next(); // Leer el origen del buque

                muelles[i] = new Buque(id, capacidad, origen); // Crear un nuevo buque y agregarlo al muelle
                System.out.println("\nBuque: " + id + " registrado exitosamente en el Muelle " + i
                        + " con capacidad de " + capacidad + " toneladas y origen en: " + origen);
                return; // Salir del método después de registrar el buque
            } else {
                System.out
                        .println("\nEl Muelle:  " + i + " ya esta ocupado por el buque con Id: " + muelles[i].getId()
                                + " con lugar de partida: " + muelles[i].getOrigen());
            }
        }
    }

    public void seleccionar() {
        boolean estado = false; // Aqui se verifica si hay un buque o no

        for (int i = 0; i < muelles.length; i++) {
            if (muelles[i] != null) {
                System.out.println("\nBuque " + muelles[i].getId() + " ubicado en el Muelle: " + i
                        + " con lugar de partida: " + muelles[i].getOrigen());
                estado = true; // Si hay un buque registrado, cambiar el estado a true
            }
        }
        if (!estado) { // Si no hay buques registrados, informar al usuario y retomar
            System.out.println("\nActualmente no hay buques registrados, porfavor registrar uno");
            return; // Salir del método si no hay buques registrados
        }

        if (this.buqueActivos != -1) {
            System.out.println("\n-----AVISO------");
            System.out.println("Cuentas actualmente con el Buque:" + muelles[buqueActivos].getId() + " seleccionado");
            System.out.print(
                    "\nDesea cambiar de buque? Presione ( 1 ) si lo desea o presione ( 2 ) si desea continuar con el actual: ");
            int revisar = sc.nextInt(); // Lee la decision del usuario

            if (revisar == 1) {
                System.out.println("\nEl buque " + muelles[buqueActivos].getId() + " ha sido liberado correctamente");
                this.buqueActivos = -1; // Procede a liberar el buque antiguo
                return; // Salir del método para seleccionar un nuevo buque
            } else if (revisar == 2) {
                System.out.println("\nContinuando con el buque " + muelles[buqueActivos].getId() + " seleccionado");
                return; // Salir del método para continuar con el buque actual
            } else {
                System.out.println("\nOpcion no valida en el codigo");
                return; // Sale del metodo si la opcion ingresada ha sido incorrecta
            }
        }

        System.out.print("\nIngrese el muelle que desea trabajar (0-4) o si desea regresarse ingrese (6):");
        int sel = sc.nextInt(); // Leer el muelle seleccionado por el usuario

        if (sel == 6) {
            System.out.println("\n-------Redireccionando al menu principal---------");
            return; // Salir del método para regresar al menú principal
        }

        if (sel >= 0 && sel < 10 && muelles[sel] != null) { // Verificar si el muelle seleccionado es valido
            buqueActivos = sel; // Establecer el muelle activo para trabajar con el buque seleccionado
            System.out.println("\nBuque: " + muelles[sel].getId() + " seleccionado");
        } else {
            System.out.println("\nEl muelle que usted esta seleccionando se encuentra vacio");
        }
    }

    public void mostrarPatios() {
        if (buqueActivos == -1) {
            System.out.println("\nEstas pendejo o que mijo? como vas a ver el patio sin buque");
            return;
        }

        Contenedor[][] patio = muelles[buqueActivos].getPatioUnico();

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

        if (buqueActivos == -1) {
            System.out.println("\nNo puede ubicar ningun contenedor sin antes seleccionar un buque");
            return;
        }

        Contenedor[][] patio = muelles[buqueActivos].getPatioUnico();

        mostrarPatios(); // Muestra en pantalla patios para que el usuario pueda elegir donde ubicar el
                         // contenedor
        System.out.print("\nEscoge fila (0-9) para ubicar el contenedor: ");
        int fi = sc.nextInt(); // Leer la fila donde se ubicara el contenedor
        System.out.print("\nEscoge columna (0-9) para ubicar el contenedor: ");
        int co = sc.nextInt(); // Leer la columna donde se ubicara el contenedor

        // Comprobar si el espacio esta ocupado
        if (fi < 0 || fi > 9 || co < 0 || co > 9) {
            System.out.println("\nLos datos ingresados se salieron del rango, intentar nuevamente con rango de (0-9)");
            return; // Salir del método si las coordenadas ingresadas estan fuera del rango
                    // permitido
        }

        if (patio[fi][co] != null) {
            System.out.println(
                    "\nEl espacio en el patio " + fi + ", " + co + " ya se encuentra ocupado por otro contenedor");
            return; // Salir del método si el espacio seleccionado ya esta ocupado por otro
                    // contenedor
        }

        // Comprobar si el hay algun contenedor debajo, en caso de que no salta una
        // alerta
        if (fi < 9 && patio[fi + 1][co] == null) {
            System.out.println("\nNo se puede ubicar el contenedor en el aire");
            return;
        }

        if (patio[fi][co] == null) { // Verificar si el espacio esta vacio para ubicar el contenedor
            System.out.print("\nIngrese el Id del contenedor: ");
            int id = sc.nextInt(); // Leer el Id del contenedor
            System.out.print("Ingrese el peso del contenedor en toneladas: ");
            double pesoAñadido = sc.nextDouble(); // Leer el peso del contenedor
            System.out.print("Ingrese el origen del contenedor: ");
            String origen = sc.next(); // Leer el origen del contenedor
            System.out.print("Ingrese el destino del contenedor: ");
            String destino = sc.next(); // Leer el destino del contenedor

            //Valida si el buque puede con la cantidad del contenedor añadido, en caso de que no, salta una alerta
            if(muelles[buqueActivos].getPesoActual() + pesoAñadido > muelles[buqueActivos].getCapacidad()) {
                System.out.println("\nEl peso del contenedor excede la capacidad del buque, no se puede ubicar el contenedor");
                System.out.println("Peso actual del buque: " + (muelles[buqueActivos].getCapacidad() - muelles[buqueActivos].getPesoActual()) + " toneladas disponibles");
                return; // Salir del método si el peso del contenedor excede la capacidad del buque
            }


            patio[fi][co] = new Contenedor(id, pesoAñadido, origen, destino); // Crear un nuevo contenedor y agregarlo al patio
            System.out.println("\nContenedor " + id + " ubicado exitosamente en la fila " + fi + " y columna " + co);
            muelles[buqueActivos].pesoActual += pesoAñadido; // Actualizar el peso actual del buque al añadir el nuevo contenedor
            System.out.println("Peso actual del buque: " + muelles[buqueActivos].getPesoActual() + " toneladas");
            muelles[buqueActivos].setPesoActual(muelles[buqueActivos].getPesoActual() + pesoAñadido); // Actualizar el peso actual del buque al añadir el nuevo contenedor
                
            }
        }

    public void desembarcar(String destinoIngresado) {
        if (buqueActivos == -1) {
            System.out.println("\nSuerte en tu viaje, no bajas ninguna pertenencia");
            return;
        }

        Contenedor[][] patio = muelles[buqueActivos].getPatioUnico();

        mostrarPatios(); // Referencia para que el usuario sepa que contenedor bajar
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
        if (patio[fi][co].destino == destinoIngresado) {
            String destinoFinal = patio[fi][co].destino; // Obtener el destino del contenedor seleccionado
            patio[fi][co] = null; // Procede a desembarcar el contenido del buque
            System.out.println("\nEl contenedor que selecciono fila " + fi + " y columna " + co + " bajara hacia: "
                    + destinoFinal);
            return;
        } else {
            System.out.println("\nEl contenedor seleccionado no tiene el destino ingresado");
            return;
        }
    }
}

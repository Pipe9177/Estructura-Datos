
import java.util.Scanner;

public class Ejecutar {

    public static void main(String[] args) {
        Puerto puerto = new Puerto();
        Scanner sp = new Scanner(System.in);
        int opcion = 0;

        while (true) {
            System.out.println("\n-----Logística Distribucciones JH----");
            System.out.println("\n1. Registrar buque en el muelle");
            System.out.println("2. Seleccionar buque para almacenar");
            System.out.println("3. Generar contenedores aleatorios");
            System.out.println("4. Mostrar patios para poder organizar los contenedores");
            System.out.println("5. Ubicar contenedores en los  lugares disponibles");
            System.out.println("6. Desembarcar debido a la llegada del destino");
            System.out.println("7. Salir");
            System.out.print("\nIngrese una opción: ");

            opcion = sp.nextInt();

            switch (opcion) {
                case 1:
                    puerto.registrarBuque();
                    break;
                case 2:
                    puerto.seleccionar();
                    break;
                case 3:
                    puerto.aleatorios();
                    break;
                case 4:
                    puerto.mostrarPatios();
                    break;
                case 5:
                    puerto.ubicarContenedor();
                    break;
                case 6:
                    //Mostrar en pantalla si hay buques registrados o seleccionados antes de solicitar el destino para desembarcar
                    if (puerto.buqueActivos == -1) {
                        System.out.println("\n!Adios¡ Te vas sin contenedores");
                        break; // Salir del caso si no hay buques registrados o seleccionados
                    } else {
                    System.out.print("\nA que Ciudad/Pais acabas de llegar: ");
                    String destinoLlegada = sp.next(); // Leer el destino para desembarcar
                    puerto.desembarcar(destinoLlegada);
                    }
                    break;
                case 7:
                    System.out.println("\nSaliendo del programa...");
                    sp.close();
                    return;
                default:
                    System.out.println("\nOpción inválida. Por favor, ingrese una opción válida");
                    // Continuar el bucle para mostrar el menú nuevamente
                    continue;
            }

        }

    }
}

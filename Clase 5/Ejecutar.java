
import java.util.Scanner;

public class Ejecutar {

    public static void main(String[] args) {
        Puerto puerto = new Puerto();
        Scanner sp = new Scanner(System.in);
        int opcion = 0;

        while (true) {
            System.out.println("\n-----Almacenar contenedores en buques disponibles----");
            System.out.println("\n1. Registrar buque en el muelle");
            System.out.println("2. Generar contenedores aleatorios");
            System.out.println("3. Mostrar patios para poder organizar los contenedores");
            System.out.println("4. Ubicar contenedores en los  lugares disponibles");
            System.out.println("5. Desembarcar debido a la llegada del destino");
            System.out.println("6. Salir");
            System.out.print("\nIngrese una opción: ");

            opcion = sp.nextInt();

            switch (opcion) {
                case 1:
                    puerto.registrarBuque();
                    break;

                case 2:
                    puerto.aleatorios();
                    break;
                case 3:
                    puerto.mostrarPatios();
                    break;
                case 4:
                    puerto.ubicarContenedor();
                    break;
                case 5:
                    System.out.print("\nIngrese el destino para desembarcar: ");
                    String destino = sp.next(); // Leer el destino para desembarcar
                    puerto.desembarcar(destino);
                    break;
                case 6:
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

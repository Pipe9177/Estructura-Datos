import java.util.Scanner;
import java.util.Stack;

public class Punto2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        // Arreglo de 6 para ignorar el índice 0 y usar 1 a 5
        Stack<Integer>[] listaPilas = new Stack[6];
        
        for (int k = 1; k < listaPilas.length; k++) {
            listaPilas[k] = new Stack<Integer>();
        }

        int i, j;
        do {
            System.out.print("Ingrese i (pila) y j (valor): ");
            i = sc.nextInt();
            j = sc.nextInt();

            if (i == 0) break;

            // abs(i) manual
            int indicePila = (i < 0) ? -i : i;

            if (indicePila >= 1 && indicePila <= 5) {
                if (i > 0) {
                    listaPilas[indicePila].push(j);
                } else {
                    if (!listaPilas[indicePila].isEmpty()) {
                        listaPilas[indicePila].pop();
                    }
                }
            }
        } while (i != 0);

        // Imprimir resultados
        for (int k = 1; k < listaPilas.length; k++) {
            System.out.print("Pila " + k + ": ");
            System.out.println(listaPilas[k]);
        }
    }

}

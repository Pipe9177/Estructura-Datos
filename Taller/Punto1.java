import java.util.Scanner;
import java.util.Stack;

public class Punto1 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduce la expresión:");
        String entrada = sc.nextLine();
        
        // Convertimos a array para no usar .charAt()
        char[] caracteres = entrada.toCharArray();
        Stack<Character> pila = new Stack<>();
        boolean error = false;

        for (int i = 0; i < caracteres.length; i++) {
            char actual = caracteres[i];

            if (actual == '(' || actual == '[' || actual == '{') {
                pila.push(actual);
            } else if (actual == ')' || actual == ']' || actual == '}') {
                if (pila.isEmpty()) {
                    error = true;
                    break;
                }
                char tope = pila.pop();
                if (!sonPareja(tope, actual)) {
                    error = true;
                    break;
                }
            }
        }

        if (!error && pila.isEmpty()) {
            System.out.println("Éxito: La expresión está equilibrada.");
        } else {
            System.out.println("Error: Símbolos mal balanceados.");
        }
    }

    private static boolean sonPareja(char a, char b) {
        if (a == '(' && b == ')') return true;
        if (a == '[' && b == ']') return true;
        if (a == '{' && b == '}') return true;
        return false;
    }
}


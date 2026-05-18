package MomentoEvaluativo3;

import java.util.Scanner;

public class Ejecutar {
    public static void main(String[] args) {
        GestionUni sistema = new GestionUni(); // Conexion con gestionUni el cual es el central
        GestionRutas rutas = new GestionRutas();
        Scanner scanner = new Scanner(System.in);
        int op = 0; //Abreviatura de opcion para el scanner

        do {
            System.out.println("\n=======================================================");
            System.out.println("     PLANIFICACION ACADEMICA - SISTEMA UNIVERSITARIO     ");
            System.out.println("=======================================================");
            System.out.println("----- GESTION DE ESTUDIANTES -----");
            System.out.println("1. Registrar estudiante");
            System.out.println("2. Buscar estudiante por ID");
            System.out.println("3. Listar todos los estudiantes");
            System.out.println("4. Eliminar estudiante");
            System.out.println("\n----- GESTION DE MATERIAS -----");
            System.out.println("5. Crear materia");
            System.out.println("6.  Agregar pre-requisito");
            System.out.println("7.  Mostrar pre-requisitos");
            System.out.println("8.  Inscribir estudiante");
            System.out.println("9.  Cancelar inscripcion");
            System.out.println("10. Mostrar cola de espera");
            System.out.println("\n----- GESTIÓN DE HORARIOS -----");
            System.out.println("11. Reservar horario en aula");
            System.out.println("12. Liberar horario");
            System.out.println("13. Consultar disponibilidad");
            System.out.println("\n----- RUTAS ENTRE EDIFICIOS -----");
            System.out.println("14. Agregar conexion entre edificios");
            System.out.println("15. Calcular ruta mas corta");
            System.out.println("\n----- REPORTES ACADEMICOS -----");
            System.out.println("16. Registrar nota");
            System.out.println("17. Ver reporte academico");
            System.out.println("18. Navegador de reportes (atras/adelante)");
            System.out.println("\n----- SISTEMA DESHACER/REHACER -----");
            System.out.println("19. Deshacer ultima operacion");
            System.out.println("20. Rehacer ultima operacion");
            System.out.println("\n----- PROCESAMIENTO POR LOTES -----");
            System.out.println("21. Procesar archivo CSV");
            System.out.println("\n----- SALIR -----");
            System.out.println("22. Salir");
            System.out.print("\nSeleccione una opcion: ");

            try{
                op = Integer.parseInt(scanner.nextLine()); // Lee la opción del usuario y la convierte a entero
                

                switch (op) {
                    case 1:
                        System.out.print("Ingrese nombre completo: ");
                        String nombre = scanner.nextLine();
                        System.out.print("Ingrese ID: ");
                        String id = scanner.nextLine();
                        System.out.print("Ingrese email: ");
                        String email = scanner.nextLine();
                        System.out.print("Ingrese semestre actual: ");
                        String semestre = scanner.nextLine();
                        sistema.agregarEstudiante(nombre, id, email, semestre);
                        break;

                    case 2:
                        System.out.print("Ingrese ID por buscar: ");
                        String idBuscar = scanner.nextLine();
                        sistema.buscarEstudiante(idBuscar).mostrarInformacion();
                        break;

                    case 3:
                        sistema.listaEstudiantes();
                        break; 

                    case 4:
                        System.out.print("Ingrese ID del estudiante a dar de baja: ");
                        String idElimi = scanner.nextLine();
                        sistema.eliminarEstudiante(idElimi);
                        break;

                    case 5:
                        System.out.print("Codigo de materia: ");
                        String codigoMateria = scanner.nextLine();
                        System.out.print("Nombre de materia: ");
                        String nombreMateria = scanner.nextLine();
                        System.out.println("Materia agregada: " + codigoMateria + " - " + nombreMateria);
                        break;

                    case 6:
                        System.out.print("Ingrese nombre completo: ");
                        String nombre = scanner.nextLine();
                        System.out.print("Ingrese ID: ");
                        String id = scanner.nextLine();
                        System.out.print("Ingrese email: ");
                        String email = scanner.nextLine();
                        System.out.print("Ingrese semestre actual: ");
                        String semestre = scanner.nextLine();
                        sistema.agregarEstudiante(nombre, id, email, semestre);
                        break;
                    
                    case 7:
                        System.out.print("Ingrese nombre completo: ");
                        String nombre = scanner.nextLine();
                        System.out.print("Ingrese ID: ");
                        String id = scanner.nextLine();
                        System.out.print("Ingrese email: ");
                        String email = scanner.nextLine();
                        System.out.print("Ingrese semestre actual: ");
                        String semestre = scanner.nextLine();
                        sistema.agregarEstudiante(nombre, id, email, semestre);
                        break;
                    
                    case 8:
                        System.out.print("Ingrese nombre completo: ");
                        String nombre = scanner.nextLine();
                        System.out.print("Ingrese ID: ");
                        String id = scanner.nextLine();
                        System.out.print("Ingrese email: ");
                        String email = scanner.nextLine();
                        System.out.print("Ingrese semestre actual: ");
                        String semestre = scanner.nextLine();
                        sistema.agregarEstudiante(nombre, id, email, semestre);
                        break;
                    
                    case 9:
                        System.out.print("Ingrese nombre completo: ");
                        String nombre = scanner.nextLine();
                        System.out.print("Ingrese ID: ");
                        String id = scanner.nextLine();
                        System.out.print("Ingrese email: ");
                        String email = scanner.nextLine();
                        System.out.print("Ingrese semestre actual: ");
                        String semestre = scanner.nextLine();
                        sistema.agregarEstudiante(nombre, id, email, semestre);
                        break;
                    
                    case 10:
                        System.out.print("Ingrese nombre completo: ");
                        String nombre = scanner.nextLine();
                        System.out.print("Ingrese ID: ");
                        String id = scanner.nextLine();
                        System.out.print("Ingrese email: ");
                        String email = scanner.nextLine();
                        System.out.print("Ingrese semestre actual: ");
                        String semestre = scanner.nextLine();
                        sistema.agregarEstudiante(nombre, id, email, semestre);
                        break;
                    
                    case 11:
                        System.out.print("Ingrese nombre completo: ");
                        String nombre = scanner.nextLine();
                        System.out.print("Ingrese ID: ");
                        String id = scanner.nextLine();
                        System.out.print("Ingrese email: ");
                        String email = scanner.nextLine();
                        System.out.print("Ingrese semestre actual: ");
                        String semestre = scanner.nextLine();
                        sistema.agregarEstudiante(nombre, id, email, semestre);
                        break;
                    
                    case 12:
                        System.out.print("Ingrese nombre completo: ");
                        String nombre = scanner.nextLine();
                        System.out.print("Ingrese ID: ");
                        String id = scanner.nextLine();
                        System.out.print("Ingrese email: ");
                        String email = scanner.nextLine();
                        System.out.print("Ingrese semestre actual: ");
                        String semestre = scanner.nextLine();
                        sistema.agregarEstudiante(nombre, id, email, semestre);
                        break;
                     
                    case 13:
                        System.out.print("Ingrese nombre completo: ");
                        String nombre = scanner.nextLine();
                        System.out.print("Ingrese ID: ");
                        String id = scanner.nextLine();
                        System.out.print("Ingrese email: ");
                        String email = scanner.nextLine();
                        System.out.print("Ingrese semestre actual: ");
                        String semestre = scanner.nextLine();
                        sistema.agregarEstudiante(nombre, id, email, semestre);
                        break;
                    
                    case 14:
                        System.out.print("Ingrese nombre completo: ");
                        String nombre = scanner.nextLine();
                        System.out.print("Ingrese ID: ");
                        String id = scanner.nextLine();
                        System.out.print("Ingrese email: ");
                        String email = scanner.nextLine();
                        System.out.print("Ingrese semestre actual: ");
                        String semestre = scanner.nextLine();
                        sistema.agregarEstudiante(nombre, id, email, semestre);
                        break;
                    
                    case 15:
                        System.out.print("Ingrese nombre completo: ");
                        String nombre = scanner.nextLine();
                        System.out.print("Ingrese ID: ");
                        String id = scanner.nextLine();
                        System.out.print("Ingrese email: ");
                        String email = scanner.nextLine();
                        System.out.print("Ingrese semestre actual: ");
                        String semestre = scanner.nextLine();
                        sistema.agregarEstudiante(nombre, id, email, semestre);
                        break;
                    
                    case 16:
                        System.out.print("Ingrese nombre completo: ");
                        String nombre = scanner.nextLine();
                        System.out.print("Ingrese ID: ");
                        String id = scanner.nextLine();
                        System.out.print("Ingrese email: ");
                        String email = scanner.nextLine();
                        System.out.print("Ingrese semestre actual: ");
                        String semestre = scanner.nextLine();
                        sistema.agregarEstudiante(nombre, id, email, semestre);
                        break;
                    
                    case 17:
                        System.out.print("Ingrese nombre completo: ");
                        String nombre = scanner.nextLine();
                        System.out.print("Ingrese ID: ");
                        String id = scanner.nextLine();
                        System.out.print("Ingrese email: ");
                        String email = scanner.nextLine();
                        System.out.print("Ingrese semestre actual: ");
                        String semestre = scanner.nextLine();
                        sistema.agregarEstudiante(nombre, id, email, semestre);
                        break;
                    
                    case 18:
                        System.out.print("Ingrese nombre completo: ");
                        String nombre = scanner.nextLine();
                        System.out.print("Ingrese ID: ");
                        String id = scanner.nextLine();
                        System.out.print("Ingrese email: ");
                        String email = scanner.nextLine();
                        System.out.print("Ingrese semestre actual: ");
                        String semestre = scanner.nextLine();
                        sistema.agregarEstudiante(nombre, id, email, semestre);
                        break;
                    
                    case 19:
                        System.out.print("Ingrese nombre completo: ");
                        String nombre = scanner.nextLine();
                        System.out.print("Ingrese ID: ");
                        String id = scanner.nextLine();
                        System.out.print("Ingrese email: ");
                        String email = scanner.nextLine();
                        System.out.print("Ingrese semestre actual: ");
                        String semestre = scanner.nextLine();
                        sistema.agregarEstudiante(nombre, id, email, semestre);
                        break;
                    
                    case 20:
                        System.out.print("Ingrese nombre completo: ");
                        String nombre = scanner.nextLine();
                        System.out.print("Ingrese ID: ");
                        String id = scanner.nextLine();
                        System.out.print("Ingrese email: ");
                        String email = scanner.nextLine();
                        System.out.print("Ingrese semestre actual: ");
                        String semestre = scanner.nextLine();
                        sistema.agregarEstudiante(nombre, id, email, semestre);
                        break;
                    
                    case 21:
                        System.out.print("Ingrese nombre completo: ");
                        String nombre = scanner.nextLine();
                        System.out.print("Ingrese ID: ");
                        String id = scanner.nextLine();
                        System.out.print("Ingrese email: ");
                        String email = scanner.nextLine();
                        System.out.print("Ingrese semestre actual: ");
                        String semestre = scanner.nextLine();
                        sistema.agregarEstudiante(nombre, id, email, semestre);
                        break;
                    
                    case 22:
                        System.out.print("Ingrese nombre completo: ");
                        String nombre = scanner.nextLine();
                        System.out.print("Ingrese ID: ");
                        String id = scanner.nextLine();
                        System.out.print("Ingrese email: ");
                        String email = scanner.nextLine();
                        System.out.print("Ingrese semestre actual: ");
                        String semestre = scanner.nextLine();
                        sistema.agregarEstudiante(nombre, id, email, semestre);
                        break;
                            
                    default:
                        break;
                }





        }
    }
}

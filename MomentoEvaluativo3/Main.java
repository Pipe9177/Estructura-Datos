package MomentoEvaluativo3;

import java.util.Scanner;

//Renombrado por problemas de compilacion ( ejecutar.java -> main.java)
public class Main {
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
                        String codMat = scanner.nextLine();
                        System.out.print("Nombre de materia: ");
                        String nomMat = scanner.nextLine();
                        System.out.print("Cupo maximo de estudiantes: ");
                        int cupoMax = Integer.parseInt(scanner.nextLine());
                        sistema.crearMateria(codMat, nomMat, cupoMax);
                        break;

                    case 6:
                        System.out.print("Codigo de materia a agregar pre-requisito: ");
                        String codiReque = scanner.nextLine();
                        System.out.print("Codigo Materia de pre-requisito obligatorio: ");
                        String codiRequeObli = scanner.nextLine();
                        sistema.agregarRequisito(codiReque, codiRequeObli);
                        break;
                    
                    case 7:
                        System.out.print("Codigo materia a consultar requisitos: ");
                        String requisiConsul = scanner.nextLine(); // Consulta los requisitos
                        sistema.mostrarRequisitos(requisiConsul);
                        break;
                    
                    case 8:
                        System.out.print("Ingrese ID del estudiante: ");
                        String idIns = scanner.nextLine(); // ID a inscribir
                        System.out.print("Ingrese codigo de la materia a inscribir: ");
                        String codIns = scanner.nextLine();
                        sistema.estudianteMateria(idIns, codIns);
                        break;
                    
                    case 9:
                        System.out.print("Ingrese ID del estudiante: ");
                        String idCan = scanner.nextLine(); //ID a cancelar materia
                        System.out.print("Ingrese codigo de la materia a cancelar: ");
                        String codCan = scanner.nextLine();
                        sistema.cancelar(idCan, codCan);
                        break;
                    
                    case 10:
                        sistema.mostrarColaEspera();
                        break;
                    
                    case 11:
                        System.out.print("\n-----RESERVAR HORARIO EN SALON-----");
                        sistema.mostrarSalonDisponibles();
                        System.out.print("Salon: ");
                        int codSalon = Integer.parseInt(scanner.nextLine()); // codigo del salon a reservar
                        System.out.print("\nDIAS: 0. Domingo, 1. Lunes, 2. Martes, 3. Miercoles, 4. Jueves, 5. Viernes, 6. Sabado");
                        int dia = Integer.parseInt(scanner.nextLine());
                        System.out.print("Hora ( 0 - 23 ): ");
                        int hora = Integer.parseInt(scanner.nextLine());
                        System.out.print("Duracion (en horas): ");
                        int duracion = Integer.parseInt(scanner.nextLine());
                        sistema.reservar(codSalon, dia, hora, duracion);
                        break;
                    
                    case 12:
                        System.out.print("\n-----LIBERAR HORARIO EN SALON-----");
                        System.out.print("Salon a liberar: ");
                        int codLib = Integer.parseInt(scanner.nextLine()); //codigo del salon a liberar
                        System.out.print("Dia: ");
                        int diaLib = Integer.parseInt(scanner.nextLine());
                        System.out.print("Hora: ");
                        int horaLib = Integer.parseInt(scanner.nextLine());
                        System.out.print("Duracion (en horas): ");
                        int duracionLib = Integer.parseInt(scanner.nextLine());
                        sistema.obtenerSalon(codLib).liberar(diaLib, horaLib, horaLib + duracionLib); // Se asume que se libera por una hora, se puede modificar para liberar por mas horas
                        break;
                     
                    case 13:
                        System.out.print("Salon consultado: ");
                        int salCon = Integer.parseInt(scanner.nextLine());
                        System.out.print("Dia: ");
                        int diaCon = Integer.parseInt(scanner.nextLine());
                        System.out.print("Hora: ");
                        int horaCon = Integer.parseInt(scanner.nextLine());
                        boolean respuesta = sistema.obtenerSalon(salCon).disponibilidad(diaCon, horaCon);
                        System.out.println("El salon " + salCon + " se encuentra " + (respuesta ? "LIBRE" : "OCUPADO") + " en el dia " + diaCon + " a la hora " + horaCon);
                        break;
                    
                    case 14:
                        rutas.mostrarMapa();
                        System.out.print("ID Edificio origen: ");
                        int origen = Integer.parseInt(scanner.nextLine());
                        System.out.print("ID Edificio destino: ");
                        int destino = Integer.parseInt(scanner.nextLine());
                        System.out.print("Distancia en metros: ");
                        int distancia = Integer.parseInt(scanner.nextLine());
                        rutas.registrarConexion(origen, destino, distancia);
                        break;
                    
                    case 15:
                        System.out.print("\n-----CALCULAR RUTA MAS CORTA-----");
                        rutas.mostrarMapa();
                        System.out.print("Origen: ");
                        int pOrigen = Integer.parseInt(scanner.nextLine()); //Punto de partida
                        System.out.print("Destino: ");
                        int pDestino = Integer.parseInt(scanner.nextLine()); //Punto de llegada
                        rutas.rutaCorta(pOrigen, pDestino);
                        break;
                    
                    case 16:
                        System.out.print("Ingrese ID del estudiante: ");
                        String idNotas = scanner.nextLine();
                        System.out.print("Semestre de la nota ( 0 - 9 ) : ");
                        int semestre = Integer.parseInt(scanner.nextLine());
                        System.out.print("Indice de materia ( 0 - 19 ) : ");
                        int mateNotas = Integer.parseInt(scanner.nextLine());
                        System.out.print("Calificacion ( 0.0 - 5.0) : ");
                        double caliNotas = Double.parseDouble(scanner.nextLine());
                        sistema.buscarEstudiante(idNotas).registrarNota(semestre, mateNotas, caliNotas);
                        System.out.println("Nota registrada exitosamente para el estudiante con ID: " + idNotas);
                        break;
                    
                    case 17:
                        System.out.print("Ingrese ID del estudiante: ");
                        String idRepor = scanner.nextLine(); // ID del estudiante para visualizar reportes academicos
                        System.out.print("Semestre a promediar: ");
                        int semesRepor = Integer.parseInt(scanner.nextLine());
                        double promedio = sistema.buscarEstudiante(idRepor).calcularPromedio(semesRepor);
                        System.out.println("El promedio del estudiante con ID " + idRepor + " en el semestre " + semesRepor + " es: " + promedio);
                        break;
                    
                    case 18:
                        System.out.println("\n-----NAVEGADOR DE REPORTES-----");
                        System.out.println("1. Ver reporte academico");
                        System.out.println("2. Ver reporte anterior ( ATRAS )");
                        System.out.println("3. Ver el reporte siguiente ( ADELANTE )");
                        System.out.print("Seleccione una opcion: ");

                        int opNavegacion = Integer.parseInt(scanner.nextLine());

                        if (opNavegacion == 1){
                            System.out.print("Nombre del reporte a visualizar: ");
                            String nomReporte = scanner.nextLine();
                            sistema.verReporte(nomReporte);
                        } else if (opNavegacion == 2){
                            sistema.navegarAtras();
                        } else if (opNavegacion == 3){
                            System.out.print("Nombre del siguiente reporte: ");
                            String reporteSigui = scanner.nextLine();
                            sistema.navegarAdelante(reporteSigui);
                        } else {
                            System.out.println("Opcion de navegacion no permitida.");
                        }
                        break;

                    case 19:
                        sistema.deshacer();
                        break;
                    
                    case 20:
                        sistema.rehacer();
                        break;
                    
                    case 21:
                        System.out.print("\n-----ARCHIVO CSV-----");
                        System.out.print("ingrese nombre del archivo ( .csv ): ");
                        String archivo = scanner.nextLine();
                        sistema.procesarCSV(archivo);
                        break;
                    
                    case 22:
                        System.out.println("Saliendo del sistema. ¡Hasta luego!");
                        break;
                            
                    default:
                        System.out.println("Opcion invalida. Intente nuevamente.");
                }
            } catch (Exception e){
                System.out.println("Error: " + e.getMessage());
            }

            if (op !=  22){
                System.out.print("\nPresione 'ENTER' para volver a desplegar el menu.");
                scanner.nextLine();
            }
        } while (op != 22) {
            
        }
    }
}

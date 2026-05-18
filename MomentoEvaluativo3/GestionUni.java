package MomentoEvaluativo3;


import java.util.*; //Importa todo
import java.io.BufferedReader; //Lectura de archivos (punto 21)
import java.io.FileReader; // Canal fisico en el archivo CSV (punto 21)

//Controlador del sistema en el cual se basa el main
public class GestionUni {

    private HashMap<String, Estudiante> mapaEstudiantes = new HashMap<>(); // Mapa para almacenar estudiantes con su ID
                                                                           // como clave
    private HashMap<String , Materia> mapaMaterias = new HashMap<>(); // Mapa para almacenar materias con su código como clave
    private TreeMap<Interger, GestionHora> inventarioSalon = new TreeMap<>();
    private Stack<String> pilaDeshacer = new Stack<>(); // Pila para almacenar acciones realizadas y permitir
                                                        // deshacerlas
    private Stack<String> pilaRehacer = new Stack<>(); // Para almacenar y rehacer lo hecho
    private Queue<String> colaEspera = new LinkedList<>(); // Gestiona el flujo de espera
    private Stack<String> pilaNavegacion = new Stack<>(); // Navegacion atras/adelante en los reportes

    public GestionUni() {
        inventarioSalon.put(305, new GestionHora("Aula 305"));
        inventarioSalon.put(490, new GestionHora("Aula 490"));
        inventarioSalon.put(101, new GestionHora("Aula 101"));
        inventarioSalon.put(207, new GestionHora("Aula 207"));

    }

    // Método para procesar el archivo CSV de inscripciones ( case 21 )
    public void procesarCSV(String rutaArchivo) throws ArchivoNoEncontradoException {
        // Uso de try-with-resources para garantizar el cierre automático de los descriptores de archivos
        try (BufferedReader lector = new BufferedReader(new FileReader(rutaArchivo)){
            String linea; //Variable temporal para almacenar
            int procesados = 0; // Contador de control para el reporte final

            System.out.println("\n------PROCESANDO ARCHIVO CSV------");
            //Contamos con un bucle que recorre todo el archivo fila a fila hasta llegar a null
            while((linea = lector.readLine()) != null){
              //Si esta vacia o no tiene linea divisoria marcar error
              if (linea.trim().isEmpty() || !linea.contains(',')){
                throw new ArchivoInvalidoException("Error: El archivo CSV no tiene el formato correcto. Asegúrese de que cada línea contenga datos separados por comas.");
              }

              String[] campos = linea.split(","); // Divide la línea en campos utilizando la coma como delimitador
              String idEstu = campos[0].trim(); // El primer campo es el ID del estudiante
              String codigoMateria = campos[1].trim(); // El segundo campo es el código de la materia

              System.out.println("Procesando inscripcion: Estudiante ID: " + idEstu + " en materia: " + codigoMateria);
              procesados++;

            }

            System.out.println("\nArchivo CSV procesado correctamente. Total de inscripciones procesadas: " + procesados);

        } catch (java.io.FileNotFoundException e) {
            throw new ArchivoNoEncontradoException("Error: El archivo CSV no se encontró en la ruta especificada: " + rutaArchivo); // Lanza una excepción personalizada si el archivo no se encuentra
        } catch (java.io.IOException e) {
            throw new ArchivoInvalidoException("Error: No se pudo leer el archivo CSV. Asegúrese de que el archivo esté en el formato correcto y sea accesible."); // Lanza una excepción personalizada para errores de lectura
        }
    }

    //Crear materia ( case 5 )
    public void crearMateria(String codigo, String nombreMate, int cupoMax){
        Materia nuevaMateria = new Materia(codigo, nombreMate, cupoMax);
        mapaMaterias.put(codigo, nuevaMateria);
        System.out.println("\nMateria {" + nombreMate + "} creado correctamente con el codigo: " + codigo + " y contando con cupo Maximo de: " + cupoMax + " estudiantes.");
    }

    //Pre-requisitos ( case 6 )
    public void agregarRequisito(String codigoMateria, String codigoReque) throws Exception{
        if (!mapaMaterias.containsKey(codigoMateria)){
            throw new Exception("ERROR: La materia con codigo " + codigoMateria + " no se encuentra en el sistema.");
        } 
        if (!mapaMaterias.containsKey(codigoReque)){
            throw new Exception("ERROR: La materia con codigo " + codigoReque + " no se encuentra en el sistema.");
        }

        Materia materia = mapaMaterias.get(codigoMateria); 
        materia.agregarRequisito(codigoReque);
        System.out.println("\nRequisito {" + codigoReque + "} agregado correctamente a la materia {" + codigoMateria + "}");
    }


    //Consultar requisitos ( case 7 )
    public void mostrarRequisitos(String codigoMateria) throws Exception{
        if(!mapaMaterias.containsKey(codigoMateria)){
            throw new Exception("ERROR: La materia con codigo " + codigoMateria + " no se encuentra en el sistema.");
        }

        Materia materia = mapaMaterias.get(codigoMateria);
        LinkedList<String> requisitos = materia.getRequisitos(); // Obtiene la lista de requisitos de la materia
        System.out.println("\nRequisitos para la materia {" + codigoMateria + "}:");
            if (requisitos.isEmpty()){
                System.out.println("La materia no cuenta con pre-requisitos.");
            } else {
                for (String requi : requisitos){
                    Materia lista = mapaMaterias.get(requi); // Obtiene el nombre de la materia requisito a partir de su código
                    System.out.println(" - {" + requi + "} - " + lista.getNombreMate());
                }
            }
        }

    // Inscribir estudiante a materia ( case 8 )
    public void estudianteMateria(String idEstudiante, String codigoMateria) throws EstudianteNoEncontradoException{
            if (!mapaEstudiantes.containsKey(idEstudiante)){
                throw new EstudianteNoEncontradoException("LASTIMOSAMENTE EL ESTUDIANTE NO HA SIDO ENCONTRADO.");
            }
            if (!mapaMaterias.containsKey(codigoMateria)){
                throw new EstudianteNoEncontradoException("EL ESTUDIANTE NO ESTA EN ESTA MATERIA REGISTRADO.");
            }

            Estudiante estudiante = mapaEstudiantes.get(idEstudiante);
            Materia materia = mapaMaterias.get(codigoMateria);

            //Verificacion de que cumpla con los requisitos previos
            for (String requi : materia.getPreRequisitos()){
                if(!estudiante.verificarAprobacion(requi)){ // Si el estudiante no ha aprobado alguno de los requisitos, lanza la excepcion
                    throw new PreRequisitoNoAprobadoException("ERROR DE INSCRIPCION: El estudiante no ha aprobado las materias requeridas.")
                }
            }

            //Verificacion de que haya cupos disponibles en la materia
            if (!materia.verificarCupo()){
                colaEspera.add("INSCRIPCION: " + idEstudiante + ", " + codigoMateria); // Si no hay cupos, agrega al estudiante a la cola de espera
                throw new CupoLlenoException("LAMENTAMOS INFORMARLE: La materia: " + materia.getNombreMate() + " se encuentra llena.");
            }

            //Si cumple con los requisitos y hay cupo, inscribir al estudiante
            materia.getInscritos().add(idEstudiante); // Agrega el ID del estudiante a la lista de inscritos de la materia
            pilaDeshacer.push("INSCRIPCION EXITOSA: " + idEstudiante + ", " + codigoMateria);
            System.out.println("\nEl estudiante con ID: " + idEstudiante + " ha sido inscrito exitosamente en la materia: " + materia.getNombreMate());


        }


    //Cancelar inscripcion ( case 9 )
    public void cancelar(String idEstudiante, String codigoMateria) throws Exception{
            if (!mapaMaterias.containsKey(codigoMateria)){
                throw new Exception("ERROR: La materia con codigo " + codigoMateria + " no se encuentra en el sistema.");
            }

            Materia materia = mapaMaterias.get(codigoMateria);
            if (!materia.getInscritos().contains(idEstudiante)){
                throw new Exception("EL ESTUDIANTE NO SE ENCUENTRA INSCRITO.");
            }

            materia.getInscritos().remove(idEstudiante); // Elimina el ID del estudiante de la lista de inscritos de la materia
            System.out.println("\nLa inscripcion del estudiante con ID: " + idEstudiante + " en la materia: " + materia.getNombreMate() + " ha sido cancelada exitosamente.");
        
            //Cola de espera ( case 10 )
            if (!colaEspera.isEmpty()){
                String solicitud = colaEspera.poll(); // Obtiene la siguiente solicitud en la cola de espera
                String[] partes = solicitud.split(":"); // Divide la solicitud en partes utilizando ":" como delimitador
                String[] datos = partes[1].split(","); // Divide los datos de la solicitud en partes utilizando "," como delimitador
                String idEstu = datos[0];
                String codMate = datos[1];

                if (codMate.equals(codigoMateria)){ // El equals sirve para comparar el código de la materia de la solicitud con el código de la materia que se acaba de liberar
                    System.out.println("\n-----COLA DE ESPERA-----");
                    materia.getInscritos().add(idEstu); // Inscribe al estudiante que estaba en la cola de espera
                    System.out.println("El estudiante con ID: " + idEstu + " ha sido inscrito exitosamente en la materia: " + materia.getNombreMate() + " desde la cola de espera.");
                } else {
                    colaEspera.add(solicitud); // Si el código de la materia no coincide, vuelve a agregar la solicitud al final de la cola de espera
                }
            }
        }

    // Registrar estudiante ( case 1 )
    public void agregarEstudiante(Estudiante estudiante) {
        mapaEstudiantes.put(estudiante.getId(), estudiante);
        pilaDeshacer.push("Agregar estudiante: " + estudiante.getNombre() + " ID: " + estudiante.getId());
        pilaRehacer.clear(); // Limpiar la pila de rehacer al realizar una nueva acción
        System.out.println("Estudiante agregado correctamente");

    }

    // Buscar estudiante por ID ( case 2 )
    public Estudiante buscarEstudiante(String id) throws EstudianteNoEncontradoException {
        if (!mapaEstudiantes.containsKey(id)) { // Verifica si el estudiante existe en el mapa
            throw new EstudianteNoEncontradoException("Error: Estudiante con ID " + id + " no encontrado."); // Lanza una excepción si no se
                                                                                       // encuentra el estudiante
        }
        return mapaEstudiantes.get(id); // Regresa al estudiante encontrado
    }

    // Mostrar información de todos los estudiantes ( case 3 )
    public void listaEstudiantes(){
        if (mapaEstudiantes.isEmpty()){ //Verifica si el mapa esta vacio
            System.out.println("LASTIMOSAMENTE: No hay estudiantes registrados");
            return;
        } else {
            for (Estudiante estudiante : mapaEstudiantes.values()){ // Muestra la lista de estudiantes registrados
                System.out.println("\n------LISTA DE ESTUDIANTES------");
                estudiante.mostrarInformacion(); // Llama al método mostrarInformación de cada estudiante para mostrar sus detalles
            }
            }
        }

    //Eliminar estudiante ( case 4 )
    public void eliminarEstudiante(String id) throws EstudianteNoEncontradoException{
        if(!mapaEstudiantes.containsKey(id)) { // Verifica si el estudiante existe en el mapa
            throw new EstudianteNoEncontradoException("Error: Estudiante con ID " + id + " no encontrado."); // Lanza una excepción si no se encuentra el estudiante
            return;
        } else {
            Estudiante eliminado = mapaEstudiantes.remove(id);
            pilaDeshacer.push("Eliminar estudiante: " + eliminado.getNombre() + " ID: " + eliminado.getId());
            System.out.println("\nEstudiante eliminado de la universidad debido a: BAJA ACADEMICA");
        }
    }

    // Obtener un salón específico ( case 11 )
    public GestionHora obtenerSalon(int salon) throws Exception{
        if (!inventarioSalon.containsKey(salon)){
                throw new Exception("LASTIMA: El salon seleccionado no se encuentra registrado");
            }
            return inventarioSalon.get(salon); 
        }

    //Muestra SALONES disponibles
    public void mostrarDisponibles(){
        System.out.println("\n------SALONES DISPONIBLES------");
        for (Interger salon : inventarioSalon.keySet()){
            System.out.println("Salon: " + salon + " - " + inventarioSalon.get(salon).getNombre()); // Muestra el número de salón y su nombre
        }
    }

    // Método auxiliar para el menú que lista las llaves del TreeMap
    public void mostrarSalonDisponibles() {
        System.out.print("Salones en el TreeMap: ");
        for (Integer numero : inventarioSalon.keySet()) { 
            System.out.print("[" + numero + "] "); 
        }
        System.out.println();
    }

    public void mostrarColaEspera() {
        System.out.println("Cola de espera de inscripciones actual: " + colaEspera); 
    }


    //Pila de navegacion para los reportes ( case 18 )
    public void verReporte(String reporte){
        pilaNavegacion.push(reporte);
        System.out.println("Visualizando reporte: " + reporte);
    }

    // ATRAS Y ADELANTE ( case 18 )
    public void navegarAtras(){
        if (pilaNavegacion.isEmpty()){
            System.out.println("No hay reportes anteriores que visualizar");
            return;
        } else {
            pilaNavegacion.pop(); // Elimina el reporte actual de la pila
            System.out.println("\nVisualizando el reporte anterior: " + pilaNavegacion.peek()); // Muestra el reporte anterior sin eliminarlo de la pila
        }
    }
    
    //Continuacion ( case 18 )
    public void navegarAdelante(String reporte){
        if (pilaNavegacion.isEmpty()){
            System.out.println("No hay reportes anteriores que visualizar");
            return;
        } else {
            pilaNavegacion.push(reporte); // Agrega el reporte actual a la pila
            System.out.println("\nVisualizando el reporte siguiente: " + reporte); // Muestra el reporte siguiente
        }
    }

    // Deshacer ( case 19 )
    public void deshacer() throws Exception{
        if (pilaDeshacer.isEmpty()){
            throw new Exception("No hay acciones para deshacer");
        } else {
            String accionDes = pilaDeshacer.pop(); // Deshace la ultima accion realizada
            pilaRehacer.push(accionDes); // Agrega la accion deshecha a la pila de rehacer
            System.out.println("Accion deshecha: " + accionDes + " correctamente");
        }
    }

    //Rehacer ( case 20 )
    public void rehacer() throws Exception{
        if (pilaRehacer.isEmpty()){
            throw new Exception("NO HAY ACCION REALIZADA ANTERIORMENTE PARA REHACER");
        } else {
            String accionRes = pilaRehacer.pop();
            pilaDeshacer.push(accionRes);
            System.out.println("Accion rehecha: " + accionRes + " correctamente");
        }
    }
}
    


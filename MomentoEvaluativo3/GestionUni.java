package MomentoEvaluativo3;

//Lectura de archivos (punto 21)
import java.util.*; //Importa todo
import java.io.BufferedReader;
import java.io.FileReader; // Canal fisico en el archivo CSV (punto 21)

//Controlador del sistema
public class GestionUni {

    private HashMap<String, Estudiante> mapaEstudiantes = new HashMap<>(); // Mapa para almacenar estudiantes con su ID
                                                                           // como clave
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

    // Registrar estudiante
    public void agregarEstudiante(Estudiante estudiante) {
        mapaEstudiantes.put(estudiante.getId(), estudiante);
        pilaDeshacer.push("Agregar estudiante: " + estudiante.getNombre() + " ID: " + estudiante.getId());
        pilaRehacer.clear(); // Limpiar la pila de rehacer al realizar una nueva acción
        System.out.println("Estudiante agregado correctamente");

    }

    public Estudiante buscarEstudiante(String id) throws Exception {
        if (!mapaEstudiantes.containsKey(id)) { // Verifica si el estudiante existe en el mapa
            throw new Exception("Error: Estudiante con ID " + id + " no encontrado."); // Lanza una excepción si no se
                                                                                       // encuentra el estudiante
        }
        return mapaEstudiantes.get(id); // Regresa al estudiante encontrado
    }

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

        public void eliminarEstudiante(String id) throws Exception{
            if(!mapaEstudiantes.containsKey(id)) { // Verifica si el estudiante existe en el mapa
                throw new Exception("Error: Estudiante con ID " + id + " no encontrado."); // Lanza una excepción si no se encuentra el estudiante
                return;
            } else {
                Estudiante eliminado = mapaEstudiantes.remove(id);
                pilaDeshacer.push("Eliminar estudiante: " + eliminado.getNombre() + " ID: " + eliminado.getId());
                System.out.println("\nEstudiante eliminado de la universidad debido a: BAJA ACADEMICA");
            }
        }

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


        //Cola de espera para materias con cupo lleno
        public void colaEspera(String solicitud){
            if (colaEspera.size() >= 10){
                System.out.println("DISCULPENOS: Ahora mismo se encuentra lleno la cola, porfavor intentar mas tarde");
            } else {
                colaEspera.add(solicitud);
                System.out.println("Estudiante asignado a la cola de espera");
            }
        }

        public void mostrarCola(){
            if (colaEspera.isEmpty()){
                System.out.println("No hay estudiantes en la cola de espera");
                return;
            } else {
                System.out.println("Cola de espera actual: " + colaEspera);
            }
        }

        //Pila de navegacion para los reportes
        public void verReporte(String reporte){
            pilaNavegacion.push(reporte);
            System.out.println("Visualizando reporte: " + reporte);
        }

        public void navegarAtras(){
            if (pilaNavegacion.isEmpty()){
                System.out.println("No hay reportes anteriores que visualizar");
                return;
            } else {
                pilaNavegacion.pop(); // Elimina el reporte actual de la pila
                System.out.println("\nVisualizando el reporte anterior: " + pilaNavegacion.peek()); // Muestra el reporte anterior sin eliminarlo de la pila
            }
        }

        public void navegarAdelante(String reporte){
            if (pilaNavegacion.isEmpty()){
                System.out.println("No hay reportes anteriores que visualizar");
                return;
            } else {
                pilaNavegacion.push(reporte); // Agrega el reporte actual a la pila
                System.out.println("\nVisualizando el reporte siguiente: " + reporte); // Muestra el reporte siguiente
            }
        }

        public void deshacer() throws Exception{
            if (pilaDeshacer.isEmpty()){
                throw new Exception("No hay acciones para deshacer");
            } else {
                String accionDes = pilaDeshacer.pop(); // Deshace la ultima accion realizada
                pilaRehacer.push(accionDes); // Agrega la accion deshecha a la pila de rehacer
                System.out.println("Accion deshecha: " + accionDes + " correctamente");
            }
        }

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
}

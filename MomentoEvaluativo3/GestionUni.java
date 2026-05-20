package MomentoEvaluativo3;


import java.util.*; //Importa todo
import java.io.BufferedReader; //Lectura de archivos (punto 21)
import java.io.FileReader; // Canal fisico en el archivo CSV (punto 21)

//Controlador del sistema en el cual se basa el main
public class GestionUni {

    private HashMap<String, Estudiante> mapaEstudiantes = new HashMap<>(); // Mapa para almacenar estudiantes con su ID
                                                                           // como clave
    private HashMap<String , Materia> mapaMaterias = new HashMap<>(); // Mapa para almacenar materias con su código como clave
    private TreeMap<Integer, GestionHora> inventarioSalon = new TreeMap<>();
    private Stack<almacenamiento> pilaDeshacer = new Stack<>(); // Pila para almacenar acciones realizadas y permitir
                                                        // deshacerlas
    private Stack<almacenamiento> pilaRehacer = new Stack<>(); // Para almacenar y rehacer lo hecho
    private Queue<String> colaEspera = new LinkedList<>(); // Gestiona el flujo de espera
    private Stack<String> pilaNavegacion = new Stack<>(); // Navegacion atras/adelante en los reportes

    public GestionUni() {
        inventarioSalon.put(305, new GestionHora("Aula 305"));
        inventarioSalon.put(490, new GestionHora("Aula 490"));
        inventarioSalon.put(101, new GestionHora("Aula 101"));
        inventarioSalon.put(207, new GestionHora("Aula 207"));

    }

    // Método para procesar el archivo CSV de inscripciones ( case 21 )
    public void procesarCSV(String rutaArchivo) throws ArchivoInvalidoException, ArchivoNoEncontradoException {
        System.out.println("\n------PROCESANDO ARCHIVO CSV------");
        int procesados = 0; //Contador de control para el reporte final
        int exitosas = 0;
        int enCola = 0;
        int errores = 0;
        
        // Uso de try-with-resources para garantizar el cierre automático de los descriptores de archivos
        try (BufferedReader lector = new BufferedReader(new FileReader(rutaArchivo))){
            String linea; //Variable temporal para almacenar
            while ((linea = lector.readLine()) != null){ //Contamos con un bucle que recorre todo el archivo fila a fila hasta llegar a null
                if(linea.trim().isEmpty()){
                    continue;
                }

                procesados++;
              
                if (!linea.contains(",")){ //Si no tiene linea divisoria marcar error
                    errores++;
                    System.out.println("FILA [" + procesados + "] -> ERROR: No se encontro separador ','");
                    continue;
                }

              String[] datos = linea.split(",");
              if(datos.length < 2){
                    errores++;
                    System.out.println("Fila [" + procesados + "] -> Error: Faltan columnas.");
                    continue;
                }

                String idEstudiante = datos[0].trim();
                String codigoMateria = datos[1].trim();

                try {
                    estudianteMateria(idEstudiante, codigoMateria);
                    exitosas++;
                } catch (CupoLlenoException e) {
                    enCola++;
                    System.out.println("Fila [" + procesados + "] -> A Cola de Espera: Estudiante " + idEstudiante);
                } catch (EstudianteNoEncontradoException e) {
                    errores++;
                    System.out.println("Fila [" + procesados + "] -> Error de Negocio: " + e.getMessage());
                } catch (PreRequisitoNoAprobadoException e) {
                    errores++;
                    System.out.println("Fila [" + procesados + "] -> Error: " + e.getMessage());
                } catch (Exception e) {
                    errores++;
                    System.out.println("Fila [" + procesados + "] -> Error de Negocio inesperado: " + e.getMessage());
                }
            }

            System.out.println("\n=======================================================");
            System.out.println("        PROCESAMIENTO POR LOTES TERMINADO");
            System.out.println("=======================================================");
            System.out.println("Total de registros evaluados: " + procesados);
            System.out.println("Inscripciones procesadas con éxito: " + exitosas);
            System.out.println("Asignados a la cola de espera: " + enCola);
            System.out.println("Inscripciones fallidas / Errores: " + errores);
            System.out.println("=======================================================");

        } catch (java.io.FileNotFoundException e) {
            throw new ArchivoNoEncontradoException("Error: El archivo CSV no se encontró en la ruta especificada: " + rutaArchivo); // Lanza una excepción personalizada si el archivo no se encuentra
        } catch (java.io.IOException e) {
            throw new ArchivoInvalidoException("Fallo en la lectura de lote: " + e.getMessage());
        }
    }

    //Crear materia ( case 5 )
    public void crearMateria(String codigo, String nombreMate, int cupoMax){

        Materia nuevaMateria = new Materia(codigo, nombreMate, cupoMax);
        mapaMaterias.put(codigo, nuevaMateria);

        //historial para conservar accion deshacer y rehacer
        pilaDeshacer.push(new almacenamiento("CREAR MATERIA", codigo, nombreMate, cupoMax));
        pilaRehacer.clear();

        System.out.println("\nMateria [" + nombreMate + "] creado correctamente con el codigo: " + codigo + " y contando con cupo Maximo de: " + cupoMax + " estudiantes.");
    }

    //Pre-requisitos ( case 6 )
    public void agregarRequisito(String codigoMateria, String codigoReque) throws Exception{
        if (!mapaMaterias.containsKey(codigoMateria) || !mapaMaterias.containsKey(codigoReque)){
            throw new Exception("ERROR: Una o ambas materias no se encuentra en el sistema.");
        } 

        mapaMaterias.get(codigoMateria).agregarRequisito(codigoReque);

        //historial para deshacer y rehacer
        pilaDeshacer.push(new almacenamiento("AGREGAR REQUISITO", codigoMateria, codigoReque));
        pilaRehacer.clear();

        System.out.println("\nRequisito {" + codigoReque + "} agregado correctamente a la materia {" + codigoMateria + "}");
    }


    //Consultar requisitos ( case 7 )
    public void mostrarRequisitos(String codigoMateria) throws Exception{
        if(!mapaMaterias.containsKey(codigoMateria)){
            throw new Exception("ERROR: La materia con codigo " + codigoMateria + " no se encuentra en el sistema.");
        }

        System.out.println("\nRequisitos para la materia {" + codigoMateria + "} son: " + mapaMaterias.get(codigoMateria).getPreRequisitos());
        
    }

    // Inscribir estudiante a materia ( case 8 )
    public void estudianteMateria(String idEstudiante, String codigoMateria) throws EstudianteNoEncontradoException, PreRequisitoNoAprobadoException, CupoLlenoException, Exception{
            if (!mapaEstudiantes.containsKey(idEstudiante)){
                throw new EstudianteNoEncontradoException("LASTIMOSAMENTE EL ESTUDIANTE " + idEstudiante + " NO HA SIDO ENCONTRADO.");
            }
            if (!mapaMaterias.containsKey(codigoMateria)){
                throw new Exception("LA MATERIA NO EXISTE");
            }

            Estudiante estudiante = mapaEstudiantes.get(idEstudiante);
            Materia materia = mapaMaterias.get(codigoMateria);

            //Verificacion de que cumpla con los requisitos previos
            for (String requi : materia.getPreRequisitos()){
                if(!estudiante.verificarAprobacion(requi)){ // Si el estudiante no ha aprobado alguno de los requisitos, lanza la excepcion
                    throw new PreRequisitoNoAprobadoException("ERROR DE INSCRIPCION: El estudiante no ha aprobado las materias requeridas.");
                }
            }

            //Verificacion de que haya cupos disponibles en la materia
            if (!materia.verificarCupo()){
                colaEspera.add("INSCRIPCION: " + idEstudiante + ", " + codigoMateria);
                throw new CupoLlenoException("CUPO AGOTADO: Procesado por la cola de espera.");
            }

            //Si cumple con los requisitos y hay cupo, inscribir al estudiante
            materia.getInscritos().add(idEstudiante); // Agrega el ID del estudiante a la lista de inscritos de la materia
            pilaDeshacer.push(new almacenamiento("INSCRIPCION EXITOSA", codigoMateria, idEstudiante));
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

            // historial 
            pilaDeshacer.push(new almacenamiento("CANCELAR", codigoMateria, idEstudiante));
            pilaRehacer.clear();

            System.out.println("\nLa inscripcion del estudiante con ID: " + idEstudiante + " en la materia: " + materia.getNombreMate() + " ha sido cancelada exitosamente.");
        
            //Cola de espera ( case 10 )
            if (!colaEspera.isEmpty()){
                String solicitud = colaEspera.peek(); // Obtiene la siguiente solicitud en la cola de espera
                String[] partes = solicitud.split(":"); // Divide la solicitud en partes utilizando ":" como delimitador
                String[] datos = partes[1].split(","); // Divide los datos de la solicitud en partes utilizando "," como delimitador
                String idSiguiente = datos[0];
                String codSiguiente = datos[1];

                if (codSiguiente.equals(codigoMateria)){ // El equals sirve para comparar el código de la materia de la solicitud con el código de la materia que se acaba de liberar
                    System.out.println("\n-----COLA DE ESPERA-----");
                    colaEspera.poll();
                    materia.getInscritos().add(idSiguiente); // Inscribe al estudiante que estaba en la cola de espera
                    System.out.println("El estudiante con ID: " + idSiguiente + " ha sido inscrito exitosamente en la cola de espera.");
                } 
            }
        }

    // Registrar estudiante ( case 1 )
    public void agregarEstudiante(String nombre, String id, String email, int semestre) {
        Estudiante nuevo = new Estudiante(nombre, id, email, semestre);
        mapaEstudiantes.put(id, nuevo);
        pilaDeshacer.push(new almacenamiento("AGREGAR ESTUDIANTE", nuevo));
        //Almaceno la informacion proporcionada para despues utilizarlo (si es necesario)
        pilaRehacer.clear(); // Limpiar la pila de rehacer al realizar una nueva acción
        System.out.println("Estudiante agregado correctamente en el HashMap");

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
        } 
            for (Estudiante estudiante : mapaEstudiantes.values()){ // Muestra la lista de estudiantes registrados
                if( estudiante != null ){
                System.out.println("\n------LISTA DE ESTUDIANTES------");
                estudiante.mostrarInformacion(); // Llama al método mostrarInformación de cada estudiante para mostrar sus detalles
                }
            }
        }

    //Eliminar estudiante ( case 4 )
    public void eliminarEstudiante(String id) throws EstudianteNoEncontradoException{
        if(!mapaEstudiantes.containsKey(id)) { // Verifica si el estudiante existe en el mapa
            throw new EstudianteNoEncontradoException("Error: Estudiante con ID " + id + " no encontrado."); // Lanza una excepción si no se encuentra el estudiante
        } 
            Estudiante estuElimi = mapaEstudiantes.get(id);
            //Se recupera la informacion antes de eliminarlo
            pilaDeshacer.push(new almacenamiento("ELIMINAR ESTUDIANTE", estuElimi));
            pilaRehacer.clear();
            mapaEstudiantes.remove(id);
            System.out.println("\nEstudiante eliminado de la universidad debido a: BAJA ACADEMICA");
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
        for (Integer salon : inventarioSalon.keySet()){
            System.out.println("Salon: " + salon + " - " + inventarioSalon.get(salon).getNombre()); // Muestra el número de salón y su nombre
        }
    }


    public void mostrarColaEspera(String solicitud) {
       if(colaEspera.size() >= 10){
        System.out.println("SE ENCUENTRA LLENA LA COLA DE ESPERA");
       } else {
        colaEspera.add(solicitud);
        System.out.println("Estudiante asignado a la cola de espera");
       }
    }

    public void mostrarCola(){
        if (colaEspera.isEmpty()) {
            System.out.println("NO HAY ESTUDIANTES EN LA COLA DE ESPERA");
        } else {
            System.out.println("COLA DE ESPERA ACTUAL: " + colaEspera);
        }
    }


    //Pila de navegacion para los reportes ( case 18 )
    public void verReporte(String reporte){
        pilaNavegacion.push(reporte);
        registrarNavegacion(reporte);
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

    // Deshacer de manera global afectando la mayoria de los case ( case 19 )
    public void deshacer(GestionRutas rutas) throws PilaDeshacerVaciaException{
        if (pilaDeshacer.isEmpty()){
            throw new PilaDeshacerVaciaException("No hay acciones para deshacer");
        }

            //Se retira la informacion de almacenamiento
            almacenamiento accionDes = pilaDeshacer.pop(); // Deshace la ultima accion realizada
            pilaRehacer.push(accionDes); // Agrega la accion deshecha a la pila de rehacer

            String comando = accionDes.getTipoAl();

                //Case 1 
                if (comando.equals("AGREGAR ESTUDIANTE")){
                    mapaEstudiantes.remove(accionDes.getId());
                } else if (comando.equals("ELIMINAR ESTUDIANTE")){
                    Estudiante estu = accionDes.getObjeto();
                    //Solo restaura los valores si el objeto es valido
                    if( estu != null){
                    mapaEstudiantes.put(accionDes.getId(), estu);
                    } else {
                        System.out.println("Error: No se pudo recuperar los datos del estudiante para deshacer.");
                    }
                }

                    //Case 5 y 6
                    else if (comando.equals("CREAR MATERIA")) {
                        mapaMaterias.remove(accionDes.getCodigoMateria());
                    }  else if (comando.equals("AGREGAR REQUISITO")) {
                        if(mapaMaterias.containsKey(accionDes.getCodigoMateria())){
                            mapaMaterias.get(accionDes.getCodigoMateria()).getPreRequisitos().remove(accionDes.getCodigoRequisito());
                        }
                    }
                        //Case 8 y 9
                        else if (comando.equals("INSCRIPCION EXITOSA")){
                            if(mapaMaterias.containsKey(accionDes.getCodigoMateria())){
                                mapaMaterias.get(accionDes.getCodigoMateria()).getInscritos().remove(accionDes.getId());
                            }
                        }  else if (comando.equals("CANCELAR")){
                            if(mapaMaterias.containsKey(accionDes.getCodigoMateria())){
                                mapaMaterias.get(accionDes.getCodigoMateria()).getInscritos().add(accionDes.getId());
                            }
                        }

                            //Case 11
                            else if (comando.equals("RESERVAR")){
                                try {
                                    inventarioSalon.get(accionDes.getSalon()).liberar(accionDes.getDia(), accionDes.getHora(), accionDes.getDuracion());
                                } catch (HorarioConflictivoException e) {
                                    System.out.println("Error al deshacer reserva: " + e.getMessage());
                                }
                             } else if (comando.equals("LIBERAR")){
                                    try {
                                    inventarioSalon.get(accionDes.getSalon()).reserva(accionDes.getDia(), accionDes.getHora(), accionDes.getDuracion());
                                    } catch (HorarioConflictivoException e) {
                                        System.out.println("Error al rehacer liberacion: " + e.getMessage());
                                    }
                                }

                                //Case 14
                                else if (comando.equals("RUTA")){
                                    rutas.registrarConexion(accionDes.getOrigen(), accionDes.getDestino(), 999999); //Distancia infinita asumiendo
                                }
                                    //Case 18
                                    else if (comando.equals("NAVEGAR REPORTE")){
                                        if(!pilaNavegacion.isEmpty()){
                                            pilaNavegacion.pop();
                                        }
                                    }

            System.out.println("Accion deshecha: " + accionDes + " correctamente");
        }
    

    //Rehacer de manera global afectando la mayoria de los case ( case 20 )
    public void rehacer(GestionRutas rutas) throws PilaDeshacerVaciaException{
        if (pilaRehacer.isEmpty()){
            throw new PilaDeshacerVaciaException("NO HAY ACCION REALIZADA ANTERIORMENTE PARA REHACER");
        } 
            //lo mismo a la inversa que deshacer
            almacenamiento accionRes = pilaRehacer.pop();
            pilaDeshacer.push(accionRes);

            String comando = accionRes.getTipoAl();

                //Case 1 
                if (comando.equals("AGREGAR ESTUDIANTE")){
                    Estudiante estu = new Estudiante(accionRes.getNombre(), accionRes.getId(), accionRes.getEmail(), accionRes.getSemestre());
                    mapaEstudiantes.put(accionRes.getId(), estu);
                } else if (comando.equals("ELIMINAR ESTUDIANTE")){
                    mapaEstudiantes.remove(accionRes.getId());
                }

                    //Case 5 y 6
                    else if (comando.equals("CREAR MATERIA")) {
                        Materia materi = new Materia(accionRes.getCodigoMateria(), accionRes.getNombre(), accionRes.getCupoMax());
                        mapaMaterias.put(accionRes.getCodigoMateria(), materi);
                    }  else if (comando.equals("AGREGAR REQUISITO")) {
                        if(mapaMaterias.containsKey(accionRes.getCodigoMateria())){
                            mapaMaterias.get(accionRes.getCodigoMateria()).agregarRequisito(accionRes.getCodigoRequisito());
                        }
                    }
                        //Case 8 y 9
                        else if (comando.equals("INSCRIPCION EXITOSA")) {
                            if(mapaMaterias.containsKey(accionRes.getCodigoMateria())){
                                mapaMaterias.get(accionRes.getCodigoMateria()).getInscritos().add(accionRes.getId());
                            }
                        }  else if (comando.equals("CANCELAR")){
                            if(mapaMaterias.containsKey(accionRes.getCodigoMateria())){
                                mapaMaterias.get(accionRes.getCodigoMateria()).getInscritos().remove(accionRes.getId());
                            }
                        }

                            //Case 11
                            else if (comando.equals("RESERVAR")){
                                try {
                                    inventarioSalon.get(accionRes.getSalon()).reserva(accionRes.getDia(), accionRes.getHora(), accionRes.getDuracion());
                                } catch (HorarioConflictivoException e) {
                                    System.out.println("Error al rehacer reserva: " + e.getMessage());
                                }
                             } else if (comando.equals("LIBERAR")){
                                    try {
                                    inventarioSalon.get(accionRes.getSalon()).liberar(accionRes.getDia(), accionRes.getHora(), accionRes.getDuracion());
                                    } catch (HorarioConflictivoException e) {
                                        System.out.println("Error al rehacer liberacion: " + e.getMessage());
                                    }
                                }

                                //Case 14
                                else if (comando.equals("RUTA")){
                                    rutas.registrarConexion(accionRes.getOrigen(), accionRes.getDestino(), accionRes.getDistancia()); 
                                }
                                    //Case 18
                                    else if (comando.equals("NAVEGAR REPORTE")){
                                            pilaNavegacion.push(accionRes.getNombre());
                                    }

            System.out.println("Accion rehecha: " + accionRes + " correctamente");
    }

    //Reservar comando de horarios para mejor funcionamiento en deshacer y rehacer
    public void registrarHorario(String nombre, int salon, int dia, int hora, int duracion){

        pilaDeshacer.push(new almacenamiento(nombre, salon, dia, hora, duracion));
        pilaRehacer.clear();

    }

    //Reservar comando de rutas para mejor funcionamiento en deshacer y rehacer
    public void registrarRutas(int origen, int destino, int distancia){

        pilaDeshacer.push(new almacenamiento("RUTA", origen, destino, distancia));
        pilaRehacer.clear();

    }

    public void registrarNavegacion (String reporte){
        pilaDeshacer.push(new almacenamiento("NAVEGAR REPORTE", reporte));
        pilaRehacer.clear();
    }
    

}


    


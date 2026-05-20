package MomentoEvaluativo3;


//Archivo creado para permitir guardar los datos ingresados
//Para en caso de querer rehacer una accion o deshacer otra, el sistema lo tenga ya 
//almacenado, y no solo sea un mensaje en pantalla

public class almacenamiento {

    private String tipoAl;  //Guardara los parametros necesarios
   
    //Datos preservados para estudiante
    private String id;
    private String nombre;
    private String email;
    private int semestre;
    private Estudiante objeto; //Guarda toda la informacion incluida sus notas

    //Datos para materias y requisitos
    private String codigoMateria;
    private String nombreMateria;
    private String codigoRequisito;
    private int cupoMax;

    // Datos para salones - Horarios y rutas

    private int salon;
    private int dia;
    private int hora;
    private int duracion;
    private int origen;
    private int destino;
    private int distancia;
    private String reporte;

    //Constructor para estudiantes
    public almacenamiento(String tipoAl, Estudiante estudiante){
        this.tipoAl = tipoAl;
        if(estudiante != null){
            this.objeto = estudiante;
            this.id = estudiante.getId();
            this.nombre = estudiante.getNombre();
            this.email = estudiante.getEmail();
            this.semestre = estudiante.getSemestreAct();
        }
    }

    //Constructor para materias
    public almacenamiento(String tipoAl, String codigoMateria, String nombreMateria, int cupoMax){
        this.tipoAl = tipoAl;
        this.codigoMateria = codigoMateria;
        this.nombreMateria = nombreMateria;
        this.cupoMax = cupoMax;
    }

    //Constructor para requisitos y inscripciones
    public almacenamiento(String tipoAl, String codigoMateria, String idRequisito){
        this.tipoAl = tipoAl;
        this.codigoMateria = codigoMateria;
        if(tipoAl.equals("AGREGAR REQUISITO")){
            this.codigoRequisito = idRequisito;
        } else {
            this.id = idRequisito; //Inscripcion exitosa o cancelar
        }
    }

    // Constructor Para Horarios (Reservar / Liberar)
    public almacenamiento(String tipoAl, int salon, int dia, int hora, int duracion) {
        this.tipoAl = tipoAl;
        this.salon = salon;
        this.dia = dia;
        this.hora = hora;
        this.duracion = duracion;
    }

    // Constructor Para Rutas
    public almacenamiento(String tipoAl, int origen, int destino, int distancia) {
        this.tipoAl = tipoAl;
        this.origen = origen;
        this.destino = destino;
        this.distancia = distancia;
    }

    // Constructor Para Reportes
    public almacenamiento(String tipoAl, String reporte) {
        this.tipoAl = tipoAl;
        this.reporte = reporte;
    }

    public String getTipoAl() {
        return tipoAl;
    }

    public Estudiante getObjeto() {
        return objeto;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public int getSemestre(){
        return semestre;
    }

    public String getCodigoMateria() {
        return codigoMateria;
    }

    public String getNombreMateria() {
        return nombreMateria;
    }

    public String getCodigoRequisito() {
        return codigoRequisito;
    }

    public int getCupoMax() {
        return cupoMax;
    }

    public int getSalon() {
        return salon;
    }

    public int getDia() {
        return dia;
    }

    public int getHora() {
        return hora;
    }

    public int getDuracion() {
        return duracion;
    }

    public int getOrigen() {
        return origen;
    }

    public int getDestino() {
        return destino;
    }

    public int getDistancia() {
        return distancia;
    }

    public String getReporte() {
        return reporte;
    }

}

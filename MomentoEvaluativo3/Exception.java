package MomentoEvaluativo3;

//Clase personalizada de excepción para manejar errores específicos en el programa


//Errores en el sistema Universitario 
class AcademicoException extends Exception {
    //Recibe el mensaje de error y muestra en pantalla el mensaje deseado
    public AcademicoException (String mensaje){
        super(mensaje); //Llama al constructor de la clase padre (Exception) para establecer el mensaje de error
    }
}

//Excepción para manejar el caso de que un estudiante no cumpla con los requisitos previos para inscribirse en una materia
class PreRequisitoNoAprobadoException extends AcademicoException {
    public PreRequisitoNoAprobadoException(String mensaje) {
        super(mensaje); //Llama al constructor de la clase padre (AcademicoException) para establecer el mensaje de error
    }
}

//Excepción para manejar el caso en que un estudiante intente inscribirse a una materia a la misma hora que otra materia en la que ya está inscrito
class HorarioConflictivoException extends AcademicoException {
    public HorarioConflictivoException(String mensaje) {
        super(mensaje); //Igual que las otras excepciones
    }
}

//Excepcion para manerjar el caso en que un estudiante intente registrar una clase con cupo lleno
class CupoLlenoException extends AcademicoException {
    public CupoLlenoException(String mensaje) {
        super(mensaje); //Igual que las otras excepciones
    }
}

//Para manejar el caso en que un estudiante no sea encontrado en el sistema al intentar realizar una acción relacionada con su cuenta
class EstudianteNoEncontradoException extends AcademicoException {
    public EstudianteNoEncontradoException(String mensaje) {
        super(mensaje); //Igual que las otras excepciones
    }
}

//Para manejar la cola de espera de una materia vacia
class ColaDeEsperaVaciaException extends AcademicoException {
    public ColaDeEsperaVaciaException(String mensaje) {
        super(mensaje); //Igual que las otras excepciones
    }
}

//Cuando no hay nada que deshacer en la pila 
class PilaDeshacerVaciaException extends AcademicoException {
    public PilaDeshacerVaciaException(String mensaje) {
        super(mensaje); //Igual que las otras excepciones
    }
}

//Para validar extensiones de archivos .csv
class ArchivoInvalidoException extends AcademicoException {
    public ArchivoInvalidoException(String mensaje) {
        super(mensaje); //Igual que las otras excepciones
    }
}
class ArchivoNoEncontradoException extends AcademicoException{
    public ArchivoNoEncontradoException(Strin mensaje){
        super(mensaje);
    }
 }

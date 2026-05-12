package MomentoEvaluativo3;

//Se crea una clase Abstracta la cual sirve como fundamentos para Estudiantes

public abstract class Persona {
    protected String nombre;
    protected String id;
    protected String email;


    public Persona(String nombre, String id, String email){
        this.nombre = nombre;
        this.id = id;
        this.email = email;
    }


    public String getNombre() {
        return nombre;
    }


    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }


    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    public abstract void mostrarInformacion();
    //Metodo que sera implementado en las clases hijas (Estudiantes) para mostrar la información específica de cada estudiante
}

package MomentoEvaluativo3;

public class GestionHora {

    private String nombre;
    private boolean[][] disponibilidad; // Matriz de disponibilidad para cada hora del día y cada día de la semana
    private String[][] dias = { "Domingo", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado" };

    public GestionHora(String nombre) {

        this.nombre = nombre;
        this.disponibilidad = new boolean[7][24]; // Disponilidad presentada en la matriz con 7 dias y 24 horas
        // true si esta ocupado, false si esta libre
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void reserva(int diaRequerido, int horaRequerida, int duracion) throws HorarioConflictivoException{ // Método para reservar una hora específica, verificando conflictos
        System.out.println("Corroborando disponibilidad en: " + nombre + "....."); // Mensaje de espera

        //Verifica si la clase solicitada se encuentra libre (false)
        for (int i = horaRequerida; i < horaRequerida + duracion; i++){
            if (disponibilidad[diaRequerido][i] == true){ //Si la hora solicitada esta ocupada (true) lanza la excepcion
                throw new HorarioConflictivoException("Error: La clase solicitada en ese horario se encuentra ocupada.")
            }
        }

        //En caso tal de que este libre, reservarlo y pasar a ocupado
        for (int i = horaRequerida; i < horaRequerida + duracion; i++){
            System.out.println("Reservando clase en: " + dias[diaRequerido] + " " + i + ":00 -> LIBRE"); //Muestra la clase disponible
            disponibilidad[diaRequerido][i] = true; //Cambia el valor de la clas a ocupado para no generar incrongruencias
        }

        System.out.println("Clase reservada exitosamente en: " + nombre);
        System.out.println("\nHorario actualizado: " + disponibilidad[diaRequerido][horaRequerida] + " -> OCUPADO\n"); //Muestra el horario actualizado

    }

    // Metodo para liberar un horario especifico, verificando que el horario se
    // encuentre ocupado
    public void liberar(int diaRequerido, int horaRequerida, int duracion) throws HorarioConflictivoException{ 
        System.out.println("Corroborando disponibilidad en: " + nombre + "....."); // Mensaje de espera

        //Comprobamos si el horario que se desea liberar se encuentra ocupado
        for (int i = horaRequerida; i < horaRequerida + duracion; i ++){
            if (disponibilidad[diaRequerido][i] == false){ //En caso tal de que se encuentre libre, lanza el mensaje de excepcion
                throw new HorarioConflictivoException("Error: la clase seleccionada ya se encuentra libre.")
            }

            //Si se encuentra ocupado, liberar el horario y actualizar el nuevo horario
            for (int i = horaRequerida; i < horaRequerida + duracion; i++){
                if (disponibilidad[diaRequerido][i] == true){
                System.out.println("Liberando clase en: " + dias[diaRequerido] + " " + i + ":00 -> OCUPADO"); //Muestra la clase ocupada
                disponibilidad[diaRequerido][i] = false; //Cambia el valor de la clase a libre para no generar incrongruencias
                }
            }

            System.out.println("Clase liberada exitosamente en: " + nombre);
            System.out.println("\nHorario actualizado: " + disponibilidad[diaRequerido][horaRequerida] + " -> LIBRE\n"); //Muestra el horario actualizado
        }
    }

    // Consultar disponibilidad a una hora requerida
    public boolean disponibilidad(int diaRequerido, int horaRequerida) {
        return !disponibilidad[diaRequerido][horaRequerida]; // Retorna true si la clase esta libre (false en la
                                                             // matriz), y false si esta ocupada (true en la matriz)
    }
}

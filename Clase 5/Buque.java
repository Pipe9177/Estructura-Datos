public class Buque {
    private int Id; // Identificador del buque
    private double Capacidad; // Capacidad del buque en toneladas
    private String Origen; // Lugar de origen del buque
    
    
    public Buque(int id, double capacidad, String origen) {
        Id = id;
        Capacidad = capacidad;
        Origen = origen;
    }


    public int getId() {
        return Id;
    }


    public void setId(int id) {
        Id = id;
    }


    public double getCapacidad() {
        return Capacidad;
    }


    public void setCapacidad(double capacidad) {
        Capacidad = capacidad;
    }


    public String getOrigen() {
        return Origen;
    }


    public void setOrigen(String origen) {
        Origen = origen;
    }


    @Override
    public String toString() {
        return "\nBuque [ Id del Buque: " + Id + ", Capacidad: " + Capacidad + ", Origen: " + Origen + "]";
    }

    

}

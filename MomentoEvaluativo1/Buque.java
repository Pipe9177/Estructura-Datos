public class Buque {
    private int Id; // Identificador del buque
    public double capacidadMax; // Capacidad del buque en toneladas
    public double pesoActual; // Peso actual del buque en toneladas
    private String Origen; // Lugar de origen del buque
    public Contenedor[][] patioUnico = new Contenedor[10][10];
    
    public Buque(int id, double capacidad, String origen) {
        Id = id;
        capacidadMax = capacidad;
        Origen = origen;
    }


    public int getId() {
        return Id;
    }


    public void setId(int id) {
        Id = id;
    }


    public double getCapacidad() {
        return capacidadMax;
    }


    public void setCapacidad(double capacidad) {
        capacidadMax = capacidad;
    }


    public String getOrigen() {
        return Origen;
    }


    public void setOrigen(String origen) {
        Origen = origen;
    }

      public Contenedor[][] getPatioUnico() {
        return patioUnico;
    }


    public double getPesoActual() {
        return pesoActual;
    }


      public void setPesoActual(double pesoActual) {
          this.pesoActual = pesoActual;
      }


    public void setPatioUnico(Contenedor[][] patioUnico) {
        this.patioUnico = patioUnico;
    }



    @Override
    public String toString() {
        return "\nBuque [ Id del Buque: " + Id + ", Capacidad: " + capacidadMax + ", Origen: " + Origen + "]";
    }

}

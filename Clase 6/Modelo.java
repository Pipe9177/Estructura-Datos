public class Modelo {

    public String id;
    public int nivelEnergia;
    public String prioridad; // ("Alta" o "Estandar")
    
    
    public Modelo(String id, int nivelEnergia, String prioridad) {
        this.id = id;
        this.nivelEnergia = nivelEnergia;
        this.prioridad = prioridad;
    }


    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public int getNivelEnergia() {
        return nivelEnergia;
    }
    public void setNivelEnergia(int nivelEnergia) {
        this.nivelEnergia = nivelEnergia;
    }
    public String getPrioridad() {
        return prioridad;
    }
    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }


    @Override
    public String toString() {
        return "Modelo [id=" + id + ", nivelEnergia=" + nivelEnergia + ", prioridad=" + prioridad + "]";
    }


    
    
}

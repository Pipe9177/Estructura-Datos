public class Contenedor {

    private int id;
    private double peso;
    private String origen;
    private String destino; //He añadido esta variable para que haya un registro de a donde va el contenedor
    
    
    public Contenedor(int id, double peso, String origen, String destino) {
        this.id = id;
        this.peso = peso;
        this.origen = origen;
        this.destino = destino;
    }


    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }


    public double getPeso() {
        return peso;
    }


    public void setPeso(double peso) {
        this.peso = peso;
    }


    public String getOrigen() {
        return origen;
    }


    public void setOrigen(String origen) {
        this.origen = origen;
    }


    public String getDestino() {
        return destino;
    }


    public void setDestino(String destino) {
        this.destino = destino;
    }


    @Override
    public String toString() {
        return "Contenedor [id=" + id + ", peso=" + peso + ", origen=" + origen + ", destino=" + destino + "]";
    }

    
    
}

import java.time.LocalDate;

public class Pagos {

    private int id;
    private int id_cliente;
    private LocalDate fecha;
    private double importe;
    private double litros;
    private String combustible;

    public Pagos(int id, int id_cliente, LocalDate fecha, double importe, double litros, String combustible) {
        this.id = id;
        this.id_cliente = this.id_cliente;
        this.fecha = fecha;
        this.importe = importe;
        this.litros = litros;
        this.combustible = this.combustible;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public double getLitros() {
        return litros;
    }

    public void setLitros(double litros) {
        this.litros = litros;
    }

    public String getCombustible() {
        return combustible;
    }

    public void setCombustible(String combustible) {
        this.combustible = combustible;
    }

    public String toString() {
        return "ID: " + id + ", ID_cliente: " + id_cliente + ", Fecha: " + fecha + ", Importe: " + importe + ", Litros: " + litros + ", Combustible: " + combustible;
    }
}

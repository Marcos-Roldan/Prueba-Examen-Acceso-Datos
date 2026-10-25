import java.util.List;

public interface Almacenamiento {

    List<Cliente> leerClientes();
    boolean escribirCliente(Cliente cliente);

    List<Pagos> leerPagos();
    boolean escribirPagos(Pagos pagos);
}
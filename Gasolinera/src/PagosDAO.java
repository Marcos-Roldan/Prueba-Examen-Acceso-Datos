import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PagosDAO {

    private final Almacenamiento almacenamiento;

    public PagosDAO(Almacenamiento almacenamiento) {
        this.almacenamiento = almacenamiento;
    }

    void procesarPago() {
        List<Cliente> clientes = almacenamiento.leerClientes();
        List<Pagos> listaPagos = almacenamiento.leerPagos();
        Scanner sc = new Scanner(System.in);

        if (clientes.size() <= 0) {
            System.out.println("Error: No hay clientes registrados");
        } else {
            for(Cliente cliente : clientes) {
                System.out.println("ID: " + cliente.getId());
                System.out.println("Nombre: " + cliente.getNombre());
                System.out.println("Telefono: " + cliente.getTelefono());
                System.out.println("Matricula: " + cliente.getMatricula());
            }

            System.out.println("Introduce un id del cliente: ");
            int id_cliente = sc.nextInt();
            if(id_cliente <= 0) {
                System.out.println("\nError: No puede haber un cliente con un id menor a cero");
                System.out.println("Introduce un id del cliente: ");
                id_cliente = sc.nextInt();
            }
            sc.nextLine();


            boolean clienteExiste = false;
            for(Cliente cliente : clientes) {
                if (id_cliente == cliente.getId()) {
                    clienteExiste = true;
                    break;
                }
            }

            if (!clienteExiste) {
                System.out.println("Ese ID no corresponde a ningun cliente");
                return;
            }

            System.out.println("Introduce una fecha(dd/MM/aaaa) o pulsa Enter para usar la fecha de hoy: ");
            String entrada = sc.nextLine();

            LocalDate fecha;

            if(entrada.isEmpty()) {
                fecha = LocalDate.now();
            } else {
                fecha = LocalDate.parse(entrada);
            }

            double importe = 0.00;
            do {
                System.out.println("Introduce un importe: ");
                importe = sc.nextDouble();
                if(importe <= 0) {
                    System.out.println("Error: El importe debe ser mayor que cero.");
                }
            } while(importe <= 0);
            sc.nextLine();

            double litros = 0.00;
            do {
                System.out.println("Introduce los litros: ");
                litros = sc.nextDouble();
                if(litros <= 0) {
                    System.out.println("Error: Los litros deben ser mayores que cero.");
                }
            } while(litros <= 0);
            sc.nextLine();

            String combustible;

            do {
                System.out.println("Introduce el combustible(Gasolina 95 / Diesel): ");
                combustible = sc.nextLine();

                if(!combustible.equals("Gasolina 95") && !combustible.equals("Diesel")) {
                    System.out.println("Error: Solo se puede introducir Gasolina 95 o Diesel");
                }
            } while(!combustible.equals("Gasolina 95") && !combustible.equals("Diesel"));

            String nombreCliente = "";

            for (Cliente cliente : clientes) {
                if (cliente.getId() == id_cliente) {
                    nombreCliente = cliente.getNombre();
                    break;
                }
            }

            int contID = 1;
            for (Pagos p : listaPagos) {
                if (p.getId() >= contID) {
                    contID = p.getId() + 1;
                }
            }

            Pagos nuevoPago = new Pagos(contID, id_cliente, fecha, importe, litros, combustible);
            listaPagos.add(nuevoPago);
            almacenamiento.escribirPagos(nuevoPago);

            System.out.println("Pago registrado con ID: " + contID + " Nombre: " + nombreCliente + " Importe: " + importe);
        }
    }

    void consultarPagos() {

        List<Pagos> listaPagos = almacenamiento.leerPagos();
        List<Cliente> listaClientes = almacenamiento.leerClientes();

        for(int i = 0; i < listaPagos.size() - 1; i++) {
            for(int j = i + 1; j < listaPagos.size(); j++) {

                Pagos c1 = listaPagos.get(i);
                Pagos c2 = listaPagos.get(j);

                int comparacion = c1.getFecha().compareTo(c2.getFecha());

                if(comparacion < 0) { //Fecha descendente(fecha mas moderna) y c1 es menor
                    listaPagos.set(i, c2);
                    listaPagos.set(j, c1);
                } else if (comparacion == 0) {
                    if(c1.getId() < c2.getId()) {
                        listaPagos.set(i, c2);
                        listaPagos.set(j, c1);
                    }
                }
            }
        }

        System.out.println("ID | CLIENTE | FECHA | IMPORTE | LITROS | COMBUSTIBLE");
        for (Pagos pagos : listaPagos) {
            String nombreCliente = "";
            for (Cliente cliente : listaClientes) {
                if(cliente.getId() == pagos.getId_cliente()) {
                    nombreCliente = cliente.getNombre();
                    break;
                }
            }
            System.out.println(pagos.getId() + " | " + nombreCliente + " | " + pagos.getFecha() + " | " + pagos.getImporte() + " € | " + pagos.getLitros() + " L | " + pagos.getCombustible());
        }
    }
}
import java.util.*;

void main() {

    Almacenamiento almacenamiento = new Almacenamiento_EN_CSV();

    ClienteDAO clienteDAO = new ClienteDAO(almacenamiento);
    PagosDAO pagosDAO = new PagosDAO(almacenamiento);

    Scanner sc = new Scanner(System.in);
    int opcion;

    do {
        menu();
        opcion = sc.nextInt();

        switch (opcion) {
            case 1:
                clienteDAO.altaCliente();
                break;
            case 2:
                clienteDAO.listarCliente();
                break;
            case 3:
                clienteDAO.buscarClientes();
                break;
            case 4:
                pagosDAO.procesarPago();
                break;
            case 5:
                pagosDAO.consultarPagos();
                break;
            case 0:
                System.out.println("Saliendo del programa...");
                break;
            default:
                System.out.println("Opcion no valida");
                break;
        }

    } while(opcion != 0);
}

void menu() {
    System.out.println("=== GESTION DE GASOLINERA ===");
    System.out.println("1. Dar de alta un cliente");
    System.out.println("2. Listar clientes");
    System.out.println("3. Buscar clientes");
    System.out.println("4. Procesar un pago de repostaje");
    System.out.println("5. Consultar pagos");
    System.out.println("0. Salir");
    System.out.println("Opcion: ");
}
import java.util.*;

public class ClienteDAO {


    private final Almacenamiento almacenamiento;

    public ClienteDAO(Almacenamiento almacenamiento) {
        this.almacenamiento = almacenamiento;
    }

    void altaCliente() {
        List<Cliente> lista = almacenamiento.leerClientes();
        Scanner sc = new Scanner(System.in);

        System.out.println("Introduce un nombre: ");
        String nombre = sc.nextLine();
        if(nombre.isEmpty()) {
            System.out.println("\nNo puede estar el campo vacio");
            System.out.println("Introduce el nombre");
            nombre = sc.nextLine();
        }

        System.out.println("Introduce un telefono: ");
        String telefono = sc.nextLine();
        if(telefono.isEmpty()) {
            System.out.println("\nNo puede estar el campo vacio");
            System.out.println("Introduce un telefono: ");
            telefono = sc.nextLine();
        }

        System.out.println("Introduce una matricula: ");
        String matricula = sc.nextLine();
        if(matricula.isEmpty()) {
            System.out.println("\nNo puede estar el campo vacio");
            System.out.println("Introduce una matricula: ");
            matricula = sc.nextLine();
        }

        for(Cliente cliente : lista) {
            if (matricula.equalsIgnoreCase(cliente.getMatricula())) {
                System.out.println("Esa matricula ya esta registrada, no se puede añadir el cliente");
                return;
            }
        }

        // Calcular el siguiente ID automáticamente
        int contID = 1;
        for (Cliente c : lista) {
            if (c.getId() >= contID) {
                contID = c.getId() + 1;
            }
        }

        Cliente nuevoCliente = new Cliente(contID, nombre, telefono, matricula);
        lista.add(nuevoCliente);
        almacenamiento.escribirCliente(nuevoCliente);

        System.out.println("Cliente registrado con ID: " + contID);
    }

    void listarCliente() {

        List<Cliente> lista = almacenamiento.leerClientes();

        for(int i = 0; i < lista.size() - 1; i++) { //i recorre el bucle
            for(int j = i + 1; j < lista.size(); j++) { //j compara i con cada uno

                Cliente c1 = lista.get(i); //Dos Clientes
                Cliente c2 = lista.get(j);

                int comparacion = c1.getNombre().compareToIgnoreCase(c2.getNombre()); //Comparamos

                if(comparacion > 0) { //De A -> Z y Si c1 es mayor, es decir, q este mas cerca de la Z
                    lista.set(i, c2);
                    lista.set(j, c1);
                } else if (comparacion == 0) {
                    if(c1.getId() > c2.getId()) { //ID ascendente
                        lista.set(i, c2);
                        lista.set(j, c1);
                    }
                }
            }
        }

        System.out.println("ID | NOMBRE | TELEFONO | MATRICULA");
        for (Cliente cliente : lista) {
            System.out.println(cliente.getId() + " | " + cliente.getNombre() + " | " + cliente.getTelefono() + " | " + cliente.getMatricula());
        }
    }

    void buscarClientes() {

        List<Cliente> lista = almacenamiento.leerClientes();

        Scanner sc = new Scanner(System.in);
        String texto;

        do {
            System.out.println("Introduce un texto: ");
            texto = sc.nextLine();

            if (texto.isEmpty()) {
                System.out.println("Error: El texto no puede estar vacio");
                texto = sc.nextLine();
            }
        } while(texto.isEmpty());

        boolean encontrado = false;
        String minuscula = texto.toLowerCase();

        for(Cliente cliente : lista) {
            if(cliente.getNombre().toLowerCase().contains(minuscula) ||
                cliente.getTelefono().toLowerCase().contains(minuscula) ||
                    cliente.getMatricula().toLowerCase().contains(minuscula)) {
                System.out.println("ID: " + cliente.getId());
                System.out.println("Nombre: " + cliente.getNombre());
                System.out.println("Telefono: " + cliente.getTelefono());
                System.out.println("Matricula: " + cliente.getMatricula());
                encontrado = true;
            }
        }

        if(!encontrado) {
            System.out.println("No se han encontrado clientes.");
        }
    }
}
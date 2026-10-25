import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.*;

public class MigraCSVToJson {

    Path archivoDestino1 = Path.of("clientes.json");
    Path archivoDestino2 = Path.of("pagos.json");

    void Migracion() {
        List<Cliente> listaClientes = new ArrayList<>();
        List<Pagos> listaPagos = new ArrayList<>();

        if(Files.exists(archivoDestino1) || Files.exists(archivoDestino2)) {
            System.out.println("Migracion cancelada, ya existe el archivo destino.");
        } else {
            if(Files.exists(Path.of(Almacenamiento_EN_CSV.archivoClientes)) && Files.exists(Path.of(Almacenamiento_EN_CSV.archivoPagos))) {
                try(BufferedReader br1 = new BufferedReader(new FileReader(Path.of(Almacenamiento_EN_CSV.archivoClientes).toFile()));
                    BufferedReader br2 = new BufferedReader(new FileReader(Path.of(Almacenamiento_EN_CSV.archivoPagos).toFile()));
                        BufferedWriter bw1 = new BufferedWriter(new FileWriter(archivoDestino1.toFile(), true));
                            BufferedWriter bw2 = new BufferedWriter(new FileWriter(archivoDestino2.toFile(), true))) {
                    String lineaClientes;
                    while((lineaClientes = br1.readLine()) != null) {
                        if(!lineaClientes.trim().isEmpty()) {
                            String[] datos = lineaClientes.split(",");
                            int id = Integer.parseInt(datos[0].trim());
                            String nombre = datos[1].trim();
                            String telefono = datos[2].trim();
                            String matricula = datos[3].trim();

                            listaClientes.add(new Cliente(id, nombre, telefono, matricula));
                        }
                    }

                    String lineaPagos;
                    while((lineaPagos = br2.readLine()) != null) {
                        if(!lineaPagos.trim().isEmpty()) {
                            String[] datos = lineaPagos.split(",");
                            int id = Integer.parseInt(datos[0].trim());
                            int id_cliente = Integer.parseInt(datos[1].trim());
                            LocalDate fecha = LocalDate.parse(datos[2].trim());
                            Double importe = Double.parseDouble(datos[3].trim());
                            Double litros = Double.parseDouble(datos[4].trim());
                            String combustible = datos[5].trim();

                            listaPagos.add(new Pagos(id, id_cliente, fecha, importe, litros, combustible));
                        }
                    }

                    bw1.write("[\n");
                    for(int i = 0; i < listaClientes.size(); i++) { //Desde 0 hasta el ultimo cliente
                        Cliente cliente = listaClientes.get(i); //Cogemos un cliente de la lista de clientes

                        bw1.write("\t{\n");
                        bw1.write(String.format("\t\t\"id\": %d, \"nombre\": \"%s\", \"telefono\": \"%s\", \"matricula\": \"%s\"\n",
                                cliente.getId(), cliente.getNombre(), cliente.getTelefono(), cliente.getMatricula()));

                        if (i < listaClientes.size() - 1) { //Si no es el ultimo cliente
                            bw1.write("\t},\n");
                        } else {
                            bw1.write("\t}\n");
                        }
                    }
                    bw1.write("]");

                    bw2.write("[\n");
                    for(int i = 0; i < listaPagos.size(); i++) {
                        Pagos pagos = listaPagos.get(i);

                        bw2.write("\t{\n");
                        bw2.write(String.format("\t\t\"id\": %d, \"id_cliente\": %d, \"fecha\": \"%s\", \"importe\": %.2f, \"litros\": %.2f, \"combustible\": \"%s\"\n",
                                pagos.getId(), pagos.getId_cliente(), pagos.getFecha(), pagos.getImporte(), pagos.getLitros(), pagos.getCombustible()));

                        if(i < listaPagos.size() -1) {
                            bw2.write("\t},\n");
                        } else {
                            bw2.write("\t}\n");
                        }
                    }
                    bw2.write("]");

                } catch (IOException e) {
                    System.out.println("Error al leer el archivo.");
                }
            } else {
                System.out.println("Error algun archivo no existe en el sistema.");
            }
        }
    }

}
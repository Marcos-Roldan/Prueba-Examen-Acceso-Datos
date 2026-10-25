import java.io.*;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Almacenamineto_EN_JSON {

    Path archivoClientesJSON = Path.of("clientes.json");
    Path archivoPagosJSON = Path.of("pagos.json");

    public List<Cliente> leerClientesJSON() {

        List<Cliente> lista = new ArrayList<>();
        File file = archivoClientesJSON.toFile();

        if(!file.exists()) {
            return lista;
        }

        try(BufferedReader br = new BufferedReader(new FileReader(file))) {

            String linea;
            int id = 0; //Cada propiedad la guardamos en una linea diferente
            String nombre = "";
            String telefono = "";
            String matricula = "";

            while((linea = br.readLine()) != null) {
                linea = linea.trim();

                if(linea.contains("\"id\":")) {
                    id = Integer.parseInt(linea.split(":")[1].replace(",", "").trim());
                } else if (linea.contains("\"nombre\":")) {
                    nombre = linea.split(":")[1].replace("\"", "").replace(",", "").trim();
                } else if (linea.contains("\"telefono\":")) {
                    telefono = linea.split(":")[1].replace("\"", "").replace(",", "").trim();
                } else if (linea.contains("\"matricula\":")) {
                    matricula = linea.split(":")[1].replace("\"","").replace(",", "").trim();
                } else if(linea.equals("}") || linea.equals("},")) {
                    lista.add(new Cliente(id, nombre, telefono, matricula));
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo.");
        }

        return lista;
    }

    public boolean escribirClientesJSON(List<Cliente> listaClientesJSON) {

        try(BufferedWriter bw = new BufferedWriter(new FileWriter(archivoClientesJSON.toFile()))) {
            bw.write("[\n");
            for(int i = 0; i < listaClientesJSON.size(); i++) {
                Cliente cliente = listaClientesJSON.get(i);

                bw.write("\t{\n");
                bw.write(String.format("\t\t\"id\": %d, \"nombre\": \"%s\", \"telefono\": \"%s\", \"matricula\": \"%s\"\n",
                        cliente.getId(), cliente.getNombre(), cliente.getTelefono(), cliente.getMatricula()));

                if(i < listaClientesJSON.size() - 1) {
                    bw.write("\t},\n");
                } else {
                    bw.write("\t}\n");
                }
            }

            bw.write("]");
            return true;
        } catch (IOException e) {
            System.out.println("Error al intentar escribir el texto en el archivo.");
            return false;
        }
    }

    public List<Pagos> leerPagosJSON() {

        List<Pagos> lista = new ArrayList<>();
        File file = archivoPagosJSON.toFile();

        if(!file.exists()) {
            return lista;
        }

        try(BufferedReader br = new BufferedReader(new FileReader(file))) {

            String linea;
            int id = 0;
            int id_cliente = 0;
            LocalDate fecha = null;
            double importe = 0.0;
            double litros = 0.0;
            String combustible = "";

            while ((linea = br.readLine()) != null) {
                linea = linea.trim();

                if(linea.contains("\"id\":")) {
                    id = Integer.parseInt(linea.split(":")[1].replace(",", "").trim());
                } else if(linea.contains("\"id_cliente\":")) {
                    id_cliente = Integer.parseInt(linea.split(":")[1].replace(",", "").trim());
                } else if (linea.contains("\"fecha\":")) {
                    fecha = LocalDate.parse(linea.split(":")[1].replace("\"", "").replace(",", "").trim());
                } else if (linea.contains("\"importe\":")) {
                    importe = Double.parseDouble(linea.split(":")[1].replace(",", "").trim());
                } else if (linea.contains("\"litros\":")) {
                    litros = Double.parseDouble(linea.split(":")[1].replace(",", "").trim());
                } else if (linea.contains("\"combustible\":")) {
                    combustible = linea.split(":")[1].replace("\"", "").replace(",", "").trim();
                } else if (linea.equals("},") || linea.equals("}")) {
                    lista.add(new Pagos(id, id_cliente, fecha, importe, litros, combustible));
                }
            }

        } catch (IOException e) {
            System.out.println("Error al leer el archivo.");
        }

        return lista;
    }

    public boolean escribirPagosJSON(List<Pagos> listaPagosJSON) {

        try(BufferedWriter bw = new BufferedWriter(new FileWriter(archivoPagosJSON.toFile()))) {
            bw.write("[\n");
            for(int i = 0; i < listaPagosJSON.size(); i++) {
                Pagos pagos = listaPagosJSON.get(i);

                bw.write("\t{\n");
                bw.write(String.format("\t\t\"id\": %d, \"id_cliente\": %d, \"fecha\": \"%s\", \"importe\": %.2f, \"litros\": %.2f, \"combustible\": \"%s\"\n",
                        pagos.getId(), pagos.getId_cliente(), pagos.getFecha(), pagos.getImporte(), pagos.getLitros(), pagos.getCombustible()));

                if(i < listaPagosJSON.size() -1) {
                    bw.write("\t},\n");
                } else {
                    bw.write("\t}\n");
                }
            }
            bw.write("]");
            return true;
        } catch (IOException e) {
            System.out.println("Error al intentar escribir en el archivo.");
            return false;
        }
    }

}
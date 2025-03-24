package proyecto;

import java.io.IOException;
import java.util.List;


public class Main {

    public static void main(String[] args) {
        Interprete interprete = new Interprete();
        String archivoLisp = "C:\\Users\\Admin\\Documents\\Yio\\U\\Proyecto-\\demo\\src\\main\\java\\proyecto\\ProblemasPresentacionProyecto.txt";

        System.out.println("Cargando y ejecutando archivo Lisp: " + archivoLisp);
        ejecutarDesdeArchivo(archivoLisp, interprete);
    }

    private static void ejecutarDesdeArchivo(String rutaArchivo, Interprete interprete) {
        try {
            String contenido = Tokenizer.muestraContenido(rutaArchivo);
            List<String> expresiones = List.of(contenido.split("\n"));

            for (String expresion : expresiones) {
                if (!expresion.trim().isEmpty()) {
                    System.out.println(">> " + expresion);
                    Object resultado = interprete.interprete(expresion);
                    System.out.println("=> " + resultado);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
    }
}

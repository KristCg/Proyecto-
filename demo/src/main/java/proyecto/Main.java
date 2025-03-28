package proyecto;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String rutaArchivo = "C:\\Users\\Admin\\Documents\\Yio\\U\\Proyecto-\\demo\\src\\main\\java\\proyecto\\ProblemasPresentacionProyecto.txt";
        
        Tokenizer tokenizer = new Tokenizer();
        Interprete interprete = new Interprete(tokenizer);
        
        try {
            List<String> expresiones = leerExpresionesLisp(rutaArchivo);
            
            System.out.println("=== Iniciando interpretación Lisp ===");
            System.out.println("Archivo: " + rutaArchivo);
            System.out.println("------------------------------------");
            
            // Procesar cada expresión
            for (String expresion : expresiones) {
                try {
                    System.out.println("Expresión: " + expresion);
                    Object resultado = interprete.interpretar(expresion);
                    System.out.println("Resultado: " + resultado);
                    System.out.println("------------------------------------");
                } catch (Exception e) {
                    System.err.println("Error al interpretar: " + expresion);
                    System.err.println("Error: " + e.getMessage());
                    System.out.println("------------------------------------");
                }
            }
            
            System.out.println("=== Interpretación completada ===");
            
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
    }

    private static List<String> leerExpresionesLisp(String rutaArchivo) throws IOException {
        List<String> expresiones = new ArrayList<>();
        BufferedReader lector = new BufferedReader(new FileReader(rutaArchivo));
        
        String linea;
        StringBuilder expresionActual = new StringBuilder();
        int nivelParentesis = 0;
        boolean enComentarioBloque = false;
        
        while ((linea = lector.readLine()) != null) {
            linea = linea.trim();
            
            if (linea.startsWith("##") || linea.isEmpty()) {
                continue;
            }
            
            if (linea.contains(";")) {
                linea = linea.substring(0, linea.indexOf(';')).trim();
            }
            
            if (linea.startsWith("#|")) {
                enComentarioBloque = true;
                continue;
            }
            if (linea.endsWith("|#")) {
                enComentarioBloque = false;
                continue;
            }
            if (enComentarioBloque) {
                continue;
            }
            
            for (char c : linea.toCharArray()) {
                if (c == '(') {
                    nivelParentesis++;
                } else if (c == ')') {
                    nivelParentesis--;
                }
            }
            
            expresionActual.append(linea).append(" ");
            
            if (nivelParentesis == 0 && expresionActual.length() > 0) {
                String expresionCompleta = expresionActual.toString().trim();
                if (!expresionCompleta.isEmpty()) {
                    expresiones.add(expresionCompleta);
                }
                expresionActual = new StringBuilder();
            }
        }
        
        lector.close();
        
        if (nivelParentesis != 0) {
            throw new IOException("Paréntesis no balanceados en el archivo");
        }
        
        return expresiones;
    }
}

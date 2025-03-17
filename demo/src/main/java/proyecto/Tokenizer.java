import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Tokenizer{    
    private List<String> tokens;

    public static String muestraContenido(String archivo) throws IOException {
        StringBuilder contenido = new StringBuilder();
        try (BufferedReader b = new BufferedReader(new FileReader(archivo))) {
            String cadena;
            while ((cadena = b.readLine()) != null) {
                contenido.append(cadena).append("\n");
            }
        }
        return contenido.toString();
    }

    public List<String> tokenize(String texto) {
        tokens = new ArrayList<>();
        for (int i = 0; i < texto.length(); i++) {
            char c = texto.charAt(i);
            if (!Character.isLetter(c)) {
                tokens.add(String.valueOf(c));
            } 
        }
        return tokens;
    }
}
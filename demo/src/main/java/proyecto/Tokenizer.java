package proyecto;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Tokenizer {
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
        StringBuilder currentToken = new StringBuilder();
        for (int i = 0; i < texto.length(); i++) {
            char c = texto.charAt(i);
            if (Character.isWhitespace(c)) {
                if (currentToken.length() > 0) {
                    tokens.add(currentToken.toString());
                    currentToken = new StringBuilder();
                }
            } else if (c == '(' || c == ')') {
                if (currentToken.length() > 0) {
                    tokens.add(currentToken.toString());
                    currentToken = new StringBuilder();
                }
                tokens.add(String.valueOf(c));
            } else {
                currentToken.append(c);
            }
        }
        if (currentToken.length() > 0) {
            tokens.add(currentToken.toString());
        }
        return tokens;
    }
}
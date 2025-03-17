package proyecto;

import java.util.List;

public class OperationalLisp {
    private Tokenizer tokenizer;

    public OperationalLisp(Tokenizer tokenizer){
        this.tokenizer = tokenizer;
    }

    public int interpretar(String expresion){
        expresion = expresion.trim();

        if (expresion.startsWith("(+") && expresion.endsWith(")")){
            expresion = expresion.substring(2, expresion.length() - 1);
            List<String> tokens = tokenizer.tokenize(expresion);
            return procesarSuma(tokens);
                } else {
                    throw new IllegalArgumentException("Expresión no válida: " + expresion);
                }
        if (expresion.startsWith("(-") && expresion.endsWith(")")){
            expresion = expresion.substring(2, expresion.length() - 1);
            List<String> tokens = tokenizer.tokenize(expresion);
            return procesarResta(tokens);
                } else {
                    throw new IllegalArgumentException("Expresión no válida: " + expresion);
                }
        if (expresion.startsWith("(*") && expresion.endsWith(")")){
            expresion = expresion.substring(2, expresion.length() - 1);
            List<String> tokens = tokenizer.tokenize(expresion);
            return procesarMultiplicacion(tokens);
                } else {
                    throw new IllegalArgumentException("Expresión no válida: " + expresion);
                }
        if (expresion.startsWith("(/") && expresion.endsWith(")")){
            expresion = expresion.substring(2, expresion.length() - 1);
            List<String> tokens = tokenizer.tokenize(expresion);
            return procesarDivision(tokens);
                } else {
                    throw new IllegalArgumentException("Expresión no válida: " + expresion);
                }

    }
    private int procesarSuma(List<String> tokens){
        int suma = 0;
        for(String token : tokens){
            suma += Integer.parseInt(token);
        }
        return suma;
    }
    private int procesarResta(List<String> tokens){
        int resta = 0;
        for(String token : tokens){
            resta -= Integer.parseInt(token);
        }
        return resta;
    }
    private int procesarMultiplicacion(List<String> tokens){
        int multiplicacion = 1;
        for(String token : tokens){
            multiplicacion *= Integer.parseInt(token);
        }
        return multiplicacion;
    }
    private int procesarDivision(List<String> tokens){
        int division = 1;
        for(String token : tokens){
            division /= Integer.parseInt(token);
        }
        return division;
    }

    public static void main(String[] args) {
        Tokenizer tokenizer = new Tokenizer();
        OperationalLisp interprete = new OperationalLisp(tokenizer);

        // Ejemplos de uso
        System.out.println(interprete.interpretar("(+ 1 2 3)"));  // 6
        System.out.println(interprete.interpretar("(- 10 5 1)")); // 4
        System.out.println(interprete.interpretar("(* 2 1)"));  // 3
        System.out.println(interprete.interpretar("(/ 10 2)")); // 5
    }
}

package proyecto;

import java.util.*;

public class AritmeticasLisp {
    private Tokenizer tokenizer;
    

    public AritmeticasLisp(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    public int interpretar(String expresion) {
        expresion = expresion.trim();
        if (!expresion.startsWith("(") || !expresion.endsWith(")")) {
            throw new IllegalArgumentException("Expresión no válida: " + expresion);
        }
        
        List<String> tokens = tokenizer.tokenize(expresion);
        return evaluar(tokens);
    }

    private int evaluar(List<String> tokens) {
    Stack<Object> stack = new Stack<>();

    for (String token : tokens) {
        token = token.trim(); 

        if (token.isEmpty()) continue; 

        if (token.equals(")")) {
            List<Integer> operandos = new ArrayList<>();
            String operador = "";

            while (!stack.isEmpty()) {
                Object elemento = stack.pop();
                if (elemento instanceof String) {
                    operador = ((String) elemento).trim();
                    break;
                } else {
                    operandos.add(0, (Integer) elemento);
                }
            }

            int resultado = calcularOperacion(operador, operandos);
            stack.push(resultado);
        } else if (!token.equals("(")) {
            try {
                stack.push(Integer.parseInt(token));
            } catch (NumberFormatException e) {
                stack.push(token); 
            }
        }
    }

    if (stack.size() != 1 || !(stack.peek() instanceof Integer)) {
        throw new IllegalArgumentException("Expresión mal formada.");
        }
        return (Integer) stack.pop();
    }
    private int calcularOperacion(String operador, List<Integer> operandos) {
        if (operandos.isEmpty()) {
            throw new IllegalArgumentException("No hay operandos para la operación " + operador);
        }

        switch (operador) {
            case "+":
                return operandos.stream().mapToInt(Integer::intValue).sum();
            case "-":
                return operandos.stream().reduce((a, b) -> a - b).orElseThrow();
            case "*":
                return operandos.stream().reduce((a, b) -> a * b).orElseThrow();
            case "/":
                if (operandos.contains(0)) throw new ArithmeticException("División por cero.");
                return operandos.stream().reduce((a, b) -> a / b).orElseThrow();
            default:
                throw new IllegalArgumentException("Operador no válido: " + operador);
        }
    }

    public static void main(String[] args) {
        Tokenizer tokenizer = new Tokenizer();
        AritmeticasLisp interprete = new AritmeticasLisp(tokenizer);

        System.out.println(interprete.interpretar("(+ 1 2 3)"));  // 6
        System.out.println(interprete.interpretar("(- 3 2 1)")); // 4
        System.out.println(interprete.interpretar("(* 2 3)"));    // 6
        System.out.println(interprete.interpretar("(/ 9 3)"));   // 3
        System.out.println(interprete.interpretar("(+ (* 2 3) 4)")); // 10
        System.out.println(interprete.interpretar("(* (+ 2 3) (/ 9 3) (- 5 3) 4)")); // 5 * 3 * 2 * 4 = 120
        System.out.println(interprete.interpretar("(+ (- 4 (* 1 1) 2) 5 9)")); // 15
    }
}

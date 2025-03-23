package proyecto;

import java.util.*;


public class LogicasLisp{
    private Tokenizer tokenizer;
    

    public LogicasLisp(Tokenizer tokenizer) {
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

        switch (operador.toUpperCase()) {
            case "AND":
                int resultadoAnd = operandos.get(0);
                for (int i = 1; i < operandos.size(); i++) {
                    resultadoAnd = resultadoAnd & operandos.get(i);
                }
                return resultadoAnd;
                
            case "OR":
                int resultadoOr = operandos.get(0);
                for (int i = 1; i < operandos.size(); i++) {
                    resultadoOr = resultadoOr | operandos.get(i);
                }
                return resultadoOr;
                
            case "NOT":
                if (operandos.size() != 1) {
                    throw new IllegalArgumentException("Operación NOT solo acepta un operando.");
                }
                return operandos.get(0) == 1 ? 0 : 1;

            default:
                throw new IllegalArgumentException("Operador no válido: " + operador);
        }
    }
    public static void main(String[] args) {
        Tokenizer tokenizer = new Tokenizer();
        LogicasLisp interprete = new LogicasLisp(tokenizer);

        // Ejemplos de uso
        System.out.println(interprete.interpretar("(AND 1 1 0)")); // 0
        System.out.println(interprete.interpretar("(OR 0 0 1)"));  // 1
        System.out.println(interprete.interpretar("(NOT 1)"));     // 0
        System.out.println(interprete.interpretar("(AND (OR 1 0) 1)")); // 1
    }

}

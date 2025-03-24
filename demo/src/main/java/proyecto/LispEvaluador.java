package proyecto;

import java.util.*;  

public abstract class LispEvaluador {
    protected Tokenizer tokenizer;

    public LispEvaluador(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    public Object interpretar(String expresion) {
        expresion = expresion.trim();
        if (!expresion.startsWith("(") || !expresion.endsWith(")")) {
            throw new IllegalArgumentException("Expresión no válida: " + expresion);
        }
        
        List<String> tokens = tokenizer.tokenize(expresion);
        return evaluar(tokens);
    }
    private Object evaluar(List<String> tokens) {
        Stack<Object> stack = new Stack<>();
        for (String token : tokens) {
            token = token.trim(); 
            if (token.isEmpty()) continue; 
            if (token.equals(")")) {
                List<Object> operandos = new ArrayList<>();
                String operador = "";
                while (!stack.isEmpty()) {
                    Object elemento = stack.pop();
                    if (elemento instanceof String) {
                        operador = ((String) elemento).trim();
                        break;
                    } else {
                        operandos.add(0, elemento);
                    }
                }
                Object resultado = calcularOperacion(operador, operandos);
                stack.push(resultado);
            } else if (token.equalsIgnoreCase("NIL")) {
                stack.push(null); // Guardamos NIL como null en la pila
            } else if (!token.equals("(")) {
                try {
                    stack.push(Integer.parseInt(token));
                } catch (NumberFormatException e) {
                    stack.push(token);
                }
            }
        }
        if (stack.size() != 1) {
            throw new IllegalArgumentException("Expresión mal formada.");
        }
        return stack.pop();
    }
    protected abstract Object calcularOperacion(String operador, List<Object> operandos);
}

package proyecto;

import java.util.*;

public abstract class LispEvaluador {
    protected Tokenizer tokenizer;
    protected Map<String, Object> entorno;

    public LispEvaluador(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
        this.entorno = new HashMap<>();
    }

    public Object interpretar(String expresion) {
        expresion = expresion.trim();
        if (!expresion.startsWith("(") || !expresion.endsWith(")")) {
            throw new IllegalArgumentException("Expresión no válida: " + expresion);
        }
        
        List<String> tokens = tokenizer.tokenize(expresion);
        return evaluar(tokens);
    }

    protected Object evaluar(List<String> tokens) {
        Stack<Object> stack = new Stack<>();
        
        for (String token : tokens) {
            token = token.trim();
            if (token.isEmpty()) continue;
            
            if (token.equals(")")) {
                List<Object> elements = new ArrayList<>();
                while (!stack.isEmpty() && !stack.peek().equals("(")) {
                    elements.add(0, stack.pop());
                }
                if (stack.isEmpty()) {
                    throw new IllegalArgumentException("Paréntesis no balanceados");
                }
                stack.pop(); // Remove "("
                
                Object result = evaluarExpresion(elements);
                stack.push(result);
            } else if (token.equalsIgnoreCase("NIL")) {
                stack.push(null);
            } else if (!token.equals("(")) {
                try {
                    stack.push(Integer.parseInt(token));
                } catch (NumberFormatException e) {
                    stack.push(token);
                }
            } else {
                stack.push(token);
            }
        }
        
        if (stack.size() != 1) {
            throw new IllegalArgumentException("Expresión mal formada.");
        }
        return stack.pop();
    }

    protected Object evaluarExpresion(List<Object> elements) {
        if (elements.isEmpty()) {
            throw new IllegalArgumentException("Expresión vacía.");
        }

        String operador = elements.get(0).toString();
        List<Object> operandos = elements.subList(1, elements.size());

        // Manejo de operaciones básicas
        switch (operador) {
            case "print":
                if (operandos.size() < 1) return null;
                Object valor = operandos.get(0);
                System.out.println(valor);
                return valor;
            case "setq":
                if (operandos.size() != 2) return null;
                String simbolo = operandos.get(0).toString();
                Object valorAsignado = evaluar(Collections.singletonList(operandos.get(1).toString()));
                entorno.put(simbolo, valorAsignado);
                return valorAsignado;
            default:
                return calcularOperacion(operador, operandos);
        }
    }

    protected abstract Object calcularOperacion(String operador, List<Object> operandos);

    protected boolean esNumero(String token) {
        try {
            Integer.parseInt(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    protected boolean esSimbolo(String token) {
        return !esNumero(token) && !token.equals("(") && !token.equals(")");
    }
}

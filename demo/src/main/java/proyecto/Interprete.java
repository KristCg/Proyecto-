package proyecto;

import java.util.*;

public class Interprete {
    private Tokenizer tokenizer;
    private AritmeticasLisp aritmeticasLisp;
    private Condicionales condicionales;
    private Predicados predicados;
    private Map<String, Object> entorno; 

    public Interprete() {
        this.tokenizer = new Tokenizer();
        this.aritmeticasLisp = new AritmeticasLisp(tokenizer);
        this.condicionales = new Condicionales();
        this.predicados = new Predicados();
        this.entorno = new HashMap<>(); 
    }

    public Object interprete(String expresion) {
        List<String> tokens = tokenizer.tokenize(expresion);
        return evaluate(tokens);
    }

    public Object evaluate(List<String> tokens) {
        Stack<Object> stack = new Stack<>();

        for (String token : tokens) {
            token = token.trim();

            if (token.isEmpty()) continue;

            if (token.equals(")")) {
                List<Object> elements = new ArrayList<>();
                while (!stack.isEmpty() && !stack.peek().equals("(")) {
                    elements.add(0, stack.pop());
                }
                stack.pop(); 

                Object result = evaluateExpression(elements);
                stack.push(result);
            } else if (!token.equals("(")) {
                if (esNumero(token)) {
                    stack.push(Integer.parseInt(token)); 
                } else if (esSimbolo(token)) {
                    stack.push(token); 
                } else {
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

    public Object evaluateExpression(List<Object> elements) {
        if (elements.isEmpty()) {
            throw new IllegalArgumentException("Expresión vacía.");
        }

        String operador = elements.get(0).toString();

        switch (operador) {
            case "+":
            case "-":
            case "*":
            case "/":
                List<Integer> operandos = new ArrayList<>();
                for (int i = 1; i < elements.size(); i++) {
                    Object elemento = elements.get(i);
                    if (elemento instanceof Integer) {
                        operandos.add((Integer) elemento);
                    } else if (elemento instanceof String) {
                        Object valor = entorno.get(elemento);
                        if (valor instanceof Integer) {
                            operandos.add((Integer) valor);
                        } else {
                            throw new IllegalArgumentException("Símbolo no definido: " + elemento);
                        }
                    } else {
                        throw new IllegalArgumentException("Operando no válido: " + elemento);
                    }
                }
                return aritmeticasLisp.calcularOperacion(operador, operandos);
            case "print":
                if (elements.size() < 2) {
                    return null;
                }
                Object valor = elements.get(1);
                System.out.println(valor);
                return valor;
            case "setq":
                if (elements.size() != 3) {
                    return null;
                }
                String simbolo = elements.get(1).toString();
                Object valorAsignado = evaluate(Collections.singletonList(elements.get(2).toString()));
                entorno.put(simbolo, valorAsignado);
                return valorAsignado;
            case "atom":
                if (elements.size() != 2) {
                    return null;
                }
                return predicados.esAtom(elements.get(1));
            default:
                throw new IllegalArgumentException("Operador no válido: " + operador);
        }
    }

    public boolean esNumero(String token) {
        try {
            Integer.parseInt(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean esSimbolo(String token) {
        return !esNumero(token) && !token.equals("(") && !token.equals(")");
    }
}
package proyecto;

import java.util.*;

public class Interprete {
    private Tokenizer tokenizer;
    private AritmeticasLisp aritmeticasLisp;
    private ComparativasLisp comparativasLisp;
    private LogicasLisp logicasLisp;
    private Condicionales condicionales;
    private Predicados predicados;
    private Map<String, Object> entorno;

    public Interprete() {
        this.tokenizer = new Tokenizer();
        this.aritmeticasLisp = new AritmeticasLisp(tokenizer);
        this.comparativasLisp = new ComparativasLisp(tokenizer);
        this.logicasLisp = new LogicasLisp(tokenizer);
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
                return aritmeticasLisp.calcularOperacion(operador, elements.subList(1, elements.size()));
            case "=":
            case "<":
            case ">":
            case "<=":
            case ">=":
                return comparativasLisp.calcularOperacion(operador, elements.subList(1, elements.size()));
            case "AND":
            case "OR":
            case "NOT":
                return logicasLisp.calcularOperacion(operador, elements.subList(1, elements.size()));
            case "cond":
                return condicionales.evaluateConditional(elements.subList(1, elements.size()));
            case "print":
                if (elements.size() < 2) return null;
                Object valor = elements.get(1);
                System.out.println(valor);
                return valor;
            case "setq":
                if (elements.size() != 3) return null;
                String simbolo = elements.get(1).toString();
                Object valorAsignado = evaluate(Collections.singletonList(elements.get(2).toString()));
                entorno.put(simbolo, valorAsignado);
                return valorAsignado;
            case "atom":
                if (elements.size() != 2) return null;
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
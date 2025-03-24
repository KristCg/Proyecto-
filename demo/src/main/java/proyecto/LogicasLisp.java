package proyecto;

import java.util.*;

public class LogicasLisp extends LispEvaluador {
    public LogicasLisp(Tokenizer tokenizer) {
        super(tokenizer);
    }

    @Override
    protected Object calcularOperacion(String operador, List<Object> operandos) {
        if (operador == null || operador.equalsIgnoreCase("NIL")) {
            throw new IllegalArgumentException("Operador no válido:" + operador);

        }
        switch (operador.toUpperCase()) {
            case "AND":
                for (Object op : operandos) {
                    if (op == null) return null; // Si hay un NIL, retorna NIL
                }
                return operandos.get(operandos.size() - 1); // Devuelve el último operando

            case "OR":
                for (Object op : operandos) {
                    if (op != null) return op; // Devuelve el primer no-NIL
                }
                return null; // Si todos son NIL, devuelve NIL

            case "NOT":
                if (operandos.size() != 1) {
                    throw new IllegalArgumentException("Operación NOT solo acepta un operando.");
                }
                return (operandos.get(0) == null) ? "T" : null; // NOT devuelve T si es NIL, sino NIL

            default:
                throw new IllegalArgumentException("Operador no válido: " + operador);
        }
    }
    public static void main(String[] args) {
        Tokenizer tokenizer = new Tokenizer();
        LogicasLisp interprete = new LogicasLisp(tokenizer);

        System.out.println(interprete.interpretar("(AND 1 2 3)"));    // 3
        System.out.println(interprete.interpretar("(AND 1 NIL 3)"));  // NIL
        System.out.println(interprete.interpretar("(OR NIL 2 3)"));   // 2
        System.out.println(interprete.interpretar("(NOT NIL)"));      // T
        System.out.println(interprete.interpretar("(NOT 1)"));        // NIL
    }
}

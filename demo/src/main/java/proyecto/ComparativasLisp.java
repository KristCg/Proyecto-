package proyecto;

import java.util.*;

public class ComparativasLisp extends LispEvaluador{
    public ComparativasLisp(Tokenizer tokenizer) {
        super(tokenizer);
    }

    @Override
    protected Object calcularOperacion(String operador, List<Object> operandos) {
        if (operandos.size() != 2) {
            throw new IllegalArgumentException("Operación de comparación solo acepta dos operandos.");
        }
        int a = (int) operandos.get(0);
        int b = (int) operandos.get(1);

        switch (operador) {
            case "=":
                return a == b ? "T" : null;

            case "<":
                return a < b ? "T" : null;

            case ">":
                return a > b ? "T" : null;

            case "<=":
                return a <= b ? "T" : null;

            case ">=":
                return a >= b ? "T" : null;

            default:
                throw new IllegalArgumentException("Operador no válido: " + operador);
        }
    }

    public static void main(String[] args) {
        Tokenizer tokenizer = new Tokenizer();
        ComparativasLisp interprete = new ComparativasLisp(tokenizer);

        System.out.println(interprete.interpretar("(= 1 1)"));  // T
        System.out.println(interprete.interpretar("(< 1 2)"));  // T
        System.out.println(interprete.interpretar("(> 1 2)"));  // NIL
        System.out.println(interprete.interpretar("(<= 1 1)")); // T
        System.out.println(interprete.interpretar("(>= 1 2)")); // NIL
    }
}

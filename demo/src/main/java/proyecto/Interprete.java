package proyecto;

import java.util.*;

public class Interprete extends LispEvaluador {
    private AritmeticasLisp aritmeticas;
    private ComparativasLisp comparativas;
    private LogicasLisp logicas;
    private Condicionales condicionales;
    private Predicados predicados;

    public Interprete(Tokenizer tokenizer) {
        super(tokenizer);
        this.aritmeticas = new AritmeticasLisp(tokenizer);
        this.comparativas = new ComparativasLisp(tokenizer);
        this.logicas = new LogicasLisp(tokenizer);
        this.condicionales = new Condicionales();
        this.predicados = new Predicados();
    }

    @Override
    protected Object calcularOperacion(String operador, List<Object> operandos) {
        switch (operador) {
            case "+":
            case "-":
            case "*":
            case "/":
                return aritmeticas.calcularOperacion(operador, operandos);
            case "=":
            case "<":
            case ">":
            case "<=":
            case ">=":
                return comparativas.calcularOperacion(operador, operandos);
            case "AND":
            case "OR":
            case "NOT":
                return logicas.calcularOperacion(operador, operandos);
            case "cond":
                for (Object clause : operandos) {
                    if (!(clause instanceof List)) {
                        throw new IllegalArgumentException("Cada cláusula cond debe ser una lista. Formato: (cond (condición resultado) ...)");
                    }
                }
            return Condicionales.evaluateConditional(operandos, this);
            case "atom":
                if (operandos.size() != 1) return null;
                return predicados.esAtom(operandos.get(0));
            default:
                throw new IllegalArgumentException("Operador no válido: " + operador);
        }
    }
}
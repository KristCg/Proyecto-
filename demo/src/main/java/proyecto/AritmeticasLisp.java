package proyecto;

import java.util.*;

public class AritmeticasLisp extends LispEvaluador {
    public AritmeticasLisp(Tokenizer tokenizer) {
        super(tokenizer);
    }

    @Override
    protected Object calcularOperacion(String operador, List<Object> operandos) {
        int resultado;
        switch (operador) {
            case "+":
                resultado = 0;
                for (Object op : operandos) resultado += (int) op;
                return resultado;

            case "-":
                if (operandos.size() == 1) return -(int) operandos.get(0);
                resultado = (int) operandos.get(0);
                for (int i = 1; i < operandos.size(); i++) resultado -= (int) operandos.get(i);
                return resultado;

            case "*":
                resultado = 1;
                for (Object op : operandos) resultado *= (int) op;
                return resultado;

            case "/":
                resultado = (int) operandos.get(0);
                for (int i = 1; i < operandos.size(); i++) {
                    if ((int) operandos.get(i) == 0) throw new ArithmeticException("División por cero.");
                    resultado /= (int) operandos.get(i);
                }
                return resultado;

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

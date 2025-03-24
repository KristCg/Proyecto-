package proyecto;

import org.junit.Test;

public class InterpreteTest {

    @Test
    public void testInterpretarExpresionAritmetica() {
        Interprete interprete = new Interprete();
        System.out.println("Probando expresión aritmética: (+ 1 2 3)");
        Object resultado = interprete.interprete("(+ 1 2 3)");
        System.out.println("Resultado: " + resultado);
    }

    @Test
    public void testInterpretarPrint() {
        Interprete interprete = new Interprete();
        System.out.println("Probando expresión con print: (print (+ 3 (* 2 5)))");
        Object resultado = interprete.interprete("(print (+ 3 (* 2 5)))");
        System.out.println("Resultado: " + resultado);
    }

    @Test
    public void testInterpretarCondicional() {
        Interprete interprete = new Interprete();
        System.out.println("Probando expresión condicional:");
        String expresion = "(cond ((> 10 15) \"10 es mayor que 15\") ((= 10 10) \"10 es igual a 10\") ((< 10 15) \"10 es menor que 15\") (t \"Ninguna condición se cumplió\"))";
        Object resultado = interprete.interprete(expresion);
        System.out.println("Resultado: " + resultado);
}

    @Test
    public void testInterpretarSetq() {
        Interprete interprete = new Interprete();
        System.out.println("Probando expresión con setq: (setq x 10)");
        Object resultado = interprete.interprete("(setq x 10)");
        System.out.println("Resultado: " + resultado);

        System.out.println("Probando expresión que usa la variable x: (+ x 5)");
        resultado = interprete.interprete("(+ x 5)");
        System.out.println("Resultado: " + resultado);
    }

    @Test
    public void testInterpretarAtom() {
        Interprete interprete = new Interprete();
        System.out.println("Probando expresión con atom: (atom 5)");
        Object resultado = interprete.interprete("(atom 5)");
        System.out.println("Resultado: " + resultado);

        System.out.println("Probando expresión con atom: (atom (1 2 3))");
        resultado = interprete.interprete("(atom (1 2 3))");
        System.out.println("Resultado: " + resultado);
    }

    @Test
    public void testInterpretarExpresion() {
        Interprete interprete = new Interprete();
        System.out.println("Probando expresión: (print (+ (* 2 3) (/ 10 2)))");
        Object resultado = interprete.interprete("(print (+ (* 2 3) (/ 10 2)))");
        System.out.println("Resultado: " + resultado);
    }
}
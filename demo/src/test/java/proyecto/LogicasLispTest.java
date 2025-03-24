package proyecto;

import org.junit.Test;
import static org.junit.Assert.*;

public class LogicasLispTest{
    Tokenizer tokenizer = new Tokenizer();
    LogicasLisp logica = new LogicasLisp(tokenizer);

    @Test
    public void testAND() {
        assertEquals((Integer) 3, logica.interpretar("(AND 1 2 3)")); // Último valor
        assertEquals("NIL", logica.interpretar("(AND 1 NIL 3)")); // NIL en la operación
        assertEquals("NIL", logica.interpretar("(AND NIL NIL NIL)")); // Todos NIL
        assertEquals("NIL", logica.interpretar("(AND)")); // AND vacío
    }

    @Test
    public void testOR() {
        assertEquals((Integer) 2, logica.interpretar("(OR NIL 2 3)")); // Primer valor verdadero
        assertEquals((Integer) 1, logica.interpretar("(OR 1 2 3)")); // Primer operando no NIL
        assertEquals("NIL", logica.interpretar("(OR NIL NIL NIL)")); // Todos NIL
        assertEquals("NIL", logica.interpretar("(OR)")); // OR vacío
    }

    @Test
    public void testNOT() {
        assertEquals("T", logica.interpretar("(NOT NIL)")); // NOT de NIL es T
        assertEquals("NIL", logica.interpretar("(NOT 1)")); // NOT de 1 es NIL
        assertEquals("NIL", logica.interpretar("(NOT 0)")); // NOT de 0 (según implementación)
        assertEquals("T", logica.interpretar("(NOT (OR NIL NIL))")); // NOT de expresión
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNOTConVariosParametros() {
        logica.interpretar("(NOT 1 2)"); // NOT solo permite un argumento
    }
}
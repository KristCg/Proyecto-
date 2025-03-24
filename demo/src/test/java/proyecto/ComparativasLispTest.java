package proyecto;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Arrays;

public class ComparativasLispTest {
    @Test
    public void testOperacionesComparativas() {
        Tokenizer tokenizer = new Tokenizer();
        ComparativasLisp comparativasLisp = new ComparativasLisp(tokenizer);

        assertEquals("T", comparativasLisp.calcularOperacion(">", Arrays.asList(5, 3)));
        assertEquals(null, comparativasLisp.calcularOperacion("<", Arrays.asList(7, 2)));
        assertEquals("T", comparativasLisp.calcularOperacion("=", Arrays.asList(4, 4)));
    }

    @Test
    public void testComparacionConValoresInvalidos() {
        Tokenizer tokenizer = new Tokenizer();
        ComparativasLisp comparativasLisp = new ComparativasLisp(tokenizer);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            comparativasLisp.calcularOperacion(">", Arrays.asList("A", 5));
        });
        assertTrue(exception.getMessage().contains("Operando no válido"));
    }
}
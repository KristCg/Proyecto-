package proyecto;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Arrays;

class ComparativosLispTest {
    @Test
    public void testOperacionesComparativas() {
        Tokenizer tokenizer = new Tokenizer();
        ComparativosLisp comparativos = new ComparativosLisp(tokenizer);

        assertEquals("T", comparativos.calcularOperacion(">", Arrays.asList(5, 3)));
        assertEquals(null, comparativos.calcularOperacion("<", Arrays.asList(7, 2)));
        assertEquals("T", comparativos.calcularOperacion("=", Arrays.asList(4, 4)));
    }

    @Test
    public void testComparacionConValoresInvalidos() {
        Tokenizer tokenizer = new Tokenizer();
        ComparativosLisp comparativos = new ComparativosLisp(tokenizer);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            comparativos.calcularOperacion(">", Arrays.asList("A", 5));
        });

        assertTrue(exception.getMessage().contains("Operando no válido"));
    }
}
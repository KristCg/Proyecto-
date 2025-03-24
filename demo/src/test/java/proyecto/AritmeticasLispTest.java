package proyecto;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Arrays;

public class AritmeticasLispTest {
    @Test
    public void testOperacionesAritmeticas() {
        Tokenizer tokenizer = new Tokenizer();
        AritmeticasLisp aritmeticas = new AritmeticasLisp(tokenizer);

        assertEquals(15, aritmeticas.calcularOperacion("+", Arrays.asList(10, 5)));
        assertEquals(5, aritmeticas.calcularOperacion("-", Arrays.asList(10, 5)));
        assertEquals(50, aritmeticas.calcularOperacion("*", Arrays.asList(10, 5)));
        assertEquals(2, aritmeticas.calcularOperacion("/", Arrays.asList(10, 5)));
    }

    @Test
    public void testDivisionPorCero() {
        Tokenizer tokenizer = new Tokenizer();
        AritmeticasLisp aritmeticas = new AritmeticasLisp(tokenizer);

        Exception exception = assertThrows(ArithmeticException.class, () -> {
            aritmeticas.calcularOperacion("/", Arrays.asList(10, 0));
        });

        assertTrue(exception.getMessage().contains("División por cero"));
    }
}
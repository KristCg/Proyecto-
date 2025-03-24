package proyecto;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.List;

public class TokenizerTest {

    @Test
    public void testTokenize() {
        Tokenizer tokenizer = new Tokenizer();
        List<String> tokens = tokenizer.tokenize("(+ 1 2)");
        System.out.println(tokens);
    }

    @Test
    public void test2Tokenize() {
        Tokenizer tokenizer = new Tokenizer();
        List<String> tokens = tokenizer.tokenize("(print (+ 3 (* 2 5)))");
        System.out.println(tokens);
    }

    @Test
    public void test3Tokenize() {
        Tokenizer tokenizer = new Tokenizer();
        List<String> tokens = tokenizer.tokenize("(print (cond (> 3 2) (+ 1 2) (- 1 2)))");
        System.out.println(tokens);
    }
}
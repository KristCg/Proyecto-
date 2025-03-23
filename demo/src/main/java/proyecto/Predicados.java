package proyecto;
import java.util.List;

public class Predicados {

    public static boolean esAtom(Object expresion) {
        return !(expresion instanceof List);
    }
}
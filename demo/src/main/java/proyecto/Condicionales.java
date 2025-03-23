package proyecto;

import java.util.List;

public class Condicionales {

    public static Object evaluateConditional(List<Object> expression) {
        if (expression.size() < 2 || expression.size() > 3) {
            return null;
        }

        Object condition = expression.get(0);
        boolean isTrue = isTrue(condition);

        if (isTrue) {
            return expression.get(1);
        } else if (expression.size() == 3) {
            return expression.get(2);
        } else {
            return null;
        }
    }

    private static boolean isTrue(Object value) {
        if (value instanceof Boolean) {
            return (Boolean) value;
        } else if (value instanceof Number) {
            return ((Number) value).doubleValue() != 0;
        } else if (value instanceof String) {
            return !((String) value).isEmpty();
        } else {
            return value != null;
        }
    }
}
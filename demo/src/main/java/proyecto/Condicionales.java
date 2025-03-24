package proyecto;

import java.util.List;

public class Condicionales {

    public static Object evaluateConditional(List<Object> expression) {
        for (int i = 0; i < expression.size(); i += 2) {
            Object condition = expression.get(i);
            if (i + 1 >= expression.size()) {
                return condition;
            }
            Object value = expression.get(i + 1);
    
            if (isTrue(condition)) {
                return value;
            }
        }
        return null; 
    }

    public static boolean isTrue(Object value) {
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
package proyecto;

import java.util.List;

public class Condicionales {
    
    public static Object evaluateConditional(List<Object> clauses, LispEvaluador evaluador) {
        if (clauses.isEmpty()) {
            return null;
        }

        for (Object clause : clauses) {
            if (!(clause instanceof List)) {
                throw new IllegalArgumentException("Cada cláusula cond debe ser una lista");
            }

            List<Object> conditionAndResult = (List<Object>) clause;
            if (conditionAndResult.isEmpty()) {
                continue;
            }

            Object condition = conditionAndResult.get(0);
            Object result = conditionAndResult.size() > 1 ? conditionAndResult.get(1) : condition;

            // Evaluar la condición
            boolean conditionResult;
            if (isTrueSymbol(condition)) {
                conditionResult = true;
            } else {
                conditionResult = evaluarCondicion(condition, evaluador);
            }

            if (conditionResult) {
                return evaluarExpresionCond(result, evaluador);
            }
        }
        
        return null;
    }

    private static boolean isTrueSymbol(Object condition) {
        return (condition instanceof String && 
               (((String) condition).equalsIgnoreCase("T") || 
                ((String) condition).equalsIgnoreCase("TRUE")));
    }

    private static boolean evaluarCondicion(Object condition, LispEvaluador evaluador) {
        if (condition == null || 
            (condition instanceof String && ((String) condition).equalsIgnoreCase("NIL"))) {
            return false;
        }
        
        if (condition instanceof List) {
            Object result = evaluador.evaluarExpresion((List<Object>) condition);
            return isTrue(result);
        }
        
        return isTrue(condition);
    }

    private static Object evaluarExpresionCond(Object expr, LispEvaluador evaluador) {
        if (expr instanceof List) {
            return evaluador.evaluarExpresion((List<Object>) expr);
        }
        return expr;
    }

    public static boolean isTrue(Object value) {
        if (value instanceof Boolean) {
            return (Boolean) value;
        } else if (value instanceof Number) {
            return ((Number) value).doubleValue() != 0;
        } else if (value instanceof String) {
            return !((String) value).isEmpty() && 
                  !((String) value).equalsIgnoreCase("NIL") &&
                  !((String) value).equalsIgnoreCase("FALSE");
        } else {
            return value != null;
        }
    }
}
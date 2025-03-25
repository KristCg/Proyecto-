package proyecto;

import java.util.List;

public class Condicionales {
    
    public static Object evaluateConditional(List<Object> clauses, LispEvaluador evaluador) {
        if (clauses.isEmpty()) {
            return null;
        }

        Object firstClause = clauses.get(0);
        
        if (!(firstClause instanceof List)) {
            throw new IllegalArgumentException("Cada cláusula cond debe ser una lista");
        }

        List<Object> clause = (List<Object>) firstClause;
        
        // Caso especial para el 'else' (T o TRUE)
        if (clause.size() >= 1 && isTrueSymbol(clause.get(0))) {
            return evaluarExpresionCond(clause.size() > 1 ? clause.get(1) : clause.get(0), evaluador);
        }

        // Evaluar condición normal
        if (clause.size() < 1) {
            throw new IllegalArgumentException("Cláusula cond incompleta");
        }

        Object condition = clause.get(0);
        boolean conditionResult = evaluarCondicion(condition, evaluador);

        if (conditionResult) {
            Object result = clause.size() > 1 ? clause.get(1) : condition;
            return evaluarExpresionCond(result, evaluador);
        } else {
            return evaluateConditional(clauses.subList(1, clauses.size()), evaluador);
        }
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
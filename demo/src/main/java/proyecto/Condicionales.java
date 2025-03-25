package proyecto;

import java.util.List;

public class Condicionales {
    
    public static Object evaluateConditional(List<Object> clauses, LispEvaluador evaluador) {
        // Caso base: si no hay cláusulas, retornar null (NIL)
        if (clauses.isEmpty()) {
            return null;
        }

        // Tomar la primera cláusula
        Object firstClause = clauses.get(0);
        
        // Verificar que sea una lista
        if (!(firstClause instanceof List)) {
            throw new IllegalArgumentException("Cada cláusula cond debe ser una lista");
        }

        List<Object> clause = (List<Object>) firstClause;
        
        // Caso especial para el 'else' (T o TRUE)
        if (clause.size() == 1 && isTrueSymbol(clause.get(0))) {
            return evaluarExpresionCond(clause.get(0), evaluador);
        }

        // Evaluar la condición
        Object condition = clause.get(0);
        boolean conditionResult = evaluarCondicion(condition, evaluador);

        if (conditionResult) {
            // Si la condición es verdadera, evaluar y retornar el resultado
            Object result = clause.size() > 1 ? clause.get(1) : condition;
            return evaluarExpresionCond(result, evaluador);
        } else {
            // Llamada recursiva con las cláusulas restantes
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
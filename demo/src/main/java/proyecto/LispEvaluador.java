package proyecto;

import java.util.*;

public abstract class LispEvaluador {
    protected Tokenizer tokenizer;
    protected Map<String, Object> entorno;
    protected Map<String, FuncionDefinida> funcionesDefinidas;

    protected static class FuncionDefinida {
        List<String> parametros;
        List<Object> cuerpo;
        
        public FuncionDefinida(List<String> parametros, List<Object> cuerpo) {
            this.parametros = parametros;
            this.cuerpo = cuerpo;
        }
        
        public Object ejecutar(LispEvaluador evaluador, List<Object> argumentos) {
            if (parametros.size() != argumentos.size()) {
                throw new IllegalArgumentException("Número incorrecto de argumentos para la función");
            }
            
            Map<String, Object> entornoOriginal = new HashMap<>(evaluador.entorno);
            
            try {
                for (int i = 0; i < parametros.size(); i++) {
                    evaluador.entorno.put(parametros.get(i), argumentos.get(i));
                }
                Object resultado = null;
                for (Object expresion : cuerpo) {
                    if (expresion instanceof String) {
                        resultado = evaluador.evaluar(Collections.singletonList((String) expresion));
                    } else if (expresion instanceof List) {
                        // Convertir List<Object> a List<String> para evaluar
                        List<String> tokens = new ArrayList<>();
                        for (Object item : (List<?>) expresion) {
                            tokens.add(item.toString());
                        }
                        resultado = evaluador.evaluar(tokens);
                    }
                }
                return resultado;
            } finally {
                evaluador.entorno = entornoOriginal;
            }
        }
    }

    public LispEvaluador(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
        this.entorno = new HashMap<>();
    }

    public Object interpretar(String expresion) {
        expresion = expresion.trim();
        if (!expresion.startsWith("(") || !expresion.endsWith(")")) {
            throw new IllegalArgumentException("Expresión no válida: " + expresion);
        }
        
        List<String> tokens = tokenizer.tokenize(expresion);
        return evaluar(tokens);
    }

    protected Object evaluar(List<String> tokens) {
        Stack<Object> stack = new Stack<>();
        
        for (String token : tokens) {
            token = token.trim();
            if (token.isEmpty()) continue;
            
            if (token.equals(")")) {
                List<Object> elements = new ArrayList<>();
                while (!stack.isEmpty() && !stack.peek().equals("(")) {
                    elements.add(0, stack.pop());
                }
                if (stack.isEmpty()) {
                    throw new IllegalArgumentException("Paréntesis no balanceados");
                }
                stack.pop(); 
                
                Object result = evaluarExpresion(elements);
                stack.push(result);
            } else if (token.equalsIgnoreCase("NIL")) {
                stack.push(null);
            } else if (!token.equals("(")) {
                try {
                    stack.push(Integer.parseInt(token));
                } catch (NumberFormatException e) {
                    if (entorno.containsKey(token)) {
                        stack.push(entorno.get(token));
                    } else {
                        stack.push(token);
                    }
                }
            } else {
                stack.push(token);
            }
        }
        
        if (stack.size() != 1) {
            throw new IllegalArgumentException("Expresión mal formada.");
        }
        return stack.pop();
    }

    protected Object evaluarExpresion(List<Object> elements) {
        if (elements.isEmpty()) {
            throw new IllegalArgumentException("Expresión vacía.");
        }
    
        String operador = elements.get(0).toString();
        List<Object> operandos = elements.subList(1, elements.size());
    
        switch (operador) {
            case "print":
                if (operandos.size() < 1) return null;
                Object valorPrint = operandos.get(0);
                if (valorPrint instanceof String) {
                    valorPrint = evaluar(Collections.singletonList((String)valorPrint));
                }
                System.out.println(valorPrint);
                return valorPrint;
                
            case "setq":
                if (operandos.size() != 2) {
                    throw new IllegalArgumentException("setq requiere exactamente 2 argumentos");
                }
                
                Object simboloObj = operandos.get(0);
                if (!Predicados.esAtom(simboloObj)) {
                    throw new IllegalArgumentException("El símbolo en setq debe ser atómico");
                }
                String simbolo = simboloObj.toString();
                
                Object valorAsignar = operandos.get(1);
                Object valorEvaluado;
                
                if (valorAsignar instanceof List) {
                    valorEvaluado = evaluarExpresion((List<Object>)valorAsignar);
                } else if (valorAsignar instanceof String) {
                    String strValor = (String)valorAsignar;
                    if (entorno.containsKey(strValor)) {
                        valorEvaluado = entorno.get(strValor);
                    } else {
                        try {
                            valorEvaluado = Integer.parseInt(strValor);
                        } catch (NumberFormatException e) {
                            if (!Predicados.esAtom(strValor)) {
                                throw new IllegalArgumentException("Valor no atómico en setq: " + strValor);
                            }
                            valorEvaluado = strValor;
                        }
                    }
                } else {
                    valorEvaluado = valorAsignar;
                }
                
                entorno.put(simbolo, valorEvaluado);
                return valorEvaluado;

            case "cond":
                for (Object clause : operandos) {
                    if (!(clause instanceof List)) {
                        throw new IllegalArgumentException("Cada cláusula cond debe ser una lista");
                    }
                }
                return Condicionales.evaluateConditional(operandos, this);

            default:
                return calcularOperacion(operador, operandos);
        }
    }

    private Object definirFuncion(List<Object> operandos) {
        if (operandos.size() < 3) {
            throw new IllegalArgumentException("defun requiere nombre, parámetros y cuerpo");
        }
        
        String nombreFuncion = operandos.get(0).toString();
        
        if (!(operandos.get(1) instanceof List)) {
            throw new IllegalArgumentException("Los parámetros deben ser una lista");
        }
        List<?> parametrosList = (List<?>) operandos.get(1);
        List<String> parametros = new ArrayList<>();
        for (Object param : parametrosList) {
            parametros.add(param.toString());
        }
        
        List<Object> cuerpo = new ArrayList<>(operandos.subList(2, operandos.size()));
        
        funcionesDefinidas.put(nombreFuncion, new FuncionDefinida(parametros, cuerpo));
        return nombreFuncion;
    }
    
    private List<String> convertToListOfStrings(List<?> list) {
        List<String> result = new ArrayList<>();
        for (Object item : list) {
            result.add(item.toString());
        }
        return result;
    }

    protected abstract Object calcularOperacion(String operador, List<Object> operandos);

    protected boolean esNumero(String token) {
        try {
            Integer.parseInt(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    protected boolean esSimbolo(String token) {
        return !esNumero(token) && !token.equals("(") && !token.equals(")");
    }
}
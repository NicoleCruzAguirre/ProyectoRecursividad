package proyectorecursividad;

/**
 * @author nicol
 */
public class Calculadora {

    public static int resolver(String expresion) {
        if (expresion == null) {
            throw new IllegalArgumentException("La expresion no puede ser nula.");
        }
        expresion = expresion.replace(" ", "");

        validarFormato(expresion);
        return resolverRecursivo(expresion);
    }

    private static int resolverRecursivo(String s) {
        while (tieneParentesisExteriores(s)) {
            s = s.substring(1, s.length() - 1);
        }

        int nivelParentesis = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            char c = s.charAt(i);
            if (c == ')') nivelParentesis++;
            else if (c == '(') nivelParentesis--;
            
            if (nivelParentesis == 0 && (c == '+' || c == '-')) {
                String izq = s.substring(0, i);
                String der = s.substring(i + 1);

                if (c == '+') {
                    return resolverRecursivo(izq) + resolverRecursivo(der);
                } else {
                    return resolverRecursivo(izq) - resolverRecursivo(der);
                }
            }
        }

        nivelParentesis = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            char c = s.charAt(i);
            if (c == ')') nivelParentesis++;
            else if (c == '(') nivelParentesis--;

            if (nivelParentesis == 0 && c == '*') {
                String izq = s.substring(0, i);
                String der = s.substring(i + 1);

                return resolverRecursivo(izq) * resolverRecursivo(der);
            }
        }
        
    
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Numero fuera de rango o invalido: " + s);
        }
    }

    private static boolean tieneParentesisExteriores(String s) {
        if (!s.startsWith("(") || !s.endsWith(")")) {
            return false;
        }
        int balance = 0;
        for (int i = 0; i < s.length() - 1; i++) {
            if (s.charAt(i) == '(') balance++;
            else if (s.charAt(i) == ')') balance--;
            if (balance == 0) return false;
        }
        return true;
    }

    private static void validarFormato(String s) {
        if (s.isEmpty()) {
            throw new IllegalArgumentException("La expresion esta vacia.");
        }
        
        if (!s.matches("[0-9+\\-*()]+")) {
            throw new IllegalArgumentException("Contiene caracteres no permitidos: " + s);
        }

        if (s.contains("()") || s.contains(")(") || s.matches(".*[0-9]\\(.*") || s.matches(".*\\)[0-9].*")) {
            throw new IllegalArgumentException("Multiplicacion implicita no permitida (falta el '*'): " + s);
        }

        if (s.startsWith("+") || s.startsWith("-") || s.startsWith("*") ||
            s.endsWith("+") || s.endsWith("-") || s.endsWith("*")) {
            throw new IllegalArgumentException("Operador invalido al inicio o al final: " + s);
        }

        if (s.matches(".*[+\\-*]{2,}.*")) {
            throw new IllegalArgumentException("Operadores consecutivos no permitidos: " + s);
        }

        int balance = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(') {
                balance++;
                
                if (i + 1 < s.length() && (s.charAt(i + 1) == '+' || s.charAt(i + 1) == '-' || s.charAt(i + 1) == '*' || s.charAt(i + 1) == ')')) {
                    throw new IllegalArgumentException("Contenido invalido tras abrir parentesis en: " + s);
                }
            } else if (c == ')') {
                balance--;
                if (balance < 0) {
                    throw new IllegalArgumentException("Parentesis de cierre sin apertura en: " + s);
                }
                if (i > 0 && (s.charAt(i - 1) == '+' || s.charAt(i - 1) == '-' || s.charAt(i - 1) == '*')) {
                    throw new IllegalArgumentException("Operador sin operando antes de cerrar parentesis en: " + s);
                }
            }
        }

        if (balance != 0) {
            throw new IllegalArgumentException("Parentesis desbalanceados en: " + s);
        }
    }
}
package proyectorecursividad;

/**
 * @author nicol
 */
public class ProyectoRecursividad {

    public static void main(String[] args) {
        System.out.println("=== PRUEBAS CALCULADORA ===");
        
        probar("7+10+2", 19);
        probar("1+5*(7-1)+10", 41);
        probar("5+7+(1+2*(8-9)+2)-10", 3);
        probar("9-2*(75-9+(6+3+(1+2)*1)+7)+9", -155);

        System.out.println("\n=== PRUEBAS DE ERROR ===");
        probarError("5++1+10");
        probarError("-5+10");
        probarError("74-7/7");
        probarError("10+9-A");
    }

    private static void probar(String ecuacion, int esperado) {
        int resultado = Calculadora.resolver(ecuacion);
        System.out.println(ecuacion + " = " + resultado + " (Esperado: " + esperado + ")");
        if (resultado == esperado) {
            System.out.println("CORRECTO\n");
        } else {
            System.out.println("ERROR\n");
        }
    }

    private static void probarError(String ecuacion) {
        try {
            Calculadora.resolver(ecuacion);
            System.out.println("FALLO: No lanzo excepcion con: " + ecuacion + "\n");
        } catch (IllegalArgumentException e) {
            System.out.println("EXITO: Atrapo el error en [" + ecuacion + "]\n");
        }
    }
}
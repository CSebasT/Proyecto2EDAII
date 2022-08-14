package consola;

import java.util.Scanner;
import laberinto.Laberinto;

/**
 *
 * @author César
 */
public class MainConsola {

    public static void main(String[] args) {
        Laberinto laberinto = new Laberinto(5);
        Scanner sc = new Scanner(System.in);
        int dimensionCeldas = 0;
        String opcion = "Y";
        boolean bandera = false;

        while (opcion.equals("Y")) {
            bandera = false;
            while (!bandera) {
                System.out.println("Ingrese un tamaño para el laberinto (Número)");
                System.out.println("Evite usar un número mayor que diez");
                opcion = sc.nextLine();
                try {
                    dimensionCeldas = Integer.parseInt(opcion);
                } catch (NumberFormatException ex) {
                    System.out.println("Debe ingresar un número");
                    continue;
                }
                if (dimensionCeldas<=0){
                    System.out.println("El número debe ser mayor que cero");
                    continue;
                }
                bandera = true;
            }

            laberinto.setDimension(dimensionCeldas);
            laberinto.crearNuevoLaberinto();
            laberinto.imprimirLaberinto();

            System.out.println("Oprima Enter para ver la solución por backtraking");
            opcion = sc.nextLine();

            laberinto.imprimirSolucion();
            
            System.out.println("Si desea crear otro laberinto ingrese \"Y\"");
            opcion = sc.nextLine().toUpperCase();
        }
        System.out.println("Fin de la ejecución");
    }
}

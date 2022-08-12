package laberinto;

/**
 *
 * @author Nicolás B, Jhon M, César T
 */
public abstract class Elemento {

    protected int posicionI; // Posición en las filas
    protected int posicionJ; // Posición en las columnas
    // Constante para cambiar el color de fondo de impresión por concola
    protected final String ANSI_RESET = "\u001B[0m";

    /**
     * Constructor de Elemento
     */
    public Elemento(int posicionI, int posicionJ) {
        this.posicionI = posicionI;
        this.posicionJ = posicionJ;
    }

    // Retorna la posición del elemento en las filas
    public int getPosicionI() {
        return posicionI;
    }

    // Retorna la posición del elemento en las columnas
    public int getPosicionJ() {
        return posicionJ;
    }
}

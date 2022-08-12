package laberinto;

/**
 *
 * @author Nicolás B, Jhon M, César T
 */
public class Pared extends Elemento {

    // Constante para cambiar el color de fondo de impresión por concola
    private final String ANSI_FONDO_BLANCO = "\u001B[47m";

    /**
     * Constructor del elemento Pared
     */
    public Pared(int posicionI, int posicionJ) {
        super(posicionI, posicionJ);
    }

    @Override
    public String toString() {
        return ANSI_FONDO_BLANCO + "   " + ANSI_RESET;
    }
}

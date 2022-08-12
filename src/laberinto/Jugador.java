package laberinto;

/**
 *
 * @author Nicolás B, Jhon M, César T
 */
public class Jugador extends Elemento {
    
    // Constante para cambiar el color de fondo de impresión por concola
    private final String ANSI_FONDO_CIAN = "\u001B[46m";

    /**
     * Constructor del elemento Jugador
     */
    public Jugador(int posicionI, int posicionJ) {
        super(posicionI, posicionJ);
    }

    // Cambia la posición en las filas
    public void setPosicionI(int posicionI) {
        this.posicionI = posicionI;
    }

    // Cambia la posición en las columnas
    public void setPosicionJ(int posicionJ) {
        this.posicionJ = posicionJ;
    }
    
    @Override
    public String toString() {
        return ANSI_FONDO_CIAN + "-J-" + ANSI_RESET;
    }
}

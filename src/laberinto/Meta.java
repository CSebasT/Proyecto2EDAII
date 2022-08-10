/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package laberinto;

/**
 *
 * @author Nicolás B, Jhon M, César T
 */
public class Meta extends Elemento {
    
    // Constante para cambiar el color de fondo de impresión por concola
    private final String ANSI_FONDO_VERDE = "\u001B[42m";

    /**
     * Constructor del elemento Meta
     */
    public Meta(int posicionI, int posicionJ) {
        super(posicionI, posicionJ);
    }

    @Override
    public String toString() {
        return ANSI_FONDO_VERDE + " M " + ANSI_RESET;
    }
}

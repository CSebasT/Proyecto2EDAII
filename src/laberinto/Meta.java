/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package laberinto;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author César
 */
public class Meta extends Elemento {
    private final String ANSI_FONDO_VERDE = "\u001B[42m";

    public Meta(int posicionI, int posicionJ) {
        super(posicionI, posicionJ);
    }

    @Override
    public String toString() {
        return ANSI_FONDO_VERDE + " M " + ANSI_RESET;
    }
}

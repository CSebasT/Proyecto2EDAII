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
public class Jugador extends Elemento {
    private final String ANSI_FONDO_CIAN = "\u001B[46m";

    public Jugador(int posicionI, int posicionJ) {
        super(posicionI, posicionJ);
    }

    public void setPosicionI(int posicionI) {
        this.posicionI = posicionI;
    }

    public void setPosicionJ(int posicionJ) {
        this.posicionJ = posicionJ;
    }
    
    @Override
    public String toString() {
        return ANSI_FONDO_CIAN + "-J-" + ANSI_RESET;
    }
}

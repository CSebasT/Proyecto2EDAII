/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package laberinto;

import javax.swing.ImageIcon;

/**
 *
 * @author César
 */
public abstract class Elemento {
    protected final String ANSI_RESET = "\u001B[0m";
    protected int posicionI;
    protected int posicionJ;

    public Elemento(int posicionI, int posicionJ) {
        this.posicionI = posicionI;
        this.posicionJ = posicionJ;
    }

    public int getPosicionI() {
        return posicionI;
    }

    public int getPosicionJ() {
        return posicionJ;
    }
}

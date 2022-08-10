/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package laberinto;

/**
 *
 * @author César
 */
public enum Direccion {
    ARRIBA(-1, 0),
    ABAJO(1, 0),
    IZQUIERDA(0, -1),
    DERECHA(0, 1);

    private int cambioEnFila;
    private int cambioEnColumna;

    Direccion(int cambioEnFila, int cambioEnColumna) {
        this.cambioEnFila = cambioEnFila;
        this.cambioEnColumna = cambioEnColumna;
    }

    public int getCambioEnFila() {
        return cambioEnFila;
    }

    public int getCambioEnColumna() {
        return cambioEnColumna;
    }
}


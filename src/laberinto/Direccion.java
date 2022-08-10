/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package laberinto;

/**
 *
 * @author Nicolás B, Jhon M, César T
 */
public enum Direccion {
    ARRIBA(-1, 0),
    ABAJO(1, 0),
    IZQUIERDA(0, -1),
    DERECHA(0, 1);

    private int cambioEnFila;
    private int cambioEnColumna;

    /**
     * Constructor de la enumeración Dirección
     */
    Direccion(int cambioEnFila, int cambioEnColumna) {
        this.cambioEnFila = cambioEnFila;
        this.cambioEnColumna = cambioEnColumna;
    }

    // Retorna la cantidad que se debe mover el indice de una fila
    public int getCambioEnFila() {
        return cambioEnFila;
    }

    // Retorna la cantidad que se debe mover el indice de una columna
    public int getCambioEnColumna() {
        return cambioEnColumna;
    }
}

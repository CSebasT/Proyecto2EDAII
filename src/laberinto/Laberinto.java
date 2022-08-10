/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package laberinto;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;

/**
 *
 * @author César
 */
public class Laberinto {

    private boolean[][] celdasVisitadas;
    private int dimensionCeldas;
    private int numeroCeldas;
    private Random rnd;
    private String[][] laberintoDibujado;
    private char[][] laberintoCaracteres;
    private int dimensionLaberinto;
    private boolean[][] espaciosVisitados;
    private ArrayList<Pared> paredes;
    private Jugador jugador;
    private Meta meta;
    private Creador creador;
    private Stack<Integer> recurI; // Auxiliar para backtracking
    private Stack<Integer> recurJ; // Auxiliar para backtracking

    public Laberinto(int dimensionCeldas) {
        rnd = new Random();
        inicializar(dimensionCeldas);
    }

    public void setDimension(int dimensionCeldas) {
        inicializar(dimensionCeldas);
    }

    void inicializar(int dimensionCeldas) {
        creador = new Creador(rnd.nextInt(dimensionCeldas), rnd.nextInt(dimensionCeldas));
        recurI = new Stack<>();
        recurJ = new Stack<>();
        this.dimensionCeldas = dimensionCeldas;
        celdasVisitadas = new boolean[dimensionCeldas][dimensionCeldas];

        numeroCeldas = dimensionCeldas * dimensionCeldas;
        dimensionLaberinto = 2 * dimensionCeldas + 1;
        espaciosVisitados = new boolean[dimensionLaberinto][dimensionLaberinto];

        paredes = new ArrayList<>();
        crearParedesIniciales();
        jugador = null;
        meta = null;
        laberintoDibujado = new String[dimensionLaberinto][dimensionLaberinto];
        laberintoCaracteres = new char[dimensionLaberinto][dimensionLaberinto];
        dibujarLaberinto();
    }

    private void crearParedesIniciales() {
        paredes.add(new Pared(0, 0));
        paredes.add(new Pared(dimensionLaberinto - 1, 0));
        paredes.add(new Pared(0, dimensionLaberinto - 1));
        paredes.add(new Pared(dimensionLaberinto - 1, dimensionLaberinto - 1));
        for (int i = 0; i < dimensionLaberinto; i++) {
            for (int j = 0; j < dimensionLaberinto; j++) {
                if (!(i % 2 == 1 & j % 2 == 1)) { // Son centros de la celda
                    paredes.add(new Pared(i, j));
                }
            }
        }
    }

    public void crearNuevoLaberinto() {
        int contador = 1;
        celdasVisitadas[creador.getPosicionI()][creador.getPosicionJ()] = true;
        imprimirLaberinto();
        dibujarLaberinto();
        while (contador < this.numeroCeldas) {
            if (!vecinosVisitados(creador.getPosicionI(), creador.getPosicionJ(), dimensionCeldas, celdasVisitadas)) {
                moverAlAzarCreador(creador.getPosicionI(), creador.getPosicionJ());
                contador++;
            } else {
                creador.setPosicionI(recurI.pop());
                creador.setPosicionJ(recurJ.pop());
            }
            imprimirLaberinto();
            dibujarLaberinto();
        }
        jugador = new Jugador(1, 1);
        meta = new Meta(dimensionLaberinto - 2, dimensionLaberinto - 2);
        creador = null;
        dibujarLaberinto();
    }

    public boolean vecinosVisitados(int i, int j, int dimension, boolean visitados[][]) {
        int mov[] = {1, 1, 1, 1};
        if (i - 1 < 0) {
            mov[0] = 0;
        }
        if (j + 1 == dimension) {
            mov[1] = 0;
        }
        if (i + 1 == dimension) {
            mov[2] = 0;
        }
        if (j - 1 < 0) {
            mov[3] = 0;
        }
        return visitados[i - mov[0]][j]
                & visitados[i][j + mov[1]]
                & visitados[i + mov[2]][j]
                & visitados[i][j - mov[3]];
        // arriba derecha abajo izquierda
    }

    public Direccion moverAlAzarCreador(int i, int j) {
        boolean cambioCelda = false;
        Direccion paso = null;
        int nuevaPosicionI;
        int nuevaPosicionJ;
        int k;
        recurI.push(i);
        recurJ.push(j);
        while (!cambioCelda) {
            paso = obtenerDireccionAlAzar();
            nuevaPosicionI = i + paso.getCambioEnFila();
            nuevaPosicionJ = j + paso.getCambioEnColumna();
            if (!puedeMoverCreador(nuevaPosicionI, nuevaPosicionJ)) {
                continue;
            }
            if (celdasVisitadas[nuevaPosicionI][nuevaPosicionJ]) {
                continue;
            }
            k = -1;
            for (Pared pared : paredes) {
                if (pared.getPosicionI() == 2 * i + 1 + paso.getCambioEnFila()
                        && pared.getPosicionJ() == 2 * j + 1 + paso.getCambioEnColumna()) {
                    k = paredes.indexOf(pared);
                }
            }
            if (k != -1) {
                paredes.remove(k);
            }
            cambiarPosicionCreador(nuevaPosicionI, nuevaPosicionJ);
            cambioCelda = true;
        }
        return paso;
    }

    public Direccion obtenerDireccionAlAzar() {
        Direccion paso = null;
        int mov = rnd.nextInt(4);
        switch (mov) {
            case 0:
                paso = Direccion.ARRIBA;
                break;
            case 1:
                paso = Direccion.DERECHA;
                break;
            case 2:
                paso = Direccion.ABAJO;
                break;
            case 3:
                paso = Direccion.IZQUIERDA;
                break;
        }
        return paso;
    }

    private boolean puedeMoverCreador(int i, int j) {
        if (j < 0 || j == dimensionCeldas) {
            return false;
        }
        if (i < 0 || i == dimensionCeldas) {
            return false;
        }
        return true;
    }

    private void cambiarPosicionCreador(int nuevaPosicionI, int nuevaPosicionJ) {
        celdasVisitadas[nuevaPosicionI][nuevaPosicionJ] = true;
        creador.setPosicionI(nuevaPosicionI);
        creador.setPosicionJ(nuevaPosicionJ);
    }

    // Dibuja el laberinto en forma de matriz
    public void dibujarLaberinto() {
        for (int i = 0; i < dimensionLaberinto; i++) {
            for (int j = 0; j < dimensionLaberinto; j++) {
                laberintoDibujado[i][j] = "   ";
                laberintoCaracteres[i][j] = ' ';
            }
        }

        for (Pared pared : paredes) {
            laberintoDibujado[pared.getPosicionI()][pared.getPosicionJ()] = pared.toString();
            laberintoCaracteres[pared.getPosicionI()][pared.getPosicionJ()] = 'P';
        }

        if (creador != null) {
            laberintoDibujado[2 * creador.getPosicionI() + 1][2 * creador.getPosicionJ() + 1] = creador.toString();
            laberintoCaracteres[2 * creador.getPosicionI() + 1][2 * creador.getPosicionJ() + 1] = 'C';
        } else {
            laberintoDibujado[meta.getPosicionI()][meta.getPosicionJ()] = meta.toString();
            laberintoDibujado[jugador.getPosicionI()][jugador.getPosicionJ()] = jugador.toString();
            laberintoCaracteres[meta.getPosicionI()][meta.getPosicionJ()] = 'M';
            laberintoCaracteres[jugador.getPosicionI()][jugador.getPosicionJ()] = 'J';
        }
    }

    // Imprime el dibujo de laberinto con la posición actual del Creador de Laberintos
    public void imprimirLaberinto() {
        dibujarLaberinto();
        System.out.println("\n----------------------\n");
        for (int i = 0; i < dimensionLaberinto; i++) {
            for (int j = 0; j < dimensionLaberinto; j++) {
                System.out.print(laberintoDibujado[i][j]);
            }
            System.out.println("");
        }

    }

    // resolver
    public Stack<Direccion> imprimirSolucion() {
        // Marcar nodos como no visitados
        for (Pared pared : paredes) {
            espaciosVisitados[pared.getPosicionI()][pared.getPosicionJ()] = true;
        }

        recurI = new Stack<>();
        recurJ = new Stack<>();

        Stack<Direccion> rutaAlgoritmo = new Stack<>();
        Direccion direccion = null;

        imprimirLaberinto();
        dibujarLaberinto();

        espaciosVisitados[jugador.getPosicionI()][jugador.getPosicionJ()] = true;

        while (!(jugador.getPosicionI() == meta.getPosicionI() && jugador.getPosicionJ() == meta.getPosicionJ())) {
            if (!vecinosVisitados(jugador.getPosicionI(), jugador.getPosicionJ(), dimensionLaberinto, espaciosVisitados)) {
                direccion = moverAlAzarJugador(jugador.getPosicionI(), jugador.getPosicionJ());
                rutaAlgoritmo.add(direccion);
            } else {
                rutaAlgoritmo.add(obtenerDireccion(recurI.peek() - jugador.getPosicionI(), recurJ.peek() - jugador.getPosicionJ()));
                jugador.setPosicionI(recurI.pop());
                jugador.setPosicionJ(recurJ.pop());
            }
            //try {
            //Thread.sleep(1000);
            //TimeUnit.SECONDS.sleep(2);
            imprimirLaberinto();
            dibujarLaberinto();
            System.out.println(rutaAlgoritmo.lastElement());
            //rutaAlgoritmo.stream().forEach(S->System.out.print(S+" "));
            //} catch (InterruptedException e) {
            //    e.printStackTrace();
            //}
        }
        return rutaAlgoritmo;
    }

    private Direccion moverAlAzarJugador(int i, int j) {
        boolean cambioUbicacion = false;
        Direccion paso = null;
        int nuevaPosicionI;
        int nuevaPosicionJ;
        recurI.push(i);
        recurJ.push(j);
        while (!cambioUbicacion) {
            paso = obtenerDireccionAlAzar();
            nuevaPosicionI = i + paso.getCambioEnFila();
            nuevaPosicionJ = j + paso.getCambioEnColumna();
            if (espaciosVisitados[nuevaPosicionI][nuevaPosicionJ]) {
                continue;
            }
            cambiarPosicionJugador(nuevaPosicionI, nuevaPosicionJ);
            cambioUbicacion = true;
        }
        return paso;
    }

    private void cambiarPosicionJugador(int nuevaPosicionI, int nuevaPosicionJ) {
        espaciosVisitados[nuevaPosicionI][nuevaPosicionJ] = true;
        jugador.setPosicionI(nuevaPosicionI);
        jugador.setPosicionJ(nuevaPosicionJ);
    }

    private Direccion obtenerDireccion(int i, int j) {
        Direccion direccionContraria = null;
        if (i == -1 && j == 0) {
            direccionContraria = Direccion.ARRIBA;
        }
        if (i == 1 && j == 0) {
            direccionContraria = Direccion.ABAJO;
        }
        if (i == 0 && j == -1) {
            direccionContraria = Direccion.IZQUIERDA;
        }
        if (i == 0 && j == 1) {
            direccionContraria = Direccion.DERECHA;
        }
        return direccionContraria;
    }

    public void moverJugador(Direccion direccion) {
        int i = jugador.getPosicionI();
        int j = jugador.getPosicionJ();
        int nuevaPosicionI = i + direccion.getCambioEnFila();
        int nuevaPosicionJ = j + direccion.getCambioEnColumna();

        if (!esPosibleMoverse(nuevaPosicionI, nuevaPosicionJ)) {
            return;
        }
        cambiarPosicionJugador(nuevaPosicionI, nuevaPosicionJ);
        dibujarLaberinto();
    }

    private boolean esPosibleMoverse(int nuevaPosicionI, int nuevaPosicionJ) {
        for (Pared pared : paredes) {
            if (pared.getPosicionI() == nuevaPosicionI
                    && pared.getPosicionJ() == nuevaPosicionJ) {
                return false;
            }
        }
        return true;
    }

    public int getDimensionLaberinto() {
        return dimensionLaberinto;
    }

    public char[][] getLaberintoCaracteres() {
        return laberintoCaracteres;
    }

    public static void main(String[] args) {
        Laberinto laberinto = new Laberinto(5);
        laberinto.crearNuevoLaberinto();
        laberinto.imprimirLaberinto();
        laberinto.imprimirSolucion();
        char[][] mapa = laberinto.getLaberintoCaracteres();
        for (int i = 0; i<mapa.length; i++){
            for (int j = 0; j<mapa[0].length; j++){
                System.out.print(mapa[i][j]);
            }
            System.out.println("");
        }
    }

}

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
 * @author Nicolás B, Jhon M, César T
 */
public class Laberinto {

    private boolean[][] celdasVisitadas; //  Matriz de celdas intermedias visitas
    private int dimensionCeldas; // Dimensión de la matriz de celdas
    private int numeroCeldas;
    private Random rnd; // Objeto para número randomicos
    private String[][] laberintoDibujado; // Matriz de elementos del laberinto en String
    private char[][] laberintoCaracteres; // Matriz de elementos del laberinto en caracteres 
    private int dimensionLaberinto; // Dimensión de la matriz del laberinto
    private boolean[][] espaciosVisitados; // Matriz de espacios del laberinto visitados
    private ArrayList<Pared> paredes; // Conjunto de paredes
    private Jugador jugador;
    private Meta meta;
    private Creador creador;
    private Stack<Integer> recurI; // Auxiliar para backtracking
    private Stack<Integer> recurJ; // Auxiliar para backtracking

    // Constructor del Laberinto
    public Laberinto(int dimensionCeldas) {
        rnd = new Random();
        inicializar(dimensionCeldas);
    }

    /**
     * Inicializa todos los atributos del Laberinto con una nueva dimensión.
     * 
     * @param dimensionCeldas la dimensión de la matriz de celdas intermedias.
     */
    public void setDimension(int dimensionCeldas) {
        inicializar(dimensionCeldas);
    }

    /**
     * Inicializa todos los atributos del Laberinto.
     * 
     * @param dimensionCeldas la dimensión de la matriz de celdas intermedias.
     */
    public void inicializar(int dimensionCeldas) {
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

    /**
     * Crea el conjunto de paredes iniciales.
     */
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

    /**
     * Crea un nuevo laberinto.
     */
    public void crearNuevoLaberinto() {
        int contador = 1;
        celdasVisitadas[creador.getPosicionI()][creador.getPosicionJ()] = true;
        imprimirLaberinto();
        dibujarLaberinto();
        while (contador < this.numeroCeldas) {
            if (!vecinosVisitados(creador.getPosicionI(), creador.getPosicionJ(), celdasVisitadas)) {
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

    /**
     * Revisa se han visitado las posiciones en las direcciones: arriba, abajo, 
     * izquierda y dereha respecto a la posición ingresada.
     * 
     * @param i indice de la fila en la matriz de la posición ingresada.
     * @param j indice de la columna en la matriz de la posición ingresada.
     * @param visitados matriz en la que se va a utilizar el método .
     * @return false si almenos uno de los vecinos no esta visitado y true en el caso contrario.
     */
    public boolean vecinosVisitados(int i, int j, boolean visitados[][]) {
        int mov[] = {1, 1, 1, 1};
        if (i - 1 < 0) {
            mov[0] = 0;
        }
        if (j + 1 == visitados[0].length) {
            mov[1] = 0;
        }
        if (i + 1 == visitados.length) {
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

    /**
     * Mueve al azar al Creador a otra posición en la matriz de celdas.
     * 
     * @param i indice de la fila de la posición ingresada.
     * @param j indice de la columna de la posición ingresada.
     * @return la dirección en la que se movio al Creador de laberintos.
     */
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

    /**
     * Genera una dirección al azar
     * 
     * @return dirección al azar.
     */
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

    /**
     * Verifica que la posición ingresada no se encuentre fuera de la matriz de
     * celdas intermedias.
     * 
     * @param i indice de la fila de la posición ingresada.
     * @param j indice de la columna de la posición ingresada.
     * @return true si la posición se encuentra dentro de la matriz de celdas, false en el caso contrario.
     */
    private boolean puedeMoverCreador(int i, int j) {
        if (j < 0 || j == dimensionCeldas) {
            return false;
        }
        if (i < 0 || i == dimensionCeldas) {
            return false;
        }
        return true;
    }

    /**
     * Cambia la posición del Creador.
     * 
     * @param i indice de la fila de la posición ingresada.
     * @param j indice de la columna de la posición ingresada.
     */
    private void cambiarPosicionCreador(int nuevaPosicionI, int nuevaPosicionJ) {
        celdasVisitadas[nuevaPosicionI][nuevaPosicionJ] = true;
        creador.setPosicionI(nuevaPosicionI);
        creador.setPosicionJ(nuevaPosicionJ);
    }
    
    /**
     * Dibuja el laberinto en forma de matriz de Strings y caracteres.
     */
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

    /**
     * Imprime el dibujo de laberinto en Strings.
     */
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

    /**
     * Resuelve el laberinto
     * 
     * @return cola con las direcciones usadas por el algoritmo.
     */
    public Queue<Direccion> imprimirSolucion() {
        // Marcar las posiciones con paredes como visitados
        for (Pared pared : paredes) {
            espaciosVisitados[pared.getPosicionI()][pared.getPosicionJ()] = true;
        }

        recurI = new Stack<>();
        recurJ = new Stack<>();

        Queue<Direccion> rutaAlgoritmo = new LinkedList<>();
        Direccion direccion = null;

        imprimirLaberinto();
        dibujarLaberinto();

        espaciosVisitados[jugador.getPosicionI()][jugador.getPosicionJ()] = true;
        
        int poicionAuxiliarI =jugador.getPosicionI();
        int poicionAuxiliarJ =jugador.getPosicionJ();

        while (!(jugador.getPosicionI() == meta.getPosicionI() && jugador.getPosicionJ() == meta.getPosicionJ())) {
            if (!vecinosVisitados(jugador.getPosicionI(), jugador.getPosicionJ(), espaciosVisitados)) {
                direccion = moverAlAzarJugador(jugador.getPosicionI(), jugador.getPosicionJ());
                rutaAlgoritmo.add(direccion);
            } else {
                rutaAlgoritmo.add(obtenerDireccion(recurI.peek() - jugador.getPosicionI(), recurJ.peek() - jugador.getPosicionJ()));
                jugador.setPosicionI(recurI.pop());
                jugador.setPosicionJ(recurJ.pop());
            }
            //try {
            //TimeUnit.MILLISECONDS.sleep(100);
            imprimirLaberinto();
            dibujarLaberinto();
            //} catch (InterruptedException e) {
            //    e.printStackTrace();
            //}
        }
        //rutaAlgoritmo.stream().forEach(S->System.out.print(S+" "));
        cambiarPosicionJugador(poicionAuxiliarI, poicionAuxiliarJ);
        return rutaAlgoritmo;
    }

    /**
     * Mueve al azar al Jugador a otra posición en la matriz de espacios del 
     * laberinto.
     * 
     * @param i indice de la fila de la posición ingresada.
     * @param j indice de la columna de la posición ingresada.
     * @return la dirección en la que se movio al Jugador.
     */
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

    /**
     * Cambia la posición del Jugador.
     * 
     * @param i indice de la fila de la posición ingresada.
     * @param j indice de la columna de la posición ingresada.
     */
    private void cambiarPosicionJugador(int nuevaPosicionI, int nuevaPosicionJ) {
        espaciosVisitados[nuevaPosicionI][nuevaPosicionJ] = true;
        jugador.setPosicionI(nuevaPosicionI);
        jugador.setPosicionJ(nuevaPosicionJ);
    }

    /**
     * Convierte los enteros de los cambios en las filas y las columnas en una 
     * dirección.
     * 
     * @param i cambio en el indice de las filas.
     * @param j cambio en el indice de las columnas.
     * @return la dirección que indican los parametros.
     */
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

    /**
     * Mueve al jugador en la dirección indicada.
     * 
     * @param direccion movimiento del jugador.
     */
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

    /**
     * Verifica que no existan paredes en la posición ingresada
     * 
     * @param i indice de la fila de la posición ingresada.
     * @param j indice de la columna de la posición ingresada.
     * @return true si no hay paredes en la posición indicada, false en el caso contrario.
     */
    private boolean esPosibleMoverse(int nuevaPosicionI, int nuevaPosicionJ) {
        for (Pared pared : paredes) {
            if (pared.getPosicionI() == nuevaPosicionI
                    && pared.getPosicionJ() == nuevaPosicionJ) {
                return false;
            }
        }
        return true;
    }

    // Retorna la dimensión de la matriz del laberinto
    public int getDimensionLaberinto() {
        return dimensionLaberinto;
    }

    // Retorna la matriz del laberinto en caracteres
    public char[][] getLaberintoCaracteres() {
        return laberintoCaracteres;
    }
    
    //Retorna la posicion en I del Jugador
    public int getPosicionJugadorI(){
        return jugador.getPosicionI();
    }
    
    //Retorna la posicion en J del Jugador
    public int getPosicionJugadorJ(){
        return jugador.getPosicionJ();
    }
    
    //Retorna la posicion en I de la Meta
    public int getPosicionMetaI(){
        return meta.getPosicionI();
    }
    
    //Retorna la posicion en J de la Meta
    public int getPosicionMetaJ(){
        return meta.getPosicionJ();
    }

    public static void main(String[] args) {
        Laberinto laberinto = new Laberinto(5);
        laberinto.crearNuevoLaberinto();
        laberinto.imprimirLaberinto();
        laberinto.imprimirSolucion();
        /*
        System.out.println("");
        char[][] mapa = laberinto.getLaberintoCaracteres();
        for (int i = 0; i<mapa.length; i++){
            for (int j = 0; j<mapa[0].length; j++){
                System.out.print(mapa[i][j]);
            }
            System.out.println("");
        }
        */  
    }

}

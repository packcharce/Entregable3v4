package com.example.entregable3.cartitas_showcase;


import com.example.entregable3.R;

import java.io.Serializable;


/**
 * Clase Carta
 */

public class Carta implements Serializable {
    private static short id = 0;
    final int recursoImagen;
    private final double precioCarta = 50.0;
    private boolean bloqueada = true;

    public Carta(int recursoImagen) {
        this.recursoImagen = recursoImagen;
        id++;
    }

    public static short getId() {
        return id;
    }

    public boolean isBloqueada() {
        return bloqueada;
    }

    public void setBloqueada(boolean desbloquear) {
        bloqueada = desbloquear;
    }

    /**
     * Metodo que muestra la carta oculta
     * o desbloqueada respectivamente
     *
     * @return El recurso de la imagen oculta/descubierta
     */
    public int getRecursoImagen() {
        int res;
        if (isBloqueada())
            res = R.drawable.bloqueado;
        else
            res = recursoImagen;
        return res;
    }

    public double getPrecioCarta() {
        return precioCarta;
    }
}

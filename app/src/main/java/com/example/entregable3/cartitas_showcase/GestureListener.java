package com.example.entregable3.cartitas_showcase;

import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * Created by Carlex on 14/12/2017.
 *
 * Clase que captura los movimientos del usuario
 * en la pantalla
 *
 */

class GestureListener extends GestureDetector.SimpleOnGestureListener {

    private static final int SWIPE_MIN_DISTANCIA = 220;

    // Ancho max vertical para que se considere movimiento horizontal
    private static final int SWIPE_MAX_VERTICALIDAD = 250;
    private static final int SWIPE_MINIMA_VELOCIDAD = 150;

    // Override todos los metodos
    @Override
    public boolean onSingleTapUp(MotionEvent ev) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent ev) {

    }

    @Override
    public void onLongPress(MotionEvent ev) {

    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public boolean onDown(MotionEvent ev) {
        return false;
    }


    /**
     * Metodo que detecta swipe en pantalla para mover cartas
     *
     * @param e1        Punto inicial al picar en pantalla
     * @param e2        Punto final al levantar el dedo
     * @param velocityX Velocidad en eje horizontal
     * @param velocityY Velocidad en eje vertical
     * @return True si se ha hecho el movimiento
     */
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_VERTICALIDAD) {
            return false;
        }

        /* positivo es de dcha a izq */
        final float distancia = e1.getX() - e2.getX();
        final boolean enoughSpeed = Math.abs(velocityX) > SWIPE_MINIMA_VELOCIDAD;
        if (distancia > SWIPE_MIN_DISTANCIA && enoughSpeed) {

            // dcha a izq
            DetalleCarta.direccionSwipe = true;
        } else if (distancia < -SWIPE_MIN_DISTANCIA && enoughSpeed) {

            // izq a dcha
            DetalleCarta.direccionSwipe = false;
        }
        return true;
    }
}
package com.example.entregable3.cartitas_showcase;

import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * Created by Carlex on 14/12/2017.
 */

class GestureListener extends GestureDetector.SimpleOnGestureListener {
    private static final int SWIPE_MIN_DISTANCE = 220;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 150;

    // Override s all the callback methods of GestureDetector.SimpleOnGestureListener
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

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH) {
            return false;
        }

        /* positive value means right to left direction */
        final float distance = e1.getX() - e2.getX();
        final boolean enoughSpeed = Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY;
        if (distance > SWIPE_MIN_DISTANCE && enoughSpeed) {
            // right to left swipe
            DetalleCarta.direccionSwipe = true;
        } else if (distance < -SWIPE_MIN_DISTANCE && enoughSpeed) {
            // left to right swipe
            DetalleCarta.direccionSwipe = false;
        }
        return true;
    }
}
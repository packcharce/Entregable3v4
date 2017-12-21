package com.example.entregable3.cartitas_showcase;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.entregable3.Jugador;
import com.example.entregable3.R;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

/**
 * Clase que muestra una carta seleccionada de la lista
 * y muestra las desbloqueadas.
 */
public class DetalleCarta extends AppCompatActivity {
    static String ARG_ID;
    static boolean direccionSwipe;
    Carta c;
    int aux;
    ImageView img;
    GestureDetector mGestureDetector;
    Jugador aux2;
    GifImageView gi;
    GifDrawable gifDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_carta);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        img = findViewById(R.id.imagenDetalle);
        Button btn = findViewById(R.id.botonDetalle);
        Button btn2 = findViewById(R.id.botonDetalleRetroceder);
        gi = findViewById(R.id.gif);

        Intent i = getIntent();
        if (i != null) {
            aux = i.getIntExtra(ARG_ID, 0);
            aux2 = i.getParcelableExtra(getResources().getString(R.string.menu_objToCartas));
            c = aux2.getListaCarta().get(aux);
            img.setImageResource(c.getRecursoImagen());
        }


        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickImagen();
            }
        });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                avanzaImagen();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retrocedeImg();
            }
        });

        mGestureDetector = new GestureDetector(this, new GestureListener());
    }


    /**
     * Metodo magico que descubre la carta
     * bloqueada y, si es la polla esa imagen,
     * pone soniditos pa las delicias del oyente
     */
    private void clickImagen() {
        if (c.isBloqueada() && (int) aux2.mostrarmonedas() >= 10) {
            c.setBloqueada(false);
            img.setImageResource(c.getRecursoImagen());
            aux2.restaMonedas();
            guardaDatos();

            if (c.getRecursoImagen() == R.drawable.casquetvolador) {
                try {
                    gifDrawable = new GifDrawable(getResources(), R.drawable.doraemon);
                    int duracion = gifDrawable.getDuration();
                    CountDownTimer ct = new CountDownTimer(duracion, 100) {
                        @Override
                        public void onTick(long l) {
                        }

                        @Override
                        public void onFinish() {
                            gifDrawable.stop();
                            gi.setImageDrawable(null);
                            gifDrawable.recycle();
                        }
                    };

                    gi.setImageDrawable(gifDrawable);
                    gifDrawable.start();
                    ct.start();
                    MediaPlayer mPlayer = MediaPlayer.create(DetalleCarta.this, R.raw.casquet_volador);
                    mPlayer.start();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
                img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (c.isBloqueada() && (int) aux2.mostrarmonedas() >= 10) {
                            c.setBloqueada(false);
                            img.setImageResource(c.getRecursoImagen());
                            aux2.restaMonedas();
                            guardaDatos();
                        } else {
                            escribeMsg(getResources().getString(R.string.detalles_error_yaComprada));

                        }
                    }
                });
            }
        } else {
            escribeMsg(getResources().getString(R.string.detalles_error_yaComprada));
        }
    }

    /**
     * Metodo que recibe del GestureListener si
     * el usuario ha hecho swipe en la pantalla
     * y muestra la siguiente o la anterior
     * en consecuencia
     *
     * @param event Evento del usuario: swipe derecha o izquierda
     * @return true si lo ha hecho, false si no ha hecho nada
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean eventConsumed = mGestureDetector.onTouchEvent(event);
        if (eventConsumed) {
            if (direccionSwipe) {
                if (aux < aux2.getListaCarta().size())
                    avanzaImagen();
            } else {
                if (aux > 0)
                    retrocedeImg();
            }
        } else {
            return false;

        }
        return true;
    }

    /**
     * Metodo magico que muestra la imagen
     * desbloqueada anterior
     */
    private void retrocedeImg() {
        if (aux > 0)
            try {
                do {
                    c = aux2.getListaCarta().get(--aux);
                    if (!c.isBloqueada())
                        img.setImageResource(c.getRecursoImagen());
                } while (c.isBloqueada() && aux > 0);

            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
    }

    /**
     * Metodo magico que muestra la imagen
     * desbloqueada siguiente
     */
    private void avanzaImagen() {
        if (aux < aux2.getListaCarta().size() - 1)
            try {
                do {
                    c = aux2.getListaCarta().get(++aux);
                    if (!c.isBloqueada())
                        img.setImageResource(c.getRecursoImagen());
                } while (c.isBloqueada() && aux < aux2.getListaCarta().size());
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
    }


    /**
     * Metodo que guarda las cartas
     * desbloqueadas en memoria
     */
    private void guardaDatos() {
        FileOutputStream fos;
        ObjectOutputStream out;

        try {
            //Guardamos cada objeto de la clase Jugador en un archivo en memoria que lleve por nombre el nombre del jugador
            fos = openFileOutput(aux2.mostrarnombre(), Context.MODE_PRIVATE);
            out = new ObjectOutputStream(fos);
            out.writeObject(aux2);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo que captura la pulsacion
     * del boton back del dispositivo
     * y cierra la activity sin que pete
     */
    @Override
    public void onBackPressed() {
        guardaDatos();
        NavUtils.navigateUpFromSameTask(this);
        finish();
    }

    /**
     * Metodo para ahorrarme el latazo
     * de escribir toasts
     *
     * @param msg el mensaje a imprimir en toast
     */
    private void escribeMsg(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public void retroceder(View view) {
        NavUtils.navigateUpFromSameTask(this);
        finish();
    }
}

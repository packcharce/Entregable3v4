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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.entregable3.Jugador;
import com.example.entregable3.R;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

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
                if (c.isBloqueada() && aux2.mostrarmonedas() >= 10) {
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
                                if (c.isBloqueada() && aux2.mostrarmonedas() >= 10) {
                                    c.setBloqueada(false);
                                    img.setImageResource(c.getRecursoImagen());
                                    aux2.restaMonedas();
                                    guardaDatos();
                                } else {
                                    Toast.makeText(getApplicationContext(), "No mony fo bitches", Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "No mony fo bitches", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        });

        mGestureDetector = new GestureDetector(this, new GestureListener());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean eventConsumed = mGestureDetector.onTouchEvent(event);
        if (eventConsumed) {
            if (direccionSwipe) {
                if (aux < aux2.getListaCarta().size())
                    try {
                        do {
                            c = aux2.getListaCarta().get(++aux);
                            if (!c.isBloqueada())
                                img.setImageResource(c.getRecursoImagen());
                        } while (c.isBloqueada() && aux < aux2.getListaCarta().size());
                    } catch (IndexOutOfBoundsException e) {
                        e.printStackTrace();
                    }
            } else {
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
        } else {
            return false;

        }
        return true;
    }

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

    @Override
    public void onBackPressed() {
        guardaDatos();
        NavUtils.navigateUpFromSameTask(this);
        finish();
    }
}

package com.example.entregable3;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Random;

import static java.lang.Integer.parseInt;

public class Jugar extends AppCompatActivity {


    private static final Random RANDOM = new Random();
    String usuario_activo;
    int NumeroAleatorio, NumeroAleatorio2;
    int CantApost;
    CountDownTimer cuneta;
    GraphView graph;
    private Jugador aux;
    private TextView tv2, tv3;
    private Button apostar, bcrash;
    private EditText cantidadApostada;
    private boolean funcion, progres;
    private LineGraphSeries<DataPoint> series;

    private double lastX = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_jugar);

        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // we get graph view instance

        cargaDatos();

        tv2 = findViewById(R.id.textView);
        tv3 = findViewById(R.id.textView2);

        apostar = findViewById(R.id.apostar);
        bcrash = findViewById(R.id.buttoncrash);
        cantidadApostada = findViewById(R.id.cantidadApostada);


        monedasQueTienes();
    }


    public void monedasQueTienes() {
        double mon;
        mon = aux.mostrarmonedas() * 100;
        mon = (double) Math.round(mon);
        mon = mon / 100;
        tv2.setText("Monedas: " + mon);
    }

    public void Apostar(View v) {


        CantApost = parseInt(cantidadApostada.getText().toString());
        if (aux.mostrarmonedas() != 0 && ((aux.mostrarmonedas() - CantApost) >= 0)) {
            aux.add_coin(-CantApost);
            apostar.setVisibility(View.INVISIBLE);
            jugara();
        }
        tv2.setText("Monedas: " + aux.mostrarmonedas());
        monedasQueTienes();


    }

    public void win(double multiplicador, Integer monedasapostdas) {

        aux.add_coin(monedasapostdas * multiplicador);

    }

    public void jugara() {

        if (progres) {

            graph.removeAllSeries();
            lastX = 0;
            graph.getViewport().setBackgroundColor(Color.WHITE);
            series.setColor(Color.BLUE);
            series.setBackgroundColor(Color.parseColor("#d9e1f9"));

        }


        graph = findViewById(R.id.graph);
        // data
        series = new LineGraphSeries<>();
        series.setColor(Color.rgb(52, 152, 219));
        graph.addSeries(series);
        // customize a little bit viewport
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(0);
        series.setDrawBackground(true);
        series.setBackgroundColor(Color.rgb(214, 234, 248));

        graph.getViewport().setMinX(0);


        // Use the most significant 52-bit from the hash to calculate the crash point


        int Cero = (int) (Math.random() * 33);
        int MenosDeUno = (int) (Math.random() * 100);
        int mult1, mult2;

        if (Cero <= 1) {
            mult1 = 0;
        } else {
            mult1 = 1000;
        }
        if (MenosDeUno <= 30) {
            mult2 = 50;
        } else if (MenosDeUno < 60 && MenosDeUno > 30) {
            mult2 = 100;
        } else {
            mult2 = 500;
        }


        funcion = true;
        NumeroAleatorio = (int) (Math.random() * mult1);
        NumeroAleatorio2 = (int) (Math.random() * mult2);
        final Integer mult = NumeroAleatorio2 * NumeroAleatorio;
        cuneta = new CountDownTimer(mult, 200) {
            double cambiante = 1.001;
            double base = 1.01;
            double x = 1.1;
            int veces = 0;

            public void onTick(long millisUntilFinished) {
                veces++;
                if (veces == ((mult - 1))) {
                    series.setColor(Color.rgb(231, 76, 60));
                }


                cambiante = cambiante * base;
                cambiante = cambiante * 100;
                cambiante = (double) Math.round(cambiante);
                x = x + x;

                if (cambiante % 10 == 0) {
                    cambiante = cambiante / 100;
                    tv3.setText(String.valueOf(cambiante) + "0");
                } else {
                    cambiante = cambiante / 100;
                    tv3.setText(String.valueOf(cambiante));
                }
                graph.getViewport().setMaxY(cambiante + 3);
                graph.getViewport().setMaxX(lastX + 3);
                series.appendData(new DataPoint(lastX++, cambiante), false, 1000);

                progres = false;


                if (millisUntilFinished < 500) {
                    //calcula cuando va a terminar la grafica mas o menos y la cambia de color
                    graph.getViewport().setBackgroundColor(Color.parseColor("#33FF0000"));
                    series.setColor(Color.RED);
                    series.setBackgroundColor(Color.parseColor("#33FF0000"));
                    tv3.setTextSize(20);
                    millisUntilFinished = 0;
                }


            }

            public void onFinish() {
                series.setColor(Color.rgb(231, 76, 60));
                tv3.setText(String.valueOf(cambiante));
                funcion = false;
                apostar.setVisibility(View.VISIBLE);
                bcrash.setVisibility(View.VISIBLE);
                progres = true;
                veces = 0;
            }
        }.start();

    }


    public void crash(View v) {
        double multiplicador = Double.parseDouble(tv3.getText().toString());
        bcrash.setVisibility(View.INVISIBLE);

        if (funcion) {
            win(multiplicador, CantApost);

        }
        monedasQueTienes();

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
            fos = openFileOutput(aux.mostrarnombre(), Context.MODE_PRIVATE);
            out = new ObjectOutputStream(fos);
            out.writeObject(aux);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Cargar los datos del jugador, para representar el recyclerview
     * respecto a la lista personal de cada jugador
     */
    private void cargaDatos() {
        SharedPreferences preferencias = getSharedPreferences(getResources().getString(R.string.clave_nombre_user_shared), Context.MODE_PRIVATE);
        String nombre = preferencias.getString(getResources().getString(R.string.clave_active_user_shared), null);
        FileInputStream fis;
        ObjectInputStream in;
        try {
            fis = openFileInput(nombre);
            in = new ObjectInputStream(fis);
            aux = (Jugador) in.readObject();
            in.close();
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

}
package com.example.entregable3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class DadosGame extends AppCompatActivity {
    private Button bmayor;
    private Button bigual;
    private Button bmenor;
    private TextView cpudado;
    private TextView mydado;
    private Button turnomio;
    private TextView frase;
    private TextView monedas;
    private Jugador aux;
    //private Jugador aux2;
    //String usuario_activo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dados_game);
        bmayor = findViewById(R.id.mayor);
        bigual = findViewById(R.id.igual);
        bmenor = findViewById(R.id.menor);
        cpudado = findViewById(R.id.dadoCPU);
        mydado = findViewById(R.id.miDado);
        turnomio = findViewById(R.id.miturno);
        frase = findViewById(R.id.frase);
        monedas = findViewById(R.id.monedas);

        getSupportActionBar().hide(); // Oculta Titulo de la ventana
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); // Oculta barra de notificaciones

        cargaDatos();
        monedas.setText(String.valueOf(aux.mostrarmonedas()));

    }

    public Dialog creaAlerta(String titulo, String mensaje) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(mensaje).setTitle(titulo);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // No se
            }
        });
        return builder.create();
    }

    public void higher(View V) {

        bmayor.setVisibility(View.INVISIBLE);
        bigual.setVisibility(View.INVISIBLE);
        bmenor.setVisibility(View.INVISIBLE);
        frase.setVisibility(View.INVISIBLE);

        final int miResultado = dadosRand();
        final int resultadoCPU = dadosRand();


        cpudado.setText(Integer.toString(resultadoCPU));
        cpudado.setVisibility(View.VISIBLE);
        turnomio.setVisibility(View.VISIBLE);

        turnomio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int miNumero = miResultado;
                int cpuNumero = resultadoCPU;
                int premio;
                if (miNumero > cpuNumero) {
                    premio = (miNumero - cpuNumero) * 10;
                    creaAlerta("Fin de la partida", "Has ganado" + String.valueOf(premio) + " monedas").show();
                    mydado.setText(Integer.toString(miNumero));
                    mydado.setVisibility(View.VISIBLE);
                    aux.add_coin(premio);

                } else if (miNumero == cpuNumero) {
                    premio = 10;
                    creaAlerta("Fin de la partida", "Has perdido " + String.valueOf(premio) + "monedas").show();
                    mydado.setText(Integer.toString(miNumero));
                    mydado.setVisibility(View.VISIBLE);
                    aux.add_coin(-premio);
                } else {

                    premio = (cpuNumero - miNumero) * 10;
                    creaAlerta("Fin de la partida", "Has perdido " + String.valueOf(premio) + " monedas").show();
                    mydado.setText(Integer.toString(miNumero));
                    mydado.setVisibility(View.VISIBLE);
                    aux.add_coin(-premio);
                }
                turnomio.setVisibility(View.INVISIBLE);
                monedas.setText(String.valueOf(aux.mostrarmonedas()));
                guardaDatos();
            }
        });

    }


    public void same(View V) {

        bmayor.setVisibility(View.INVISIBLE);
        bigual.setVisibility(View.INVISIBLE);
        bmenor.setVisibility(View.INVISIBLE);
        frase.setVisibility(View.INVISIBLE);

        final int miResultado = dadosRand();
        final int resultadoCPU = dadosRand();

        cpudado.setText(Integer.toString(resultadoCPU));
        cpudado.setVisibility(View.VISIBLE);
        turnomio.setVisibility(View.VISIBLE);

        turnomio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int miNumero = miResultado;
                int cpuNumero = resultadoCPU;
                int premio;
                if (miNumero == cpuNumero) {
                    premio = 100;
                    creaAlerta("Fin de la partida", "Has ganado " + String.valueOf(premio) + "monendas").show();
                    mydado.setText(Integer.toString(miNumero));
                    mydado.setVisibility(View.VISIBLE);
                    aux.add_coin(premio);
                } else if (miNumero > cpuNumero) {
                    premio = (miNumero - cpuNumero) * 10;
                    creaAlerta("Fin de la partida", "Has perdido" + String.valueOf(premio) + " monedas").show();
                    mydado.setText(Integer.toString(miNumero));
                    mydado.setVisibility(View.VISIBLE);
                    aux.add_coin(-premio);
                } else {
                    premio = (cpuNumero - miNumero) * 10;
                    creaAlerta("Fin de la partida", "Has perdido" + String.valueOf(premio) + " monedas").show();
                    mydado.setText(Integer.toString(miNumero));
                    mydado.setVisibility(View.VISIBLE);
                    aux.add_coin(-premio);
                }
                turnomio.setVisibility(View.INVISIBLE);
                monedas.setText(String.valueOf(aux.mostrarmonedas()));
                guardaDatos();
            }
        });
    }

    public void less(View V) {

        bmayor.setVisibility(View.INVISIBLE);
        bigual.setVisibility(View.INVISIBLE);
        bmenor.setVisibility(View.INVISIBLE);
        frase.setVisibility(View.INVISIBLE);

        final int miResultado = dadosRand();
        final int resultadoCPU = dadosRand();

        cpudado.setText(Integer.toString(resultadoCPU));
        cpudado.setVisibility(View.VISIBLE);
        turnomio.setVisibility(View.VISIBLE);

        turnomio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int miNumero = miResultado;
                int cpuNumero = resultadoCPU;
                if (miNumero < cpuNumero) {
                    int premio = (cpuNumero - miNumero) * 10;
                    creaAlerta("Fin de la partida", "Has ganado" + String.valueOf(premio) + " monedas").show();
                    mydado.setText(Integer.toString(miNumero));
                    mydado.setVisibility(View.VISIBLE);
                    aux.add_coin(premio);
                } else if (miNumero == cpuNumero) {
                    int premio = 10;
                    creaAlerta("Fin de la partida", "Has perdido " + String.valueOf(premio) + "monedas").show();
                    mydado.setText(Integer.toString(miNumero));
                    mydado.setVisibility(View.VISIBLE);
                    aux.add_coin(-premio);
                } else {
                    int premio = (miNumero - cpuNumero) * 10;
                    creaAlerta("Fin de la partida", "Has perdido " + String.valueOf(premio) + "monedas").show();
                    mydado.setText(Integer.toString(miNumero));
                    mydado.setVisibility(View.VISIBLE);
                    aux.add_coin(-premio);
                }
                turnomio.setVisibility(View.INVISIBLE);
                monedas.setText(String.valueOf(aux.mostrarmonedas()));
                guardaDatos();
            }
        });
    }

    private int dadosRand() {
        int dado;
        dado = (int) (Math.random() * 6 + 1);
        return dado;
    }

    public void repetir(View v) {
        Intent jugar2 = new Intent(this, DadosGame.class);
        startActivity(jugar2);
    }

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
    /*public void monedasQueTienes(int i){

        int mon;

        if (usuario_activo != "Invitado"){ //Si tenemos un jugador activo cargamos de la memoria el objeto que se corresponde a ese usuario
            mon= aux.mostrarmonedas();
            monedas.setText(+mon);
        }

        else{
            monedas.setText("Monedas: 5");
        }

    }*/

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

    public void retroceder(View view) {
        NavUtils.navigateUpFromSameTask(this);
        finish();
    }
}

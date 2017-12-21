package com.example.entregable3;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.entregable3.cartitas_showcase.Cartas_Principal;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.makeText;

/**
 * Clase principal que muestra el usuario logueado y el menu
 */
public class MainActivity extends AppCompatActivity {

    String usuario_activo;
    Jugador aux;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tv1 = findViewById(R.id.tv1);
        TextView tv2 = findViewById(R.id.tv2);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        SharedPreferences prefe = getSharedPreferences(getResources().getString(R.string.clave_nombre_user_shared), Context.MODE_PRIVATE); //Usamos el sharedpreferences para saber que usuario esta activo
        usuario_activo = prefe.getString(getResources().getString(R.string.clave_active_user_shared), getResources().getString(R.string.default_user));

        Resources res = getResources();
        String auxText = String.format(res.getString(R.string.menu_user_logeado), usuario_activo);
        tv1.setText(auxText);

        if (!usuario_activo.equals(getResources().getString(R.string.default_user))) { //Si tenemos un jugador activo cargamos de la memoria el objeto que se corresponde a ese usuario

            try {

                FileInputStream fis = openFileInput(usuario_activo);
                ObjectInputStream in = new ObjectInputStream(fis);
                aux = (Jugador) in.readObject();
                in.close();
                auxText = String.format(res.getString(R.string.menu_monedas), (int) aux.mostrarmonedas());
                tv2.setText(auxText);


            } catch (Exception e) {
                e.printStackTrace();

            }

        } else {
            double monedasDefault = 5.0;
            auxText = String.format(res.getString(R.string.menu_monedas), (int) monedasDefault);
            tv2.setText(auxText);
        }
    }


    public void Usuarios(View view) {

        Intent intent = new Intent(this, Usuarios.class);
        startActivity(intent);
    }

    public void Jugar(View view) {
        if (!usuario_activo.equals(getResources().getString(R.string.default_user))) {
            Intent intent = new Intent(this, Jugar.class);
            startActivity(intent);
        } else
            makeText(getApplicationContext(), getResources().getString(R.string.alert_no_logeado), LENGTH_LONG).show();
    }

    public void Cartas(View view) {
        if (!usuario_activo.equals(getResources().getString(R.string.default_user))) {
            Intent intent = new Intent(this, Cartas_Principal.class);
            intent.putExtra(getResources().getString(R.string.menu_objToCartas), (Parcelable) aux);
            startActivity(intent);
        } else
            makeText(getApplicationContext(), getResources().getString(R.string.alert_no_logeado), LENGTH_LONG).show();
    }

    public void Salir(View view) {

        //Salimos y borramos el sharedpreferences del usuario activo, al iniciar la app otra vez volvera a salir por defecto invitado
        SharedPreferences preferencias = getSharedPreferences(getResources().getString(R.string.clave_nombre_user_shared), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();
        editor.clear();
        editor.apply();
        ventanaSalir();

    }

    /**
     * Ventanita de confirmacion para salir
     */
    private void ventanaSalir() {
        Resources res = getResources();
        AlertDialog ventana;
        ventana = new AlertDialog.Builder(this).create();
        ventana.setTitle(res.getString(R.string.menu_alert_titulo));
        ventana.setMessage(res.getString(R.string.menu_alert_msg));
        final MediaPlayer sonidoSalir = MediaPlayer.create(MainActivity.this, R.raw.queno);

        ventana.setButton(DialogInterface.BUTTON_NEGATIVE, res.getString(R.string.menu_alert_opcNo), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                sonidoSalir.start();
                sonidoSalir.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        finish();

                    }
                });

            }
        });
        ventana.setButton(DialogInterface.BUTTON_POSITIVE, res.getString(R.string.menu_alert_opcSi), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MainActivity.this.finish();
            }
        });
        ventana.show();
    }
}

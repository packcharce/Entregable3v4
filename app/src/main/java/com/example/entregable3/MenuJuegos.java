package com.example.entregable3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class MenuJuegos extends AppCompatActivity {

    String usuario_activo;
    Jugador aux;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_juegos);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        TextView tv1 = findViewById(R.id.textView5);
        TextView tv2 = findViewById(R.id.textView6);

        SharedPreferences prefe = getSharedPreferences(getResources().getString(R.string.clave_nombre_user_shared), Context.MODE_PRIVATE); //Usamos el sharedpreferences para saber que usuario esta activo
        usuario_activo = prefe.getString(getResources().getString(R.string.clave_active_user_shared), getResources().getString(R.string.default_user));

        Resources res = getResources();
        String auxText = String.format(res.getString(R.string.menu_user_logeado), usuario_activo);
        tv1.setText(auxText);

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


    }

    public void dados(View view) {
        Intent intent = new Intent(this, DadosGame.class);
        startActivity(intent);
    }

    public void grafica(View view) {
        Intent intent = new Intent(this, Jugar.class);
        startActivity(intent);
    }

    public void retroceder(View view) {
        NavUtils.navigateUpFromSameTask(this);
        finish();
    }

    /**
     * Metodo que captura la pulsacion
     * del boton back del dispositivo
     * y cierra la activity sin que pete
     */
    @Override
    public void onBackPressed() {
        NavUtils.navigateUpFromSameTask(this);
        finish();
    }
}

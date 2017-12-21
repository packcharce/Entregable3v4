package com.example.entregable3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Usuarios extends AppCompatActivity {

    private EditText et1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_usuarios);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        et1 = findViewById(R.id.input_usuario);
    }


    public void Guardar(View view) {

        String nombre = et1.getText().toString();
        File file = getFileStreamPath(nombre); //Esta funcion se usa para comprobar si existe ya un archivo creado en memoria

        if (!nombre.isEmpty())
            if (!file.exists()) {

                Jugador player = new Jugador(this, nombre);

                FileOutputStream fos;
                ObjectOutputStream out;

                try {

                    fos = openFileOutput(nombre, Context.MODE_PRIVATE); //Guardamos cada objeto de la clase Jugador en un archivo en memoria que lleve por nombre el nombre del jugador
                    out = new ObjectOutputStream(fos);
                    out.writeObject(player);
                    out.close();
                    escribeMsg(String.format(getResources().getString(R.string.usuarios_nuevo), nombre));
                    SharedPreferences preferencias = getSharedPreferences(getResources().getString(R.string.clave_nombre_user_shared), Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferencias.edit();
                    editor.putString(getResources().getString(R.string.clave_active_user_shared), player.mostrarnombre());
                    editor.apply();
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    finishAffinity(); //Esto se usa para cerrar esta activity y todos sus padres, asi no nos quedan actividades colgadas por ahí

                } catch (Exception e) {

                    escribeMsg(getResources().getString(R.string.usuarios_nombre_invalido));
                    e.printStackTrace();
                }
            } else {
                escribeMsg(getResources().getString(R.string.usuarios_nombre_repe));
            }

        else
            escribeMsg(getResources().getString(R.string.usuarios_error_text_vacio));

    }

    public void Cargar(View view) {

        Jugador aux;

        String nombre = et1.getText().toString();
        File file = getFileStreamPath(nombre);
        Toast toast1 = Toast.makeText(getApplicationContext(), getResources().getString(R.string.usuarios_no_existe), Toast.LENGTH_LONG);

        if (!nombre.isEmpty())
            if (file.exists()) {
                FileInputStream fis;
                ObjectInputStream in;

                try {
                    fis = openFileInput(nombre);
                    in = new ObjectInputStream(fis);
                    aux = (Jugador) in.readObject();
                    in.close();

                    SharedPreferences preferencias = getSharedPreferences(getResources().getString(R.string.clave_nombre_user_shared), Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferencias.edit();
                    editor.putString(getResources().getString(R.string.clave_active_user_shared), aux.mostrarnombre());
                    editor.apply();

                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    finishAffinity();

                } catch (Exception e) {
                    toast1.show();
                    e.printStackTrace();

                }
            } else {
                toast1.show();

            }
        else
            escribeMsg(getResources().getString(R.string.usuarios_error_text_vacio));

    }

    public void Borrar(View view) {

        String nombre = et1.getText().toString();
        File file = getFileStreamPath(nombre);

        if (!nombre.isEmpty())
            if (file.exists()) {
                if (file.delete())
                    escribeMsg(String.format(getResources().getString(R.string.usuarios_borrado), nombre));
                SharedPreferences preferencias = getSharedPreferences(getResources().getString(R.string.clave_nombre_user_shared), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferencias.edit();
                editor.putString(getResources().getString(R.string.clave_active_user_shared), getResources().getString(R.string.default_user));
                editor.apply();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finishAffinity();
            } else {
                escribeMsg(getResources().getString(R.string.usuarios_no_existe));
            }
        else
            escribeMsg(getResources().getString(R.string.usuarios_error_text_vacio));

    }

    public void cerrarSesion(View view) {

        String nombre = et1.getText().toString();
        File file = getFileStreamPath(nombre);

        if (!nombre.isEmpty())
            if (file.exists()) {
                SharedPreferences preferencias = getSharedPreferences(getResources().getString(R.string.clave_nombre_user_shared), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferencias.edit();
                editor.putString(getResources().getString(R.string.clave_active_user_shared), getResources().getString(R.string.default_user));
                editor.apply();
                escribeMsg(getResources().getString(R.string.usuarios_cerrar_mensaje));
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finishAffinity();
            } else {
                escribeMsg(getResources().getString(R.string.usuarios_no_existe));
            }
        else
            escribeMsg(getResources().getString(R.string.usuarios_error_text_vacio));

    }

    private void escribeMsg(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public void retroceder(View view) {
        NavUtils.navigateUpFromSameTask(this);
        finish();
    }
}

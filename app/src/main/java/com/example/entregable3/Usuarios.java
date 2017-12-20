package com.example.entregable3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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
                    Toast.makeText(getApplicationContext(), String.format(getResources().getString(R.string.usuarios_nuevo), nombre), Toast.LENGTH_LONG).show();
                    SharedPreferences preferencias = getSharedPreferences(getResources().getString(R.string.clave_nombre_user_shared), Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferencias.edit();
                    editor.putString(getResources().getString(R.string.clave_active_user_shared), player.mostrarnombre());
                    editor.apply();
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    finishAffinity(); //Esto se usa para cerrar esta activity y todos sus padres, asi no nos quedan actividades colgadas por ahí

                } catch (Exception e) {

                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.usuarios_nombre_invalido), Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.usuarios_nombre_repe), Toast.LENGTH_LONG).show();
            }

        else
            Toast.makeText(this, getResources().getString(R.string.usuarios_error_text_vacio), Toast.LENGTH_SHORT).show();

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
            Toast.makeText(this, getResources().getString(R.string.usuarios_error_text_vacio), Toast.LENGTH_SHORT).show();

    }

    public void Borrar(View view) {

        String nombre = et1.getText().toString();
        File file = getFileStreamPath(nombre);

        if (!nombre.isEmpty())
            if (file.exists()) {
                if (file.delete())
                    Toast.makeText(getApplicationContext(), String.format(getResources().getString(R.string.usuarios_borrado), nombre), Toast.LENGTH_LONG).show();
                SharedPreferences preferencias = getSharedPreferences(getResources().getString(R.string.clave_nombre_user_shared), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferencias.edit();
                editor.putString(getResources().getString(R.string.clave_active_user_shared), getResources().getString(R.string.default_user));
                editor.apply();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finishAffinity();
            } else {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.usuarios_no_existe), Toast.LENGTH_LONG).show();
            }
        else
            Toast.makeText(this, getResources().getString(R.string.usuarios_error_text_vacio), Toast.LENGTH_SHORT).show();

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
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finishAffinity();
            } else {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.usuarios_no_existe), Toast.LENGTH_LONG).show();
            }
        else
            Toast.makeText(this, getResources().getString(R.string.usuarios_error_text_vacio), Toast.LENGTH_SHORT).show();

    }
}

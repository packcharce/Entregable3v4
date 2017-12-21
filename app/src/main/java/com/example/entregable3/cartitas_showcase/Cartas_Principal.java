package com.example.entregable3.cartitas_showcase;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.entregable3.Jugador;
import com.example.entregable3.R;

import java.io.FileInputStream;
import java.io.ObjectInputStream;


public class Cartas_Principal extends AppCompatActivity {

    TextView mostrMon;
    AdaptadorCartas adc;
    private RecyclerView rv;
    private Jugador aux;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cartas_principal);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        cargaDatos();

        mostrMon = findViewById(R.id.mostradorMonedas);

        mostrMon.setText(String.format(getResources().getQuantityString(R.plurals.expositor_mostrador_monedas, aux.mostrarmonedas()), aux.mostrarmonedas()));
        adc = new AdaptadorCartas(this, aux.getListaCarta());
        rv = findViewById(R.id.recyclerView);
        rv.setAdapter(adc);
        RecyclerView.LayoutManager lm = new GridLayoutManager(this, 2);
        rv.setLayoutManager(lm);

        /**
         * Aqui se llama a los detalles de cada carta
         */
        adc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), DetalleCarta.class);
                i.putExtra(DetalleCarta.ARG_ID, rv.getChildAdapterPosition(view));
                i.putExtra(getResources().getString(R.string.menu_objToCartas), (Parcelable) aux);
                startActivity(i);
            }
        });
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
        NavUtils.navigateUpFromSameTask(this);
        finish();
    }

    public void retroceder(View view) {
        NavUtils.navigateUpFromSameTask(this);
        finish();
    }
}

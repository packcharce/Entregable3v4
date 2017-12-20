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

        //Intent i= getIntent();
        //aux = i.getParcelableExtra(getResources().getString(R.string.menu_objToCartas));

        cargaDatos();

        mostrMon = findViewById(R.id.mostradorMonedas);

        mostrMon.setText(String.format(getResources().getString(R.string.expositor_mostrador_monedas), aux.mostrarmonedas()));
        adc = new AdaptadorCartas(this, aux.getListaCarta());
        rv = findViewById(R.id.recyclerView);
        rv.setAdapter(adc);
        RecyclerView.LayoutManager lm = new GridLayoutManager(this, 2);
        rv.setLayoutManager(lm);

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

    @Override
    public void onBackPressed() {
        //guardaDatos();
        NavUtils.navigateUpFromSameTask(this);
        finish();
    }
}

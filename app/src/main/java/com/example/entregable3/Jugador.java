package com.example.entregable3;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.entregable3.cartitas_showcase.Carta;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marina on 25-Nov-17.
 */

public class Jugador implements Serializable, Parcelable {

    public static final Creator<Jugador> CREATOR = new Creator<Jugador>() {
        @Override
        public Jugador createFromParcel(Parcel in) {
            return new Jugador(in);
        }

        @Override
        public Jugador[] newArray(int size) {
            return new Jugador[size];
        }
    };
    private final String nombre; //Nombre del usuario
    private final byte precioCarta = 10;
    private int monedas;
    private transient Context myContext;
    // Array que guarda las cartas disponibles de la baraja del tipo 1
    private List<Carta> listaCarta;

    Jugador(Context c, String nombre) {
        this.nombre = nombre;
        this.monedas = 200;
        myContext = c;
        listaCarta = ejemploCartas(myContext);
    }

    private Jugador(Parcel in) {
        nombre = in.readString();
        monedas = in.readInt();
        listaCarta = new ArrayList<>();
        in.readList(listaCarta, null);
    }

    private List<Carta> ejemploCartas(Context context) {
        List<Carta> listaCarta = new ArrayList<>();
        TypedArray imgs = context.getResources().obtainTypedArray(R.array.lista_imagenes);

        for (int i = 0; i < imgs.length(); i++)
            listaCarta.add(new Carta(imgs.getResourceId(i, -1)));
        imgs.recycle();
        return listaCarta;
    }

    public List<Carta> getListaCarta() {
        return listaCarta;
    }

    public String mostrarnombre() {
        return nombre;
    }

    public int mostrarmonedas() {
        return monedas;
    }

    public void restaMonedas() {
        monedas = monedas - precioCarta;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(nombre);
        parcel.writeInt(monedas);
        parcel.writeList(listaCarta);
    }
}


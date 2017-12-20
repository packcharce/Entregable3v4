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
 * Created by Marina, tuneado por Carlos on 20-Dic-17.
 * Clase de cada jugador, con la lista de cartas desbloqueadas,
 * monedas disponibles, y el precio personal de las cartas
 *
 * Lo de serializable y parcelable es con vistas a guardar
 * el jugador en una Base de datos, pero mas adelante
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
    // List que guarda las cartas
    private List<Carta> listaCarta;

    Jugador(Context c, String nombre) {
        this.nombre = nombre;
        this.monedas = 100;
        myContext = c;
        listaCarta = ejemploCartas(myContext);
    }

    private Jugador(Parcel in) {
        nombre = in.readString();
        monedas = in.readInt();
        listaCarta = new ArrayList<>();
        in.readList(listaCarta, null);
    }

    /**
     * Metodo que carga las cartas con respecto al array de string
     * con los recursos de todas las cartas
     *
     * @param context Esto es para poder acceder a los resources
     * @return Lista con las cartas
     */
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

    /**
     * Metodo para parcelar el objeto
     *
     * @param parcel Donde se va a guardar
     * @param i
     */
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(nombre);
        parcel.writeInt(monedas);
        parcel.writeList(listaCarta);
    }
}


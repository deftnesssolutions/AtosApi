package com.trainer.mobilcode.cadastroapirestatos.model;

import android.content.ContentValues;
import android.content.Context;

import com.trainer.mobilcode.cadastroapirestatos.R;
import com.trainer.mobilcode.cadastroapirestatos.db.BaseDatos;
import com.trainer.mobilcode.cadastroapirestatos.db.ConstantesBaseDatos;

import java.util.ArrayList;


/**
 * Created by Gustavo Ovelar on 17/09/2018.
 */
public class ConstructorContactos {

    private Context context;

    public ConstructorContactos(Context context) {
        this.context = context;
    }

    public ArrayList<Contacto> obtenerDatos() {
        BaseDatos db = new BaseDatos(context);
        return  db.obtenerTodosLosContactos();
    }

    public void insertarContacto(Contacto contacto){
        BaseDatos db = new BaseDatos(context);
        ContentValues contentValues = new ContentValues();
        contentValues.put(ConstantesBaseDatos.TABLE_CONTACTS, contacto.getNome());
        contentValues.put(ConstantesBaseDatos.TABLE_CONTACTS, contacto.getIdade());
        contentValues.put(ConstantesBaseDatos.TABLE_CONTACTS, contacto.getSexo());
        db.insertarContacto(contentValues);
    }

}

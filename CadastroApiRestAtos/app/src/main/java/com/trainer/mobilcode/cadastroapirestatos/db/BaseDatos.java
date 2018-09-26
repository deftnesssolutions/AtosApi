package com.trainer.mobilcode.cadastroapirestatos.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.trainer.mobilcode.cadastroapirestatos.model.Contacto;

import java.util.ArrayList;

/**
 * Created by Gustavo Ovelar on 17/09/2018.
 */
public class BaseDatos extends SQLiteOpenHelper {

    private Context context;

    public BaseDatos(Context context) {
        super(context, ConstantesBaseDatos.DATABASE_NAME, null, ConstantesBaseDatos.DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryCrearTablaContacto = "CREATE TABLE " + ConstantesBaseDatos.TABLE_CONTACTS + "(" +
                ConstantesBaseDatos.TABLE_CONTACTS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ConstantesBaseDatos.TABLE_CONTACTS_NOME + " TEXT, " +
                ConstantesBaseDatos.TABLE_CONTACTS_IDADE + " TEXT, " +
                ConstantesBaseDatos.TABLE_CONTACTS_SEXO + " INTEGER" +
                ")";
        db.execSQL(queryCrearTablaContacto);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ConstantesBaseDatos.TABLE_CONTACTS);
        onCreate(db);
    }

    public ArrayList<Contacto> obtenerTodosLosContactos() {
        ArrayList<Contacto> contactos = new ArrayList<>();

        String query = "SELECT * FROM " + ConstantesBaseDatos.TABLE_CONTACTS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor registros = db.rawQuery(query, null);

        while (registros.moveToNext()){
            Contacto contactoActual = new Contacto();
            contactoActual.setId(registros.getInt(0));
            contactoActual.setNome(registros.getString(1));
            contactoActual.setIdade(registros.getString(2));
            contactoActual.setSexo(registros.getString(3));
            contactos.add(contactoActual);
        }

        db.close();

        return contactos;
    }

    public void insertarContacto(ContentValues contentValues){
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(ConstantesBaseDatos.TABLE_CONTACTS,null, contentValues );
        db.close();
    }
    public void updateContacto(ContentValues contentValues,String[] datos ){
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(ConstantesBaseDatos.TABLE_CONTACTS,contentValues, "id=?",datos );
        db.close();
    }

    public void deleteContacto(String[] param ){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ConstantesBaseDatos.TABLE_CONTACTS,ConstantesBaseDatos.TABLE_CONTACTS_ID+"=?",param);
        db.close();
    }

}
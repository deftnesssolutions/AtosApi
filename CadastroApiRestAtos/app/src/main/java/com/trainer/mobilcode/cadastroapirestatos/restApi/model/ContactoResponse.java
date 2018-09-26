package com.trainer.mobilcode.cadastroapirestatos.restApi.model;

import android.provider.ContactsContract;

import com.trainer.mobilcode.cadastroapirestatos.model.Contacto;

import java.util.ArrayList;

/**
 * Created by Gustavo Ovelar on 19/09/2018.
 */
public class ContactoResponse {

    ArrayList<Contacto> contactos;

    public ArrayList<Contacto> getContactos() {
        return contactos;
    }

    public void setContactos(ArrayList<Contacto> contactos) {
        this.contactos = contactos;
    }
}

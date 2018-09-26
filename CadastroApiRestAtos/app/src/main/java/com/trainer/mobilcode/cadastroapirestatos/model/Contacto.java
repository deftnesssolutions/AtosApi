package com.trainer.mobilcode.cadastroapirestatos.model;


import java.io.Serializable;

/**
 * Created by Gustavo Ovelar on 17/09/18.
 */
public class Contacto implements Serializable {

    private int id;
    private String nome;
    private String idade;
    private String sexo;


    public Contacto(String nome, String idade, String sexo) {
        this.nome = nome;
        this.idade = idade;
        this.sexo = sexo;
    }

    public Contacto() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIdade() {
        return idade;
    }

    public void setIdade(String idade) {
        this.idade = idade;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    @Override
    public String toString() {
        return nome.toString();
    }
}

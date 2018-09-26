package com.trainer.mobilcode.cadastroapirestatos;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.trainer.mobilcode.cadastroapirestatos.db.BaseDatos;
import com.trainer.mobilcode.cadastroapirestatos.db.ConstantesBaseDatos;
import com.trainer.mobilcode.cadastroapirestatos.model.Contacto;

/**
 * Created by Gustavo Ovelar on 17/09/2018.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText etNome;
    private EditText etIdade;
    private EditText etSexo;
    private Button btnCadastro;
    private Contacto contacto,altContacto;
    BaseDatos db;
    TextInputLayout til_nome,til_idade,til_sexo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        capturarObjetos();

        btnCadastro.setOnClickListener(this);
        Intent i = getIntent();
        altContacto = (Contacto) i.getSerializableExtra("contacto_enviado");
        if  (altContacto != null)//(extras != null)
        {
            btnCadastro.setText("Alterar");
            etNome.setText(altContacto.getNome());
            etIdade.setText(altContacto.getIdade());
            etSexo.setText(altContacto.getSexo());

        }else {
            btnCadastro.setText("Cadastrar");
        }


    }

    @Override
    public void onClick(View view) {
        if(view == btnCadastro)
        {
            if(btnCadastro.getText().toString().equals("Cadastrar"))
            {

                if (!validateName()) {
                    return;
                }

                if (!validateIdade())
                {
                    return;
                }

                if(!validateSexo())
                {
                    return;
                }


                db = new BaseDatos(this);
                cadastrarPessoa(db);
                Toast.makeText(MainActivity.this, "Dados Cadastrados... ",
                        Toast.LENGTH_SHORT).show();

                chamaTransmitir();
            }else
            {
                String[] param = {String.valueOf(altContacto.getId())};
                db = new BaseDatos(this);
                alterarPessoa(db,param);
                alert("Dados alterados com sucesso!");
                chamaTransmitir();
            }
        }
    }

    //Metodo para ejercer el control del envio del formulario chequeando que los campos no esten vacios y que los datos sean correctos
    private void submitForm() {


    }
    private void chamaTransmitir() {
        Intent intent = new Intent(MainActivity.this, DetalleContacto.class);
        Contacto contactoEnviar = new Contacto();
        contactoEnviar.setNome(etNome.getText().toString());
        contactoEnviar.setIdade(etIdade.getText().toString());
        contactoEnviar.setSexo(etSexo.getText().toString());
        intent.putExtra("contacto_enviar",contactoEnviar);
        startActivity(intent);
    }

    public void alert(String a){
        Toast.makeText(this,a,Toast.LENGTH_SHORT).show();
    }
    public void cadastrarPessoa(BaseDatos db) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(ConstantesBaseDatos.TABLE_CONTACTS_NOME, etNome.getText().toString());
        contentValues.put(ConstantesBaseDatos.TABLE_CONTACTS_IDADE, etIdade.getText().toString());
        contentValues.put(ConstantesBaseDatos.TABLE_CONTACTS_SEXO, etSexo.getText().toString());
        db.insertarContacto(contentValues);
    }
    public void alterarPessoa(BaseDatos db, String[] param){

        ContentValues contentValues = new ContentValues();
        contentValues.put(ConstantesBaseDatos.TABLE_CONTACTS_NOME, etNome.getText().toString());
        contentValues.put(ConstantesBaseDatos.TABLE_CONTACTS_IDADE, etIdade.getText().toString());
        contentValues.put(ConstantesBaseDatos.TABLE_CONTACTS_SEXO, etSexo.getText().toString());
        db.updateContacto(contentValues,param);

    }

    public void controlError()//Metodo que implementa la escucha de los InputText para contralar el TextChange
    {
        etNome.addTextChangedListener(new MyTextWatcher(etNome));
        etIdade.addTextChangedListener(new MyTextWatcher(etIdade));
        etSexo.addTextChangedListener(new MyTextWatcher(etSexo));

    }
    public void capturarObjetos()//Metodo para instanciar los objetos del activity
    {
        etNome     = (EditText) findViewById(R.id.et_nome);
        etIdade    = (EditText) findViewById(R.id.et_idade);
        etSexo  = (EditText) findViewById(R.id.et_sexo);

        btnCadastro     = (Button) findViewById(R.id.btn_cadastro);

        til_nome = (TextInputLayout) findViewById(R.id.input_layout_name);
        til_idade = (TextInputLayout) findViewById(R.id.input_layout_idade);
        til_sexo = (TextInputLayout) findViewById(R.id.input_layout_sexo);

    }
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
    private boolean validateName() {
        if (etNome.getText().toString().trim().isEmpty()) {
            til_nome.setError(getString(R.string.err_msg_name));
            requestFocus(etNome);
            return false;
        } else {
            til_nome.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateIdade() {
        if (etIdade.getText().toString().trim().isEmpty()) {
            til_idade.setError(getString(R.string.err_msg_idade));
            requestFocus(etIdade);
            return false;
        } else {
            til_idade.setErrorEnabled(false);
        }

        return true;
    }
    private boolean validateSexo() {
        if (etSexo.getText().toString().trim().isEmpty()) {
            til_sexo.setError(getString(R.string.err_msg_sexo));
            requestFocus(etSexo);
            return false;
        } else {
            til_sexo.setErrorEnabled(false);
        }

        return true;
    }
    // ####  Clase que implementa TextWatcher para controlar el rellenado correcto de los campos
    private class MyTextWatcher implements TextWatcher {
        private View view;

        private MyTextWatcher(View v)
        {
            this.view=v;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            switch (view.getId()) {
                case R.id.et_nome:
                    validateName();
                    break;
                case R.id.et_idade:
                    validateIdade();
                    break;
                case R.id.et_sexo:
                    validateSexo();
                    break;
            }
        }
    }
}

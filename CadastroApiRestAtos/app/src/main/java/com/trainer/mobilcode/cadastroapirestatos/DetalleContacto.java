package com.trainer.mobilcode.cadastroapirestatos;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.trainer.mobilcode.cadastroapirestatos.db.BaseDatos;
import com.trainer.mobilcode.cadastroapirestatos.model.Contacto;
import com.trainer.mobilcode.cadastroapirestatos.restApi.EndpointsApi;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Gustavo Ovelar on 17/09/2018.
 */
public class DetalleContacto extends AppCompatActivity{
    private static final String KEY_EXTRA_URL = "url";

    private TextView tvNome;
    private TextView tvIdade;
    private TextView tvSexo;
    private ListView lvVisivel;
    private ImageView imgTransmitir;
    private Contacto contacto,contactoRecibido;
    BaseDatos bd;
    ArrayList<Contacto> contactos;
    ArrayAdapter<Contacto> adapter;
    ProgressDialog dialog;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_contacto);

        lvVisivel = (ListView) findViewById(R.id.lvPessoa);
        registerForContextMenu(lvVisivel);
        Intent i = getIntent();
        contactoRecibido = (Contacto) i.getSerializableExtra("contacto_enviar");


        tvNome    = (TextView) findViewById(R.id.tvNome);
        tvIdade  = (TextView) findViewById(R.id.tvIdade);
        tvSexo     = (TextView) findViewById(R.id.tvSexo);

        tvNome.setText(contactoRecibido.getNome());
        tvIdade.setText(contactoRecibido.getIdade());
        tvSexo.setText(contactoRecibido.getSexo());


        imgTransmitir = (ImageView) findViewById(R.id.imgSend);

        lvVisivel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Contacto contactoEnviado = (Contacto) adapter.getItem(i);
                Intent intent = new Intent(DetalleContacto.this, MainActivity.class);
                intent.putExtra("contacto_enviado",contactoEnviado);
                startActivity(intent);
            }
        });
        lvVisivel.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                contacto = adapter.getItem(i);
                return false;
            }
        });

        imgTransmitir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new ProgressDialog(DetalleContacto.this);
                dialog.setMessage("Enviando...");
                dialog.setCancelable(false);
                dialog.show();

                EndpointsApi iContactoREST = EndpointsApi.retrofit.create(EndpointsApi.class);
                final Call<Void> call = iContactoREST.saveContacto(contactoRecibido);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (dialog.isShowing())
                            dialog.dismiss();
                            alert( "Pessoa inserido com sucesso");
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        if (dialog.isShowing())
                            dialog.dismiss();
                            alert( "Não foi possível fazer a conexão");
                    }
                });

            }
        });
    }

    public void popularLista()
    {
        bd = new BaseDatos(this);
        contactos = bd.obtenerTodosLosContactos();
        if(lvVisivel != null){
            adapter = new ArrayAdapter<Contacto>(this,android.R.layout.simple_list_item_1,contactos);
            lvVisivel.setAdapter(adapter);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        popularLista();
    }

    public void deletarPessoa(String[] param){
        bd = new BaseDatos(this);
        bd.deleteContacto(param);
        alert("Dado apagado com sucesso!");
    }
    public void alert(String a){
        Toast.makeText(this,a,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem mDelete = menu.add("Delete Registro");
        mDelete.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                String[] param = {String.valueOf(contacto.getId())};
                deletarPessoa(param);
                alert("Registro Excluido com sucesso!");
                popularLista();

                return false;
            }
        });
        super.onCreateContextMenu(menu, v, menuInfo);

    }
}

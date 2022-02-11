package com.spania.sala17;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.spania.sala17.interfaces.ConsultaApi;
import com.spania.sala17.models.Evento;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class  Eventos extends AppCompatActivity
{
    Button comprar;
    public static ArrayList<Entrada> listadoEntradas = new ArrayList<>();
    public static CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventos);


        Intent a = getIntent();

        ListView listView = findViewById(R.id.listView);
        //listadoEntradas.add(new Entrada("Barceló 23 de Marzo", R.drawable.pari));


        Intent llegar = getIntent();
        String textoRecogido = llegar.getStringExtra("infoEntrada");
        String vipRecogido = llegar.getStringExtra("vipSI");
        listadoEntradas.add(new Entrada(textoRecogido, R.drawable.pari, vipRecogido));
        adapter = new CustomAdapter(listadoEntradas, this);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        //guardar entrada en base de datos
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://ec2-54-205-129-91.compute-1.amazonaws.com:8080/").addConverterFactory(GsonConverterFactory.create()).build();
        ConsultaApi consultaApi = retrofit.create(ConsultaApi.class);
        long zakas = 0;
        Call<Evento> call = consultaApi.insertarEvento(textoRecogido, zakas);
        call.enqueue(new Callback<Evento>() {
            @Override
            public void onResponse(Call<Evento> call, Response<Evento> response) {
                if (response.isSuccessful()){
                    System.out.println("todo ok");
                }
            }

            @Override
            public void onFailure(Call<Evento> call, Throwable t) {
                System.out.println("No conexión");
            }
        });

        //retornar = findViewById();
        comprar = findViewById(R.id.btCompra);

        //voy a dar funciones al boton
        comprar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent k = new Intent(Eventos.this, CompraEntradas.class);
                startActivity(k);
            }
        });

        /*retornar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent volverAtras = new Intent(Eventos.this, DatosUser.class);
                startActivity(volverAtras);
            }
        });*/
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.datos:
                Toast.makeText(Eventos.this,"Inicio",Toast.LENGTH_SHORT).show();
                Intent p = new Intent(this, DatosUser.class);
                startActivity(p);
                break;
            case R.id.tinder:
                Toast.makeText(Eventos.this,"Entrando a tinder",Toast.LENGTH_SHORT).show();
                Intent t = new Intent(this, Tinder.class);
                startActivity(t);
                break;
            case R.id.copas:
                Intent i = new Intent(this, Copas.class);
                Toast.makeText(Eventos.this, "Entrando a copas", Toast.LENGTH_SHORT).show();
                startActivity(i);
                break;
            case R.id.eventos:
                Intent e = new Intent(this,Eventos.class);
                startActivity(e);
                Toast.makeText(Eventos.this,"Entrando a eventos", Toast.LENGTH_SHORT).show();
                break;
            case R.id.music:
                Intent m = new Intent(this,Music.class);
                startActivity(m);
                Toast.makeText(Eventos.this, "Entrando a Spotify",Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(Eventos.this,"Reinicie la aplicacion", Toast.LENGTH_LONG).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
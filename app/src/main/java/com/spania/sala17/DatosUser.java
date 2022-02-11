package com.spania.sala17;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DatosUser extends AppCompatActivity
{
    Button btTinder;
    TextView bienvenida;
    String welcome;
    Button btEventos;
    Button btcompra;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_user);

        //aqui vuelvo de la seccion de eventos
        Intent volverAtras = getIntent();
        Intent registros = getIntent();
        String nombreRegistro = registros.getStringExtra("nombreUser");

        btTinder = findViewById(R.id.btTinder);
        btEventos = findViewById(R.id.btEventos);
        bienvenida = findViewById(R.id.bienvenida);
        btcompra=findViewById(R.id.btCompra);
        Intent j = getIntent();
        welcome = j.getStringExtra("nombreUser");

        //si viene de registros seteo un texto al textView, si viene de login seteo otro
        if(!nombreRegistro.equals(""))
        {
            bienvenida.setText("Willkommen, " + nombreRegistro + "!");
        }
        else
            {
                bienvenida.setText("Willkommen, " + welcome + "!");
            }

        //Pasar de esta activity a la del Tinder
        btTinder.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(DatosUser.this, Tinder.class);
                startActivity(i);
            }
        });
        btEventos.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent a = new Intent(DatosUser.this, Eventos.class);
                startActivity(a);
            }
        });
        btcompra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(DatosUser.this, Copas.class);
                startActivity(a);
            }
        });

    }
}
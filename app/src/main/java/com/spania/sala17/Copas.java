package com.spania.sala17;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.spania.sala17.interfaces.ConsultaApi;
import com.spania.sala17.models.Bebida;

import java.util.ArrayList;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Copas extends AppCompatActivity {
TextView [] calcopa=new TextView[25];
TextView [] nomcopa=new TextView[25];
Button []sum=new Button[25];
Button []rest= new Button[25];
Button pagar;
ImageView []fotocopa= new ImageView[2];
    int total;
    int copa = 0;
String [] id={"calcopa","nomcopa","sum","rest"};
String listanombre[]=new String[25];
int listaprecio[]=new int[25];
int resulcopa[]=new int[25];
int cant[]=new int[25];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_copas);
        pagar = findViewById(R.id.button);
        fotocopa[0]=findViewById(R.id.fotocopa0);
        autofinbyid();
       fined();

    }

    //auto findbyid
    private void autofinbyid(){
        int temp;
        for (int i = 0; i < id.length; i++) {
            for (int k = 0; k < nomcopa.length; k++) {
                temp =getResources().getIdentifier(id[i]+k,"id",getPackageName());
                switch(i){
                    case 0:
                        calcopa[k]=findViewById(temp);
                        break;
                    case 1:
                        nomcopa[k]=findViewById(temp);
                        break;
                    case 2:
                        sum[k]=findViewById(temp);
                        break;
                    case 3:
                        rest[k]=findViewById(temp);
                        break;

                }
            }
        }
    }
    //autobusqueda de boton y calculo suma
    public void sumar(View v){
        for (int i = 0; i < sum.length; i++) {
            if (v.getId()==getResources().getIdentifier(id[2]+i,"id",getPackageName())){
                resulcopa[i]+=listaprecio[i];
                cant[i]=resulcopa[i]/listaprecio[i];
                calcopa[i].setText(cant[i]+" x "+listaprecio[i]+" = "+resulcopa[i]+"€");
            }
        }
        Glide.with(this).load("https://previews.dropbox.com/p/thumb/ABaxvhyJQMIqsiG-ECPvx3_gqoO5Wg51lzvBA_2DRPoWqO4hKCkSlKT4vXooF2VGbjXDM46BD2AwyM0LgQVY6oEdDQcnGBp8J8Vu5DKPholOkaDMhEkWwy6bFVfw4eKqk-8pMwEQSdwadmByPgHz2AcxKwyiRxy8WUPzP4jrRUOSVVC34GhsVWBY7Vdnq1GyVl-9fGdkfOEjQ_2s-Nz8Db8LLjVsnviWpZfMYg-VMMYmVKY60Yj7jVQydhl9WPEBGwWPX32HzkbCF_s5mav-solqALr2VB6uabiCCEVmnjVDsN6Q4S_qkvqiqH03UIlzsQZFsnxLMYC7AdLAv-3oWUq53AHCRU2LLfYa4F5ez-yeRQ/p.png").into(fotocopa[0]);
        total();
    }
    //autobusqueda de boton y calculo resta
    public void restar(View v){
        for (int i = 0; i < rest.length; i++) {
            if (cant[i]>0){
                if (v.getId()==getResources().getIdentifier(id[3]+i,"id",getPackageName())){
                    resulcopa[i]-=listaprecio[i];
                    cant[i]=resulcopa[i]/listaprecio[i];
                    calcopa[i].setText(cant[i]+" x "+listaprecio[i]+" = "+resulcopa[i]+"€");
                }
            }
        }
        total();
    }
    private void total(){
        total=0;
        for (int i = 0; i < resulcopa.length; i++) {
            total+=resulcopa[i];
        }
        pagar.setText("El total es de "+total+"€");
    }

    public void pagar(View v){
        Intent i = new Intent(this,codigoqr.class);
        for (int k = 0; k < nomcopa.length; k++) {
            if (cant[k]>=1){
                i.putExtra("message"+k,listanombre[k]+" =  " + cant[k] + "\n" + resulcopa[k]+"€");
            }
        }
        /*(copa >= 1){
            i.putExtra("message","RonCola =  " + copa + "\n" + total);
        }else{

        }*/


        startActivity(i);
    }
    //recoger las bebidas y su precio de la base de datos.
    private void fined(){
        long cod;
        for (int i = 0; i < 25; i++) {
            cod=i+1;
            Retrofit retrofit = new Retrofit.Builder().baseUrl("http://10.0.2.2:8080/").addConverterFactory(GsonConverterFactory.create()).build();
            ConsultaApi consultaApi = retrofit.create(ConsultaApi.class);
            Call<Bebida> call = consultaApi.find((cod));
            int finalI = i;
            call.enqueue(new Callback<Bebida>() {
                @Override
                public void onResponse(Call<Bebida> call, Response<Bebida> response) {
                    try {
                        if (response.isSuccessful()) {
                            Bebida bebida = response.body();
                            nomcopa[finalI].setText(bebida.getNombreBebida());
                            calcopa[finalI].setText("0 x "+String.valueOf(bebida.getPrecio())+" ");
                            listaprecio[finalI]=bebida.getPrecio();
                            listanombre[finalI]=bebida.getNombreBebida();
                        }
                    } catch (Exception ex) {
                        Toast.makeText(Copas.this, "pepe", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<Bebida> call, Throwable t) {
                    System.out.println(t.toString());
                    Toast.makeText(Copas.this, t.toString(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.tinder:
                Toast.makeText(Copas.this,"Entrando a tinder",Toast.LENGTH_SHORT).show();
                Intent t = new Intent(this, Tinder.class);
                startActivity(t);
                break;
            case R.id.copas:
                Intent ma = new Intent(this, MainActivity.class);
                Toast.makeText(Copas.this, "Entrando a copas", Toast.LENGTH_SHORT).show();
                startActivity(ma);
                break;
            case R.id.eventos:
                Intent e = new Intent(this,Eventos.class);
                startActivity(e);
                Toast.makeText(Copas.this,"Entrando a eventos", Toast.LENGTH_SHORT).show();
                break;
            case R.id.music:
                Intent m = new Intent(this,Music.class);
                startActivity(m);
                Toast.makeText(Copas.this, "Entrando a Spotify",Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(Copas.this,"Reinicie la aplicacion", Toast.LENGTH_LONG).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }



}
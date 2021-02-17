package com.example.dss;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dss.api.CamisetaAPI;
import com.example.dss.object.Camiseta;
import com.example.dss.object.Carrito;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MostrarCamiseta extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // remove title
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_mostrar_camiseta);

        Intent i = getIntent();
        Camiseta camiseta = (Camiseta) i.getSerializableExtra("camiseta");

        TextView modelo = findViewById(R.id.modelo_show);
        TextView precio = findViewById(R.id.price_show);
        ImageView camisetaImg = findViewById(R.id.foto_camiseta);
        ImageView profileImg = findViewById(R.id.profile_image);
        modelo.setText(camiseta.getModelo() + " (talla: " + camiseta.getTalla() + ")");
        precio.setText(camiseta.getPrecio().toString() + "€");
        Picasso.get().load(camiseta.getImage()).into(camisetaImg);
        Picasso.get().load(camiseta.getImageShop()).into(profileImg);

        View lyMap = findViewById(R.id.map);
        lyMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), LocationMap.class);
                intent.putExtra("camiseta", camiseta);
                v.getContext().startActivity(intent);
            }
        });
        View lyReservar = findViewById(R.id.reservar);
        if (camiseta.getVendida()) {
            lyReservar.setVisibility(View.GONE);
        } else {
            lyReservar.setVisibility(View.VISIBLE);
        }
        lyReservar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Formulario.class);
                intent.putExtra("camiseta", camiseta);
                v.getContext().startActivity(intent);
            }
        });
    }
}
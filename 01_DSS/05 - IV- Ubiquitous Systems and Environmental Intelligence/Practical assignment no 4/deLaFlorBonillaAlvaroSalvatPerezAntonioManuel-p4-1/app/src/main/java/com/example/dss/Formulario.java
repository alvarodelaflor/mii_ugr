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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

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

public class Formulario extends AppCompatActivity {

    EditText direccion;
    Button enviar;
    ImageView imgCamiseta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        direccion = findViewById(R.id.direccion);
        enviar = findViewById(R.id.enviar);
        imgCamiseta = findViewById(R.id.img_camiseta);

        Intent i = getIntent();
        Camiseta camiseta = (Camiseta) i.getSerializableExtra("camiseta");

        Picasso.get().load(camiseta.getImage()).into(imgCamiseta);

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = MainActivity.user;
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://10.0.2.2:8080/mio.jersey.segundo.maven/api/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                CamisetaAPI service = retrofit.create(CamisetaAPI.class);

                Call<Carrito> call = service.reservarCamiseta(direccion.getText().toString(), user+"$carrito", camiseta.getId());

                call.enqueue(new Callback<Carrito>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(Call<Carrito> call, Response<Carrito> response) {
                        if (response.isSuccessful()) {
                            Log.e("PERFECTO", "Camiseta creada");
                            Call<List<Camiseta>> call2 = service.obtenerCamisetas();
                            call2.enqueue(new Callback<List<Camiseta>>() {
                                @RequiresApi(api = Build.VERSION_CODES.N)
                                @Override
                                public void onResponse(Call<List<Camiseta>> call, Response<List<Camiseta>> response) {
                                    if (!response.isSuccessful())  {
                                        Log.e("FALLO", "No se han recuperado bien los datos");
                                    }
                                    try {
                                        RecyclerView recyclerView = findViewById(R.id.rv_list);
                                        Adapter adapter = new Adapter(v.getContext(), new ArrayList<>());
                                        recyclerView.setAdapter(adapter);
                                        recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));
                                        List<Camiseta> mList =response.body().stream().filter(x -> x.getVendida().equals(false)).collect(Collectors.toList());
                                        Log.e("PERFECTO", mList.toString());
                                        adapter.setCamisetas(mList);
                                        Log.e("PERFECTO", String.valueOf(adapter.mData.size()));

                                    } catch (Exception e) {

                                    }
                                }

                                @Override
                                public void onFailure(Call<List<Camiseta>> call, Throwable t) {
                                    Log.e("FALLO", t.getLocalizedMessage());

                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(Call<Carrito> call, Throwable t) {

                    }
                });

                Intent intent = new Intent(v.getContext(), MainActivity.class);
                v.getContext().startActivity(intent);
            }
        });
    }
}
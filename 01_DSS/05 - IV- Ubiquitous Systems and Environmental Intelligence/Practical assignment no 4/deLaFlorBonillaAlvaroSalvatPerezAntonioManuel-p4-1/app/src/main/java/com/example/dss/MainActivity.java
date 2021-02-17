package com.example.dss;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.example.dss.api.CamisetaAPI;
import com.example.dss.object.Camiseta;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    public static String user = "15408846L";
    public static FloatingActionButton fab1, fab2, fab3;
    Animation fabOpen, fabClose, fabRotate1, fabRotateAux;
    Boolean isOpen = false;
    public static LinearLayout ly;
    public static Boolean carrito = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        ly = findViewById(R.id.empty);
        ly.setVisibility(View.GONE);

        fab1 = findViewById(R.id.fab);
        fab2 = findViewById(R.id.fab2);
        fab3 = findViewById(R.id.fab3);

        fabOpen = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fabClose = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        fabRotate1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
        fabRotateAux = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_aux);

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        RecyclerView recyclerView = findViewById(R.id.rv_list);

        List<Camiseta> mlist = new ArrayList<>();

        update(recyclerView, mlist);

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOpen) {
                    fab2.startAnimation(fabClose);
                    fab3.startAnimation(fabClose);
                    fab1.startAnimation(fabRotate1);
                    fab2.setClickable(false);
                    fab3.setClickable(false);
                    isOpen = false;
                    carrito = false;
                    update(recyclerView, mlist);
                    Toast.makeText(MainActivity.this, "Mostrando camisetas disponibles", Toast.LENGTH_LONG).show();
                } else {
                    fab2.startAnimation(fabOpen);
                    fab3.startAnimation(fabOpen);
                    fab1.startAnimation(fabRotateAux);
                    fab2.setClickable(true);
                    fab3.setClickable(true);
                    isOpen = true;
                }
            }
        });

        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carrito = true;
                update(recyclerView, mlist);
                Toast.makeText(MainActivity.this, "Mostrando sus reservas", Toast.LENGTH_LONG).show();
            }
        });

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user.equals("15408846L")) {
                    user = "15408845W";
                    Toast.makeText(MainActivity.this, user, Toast.LENGTH_LONG).show();
                } else {
                    user ="15408846L";
                    Toast.makeText(MainActivity.this, user, Toast.LENGTH_LONG).show();
                }
                fab3.performClick();
            }
        });
    }

    public void update(RecyclerView recyclerView, List<Camiseta> mlist) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                String url = "http://10.0.2.2:8080/mio.jersey.segundo.maven/api/";


                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(url)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                CamisetaAPI service = retrofit.create(CamisetaAPI.class);

                Call<List<Camiseta>> call;
                if (carrito) {
                    call = service.obtenerCamisetasUsuario(user);
                } else {
                    call = service.obtenerCamisetas();
                }

                Adapter adapter = new Adapter(MainActivity.this, mlist);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

                call.enqueue(new Callback<List<Camiseta>>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(Call<List<Camiseta>> call, Response<List<Camiseta>> response) {
                        if (!response.isSuccessful())  {
                            Log.e("FALLO", "No se han recuperado bien los datos");
                        }
                        try {
                            Log.e("FALLO RETRIEVE", response.body().toString());
                            List<Camiseta> mList =response.body().stream().filter(x -> x.getVendida().equals(carrito)).collect(Collectors.toList());
                            Log.e("PERFECTO", mList.toString());
                            adapter.setCamisetas(mList);
                            if (mList.size() > 0) {
                                ly.setVisibility(View.GONE);
                            } else {
                                ly.setVisibility(View.VISIBLE);
                            }
                            Log.e("PERFECTO", String.valueOf(adapter.mData.size()));
                        } catch (Exception ex) {
                            Log.e("FALLO CATCH", ex.getLocalizedMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Camiseta>> call, Throwable t) {
                        Log.e("FALLO FAILURE", t.getLocalizedMessage());

                    }
                });
            }
        }, 1000);
    }
}
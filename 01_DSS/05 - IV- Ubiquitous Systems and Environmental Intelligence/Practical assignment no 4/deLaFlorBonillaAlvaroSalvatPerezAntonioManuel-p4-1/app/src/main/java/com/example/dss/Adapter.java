package com.example.dss;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dss.api.CamisetaAPI;
import com.example.dss.object.Camiseta;
import com.example.dss.object.Carrito;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Adapter extends RecyclerView.Adapter<Adapter.myViewHolder> {

    Context mContext;
    List<Camiseta> mData;

    public void setCamisetas(List<Camiseta> camisetas) {
        this.mData = camisetas;
        notifyDataSetChanged();
    }

    public Adapter(Context mContext, List<Camiseta> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.card_item, parent, false);
        return new myViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        Picasso.get().load(mData.get(position).getImageShop()).into(holder.profile_photo);
        Picasso.get().load(mData.get(position).getImage()).into(holder.background_img);
        holder.tv_title.setText(mData.get(position).getModelo() + " (talla: " + mData.get(position).getTalla() + ")");
        holder.tv_description.setText(mData.get(position).getPrecio().toString() + "€");
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = MainActivity.user;
                Log.e("USER", user);

                Intent intent = new Intent(v.getContext(), MostrarCamiseta.class);
                intent.putExtra("camiseta", mData.get(position));
                v.getContext().startActivity(intent);
            }
        });
        if (MainActivity.carrito) {
            holder.button_delete.setVisibility(View.VISIBLE);
        } else {
            holder.button_delete.setVisibility(View.GONE);
        }
        holder.button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://10.0.2.2:8080/mio.jersey.segundo.maven/api/";
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(url)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                CamisetaAPI service = retrofit.create(CamisetaAPI.class);
                Call<List<Camiseta>> call = service.borrarReserva(MainActivity.user+"$carrito",mData.get(position).getId());
                call.enqueue(new Callback<List<Camiseta>>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(Call<List<Camiseta>> call, Response<List<Camiseta>> response) {
                        if (!response.isSuccessful())  {
                            Log.e("FALLO", "No se han recuperado bien los datos");
                        } else {

                        }
                    }

                    @Override
                    public void onFailure(Call<List<Camiseta>> call, Throwable t) {
                        Log.e("FALLO FAILURE", t.getLocalizedMessage());

                    }
                });
                MainActivity.fab3.performClick();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {

        ImageView profile_photo, background_img;
        TextView tv_title, tv_description;
        Button button, button_delete;

        public myViewHolder(View itemView) {
            super(itemView);
            profile_photo = itemView.findViewById(R.id.profile_img);
            background_img = itemView.findViewById(R.id.card_view_background);
            tv_title = itemView.findViewById(R.id.card_title);
            tv_description = itemView.findViewById(R.id.card_description);
            button = itemView.findViewById(R.id.button_add);
            button_delete = itemView.findViewById(R.id.button_delete);
        }
    }
}

package com.example.dss.api;

import com.example.dss.object.Camiseta;
import com.example.dss.object.Carrito;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CamisetaAPI {
    @GET("camiseta")
    Call<List<Camiseta>> obtenerCamisetas();

    @GET("camiseta/usuario/{id}")
    Call<List<Camiseta>> obtenerCamisetasUsuario(@Path("id") String id);

    @DELETE("carrito/{idCarrito}/camiseta/{idCamiseta}")
    Call<List<Camiseta>> borrarReserva(@Path("idCarrito") String idCarrito, @Path("idCamiseta") String idCamiseta);

    @POST("carrito/{idCarrito}/camiseta/{idCamiseta}")
    Call<Carrito> reservarCamiseta(@Body String body, @Path("idCarrito") String idCarrito, @Path("idCamiseta")String idCamiseta);
}

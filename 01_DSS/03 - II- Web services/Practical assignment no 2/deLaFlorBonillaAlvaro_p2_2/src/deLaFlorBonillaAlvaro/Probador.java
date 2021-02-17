package deLaFlorBonillaAlvaro;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

import javafx.print.Collation;
import model.Camiseta;
import model.Carrito;
import model.Usuario;

public class Probador {
	
	public static String user = "";
	
	public static String getRefreshPage(String idUser) {
		return "refreshPage" + idUser + "()";
	}
	
	public static void editUser(String userChange) {
		String test = user;
		user = userChange;
	}
	
	public static String hola() {
		return "hola";
	}
	
	public static URI getBaseURI() {
		return UriBuilder.fromUri("http://localhost:8080/deLaFlorBonillaAlvaro").build();
	}
	
	public static List<Camiseta> getCamisetas() {
		ClientConfig config = new DefaultClientConfig();
		Client cliente = Client.create(config);
		WebResource servicio = cliente.resource(getBaseURI());
		String preJson = servicio.path("api")
				.path("camiseta")
				.accept(MediaType.APPLICATION_JSON)
				.get(String.class);
		String aux = preJson.toString();
		Gson gson = new Gson();
		Camiseta[] camisetaArray = gson.fromJson(aux, Camiseta[].class);
		
		List<Camiseta> res = Arrays.asList(camisetaArray);

		return res;
	}
	
	public static List<Camiseta> getCamisetasDisponibles() {

		List<Camiseta> aux = getCamisetas();
		
		List<Camiseta> res = aux != null ? aux.stream().filter(x -> x.getVendida().equals(false)).collect(Collectors.toList()) : null;

		return res;
	}
	
	public static List<Camiseta> getCamisetasReservadas() {
		ClientConfig config = new DefaultClientConfig();
		Client cliente = Client.create(config);
		WebResource servicio = cliente.resource(getBaseURI());
		String preJson = servicio.path("api")
				.path("camiseta")
				.accept(MediaType.APPLICATION_JSON)
				.get(String.class);
		String aux = preJson.toString();
		Gson gson = new Gson();
		Camiseta[] camisetaArray = gson.fromJson(aux, Camiseta[].class);
		
		List<Camiseta> res = Arrays.asList(camisetaArray);
		
		res = res.stream().filter(x -> x.getVendida().equals(true)).collect(Collectors.toList());

		return res;
	}
	
	public static List<Carrito> obtenerCarritos() {
		List<Carrito> res = new ArrayList<>();
		ClientConfig config = new DefaultClientConfig();
		Client cliente = Client.create(config);
		WebResource servicio = cliente.resource(getBaseURI());
		String preJson = servicio.path("api")
				.path("carrito")
				.accept(MediaType.APPLICATION_JSON)
				.get(String.class);
		String aux = preJson.toString();
		Gson gson = new Gson();
		Carrito[] carritoArray = gson.fromJson(aux, Carrito[].class);
		
		if (carritoArray.length > 0) {
			res = Arrays.asList(carritoArray);
		}

		return res;
	}
	
	public static List<Usuario> obtenerUsuarios() {
		List<Usuario> res = new ArrayList<>();
		ClientConfig config = new DefaultClientConfig();
		Client cliente = Client.create(config);
		WebResource servicio = cliente.resource(getBaseURI());
		String preJson = servicio.path("api")
				.path("usuario")
				.accept(MediaType.APPLICATION_JSON)
				.get(String.class);
		String aux = preJson.toString();
		Gson gson = new Gson();
		Usuario[] usuarioArray = gson.fromJson(aux, Usuario[].class);
		
		if (usuarioArray.length > 0) {
			res = Arrays.asList(usuarioArray);
		}

		return res;
	}
	
	public static Carrito obtenerCarritoPorId(String id) {
		ClientConfig config = new DefaultClientConfig();
		Client cliente = Client.create(config);
		WebResource servicio = cliente.resource(getBaseURI());
		String preJson = servicio.path("api")
				.path("carrito/" + id)
				.accept(MediaType.APPLICATION_JSON)
				.get(String.class);
		String aux = "[" + preJson.toString() + "]";
		final GsonBuilder gsonBuilder = new GsonBuilder();
		final Gson gson = gsonBuilder.create();
		Carrito[] carrito = null;
		try {
			carrito = gson.fromJson(aux, Carrito[].class);
		} catch (Exception e) {
			System.out.println(e);
		}
		
		Carrito res = null;
		if (carrito != null && carrito.length > 0) {
			res = carrito[0];
		}
		return res;
	}
	
	public static Boolean sendJsonPost(String url, String json) {
		HttpClient httpClient = HttpClientBuilder.create().build(); //Use this instead 
		Boolean res = true;
		try {
		       HttpPost request = new HttpPost(url);
		       StringEntity params =new StringEntity(json);
		       request.addHeader("content-type", "application/json");
		       request.setEntity(params);
		       HttpResponse response = httpClient.execute(request);
		       if (response.getStatusLine().getStatusCode() != 200) {
		    	   res = false;
		       }
		}catch (Exception ex) {
	    	   res = false;
		       //handle exception here

		} finally {
		       //Deprecated
		       //httpClient.getConnectionManager().shutdown(); 
		}
		return res;
	}
}

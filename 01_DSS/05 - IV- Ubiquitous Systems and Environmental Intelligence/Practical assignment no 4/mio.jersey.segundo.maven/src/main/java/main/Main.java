package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

public class Main {

	public static void main(String[] args) {
		
		sendJsonPost("http://localhost:8080/mio.jersey.segundo.maven/api/usuario", "{\r\n"
				+ "\"dni\":\"15408846L\",\r\n"
				+ "\"nombre\":\"Alvaro\",\r\n"
				+ "\"apellidos\":\"de la Flor Bonilla\",\r\n"
				+ "\"email\":\"alvdebon@correo.ugr.es\",\r\n"
				+ "\"esAdministrador\":false\r\n"
				+ "}");
		
		sendJsonPost("http://localhost:8080/mio.jersey.segundo.maven/api/usuario", "{\r\n"
				+ "\"dni\":\"15408845W\",\r\n"
				+ "\"nombre\":\"Alberto\",\r\n"
				+ "\"apellidos\":\"Gomez Blanco\",\r\n"
				+ "\"email\":\"alberto@correo.ugr.es\",\r\n"
				+ "\"esAdministrador\":false\r\n"
				+ "}");

		sendJsonPost("http://localhost:8080/mio.jersey.segundo.maven/api/camiseta", "{\r\n"
				+ "\"id\": \"AOAO24O2\",\r\n"
				+ "\"modelo\": \"GAP Verano\",\r\n"
				+ "\"talla\": \"XL\",\r\n"
				+ "\"precio\": 21.32,\r\n"
				+ "\"vendida\": false,\r\n"
				+ "\"image\": \"https://http2.mlstatic.com/D_NQ_NP_778165-MCO29610680029_032019-V.jpg\",\r\n"
				+ "\"imageShop\": \"https://logos-marcas.com/wp-content/uploads/2020/09/Gap-Logo.png\",\r\n"
				+ "\"location\":\"37.183054,-3.6021928\"\r\n"
				+ "}");
		
		sendJsonPost("http://localhost:8080/mio.jersey.segundo.maven/api/camiseta", "{\r\n"
				+ "\"id\": \"AOAO24O3\",\r\n"
				+ "\"modelo\": \"Polo Verano\",\r\n"
				+ "\"talla\": \"L\",\r\n"
				+ "\"precio\": 32.16,\r\n"
				+ "\"vendida\": false,\r\n"
				+ "\"image\": \"https://cdn-images.farfetch-contents.com/14/15/22/04/14152204_19009338_1000.jpg\",\r\n"
				+ "\"imageShop\": \"https://logos-marcas.com/wp-content/uploads/2020/04/Ralph-Lauren-Logo.png\",\r\n"
				+ "\"location\":\"37.17692565917969,-3.611699342727661\"\r\n"
				+ "}");
		
		sendJsonPost("http://localhost:8080/mio.jersey.segundo.maven/api/camiseta", "{\r\n"
				+ "\"id\": \"AOAO2404\",\r\n"
				+ "\"modelo\": \"Guess\",\r\n"
				+ "\"talla\": \"S\",\r\n"
				+ "\"precio\": 41.56,\r\n"
				+ "\"vendida\": false,\r\n"
				+ "\"image\": \"https://neverlandstreetwear.com/2594-foto_producto/camiseta-guess-original-logo-blanco.jpg\",\r\n"
				+ "\"imageShop\": \"https://logos-marcas.com/wp-content/uploads/2020/05/Guess-Logo.png\",\r\n"
				+ "\"location\":\"37.17692565917969,-3.611699342727661\"\r\n"
				+ "}");
		
		
		sendJsonPost("http://localhost:8080/mio.jersey.segundo.maven/api/camiseta", "{\r\n"
				+ "\"id\": \"AOAO2405\",\r\n"
				+ "\"modelo\": \"Levis\",\r\n"
				+ "\"talla\": \"XL\",\r\n"
				+ "\"precio\": 34.26,\r\n"
				+ "\"vendida\": false,\r\n"
				+ "\"image\": \"https://media.revistagq.com/photos/5ca5f75b33e7511021153542/master/w_1000,c_limit/tee_3379.jpg\",\r\n"
				+ "\"imageShop\": \"https://www.pngkit.com/png/full/273-2736256_levis-logo-collection-levi-strauss-co.png\",\r\n"
				+ "\"location\":\"37.17692565917969,-3.611699342727661\"\r\n"
				+ "}");
	}
	
	public static void sendJsonPost(String url, String json) {
		HttpClient httpClient = HttpClientBuilder.create().build(); //Use this instead 

		try {
		       HttpPost request = new HttpPost(url);
		       StringEntity params =new StringEntity(json);
		       request.addHeader("content-type", "application/json");
		       request.setEntity(params);
		       HttpResponse response = httpClient.execute(request);

		}catch (Exception ex) {

		       //handle exception here

		} finally {
		       //Deprecated
		       //httpClient.getConnectionManager().shutdown(); 
		}
	}
}
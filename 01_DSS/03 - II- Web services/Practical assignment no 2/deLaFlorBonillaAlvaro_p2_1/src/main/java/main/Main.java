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
		
		sendJsonPost("http://localhost:8080/deLaFlorBonillaAlvaro/api/usuario", "{\r\n"
				+ "\"dni\":\"15408846L\",\r\n"
				+ "\"nombre\":\"Alvaro\",\r\n"
				+ "\"apellidos\":\"de la Flor Bonilla\",\r\n"
				+ "\"email\":\"alvdebon@correo.ugr.es\",\r\n"
				+ "\"esAdministrador\":false\r\n"
				+ "}");
		
		sendJsonPost("http://localhost:8080/deLaFlorBonillaAlvaro/api/usuario", "{\r\n"
				+ "\"dni\":\"15408845W\",\r\n"
				+ "\"nombre\":\"Alberto\",\r\n"
				+ "\"apellidos\":\"Gomez Blanco\",\r\n"
				+ "\"email\":\"alberto@correo.ugr.es\",\r\n"
				+ "\"esAdministrador\":false\r\n"
				+ "}");
		
		sendJsonPost("http://localhost:8080/deLaFlorBonillaAlvaro/api/camiseta", "{\r\n"
				+ "\"id\":\"AOAO24O2\",\r\n"
				+ "\"modelo\":\"GAP Verano\",\r\n"
				+ "\"talla\":\"XL\",\r\n"
				+ "\"precio\":21.32,\r\n"
				+ "\"vendida\":false,\r\n"
				+ "\"image\":\"https://cdn.grupoelcorteingles.es/SGFM/dctm/MEDIA03/201912/23/00185641500013____2__516x640.jpg\"\r\n"
				+ "}");
		
		sendJsonPost("http://localhost:8080/deLaFlorBonillaAlvaro/api/camiseta", "{\r\n"
				+ "\"id\":\"AOAO24O3\",\r\n"
				+ "\"modelo\":\"Polo Verano\",\r\n"
				+ "\"talla\":\"XL\",\r\n"
				+ "\"precio\":21.32,\r\n"
				+ "\"vendida\":false,\r\n"
				+ "\"image\":\"https://cdn-images.farfetch-contents.com/14/15/22/04/14152204_19009338_1000.jpg\"\r\n"
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
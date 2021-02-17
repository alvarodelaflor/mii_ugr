package test;

import java.net.URI;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;

//import mio.jersey.segundo.maven.modelo.Todo;

public class Probador {

	public static void main(String[] args) {

//			ClientConfig config = new ClientConfig();
//			Client cliente = ClientBuilder.newClient(config);
//			WebTarget servicio = cliente.target(getBaseURI());
//				
//			//crearse un todo
//			Todo todo= new Todo("99", "Este es un resumen de otro registro");
//			Response respuesta = servicio.path("rest").path("todos").path(todo.getId()).
//			request(MediaType.APPLICATION_XML).
//			put(Entity.entity(todo, MediaType.APPLICATION_XML), Response.class);
//
//			// Si devuelve el codigo 201 == recurso creado
//			System.out.println(respuesta.getStatus());
//    
//			//Obtener todos
//			System.out.println(servicio.path("rest").path("todos").
//					request().accept(MediaType.TEXT_XML).get(String.class));
//			//Obtener JSON de aplicacion  
//			System.out.println(servicio.path("rest").path("todos").
//					request().accept(MediaType.APPLICATION_JSON).get(String.class));
// 
//			// Obtener XML para aplicaciones
//			System.out.println(servicio.path("rest").path("todos").
//					request().accept(MediaType.APPLICATION_XML).get(String.class));
//
//			//Obtener el Todo con identificador  1
//			Response obtenido = servicio.path("rest").path("todos/1").
//					request().accept(MediaType.APPLICATION_XML).get();
//			System.out.println(obtenido);
//
//			//Eliminar el Todo con identificador 1
//			servicio.path("rest").path("todos/1").request().delete();
//
//			//Comprobar que todos los Todos con identificador 1 han debido ser eliminados
//			System.out.println(servicio.path("rest").path("todos").
//					request().accept(MediaType.APPLICATION_XML).get(String.class));
//    
//			//Crearse un Todo con ayuda de un formulario
//			Form form =new Form();
//			form.param("id", "4");
//			form.param("resumen","Demostracion del cliente lib para formularios");
//			respuesta = servicio.path("rest").path("todos").
//					request().post(Entity.entity(form,MediaType.APPLICATION_FORM_URLENCODED),Response.class);
//			System.out.println("Formulario respuesta " + respuesta.getStatus());
//			
//			//Obtener todos los todos, con identificador 4 que han debido ser creados
//			System.out.println(servicio.path("rest").path("todos").
//					request().accept(MediaType.APPLICATION_XML).get(String.class));
}

	private static URI getBaseURI() {
		return UriBuilder.fromUri("http://localhost:8080/mio.jersey.segundo.maven").build();
	}

}

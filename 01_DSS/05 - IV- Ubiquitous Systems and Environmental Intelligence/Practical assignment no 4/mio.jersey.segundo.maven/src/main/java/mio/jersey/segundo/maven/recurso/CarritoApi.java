package mio.jersey.segundo.maven.recurso;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import mio.jersey.segundo.maven.modelo.Respuesta;
import mio.jersey.segundo.maven.modelo.Carrito;
import mio.jersey.segundo.maven.modelo.CarritoDao;

import java.util.ArrayList;
import java.util.List;

@Path("/carrito")
public class CarritoApi {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	
	// Obtener todas las carritos existentes en el sistema /////////////
	@GET
	@Produces(MediaType.TEXT_XML)
	public List<Carrito> obtenerTodasCarritosNavegador() {
		List<Carrito> carritos = new ArrayList<Carrito>();
		carritos.addAll(CarritoDao.INSTANCE.obtenerTodasCarritos());
		return carritos;
	}
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<Carrito> obtenerTodasCarritos() {
		List<Carrito> carritos = new ArrayList<Carrito>();
		carritos.addAll(CarritoDao.INSTANCE.obtenerTodasCarritos());
		return carritos;
	}
	////////////////////////////////////////////////////////////////////
	
	// Obtener carrito buscando por DNI ////////////////////////////////
	@GET
	@Produces(MediaType.TEXT_XML)
	@Path("{id}")
	public Carrito obtenerCarritoNavegador(@PathParam("id") String id) {
		return CarritoDao.INSTANCE.obtenerCarritoPorId(id);
	}
	
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("{id}")
	public Carrito obtenerCarrito(@PathParam("id") String id) {
		return CarritoDao.INSTANCE.obtenerCarritoPorId(id);
	}
	////////////////////////////////////////////////////////////////////
	
	// Obtener carrito buscando por USUARIO ID ////////////////////////////
	@GET
	@Produces(MediaType.TEXT_XML)
	@Path("usuario/{id}")
	public List<Carrito> obtenerCarritoPorNombreNavegador(@PathParam("id") String id) {
		return CarritoDao.INSTANCE.obtenerCarritosPorUsuarioId(id);
	}
	
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("usuario/{id}")
	public List<Carrito> obtenerCarritosPorNombre(@PathParam("id") String id) {
		return CarritoDao.INSTANCE.obtenerCarritosPorUsuarioId(id);
	}
	////////////////////////////////////////////////////////////////////
	
	
	// Editar un carrito /////////////////////////////////////////////
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("{id}")
	public Respuesta update(@PathParam("id") String id, Carrito carrito) {
	    carrito.setId(id);
	    if (CarritoDao.INSTANCE.editarCarrito(carrito)) {
	        return new Respuesta("Se ha modificado correctamente la entidad", carrito.toString());
	    } else {
	        return new Respuesta("No se ha modificado la entidad. Error", carrito.toString());
	    }
	}
	//////////////////////////////////////////////////////////////////////
	
	// Añadir un nueva carrito /////////////////////////////////////////
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Respuesta anadirCarrito(Carrito carrito) {
	    if (CarritoDao.INSTANCE.anadirCarrito(carrito)) {
	        return new Respuesta("Se ha creado correctamente la entidad", carrito.toString());
	    } else {
	        return new Respuesta("No se ha creado la entidad. Error", carrito.toString());
	    }
	}
	////////////////////////////////////////////////////////////////////
	
	// Borrar un carrito ////////////////////////////////////////////
	@DELETE
	@Produces(MediaType.TEXT_XML)
	@Path("{id}")
	public Respuesta borrarCarritoNavegador(@PathParam("id") String id) {
		if (CarritoDao.INSTANCE.borrarCarritoPorID(id)) {
			return new Respuesta("Se ha borrado correctamente", "Entidad con id" + id);
		} else {
			return new Respuesta("No se ha borrado", "Entidad con id" + id);
		}
	}
	
	@DELETE
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("{id}")
	public Respuesta borrarCarrito(@PathParam("id") String id) {
		if (CarritoDao.INSTANCE.borrarCarritoPorID(id)) {
			return new Respuesta("Se ha borrado correctamente", "Entidad con id" + id);
		} else {
			return new Respuesta("No se ha borrado", "Entidad con id: " + id);
		}
	}
	///////////////////////////////////////////////////////////////////
	
	// Añadir camiseta a carrito /////////////////////////////////////
	@GET
	@Produces(MediaType.TEXT_XML)
	@Path("{id1}/camiseta/{id2}")
	public Carrito anadirNuevaCamisetaNavegador(@PathParam("id1") String id1, @PathParam("id2") String id2) {
		return CarritoDao.INSTANCE.anadirNuevaCamiseta(id1, id2, "");
	}
	
	@POST
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("{id1}/camiseta/{id2}")
	public Carrito anadirNuevaCamiseta(String envio, @PathParam("id1") String id1, @PathParam("id2") String id2) {
		return CarritoDao.INSTANCE.anadirNuevaCamiseta(id1, id2, envio);
	}
	/////////////////////////////////////////////////////////////////
	
	// Borrar camiseta del carrito //////////////////////////////////
	@DELETE
	@Produces(MediaType.TEXT_XML)
	@Path("{id1}/camiseta/{id2}")
	public Carrito borrarCamisetaNavegador(@PathParam("id1") String id1, @PathParam("id2") String id2) {
		return CarritoDao.INSTANCE.borrarCamiseta(id1, id2);
	}
	
	@DELETE
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("{id1}/camiseta/{id2}")
	public Carrito borrarNuevaCamiseta(@PathParam("id1") String id1, @PathParam("id2") String id2) {
		return CarritoDao.INSTANCE.borrarCamiseta(id1, id2);
	}
	/////////////////////////////////////////////////////////////////
}
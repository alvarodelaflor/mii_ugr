package deLaFlorBonillaAlvaro.recurso;

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

import deLaFlorBonillaAlvaro.modelo.Camiseta;
import deLaFlorBonillaAlvaro.modelo.CamisetaDao;
import deLaFlorBonillaAlvaro.modelo.Respuesta;

import java.util.ArrayList;
import java.util.List;

@Path("/camiseta")
public class CamisetaApi {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	
	// Obtener todas las camisetas existentes en el sistema /////////////
	@GET
	@Produces(MediaType.TEXT_XML)
	public List<Camiseta> obtenerTodasCamisetasNavegador() {
		List<Camiseta> camisetas = new ArrayList<Camiseta>();
		camisetas.addAll(CamisetaDao.INSTANCE.obtenerTodasCamisetas());
		return camisetas;
	}
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<Camiseta> obtenerTodasCamisetas() {
		List<Camiseta> camisetas = new ArrayList<Camiseta>();
		camisetas.addAll(CamisetaDao.INSTANCE.obtenerTodasCamisetas());
		return camisetas;
	}
	////////////////////////////////////////////////////////////////////
	
	// Obtener camiseta buscando por ID ////////////////////////////////
	@GET
	@Produces(MediaType.TEXT_XML)
	@Path("{id}")
	public Camiseta obtenerCamisetaNavegador(@PathParam("id") String id) {
		return CamisetaDao.INSTANCE.obtenerCamisetaPorID(id);
	}
	
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("{id}")
	public Camiseta obtenerCamiseta(@PathParam("id") String id) {
		return CamisetaDao.INSTANCE.obtenerCamisetaPorID(id);
	}
	////////////////////////////////////////////////////////////////////
	
	// Obtener camiseta buscando por MODELO ////////////////////////////
	@GET
	@Produces(MediaType.TEXT_XML)
	@Path("modelo/{modelo}")
	public List<Camiseta> obtenerCamisetaPorModeloNavegador(@PathParam("modelo") String modelo) {
		return CamisetaDao.INSTANCE.obtenerCamisetasPorModelo(modelo);
	}
	
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("modelo/{modelo}")
	public List<Camiseta> obtenerCamisetasPorModelo(@PathParam("modelo") String modelo) {
		return CamisetaDao.INSTANCE.obtenerCamisetasPorModelo(modelo);
	}
	////////////////////////////////////////////////////////////////////
	
	// Obtener camiseta buscando por TALLA ////////////////////////////
	@GET
	@Produces(MediaType.TEXT_XML)
	@Path("talla/{talla}")
	public List<Camiseta> obtenerCamisetaPorTallaNavegador(@PathParam("talla") String talla) {
		return CamisetaDao.INSTANCE.obtenerCamisetasPorTalla(talla);
	}
	
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("talla/{talla}")
	public List<Camiseta> obtenerCamisetasPorTalla(@PathParam("talla") String talla) {
		return CamisetaDao.INSTANCE.obtenerCamisetasPorTalla(talla);
	}
	////////////////////////////////////////////////////////////////////
	
	// Obtener camiseta buscando por PRECIO ////////////////////////////
	@GET
	@Produces(MediaType.TEXT_XML)
	@Path("precio/{precio}")
	public List<Camiseta> obtenerCamisetaPorPrecioNavegador(@PathParam("precio") String precio) {
		return CamisetaDao.INSTANCE.obtenerCamisetasPorPrecio(precio);
	}
	
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("precio/{precio}")
	public List<Camiseta> obtenerCamisetasPorPrecio(@PathParam("precio") String precio) {
		return CamisetaDao.INSTANCE.obtenerCamisetasPorPrecio(precio);
	}
	////////////////////////////////////////////////////////////////////
	
	// Editar una camiseta /////////////////////////////////////////////
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("{id}")
	public Respuesta update(@PathParam("id") String id, Camiseta camiseta) {
	    camiseta.setId(id);
	    if (CamisetaDao.INSTANCE.editarCamiseta(camiseta)) {
	        return new Respuesta("Se ha modificado correctamente la entidad", camiseta.toString());
	    } else {
	        return new Respuesta("No se ha modificado la entidad. Error", camiseta.toString());
	    }
	}
	//////////////////////////////////////////////////////////////////////
	
	// Añadir una nueva camiseta /////////////////////////////////////////
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Respuesta anadirCamiseta(Camiseta camiseta) {
	    if (CamisetaDao.INSTANCE.anadirCamiseta(camiseta)) {
	        return new Respuesta("Se ha creado correctamente la entidad", camiseta.toString());
	    } else {
	        return new Respuesta("No se ha creado la entidad. Error", camiseta.toString());
	    }
	}
	////////////////////////////////////////////////////////////////////
	
	// Borrar una camiseta ////////////////////////////////////////////
	@DELETE
	@Produces(MediaType.TEXT_XML)
	@Path("{id}")
	public Respuesta borrarCamisetaNavegador(@PathParam("id") String id) {
		if (CamisetaDao.INSTANCE.borrarCamisetaPorID(id)) {
			return new Respuesta("Se ha borrado correctamente", "Entidad con id" + id);
		} else {
			return new Respuesta("No se ha borrado", "Entidad con id" + id);
		}
	}
	
	@DELETE
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("{id}")
	public Respuesta borrarCamiseta(@PathParam("id") String id) {
		if (CamisetaDao.INSTANCE.borrarCamisetaPorID(id)) {
			return new Respuesta("Se ha borrado correctamente", "Entidad con id" + id);
		} else {
			return new Respuesta("No se ha borrado", "Entidad con id: " + id);
		}
	}
	///////////////////////////////////////////////////////////////////
}
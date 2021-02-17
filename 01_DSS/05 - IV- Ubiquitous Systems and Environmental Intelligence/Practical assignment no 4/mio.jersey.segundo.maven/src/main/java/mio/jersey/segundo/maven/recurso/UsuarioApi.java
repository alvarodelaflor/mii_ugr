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
import mio.jersey.segundo.maven.modelo.Usuario;
import mio.jersey.segundo.maven.modelo.UsuarioDao;

import java.util.ArrayList;
import java.util.List;

@Path("/usuario")
public class UsuarioApi {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	
	// Obtener todas las usuarios existentes en el sistema /////////////
	@GET
	@Produces(MediaType.TEXT_XML)
	public List<Usuario> obtenerTodasUsuariosNavegador() {
		List<Usuario> usuarios = new ArrayList<Usuario>();
		usuarios.addAll(UsuarioDao.INSTANCE.obtenerTodasUsuarios());
		return usuarios;
	}
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<Usuario> obtenerTodasUsuarios() {
		List<Usuario> usuarios = new ArrayList<Usuario>();
		usuarios.addAll(UsuarioDao.INSTANCE.obtenerTodasUsuarios());
		return usuarios;
	}
	////////////////////////////////////////////////////////////////////
	
	// Obtener usuario buscando por DNI ////////////////////////////////
	@GET
	@Produces(MediaType.TEXT_XML)
	@Path("{dni}")
	public Usuario obtenerUsuarioNavegador(@PathParam("dni") String dni) {
		return UsuarioDao.INSTANCE.obtenerUsuarioPorDni(dni);
	}
	
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("{dni}")
	public Usuario obtenerUsuario(@PathParam("dni") String dni) {
		return UsuarioDao.INSTANCE.obtenerUsuarioPorDni(dni);
	}
	////////////////////////////////////////////////////////////////////
	
	// Obtener usuario buscando por NOMBRE ////////////////////////////
	@GET
	@Produces(MediaType.TEXT_XML)
	@Path("nombre/{nombre}")
	public List<Usuario> obtenerUsuarioPorNombreNavegador(@PathParam("nombre") String nombre) {
		return UsuarioDao.INSTANCE.obtenerUsuariosPorNombre(nombre);
	}
	
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("nombre/{nombre}")
	public List<Usuario> obtenerUsuariosPorNombre(@PathParam("nombre") String nombre) {
		return UsuarioDao.INSTANCE.obtenerUsuariosPorNombre(nombre);
	}
	////////////////////////////////////////////////////////////////////
	
	// Obtener usuario buscando por APELLIDOS ////////////////////////////
	@GET
	@Produces(MediaType.TEXT_XML)
	@Path("apellidos/{apellidos}")
	public List<Usuario> obtenerUsuarioPorApellidosNavegador(@PathParam("apellidos") String apellidos) {
		return UsuarioDao.INSTANCE.obtenerUsuariosPorApellidos(apellidos);
	}
	
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("apellidos/{apellidos}")
	public List<Usuario> obtenerUsuariosPorApellidos(@PathParam("apellidos") String apellidos) {
		return UsuarioDao.INSTANCE.obtenerUsuariosPorApellidos(apellidos);
	}
	////////////////////////////////////////////////////////////////////
	
	// Obtener usuario buscando por EMAIL ////////////////////////////
	@GET
	@Produces(MediaType.TEXT_XML)
	@Path("email/{email}")
	public List<Usuario> obtenerUsuarioPorEmailNavegador(@PathParam("email") String email) {
		return UsuarioDao.INSTANCE.obtenerUsuariosPorEmail(email);
	}
	
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("email/{email}")
	public List<Usuario> obtenerUsuariosPorEmail(@PathParam("email") String email) {
		return UsuarioDao.INSTANCE.obtenerUsuariosPorEmail(email);
	}
	////////////////////////////////////////////////////////////////////
	
	// Editar un usuario /////////////////////////////////////////////
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("{id}")
	public Respuesta update(@PathParam("id") String id, Usuario usuario) {
	    usuario.setDni(id);
	    if (UsuarioDao.INSTANCE.editarUsuario(usuario)) {
	        return new Respuesta("Se ha modificado correctamente la entidad", usuario.toString());
	    } else {
	        return new Respuesta("No se ha modificado la entidad. Error", usuario.toString());
	    }
	}
	//////////////////////////////////////////////////////////////////////
	
	// Añadir un nueva usuario /////////////////////////////////////////
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Respuesta anadirUsuario(Usuario usuario) {
	    if (UsuarioDao.INSTANCE.anadirUsuario(usuario)) {
	        return new Respuesta("Se ha creado correctamente la entidad", usuario.toString());
	    } else {
	        return new Respuesta("No se ha creado la entidad. Error", usuario.toString());
	    }
	}
	////////////////////////////////////////////////////////////////////
	
	// Borrar un usuario ////////////////////////////////////////////
	@DELETE
	@Produces(MediaType.TEXT_XML)
	@Path("{id}")
	public Respuesta borrarUsuarioNavegador(@PathParam("id") String id) {
		if (UsuarioDao.INSTANCE.borrarUsuarioPorID(id)) {
			return new Respuesta("Se ha borrado correctamente", "Entidad con id" + id);
		} else {
			return new Respuesta("No se ha borrado", "Entidad con id" + id);
		}
	}
	
	@DELETE
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("{id}")
	public Respuesta borrarUsuario(@PathParam("id") String id) {
		if (UsuarioDao.INSTANCE.borrarUsuarioPorID(id)) {
			return new Respuesta("Se ha borrado correctamente", "Entidad con id" + id);
		} else {
			return new Respuesta("No se ha borrado", "Entidad con id: " + id);
		}
	}
	///////////////////////////////////////////////////////////////////
}
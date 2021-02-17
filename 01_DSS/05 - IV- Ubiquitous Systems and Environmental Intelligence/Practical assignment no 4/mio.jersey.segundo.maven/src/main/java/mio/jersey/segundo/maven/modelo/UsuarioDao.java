package mio.jersey.segundo.maven.modelo;
import java.util.ArrayList;
import java.util.List;

import repository.UsuarioRepositorio;

public enum UsuarioDao {
	INSTANCE;
	private UsuarioDao() {
	}
	
	public List<Usuario> obtenerTodasUsuarios() {
		return UsuarioRepositorio.obtenerTodasUsuarios();
	}
	
	public Usuario obtenerUsuarioPorDni(String id) {
		return UsuarioRepositorio.obtenerUsuarioPorDni(id);
	}
	
	public List<Usuario> obtenerUsuariosPorNombre(String nombre) {
		return UsuarioRepositorio.obtenerUsuariosPorAtributo("nombre", nombre);
	}
	
	public List<Usuario> obtenerUsuariosPorApellidos(String apellidos) {
		return UsuarioRepositorio.obtenerUsuariosPorAtributo("apellidos", apellidos);
	}

	public List<Usuario> obtenerUsuariosPorEmail(String email) {
		return UsuarioRepositorio.obtenerUsuariosPorAtributo("email", email);
	}

	public List<Usuario> obtenerUsuariosPorRol(String rol) {
		List<Usuario> res;
		try {
			Boolean rolAux = Boolean.valueOf(rol);
			res = UsuarioRepositorio.obtenerUsuariosPorAtributo("rol", rolAux);
		} catch (Exception e) {
			res = new ArrayList<>();
		}
		return res;
	}

	public Boolean editarUsuario(Usuario usuario) {
		Boolean res = true;
		Usuario aux = UsuarioRepositorio.obtenerUsuarioPorDni(usuario.getDni());
		if (aux != null) {
			aux.setNombre(usuario.getNombre());
			aux.setApellidos(usuario.getApellidos());
			aux.setDni(usuario.getDni());
			aux.setEmail(usuario.getEmail());
			aux.setEsAdministrador(usuario.getEsAdministrador());
			res = UsuarioRepositorio.actualizarUsuario(aux);
		} else {
			res = false;
		}
		return res;
	}

	public Boolean anadirUsuario(Usuario usuario) {
		Boolean res = true;
		Usuario aux = UsuarioRepositorio.obtenerUsuarioPorDni(usuario.getDni());
		if (aux == null) {
			res = UsuarioRepositorio.anadirUsuario(usuario.getDni(), usuario.getNombre(), usuario.getApellidos(), usuario.getEmail(), usuario.getEsAdministrador()) != null ? true : false;
		} else {
			res = false;
		}
		return res;
	}

	public Boolean borrarUsuarioPorID(String id) {
		Boolean res = true;
		Usuario aux = UsuarioRepositorio.obtenerUsuarioPorDni(id);
		if (aux != null) {
			res = UsuarioRepositorio.borrarUsuario(aux) ? true : false;
		} else {
			res = false;
		}
		return res;
	}
}
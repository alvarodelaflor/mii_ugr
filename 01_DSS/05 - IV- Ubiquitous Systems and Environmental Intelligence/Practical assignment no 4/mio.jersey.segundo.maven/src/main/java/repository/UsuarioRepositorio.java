package repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import mio.jersey.segundo.maven.modelo.Carrito;
import mio.jersey.segundo.maven.modelo.Usuario;

public class UsuarioRepositorio {
	
	private static final String PERSISTENCE_UNIT_NAME = "tienda";
	private static EntityManagerFactory factoria;
	
	public static List<Usuario> obtenerTodasUsuarios() {
		factoria = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factoria.createEntityManager();
		Query q2 = em.createQuery("select c from Usuario c");
		@SuppressWarnings("unchecked")
		List<Usuario> res = q2.getResultList();
		em.close();
		return res;
	}

	public static Usuario obtenerUsuarioPorDni(String id) {
		Usuario res;
		factoria = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factoria.createEntityManager();
		TypedQuery<Usuario> query = em.createQuery("SELECT c FROM Usuario c WHERE c.dni = '" + id + "'", Usuario.class);
		try {
			res = query.getSingleResult();
		} catch (NoResultException e) {
			res = null;
		}
		em.close();
		return res;
	}
	
	public static <E> List<Usuario> obtenerUsuariosPorAtributo(String atributo, E valor) {
		factoria = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factoria.createEntityManager();
		String aux = "'";
		if (valor.getClass().equals(String.class)) {
			aux = "'";
		} else {
			aux = "";
		}
		Query q2 = em.createQuery("select c from Usuario c where c." + atributo + " = " + aux + valor + aux);
		@SuppressWarnings("unchecked")
		List<Usuario> res = q2.getResultList();
		em.close();
		return res;
	}

	public static Usuario anadirUsuario(String dni, String nombre, String apellidos, String email, Boolean esAdministrador) {
		Usuario res = null;
		try {
			factoria = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
			EntityManager em = factoria.createEntityManager();
			Usuario usuario = new Usuario(dni, nombre, apellidos, email, esAdministrador);
			Carrito carrito = new Carrito(usuario.getDni()+"$carrito", usuario, new ArrayList<>());
			em.getTransaction().begin();
			em.merge(usuario);
			em.merge(carrito);
			em.getTransaction().commit();
			em.close();
			res = usuario;
		} catch (Exception e) {
			res = null;
		}
		return res;
	}
	
	public static Boolean actualizarUsuario(Usuario usuario) {
		Boolean res = true;
		try {
			factoria = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
			EntityManager em = factoria.createEntityManager();
			em.getTransaction().begin();
			em.merge(usuario);
			em.getTransaction().commit();
			em.close();	
		} catch (Exception e) {
			res = false;
		}
		return res;
	}

	public static Boolean borrarUsuario(Usuario usuario) {
		Boolean res = true;
		try {
			factoria = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
			EntityManager em = factoria.createEntityManager();
			Usuario aux = em.createQuery("SELECT c FROM Usuario c WHERE c.dni = '" + usuario.getDni() + "'", Usuario.class).getSingleResult();
			em.getTransaction().begin();
			em.remove(aux);
			em.getTransaction().commit();
			em.close();	
		} catch (Exception e) {
			res = false;
		}
		return res;
	}
}
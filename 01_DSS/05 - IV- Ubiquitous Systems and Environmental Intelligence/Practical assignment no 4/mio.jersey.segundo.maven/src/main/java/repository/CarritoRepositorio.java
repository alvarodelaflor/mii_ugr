package repository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import mio.jersey.segundo.maven.modelo.Camiseta;
import mio.jersey.segundo.maven.modelo.Carrito;
import mio.jersey.segundo.maven.modelo.Usuario;

public class CarritoRepositorio {
	
	private static final String PERSISTENCE_UNIT_NAME = "tienda";
	private static EntityManagerFactory factoria;
	
	public static List<Carrito> obtenerTodasCarritos() {
		factoria = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factoria.createEntityManager();
		Query q2 = em.createQuery("select c from Carrito c");
		@SuppressWarnings("unchecked")
		List<Carrito> res = q2.getResultList();
		em.close();
		return res;
	}

	public static Carrito obtenerCarritoPorId(String id) {
		Carrito res;
		factoria = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factoria.createEntityManager();
		TypedQuery<Carrito> query = em.createQuery("SELECT c FROM Carrito c WHERE c.id = '" + id + "'", Carrito.class);
		try {
			res = query.getSingleResult();
		} catch (NoResultException e) {
			res = null;
		}
		em.close();
		return res;
	}
	
	public static <E> List<Carrito> obtenerCarritosPorAtributo(String atributo, E valor) {
		factoria = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factoria.createEntityManager();
		String aux = "'";
		if (valor.getClass().equals(String.class)) {
			aux = "'";
		} else {
			aux = "";
		}
		Query q2 = em.createQuery("select c from Carrito c where c." + atributo + " = " + aux + valor + aux);
		@SuppressWarnings("unchecked")
		List<Carrito> res = q2.getResultList();
		em.close();
		return res;
	}

	public static Carrito anadirCarritoNavegador(String id, Usuario usuario, List<Camiseta> camisetas) {
		Carrito res = null;
		try {
			factoria = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
			EntityManager em = factoria.createEntityManager();
			Carrito carrito = new Carrito(id, usuario, camisetas);
			em.getTransaction().begin();
			em.merge(carrito);
			em.getTransaction().commit();
			em.close();
			res = carrito;
		} catch (Exception e) {
			res = null;
		}
		return res;
	}
	
	public static Boolean actualizarCarrito(Carrito carrito) {
		Boolean res = true;
		try {
			factoria = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
			EntityManager em = factoria.createEntityManager();
			em.getTransaction().begin();
			em.merge(carrito);
			em.getTransaction().commit();
			em.close();	
		} catch (Exception e) {
			res = false;
		}
		return res;
	}

	public static Boolean borrarCarrito(Carrito carrito) {
		Boolean res = true;
		try {
			factoria = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
			EntityManager em = factoria.createEntityManager();
			Carrito aux = em.createQuery("SELECT c FROM Carrito c WHERE c.id = '" + carrito.getId() + "'", Carrito.class).getSingleResult();
			em.getTransaction().begin();
			em.remove(aux);
			em.getTransaction().commit();
			em.close();	
		} catch (Exception e) {
			res = false;
		}
		return res;
	}

	public static Carrito anadirCamiseta(String id1, String id2, String envio) {
		Carrito res = obtenerCarritoPorId(id1);
		if (res != null) {
			Camiseta camiseta = CamisetaRepositorio.obtenerCamisetaPorID(id2);
			if (camiseta != null && !camiseta.getVendida()) {
				try {
					factoria = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
					EntityManager em = factoria.createEntityManager();
					em.getTransaction().begin();
					camiseta.setVendida(true);
					camiseta.setEnvio(envio);
					CamisetaRepositorio.actualizarCamiseta(camiseta);
					if (res.getCamisetas() != null) {
						res.getCamisetas().add(camiseta);
					} else {
						res.setCamisetas(Arrays.asList(camiseta));
					}
					actualizarCarrito(res);
					em.getTransaction().commit();
					em.close();	
				} catch (Exception e) {
					System.out.println(e);
				}
			}
		}
		return res;
	}

	public static Carrito borrarCamiseta(String id1, String id2) {
		Carrito res = obtenerCarritoPorId(id1);
		if (res != null) {
			Camiseta camiseta = CamisetaRepositorio.obtenerCamisetaPorID(id2);
			if (camiseta != null && camiseta.getVendida()) {
				try {
					factoria = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
					EntityManager em = factoria.createEntityManager();
					em.getTransaction().begin();
					camiseta.setVendida(false);
					CamisetaRepositorio.actualizarCamiseta(camiseta);
					if (res.getCamisetas() != null) {
						List<Camiseta> auxCamisetas = res.getCamisetas();
						auxCamisetas = auxCamisetas.stream().filter(x -> !x.getId().equals(camiseta.getId())).collect(Collectors.toList());
						res.setCamisetas(auxCamisetas);
					}
					actualizarCarrito(res);
					em.getTransaction().commit();
					em.close();	
				} catch (Exception e) {
					System.out.println(e);
				}
			}
		}
		return res;
	}
}
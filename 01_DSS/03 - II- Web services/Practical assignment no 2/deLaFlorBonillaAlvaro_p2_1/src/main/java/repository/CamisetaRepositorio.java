package repository;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import deLaFlorBonillaAlvaro.modelo.Camiseta;
import deLaFlorBonillaAlvaro.modelo.Carrito;

public class CamisetaRepositorio {
	
	private static final String PERSISTENCE_UNIT_NAME = "tienda";
	private static EntityManagerFactory factoria;
	
	public static List<Camiseta> obtenerTodasCamisetas() {
		factoria = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factoria.createEntityManager();
		Query q2 = em.createQuery("select c from Camiseta c");
		@SuppressWarnings("unchecked")
		List<Camiseta> res = q2.getResultList();
		em.close();
		return res;
	}

	public static Camiseta obtenerCamisetaPorID(String id) {
		Camiseta res;
		factoria = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factoria.createEntityManager();
		TypedQuery<Camiseta> query = em.createQuery("SELECT c FROM Camiseta c WHERE c.id = '" + id + "'", Camiseta.class);
		try {
			res = query.getSingleResult();
		} catch (NoResultException e) {
			res = null;
		}
		em.close();
		return res;
	}
	
	public static <E> List<Camiseta> obtenerCamisetasPorAtributo(String atributo, E valor) {
		factoria = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factoria.createEntityManager();
		String aux = "'";
		if (valor.getClass().equals(String.class)) {
			aux = "'";
		} else {
			aux = "";
		}
		Query q2 = em.createQuery("select c from Camiseta c where c." + atributo + " = " + aux + valor + aux);
		@SuppressWarnings("unchecked")
		List<Camiseta> res = q2.getResultList();
		em.close();
		return res;
	}

	public static Camiseta anadirCamisetaNavegador(String id, String modelo, Double precio, String talla, Boolean vendida, String image) {
		Camiseta res = null;
		try {
			factoria = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
			EntityManager em = factoria.createEntityManager();
			Camiseta camiseta = new Camiseta(id, modelo, talla, precio, vendida, image);
			em.getTransaction().begin();
			em.merge(camiseta);
			em.getTransaction().commit();
			em.close();
			res = camiseta;
		} catch (Exception e) {
			res = null;
		}
		return res;
	}
	
	public static Boolean actualizarCamiseta(Camiseta camiseta) {
		Boolean res = true;
		try {
			factoria = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
			EntityManager em = factoria.createEntityManager();
			em.getTransaction().begin();
			em.merge(camiseta);
			em.getTransaction().commit();
			em.close();	
		} catch (Exception e) {
			res = false;
		}
		return res;
	}

	public static Boolean borrarCamiseta(Camiseta camiseta) {
		Boolean res = true;
		try {
			factoria = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
			EntityManager em = factoria.createEntityManager();
			Camiseta aux = em.createQuery("SELECT c FROM Camiseta c WHERE c.id = '" + camiseta.getId() + "'", Camiseta.class).getSingleResult();
			em.getTransaction().begin();
			if (camiseta.getVendida()) {
				Carrito carrito = em.createQuery("SELECT ca FROM Carrito ca JOIN ca.camisetas x WHERE x.id = '" + camiseta.getId() + "'", Carrito.class).getSingleResult();
				List<Camiseta> camisetas = carrito.getCamisetas();
				camisetas = camisetas.stream().filter(x -> !x.getId().equals(camiseta.getId())).collect(Collectors.toList());
				carrito.setCamisetas(camisetas);
				CarritoRepositorio.actualizarCarrito(carrito);
			}
			em.remove(aux);
			em.getTransaction().commit();
			em.close();	
		} catch (Exception e) {
			res = false;
		}
		return res;
	}
}
package deLaFlorBonillaAlvaro.modelo;
import java.util.List;

import repository.CarritoRepositorio;

public enum CarritoDao {
	INSTANCE;
	private CarritoDao() {
	}
	
	public List<Carrito> obtenerTodasCarritos() {
		return CarritoRepositorio.obtenerTodasCarritos();
	}
	
	public Carrito obtenerCarritoPorId(String id) {
		return CarritoRepositorio.obtenerCarritoPorId(id);
	}
	
	public List<Carrito> obtenerCarritosPorUsuarioId(String id) {
		return CarritoRepositorio.obtenerCarritosPorAtributo("id", id);
	}
	
	public Boolean editarCarrito(Carrito carrito) {
		Boolean res = true;
		Carrito aux = CarritoRepositorio.obtenerCarritoPorId(carrito.getId());
		if (aux != null) {
			aux.setUsuario(carrito.getUsuario());
			aux.setCamisetas(carrito.getCamisetas());
			res = CarritoRepositorio.actualizarCarrito(aux);
		} else {
			res = false;
		}
		return res;
	}

	public Boolean anadirCarrito(Carrito carrito) {
		Boolean res = true;
		Carrito aux = CarritoRepositorio.obtenerCarritoPorId(carrito.getId());
		if (aux == null) {
			res = CarritoRepositorio.anadirCarritoNavegador(carrito.getId(), carrito.getUsuario(), carrito.getCamisetas()) != null ? true : false;
		} else {
			res = false;
		}
		return res;
	}

	public Boolean borrarCarritoPorID(String id) {
		Boolean res = true;
		Carrito aux = CarritoRepositorio.obtenerCarritoPorId(id);
		if (aux != null) {
			res = CarritoRepositorio.borrarCarrito(aux) ? true : false;
		} else {
			res = false;
		}
		return res;
	}

	public Carrito anadirNuevaCamiseta(String id1, String id2) {
		return CarritoRepositorio.anadirCamiseta(id1, id2);
	}

	public Carrito borrarCamiseta(String id1, String id2) {
		return CarritoRepositorio.borrarCamiseta(id1, id2);
	}
}
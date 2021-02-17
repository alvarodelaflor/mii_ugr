package mio.jersey.segundo.maven.modelo;
import java.util.ArrayList;
import java.util.List;

import repository.CamisetaRepositorio;

public enum CamisetaDao {
	INSTANCE;
	private CamisetaDao() {
	}
	
	public List<Camiseta> obtenerTodasCamisetas() {
		return CamisetaRepositorio.obtenerTodasCamisetas();
	}
	
	public Camiseta obtenerCamisetaPorID(String id) {
		return CamisetaRepositorio.obtenerCamisetaPorID(id);
	}
	public List<Camiseta> obtenerCamisetasPorModelo(String modelo) {
		return CamisetaRepositorio.obtenerCamisetasPorAtributo("modelo", modelo);
	}

	public List<Camiseta> obtenerCamisetasPorTalla(String talla) {
		return CamisetaRepositorio.obtenerCamisetasPorAtributo("talla", talla);
	}
	
	public List<Camiseta> obtenerCamisetasPorUsuario(String id) {
		return CamisetaRepositorio.obtenerCamisetaPorUsuario(id);
	}

	public List<Camiseta> obtenerCamisetasPorPrecio(String precio) {
		List<Camiseta> res;
		try {
			Double precioAux = Double.valueOf(precio);
			res = CamisetaRepositorio.obtenerCamisetasPorAtributo("precio", precioAux);
		} catch (Exception e) {
			res = new ArrayList<>();
		}
		return res;
	}

	public Boolean editarCamiseta(Camiseta camiseta) {
		Boolean res = true;
		Camiseta aux = CamisetaRepositorio.obtenerCamisetaPorID(camiseta.getId());
		if (aux != null) {
			aux.setModelo(camiseta.getModelo());
			aux.setPrecio(camiseta.getPrecio());
			aux.setTalla(camiseta.getTalla());
			res = CamisetaRepositorio.actualizarCamiseta(aux);
		} else {
			res = false;
		}
		return res;
	}

	public Boolean anadirCamiseta(Camiseta camiseta) {
		Boolean res = true;
		Camiseta aux = CamisetaRepositorio.obtenerCamisetaPorID(camiseta.getId());
		if (aux == null) {
			res = CamisetaRepositorio.anadirCamisetaNavegador(camiseta.getId(), camiseta.getModelo(), camiseta.getPrecio(), camiseta.getTalla(), camiseta.getVendida(), camiseta.getImage(), camiseta.getImageShop(), camiseta.getLocation(), camiseta.getEnvio()) != null ? true : false;
		} else {
			res = false;
		}
		return res;
	}

	public Boolean borrarCamisetaPorID(String id) {
		Boolean res = true;
		Camiseta aux = CamisetaRepositorio.obtenerCamisetaPorID(id);
		if (aux != null) {
			res = CamisetaRepositorio.borrarCamiseta(aux) ? true : false;
		} else {
			res = false;
		}
		return res;
	}
}
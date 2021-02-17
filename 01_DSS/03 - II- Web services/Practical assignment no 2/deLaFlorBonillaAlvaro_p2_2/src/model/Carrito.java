package model;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public class Carrito {

	private String id;
	private Usuario usuario;
	private List<Camiseta> camisetas;
	
	public Carrito() {
	}
	
	public Carrito(String id, Usuario usuario, List<Camiseta> camisetas) {
		this.id = id;
		this.usuario = usuario;
		this.camisetas = camisetas;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public List<Camiseta> getCamisetas() {
		return camisetas;
	}
	public void setCamisetas(List<Camiseta> camisetas) {
		this.camisetas = camisetas;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((camisetas == null) ? 0 : camisetas.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Carrito other = (Carrito) obj;
		if (camisetas == null) {
			if (other.camisetas != null)
				return false;
		} else if (!camisetas.equals(other.camisetas))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (usuario == null) {
			if (other.usuario != null)
				return false;
		} else if (!usuario.equals(other.usuario))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Carrito [id=" + id + ", usuario=" + usuario + ", camisetas=" + camisetas + "]";
	}
}
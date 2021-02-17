package model;

public class Camiseta {

	private String id;
	private String modelo;
	private String talla;
	private Double precio;
	private Boolean vendida;
	private String image;
	
	public Camiseta() {
	}

	public Camiseta(String id, String modelo, String talla, Double precio, Boolean vendida, String image) {
		this.id = id;
		this.modelo = modelo;
		this.talla = talla;
		this.precio = precio;
		this.vendida = vendida;
		this.image = image;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getTalla() {
		return talla;
	}

	public void setTalla(String talla) {
		this.talla = talla;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	public Boolean getVendida() {
		return vendida;
	}

	public void setVendida(Boolean vendida) {
		this.vendida = vendida;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((image == null) ? 0 : image.hashCode());
		result = prime * result + ((modelo == null) ? 0 : modelo.hashCode());
		result = prime * result + ((precio == null) ? 0 : precio.hashCode());
		result = prime * result + ((talla == null) ? 0 : talla.hashCode());
		result = prime * result + ((vendida == null) ? 0 : vendida.hashCode());
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
		Camiseta other = (Camiseta) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (image == null) {
			if (other.image != null)
				return false;
		} else if (!image.equals(other.image))
			return false;
		if (modelo == null) {
			if (other.modelo != null)
				return false;
		} else if (!modelo.equals(other.modelo))
			return false;
		if (precio == null) {
			if (other.precio != null)
				return false;
		} else if (!precio.equals(other.precio))
			return false;
		if (talla == null) {
			if (other.talla != null)
				return false;
		} else if (!talla.equals(other.talla))
			return false;
		if (vendida == null) {
			if (other.vendida != null)
				return false;
		} else if (!vendida.equals(other.vendida))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Camiseta [id=" + id + ", modelo=" + modelo + ", talla=" + talla + ", precio=" + precio + ", vendida="
				+ vendida + ", image=" + image + "]";
	}
}
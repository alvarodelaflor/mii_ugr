package mio.jersey.segundo.maven.modelo;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
@Entity
@Table(name = "Camisetas")
public class Camiseta {
	@Id
	private String id;
	private String modelo;
	private String talla;
	private Double precio;
	private Boolean vendida;
	private String image;
	private String imageShop;
	private String location;
	private String envio;
	
	public Camiseta() {
	}

	public Camiseta(String id, String modelo, String talla, Double precio, Boolean vendida, String image, String imageShop, String location, String envio) {
		this.id = id;
		this.modelo = modelo;
		this.talla = talla;
		this.precio = precio;
		this.vendida = vendida;
		this.image = image;
		this.imageShop = imageShop;
		this.location = location;
		this.envio = envio;
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

	public String getImage_shop() {
		return imageShop;
	}

	public void setImage_shop(String image_shop) {
		this.imageShop = image_shop;
	}

	public String getImageShop() {
		return imageShop;
	}

	public void setImageShop(String imageShop) {
		this.imageShop = imageShop;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getEnvio() {
		return envio;
	}

	public void setEnvio(String envio) {
		this.envio = envio;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((envio == null) ? 0 : envio.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((image == null) ? 0 : image.hashCode());
		result = prime * result + ((imageShop == null) ? 0 : imageShop.hashCode());
		result = prime * result + ((location == null) ? 0 : location.hashCode());
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
		if (envio == null) {
			if (other.envio != null)
				return false;
		} else if (!envio.equals(other.envio))
			return false;
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
		if (imageShop == null) {
			if (other.imageShop != null)
				return false;
		} else if (!imageShop.equals(other.imageShop))
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
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
				+ vendida + ", image=" + image + ", imageShop=" + imageShop + ", location=" + location + ", envio="
				+ envio + "]";
	}
}
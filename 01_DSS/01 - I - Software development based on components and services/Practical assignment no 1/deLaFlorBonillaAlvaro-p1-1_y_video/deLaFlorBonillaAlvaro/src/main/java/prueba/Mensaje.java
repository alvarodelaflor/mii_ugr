package prueba;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean(name = "mensaje", eager = true)
@RequestScoped
public class Mensaje implements Serializable {
	private static final long serialVersionUID = 1L;
	private String mensaje;
	
	public Mensaje() {
		this.mensaje = "Constructor mensaje";
	}
	
	public String getMensaje() {
		return mensaje;
	}
	
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
}

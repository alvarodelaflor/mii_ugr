package prueba;
import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

@ManagedBean(name="holaMundo", eager=true)
@RequestScoped
public class HolaMundo implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	@ManagedProperty(value = "#{ mensaje }")
	
	private Mensaje mensajeBean;
	private String mensaje = "Nada aún";
	
	
	public HolaMundo(){
		System.out.println("Hola Mundo ha comenzado!");
	}
	
	
	public String getMensajeP() {
		return this.mensaje;
	}
	
	public String getMensaje(){
		mensaje = mensajeBean.getMensaje();
		return "Hola Mundo";
	}
	
	public void setMensajeBean(Mensaje mensaje) {
		mensajeBean = mensaje;
	}
}
package practica1;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean(name = "acceleration", eager = true)
@RequestScoped
public class Acceleration implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer acceleration;
	private Integer maxAcceleration;

	public Acceleration() {
		this.acceleration = 0;
		this.maxAcceleration = 5;
	}

	public Integer getAcceleration() {
		return acceleration;
	}

	public void setAcceleration(Integer acceleration) {
		this.acceleration = acceleration;
	}

	public Integer getMaxAcceleration() {
		return maxAcceleration;
	}

	public void setMaxAcceleration(Integer maxAcceleration) {
		this.maxAcceleration = maxAcceleration;
	}
	
}

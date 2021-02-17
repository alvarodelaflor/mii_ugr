package practica1;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

@ManagedBean(name="engineBean", eager=true)
@SessionScoped
public class Engine implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@ManagedProperty(value = "#{ acceleration }")
	private Acceleration acceleration = new Acceleration();
	private Status status = Status.OFF;
	
	public Engine() {
		System.out.println("Init Engine");
	}
	public Acceleration getAcceleration() {
		return acceleration;
	}
	public void setAcceleration(Acceleration acceleration) {
		this.acceleration = acceleration;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public void accelerate() {
		this.status = Status.ON;
		Acceleration accelerationAux = new Acceleration();
		if (acceleration.getAcceleration() <= acceleration.getMaxAcceleration()) {
			accelerationAux.setAcceleration(acceleration.getAcceleration() + 1);
			accelerationAux.setMaxAcceleration(acceleration.getMaxAcceleration());
			this.acceleration = accelerationAux;
			System.out.println("Accelerating");			
		} else {
			System.out.println("Not accelerating");
		}
	}
	public void stop() {
		this.status = Status.OFF;
		Acceleration accelerationAux = new Acceleration();
		accelerationAux.setMaxAcceleration(acceleration.getMaxAcceleration());
		accelerationAux.setAcceleration(0);
		this.acceleration = accelerationAux;
		System.out.println("Stop");
	}
	public void increase() {
		Acceleration accelerationAux = new Acceleration();
		accelerationAux.setMaxAcceleration(acceleration.getMaxAcceleration() + 1);
		accelerationAux.setAcceleration(acceleration.getAcceleration());
		this.acceleration = accelerationAux;
		System.out.println("Increase max acceleration");			
	}
	public void decrease() {
		Acceleration accelerationAux = new Acceleration();
		if (acceleration.getMaxAcceleration() > 1) {
			accelerationAux.setMaxAcceleration(acceleration.getMaxAcceleration() - 1);
			if (acceleration.getAcceleration() > accelerationAux.getMaxAcceleration()) {
				accelerationAux.setAcceleration(accelerationAux.getMaxAcceleration());
			} else {
				accelerationAux.setAcceleration(acceleration.getAcceleration());	
			}
			this.acceleration = accelerationAux;
			System.out.println("Decrease");
		
		} else {
			System.out.println("Minimum max acceleration is 1");	
		}
	}
	@Override
	public String toString() {
		return "Motor [acceleration=" + acceleration + ", status=" + status + "]";
	}

}

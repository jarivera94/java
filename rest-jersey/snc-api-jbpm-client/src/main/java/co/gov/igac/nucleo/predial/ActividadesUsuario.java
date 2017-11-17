package co.gov.igac.nucleo.predial;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ActividadesUsuario {

	@SerializedName("actividad")
	@Expose
	private String actividad;
	@SerializedName("usuarios")
	@Expose
	private List<Usuario> usuarios = null;
	@SerializedName("rol")
	@Expose
	private Object rol;

	public String getActividad() {
		return actividad;
	}

	public void setActividad(String actividad) {
		this.actividad = actividad;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public Object getRol() {
		return rol;
	}

	public void setRol(Object rol) {
		this.rol = rol;
	}
	
	public void destuyeUsuarios(){
		this.usuarios=null;
	}

}
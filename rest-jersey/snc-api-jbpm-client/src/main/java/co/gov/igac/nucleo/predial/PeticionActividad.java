package co.gov.igac.nucleo.predial;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PeticionActividad {

	@SerializedName("tipoPeticion")
	@Expose
	private Object tipoPeticion;
	@SerializedName("usuario")
	@Expose
	private Usuario usuario;
	@SerializedName("idActividad")
	@Expose
	private Object idActividad;
	@SerializedName("actividad")
	@Expose
	private Object actividad;

	@SerializedName("fechaReanudacion")
	@Expose
	private Integer fechaReanudacion;
	@SerializedName("motivo")
	@Expose
	private String motivo;

	public void setIdActividad(String idActividad) {
	this.idActividad = idActividad;
	}

	public Integer getFechaReanudacion() {
	return fechaReanudacion;
	}

	public void setFechaReanudacion(Integer fechaReanudacion) {
	this.fechaReanudacion = fechaReanudacion;
	}

	public String getMotivo() {
	return motivo;
	}

	public void setMotivo(String motivo) {
	this.motivo = motivo;
	}

	
	public Object getTipoPeticion() {
		return tipoPeticion;
	}
	public void setTipoPeticion(Object tipoPeticion) {
		this.tipoPeticion = tipoPeticion;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public Object getIdActividad() {
		return idActividad;
	}
	
	public Object getActividad() {
		return actividad;
	}
	public void setActividad(Object actividad) {
		this.actividad = actividad;
	}
}

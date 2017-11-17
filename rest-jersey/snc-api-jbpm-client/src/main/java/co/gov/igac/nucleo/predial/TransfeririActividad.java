package co.gov.igac.nucleo.predial;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TransfeririActividad {

	@SerializedName("tipoPeticion")
	@Expose
	private Object tipoPeticion;
	@SerializedName("usuario")
	@Expose
	private Usuario usuario;
	@SerializedName("idActividad")
	@Expose
	private Object idActividad;
	@SerializedName("actividades")
	@Expose
	private List<String> actividades = null;
	@SerializedName("usuarioActual")
	@Expose
	private Object usuarioActual;
	@SerializedName("nuevoUsuario")
	@Expose
	private Usuario nuevoUsuario;

	public Object getTipoPeticion() {
	return tipoPeticion;
	}

	public void setTipoPeticion(Object tipoPeticion) {
	this.tipoPeticion = tipoPeticion;
	}

	public Object getUsuario() {
	return usuario;
	}

	public void setUsuario(Usuario usuario) {
	this.usuario = usuario;
	}

	public Object getIdActividad() {
	return idActividad;
	}

	public void setIdActividad(Object idActividad) {
	this.idActividad = idActividad;
	}

	public List<String> getActividades() {
	return actividades;
	}

	public void setActividades(List<String> actividades) {
	this.actividades = actividades;
	}

	public Object getUsuarioActual() {
	return usuarioActual;
	}

	public void setUsuarioActual(Object usuarioActual) {
	this.usuarioActual = usuarioActual;
	}

	public Usuario getNuevoUsuario() {
	return nuevoUsuario;
	}

	public void setNuevoUsuario(Usuario nuevoUsuario) {
	this.nuevoUsuario = nuevoUsuario;
	}
}

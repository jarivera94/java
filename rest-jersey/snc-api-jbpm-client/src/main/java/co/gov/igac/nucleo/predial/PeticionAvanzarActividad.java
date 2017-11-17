package co.gov.igac.nucleo.predial;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PeticionAvanzarActividad {
	@SerializedName("objetoNegocio")
	@Expose
	private ObjetoNegocio objetoNegocio;
	@SerializedName("idObjeto")
	@Expose
	private String idObjeto;
	@SerializedName("usuario")
	@Expose
	private Usuario usuario;
	@SerializedName("tipoPeticion")
	@Expose
	private String tipoPeticion;

	public ObjetoNegocio getObjetoNegocio() {
	return objetoNegocio;
	}

	public void setObjetoNegocio(ObjetoNegocio objetoNegocio) {
	this.objetoNegocio = objetoNegocio;
	}

	public String getIdObjeto() {
	return idObjeto;
	}

	public void setIdObjeto(String idObjeto) {
	this.idObjeto = idObjeto;
	}

	public Usuario getUsuario() {
	return usuario;
	}

	public void setUsuario(Usuario usuario) {
	this.usuario = usuario;
	}

	public String getTipoPeticion() {
	return tipoPeticion;
	}

	public void setTipoPeticion(String tipoPeticion) {
	this.tipoPeticion = tipoPeticion;
	}

}

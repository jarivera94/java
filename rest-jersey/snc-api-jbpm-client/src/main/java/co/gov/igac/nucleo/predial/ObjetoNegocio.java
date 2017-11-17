package co.gov.igac.nucleo.predial;

import java.math.BigDecimal;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ObjetoNegocio {
	
	@SerializedName("identificador")
	@Expose
	private BigDecimal identificador;
	@SerializedName("idCorrelacion")
	@Expose
	private BigDecimal idCorrelacion;
	@SerializedName("actividadesUsuarios")
	@Expose
	private List<ActividadesUsuario> actividadesUsuarios = null;
	@SerializedName("territorial")
	@Expose
	private Object territorial;
	@SerializedName("resultado")
	@Expose
	private Object resultado;
	@SerializedName("observaciones")
	@Expose
	private Object observaciones;
	@SerializedName("operacion")
	@Expose
	private String operacion;
	@SerializedName("duracion")
	@Expose
	private Object duracion;
	@SerializedName("proceso")
	@Expose
	private String proceso;
	@SerializedName("numeroSolicitud")
	@Expose
	private String numeroSolicitud;
	@SerializedName("tipoSolicitud")
	@Expose
	private String tipoSolicitud;
	@SerializedName("fechaSolicitud")
	@Expose
	private BigDecimal fechaSolicitud;
	@SerializedName("tramitesAsociados")
	@Expose
	private List<String> tramitesAsociados = null;

	@SerializedName("transicion")
	@Expose
	private Object transicion;
	
	public BigDecimal getIdentificador() {
	return identificador;
	}

	public void setIdentificador(BigDecimal identificador) {
	this.identificador = identificador;
	}

	public BigDecimal getIdCorrelacion() {
	return idCorrelacion;
	}

	public void setIdCorrelacion(BigDecimal idCorrelacion) {
	this.idCorrelacion = idCorrelacion;
	}

	public List<ActividadesUsuario> getActividadesUsuarios() {
	return actividadesUsuarios;
	}

	public void setActividadesUsuarios(List<ActividadesUsuario> actividadesUsuarios) {
	this.actividadesUsuarios = actividadesUsuarios;
	}

	public Object getTerritorial() {
	return territorial;
	}

	public void setTerritorial(Object territorial) {
	this.territorial = territorial;
	}

	public Object getResultado() {
	return resultado;
	}
	
	public void setTransicion(Object transicion) {
		this.transicion = transicion;
	}

	public void setResultado(Object resultado) {
	this.resultado = resultado;
	}

	public Object getObservaciones() {
	return observaciones;
	}

	public void setObservaciones(Object observaciones) {
	this.observaciones = observaciones;
	}

	public String getOperacion() {
	return operacion;
	}

	public void setOperacion(String operacion) {
	this.operacion = operacion;
	}

	public Object getDuracion() {
	return duracion;
	}

	public void setDuracion(Object duracion) {
	this.duracion = duracion;
	}

	public String getProceso() {
	return proceso;
	}

	public void setProceso(String proceso) {
	this.proceso = proceso;
	}

	public String getNumeroSolicitud() {
	return numeroSolicitud;
	}

	public void setNumeroSolicitud(String numeroSolicitud) {
	this.numeroSolicitud = numeroSolicitud;
	}

	public String getTipoSolicitud() {
	return tipoSolicitud;
	}

	public void setTipoSolicitud(String tipoSolicitud) {
	this.tipoSolicitud = tipoSolicitud;
	}

	public BigDecimal getFechaSolicitud() {
	return fechaSolicitud;
	}

	public void setFechaSolicitud(BigDecimal fechaSolicitud) {
	this.fechaSolicitud = fechaSolicitud;
	}

	public List<String> getTramitesAsociados() {
	return tramitesAsociados;
	}

	public void setTramitesAsociados(List<String> tramitesAsociados) {
	this.tramitesAsociados = tramitesAsociados;
	}
	
	public void destruyeUsuarios(){
		this.actividadesUsuarios.get(0).destuyeUsuarios();
	}

	public Object getTransicion() {
		return transicion;
	}

	
}

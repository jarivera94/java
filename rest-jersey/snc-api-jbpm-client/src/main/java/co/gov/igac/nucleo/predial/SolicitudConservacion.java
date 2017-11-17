package co.gov.igac.nucleo.predial;

import java.math.BigDecimal;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SolicitudConservacion {
	
	public static final String ID_PROCESS = "Conservacion.ProcesoConservacion";
	public static final String ID_CONTAINER = "NucleoPredial.Conservacion";
	
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
	@SerializedName("fechaRadicacion")
	@Expose
	private BigDecimal fechaRadicacion;
	@SerializedName("numeroSolicitud")
	@Expose
	private String numeroSolicitud;
	@SerializedName("numeroRadicacion")
	@Expose
	private String numeroRadicacion;
	@SerializedName("numeroPredial")
	@Expose
	private String numeroPredial;
	@SerializedName("tipoTramite")
	@Expose
	private String tipoTramite;
	@SerializedName("tramitesAsociados")
	@Expose
	private Object tramitesAsociados;

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

	public BigDecimal getFechaRadicacion() {
	return fechaRadicacion;
	}

	public void setFechaRadicacion(BigDecimal fechaRadicacion) {
	this.fechaRadicacion = fechaRadicacion;
	}

	public String getNumeroSolicitud() {
	return numeroSolicitud;
	}

	public void setNumeroSolicitud(String numeroSolicitud) {
	this.numeroSolicitud = numeroSolicitud;
	}

	public String getNumeroRadicacion() {
	return numeroRadicacion;
	}

	public void setNumeroRadicacion(String numeroRadicacion) {
	this.numeroRadicacion = numeroRadicacion;
	}

	public String getNumeroPredial() {
	return numeroPredial;
	}

	public void setNumeroPredial(String numeroPredial) {
	this.numeroPredial = numeroPredial;
	}

	public String getTipoTramite() {
	return tipoTramite;
	}

	public void setTipoTramite(String tipoTramite) {
	this.tipoTramite = tipoTramite;
	}

	public Object getTramitesAsociados() {
	return tramitesAsociados;
	}

	public void setTramitesAsociados(Object tramitesAsociados) {
	this.tramitesAsociados = tramitesAsociados;
	}
}

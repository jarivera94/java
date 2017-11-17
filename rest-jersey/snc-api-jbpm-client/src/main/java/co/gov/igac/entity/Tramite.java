package co.gov.igac.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(schema="SNC_TRAMITE")
public class Tramite implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TRAMITE_ID_GENERATOR", sequenceName="TRAMITE_ID_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TRAMITE_ID_GENERATOR")
	private long id;

	private String archivado;

	@Column(name="AREA_CONSTRUCCION")
	private BigDecimal areaConstruccion;

	@Column(name="AREA_TERRENO")
	private BigDecimal areaTerreno;

	@Column(name="AUTOAVALUO_CONSTRUCCION")
	private BigDecimal autoavaluoConstruccion;

	@Column(name="AUTOAVALUO_TERRENO")
	private BigDecimal autoavaluoTerreno;

	@Column(name="CARGO_FUNCIONARIO_EJECUTOR")
	private String cargoFuncionarioEjecutor;

	@Column(name="CLASE_MUTACION")
	private String claseMutacion;

	private String clasificacion;

	private String comisionado;

	@Column(name="DEPARTAMENTO_CODIGO")
	private String departamentoCodigo;

	@Column(name="DOCUMENTACION_COMPLETA")
	private String documentacionCompleta;

	private String estado;

	@Temporal(TemporalType.DATE)
	@Column(name="FECHA_INSCRIPCION_CATASTRAL")
	private Date fechaInscripcionCatastral;

	@Temporal(TemporalType.DATE)
	@Column(name="FECHA_LOG")
	private Date fechaLog;

	@Temporal(TemporalType.DATE)
	@Column(name="FECHA_RADICACION")
	private Date fechaRadicacion;

	private String fuente;

	@Column(name="FUNCIONARIO_EJECUTOR")
	private String funcionarioEjecutor;

	@Column(name="FUNCIONARIO_RADICADOR")
	private String funcionarioRadicador;

	@Column(name="FUNDAMENTO_PETICION")
	private String fundamentoPeticion;

	@Column(name="MANTIENE_NUMERO_PREDIAL")
	private String mantieneNumeroPredial;

	@Column(name="MARCO_JURIDICO")
	private String marcoJuridico;

	@Column(name="MUNICIPIO_CODIGO")
	private String municipioCodigo;

	@Column(name="NOMBRE_FUNCIONARIO_EJECUTOR")
	private String nombreFuncionarioEjecutor;

	@Column(name="NOMBRE_FUNCIONARIO_RADICADOR")
	private String nombreFuncionarioRadicador;

	@Column(name="NOMBRE_PREDIO")
	private String nombrePredio;

	@Column(name="ESTADO_GDB")
	private String estadoGdb;

	@Column(name="NUMERO_PREDIAL")
	private String numeroPredial;

	@Column(name="NUMERO_RADICACION")
	private String numeroRadicacion;

	@Column(name="NUMERO_RADICACION_REFERENCIA")
	private String numeroRadicacionReferencia;

	@Column(name="OBSERVACIONES_RADICACION")
	private String observacionesRadicacion;

	@Column(name="ORDEN_EJECUCION")
	private BigDecimal ordenEjecucion;

	@Column(name="PREDIO_ID")
	private BigDecimal predioId;

	@Column(name="PROCESO_INSTANCIA_ID")
	private String procesoInstanciaId;

	@Column(name="PROYECCION_ASOCIADA")
	private String proyeccionAsociada;

	@Column(name="RESOLUCION_CONTRA_PROCEDE")
	private String resolucionContraProcede;

	@Column(name="RESULTADO_DOCUMENTO_ID")
	private BigDecimal resultadoDocumentoId;

	private String resumen;

	@Column(name="SOLICITUD_ID")
	private BigDecimal solicitudId;

	private String subtipo;

	@Column(name="TIPO_INSCRIPCION")
	private String tipoInscripcion;

	@Column(name="TIPO_TRAMITE")
	private String tipoTramite;

	@Column(name="UBICACION_PREDIO")
	private String ubicacionPredio;

	@Column(name="USUARIO_LOG")
	private String usuarioLog;

	//bi-directional many-to-one association to Tramite
	@ManyToOne
	@JoinColumn(name="ASOCIADO_TRAMITE_ID")
	private Tramite tramite;

	//bi-directional many-to-one association to Tramite
	@OneToMany(mappedBy="tramite")
	private List<Tramite> tramites;

	public Tramite() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getArchivado() {
		return this.archivado;
	}

	public void setArchivado(String archivado) {
		this.archivado = archivado;
	}

	public BigDecimal getAreaConstruccion() {
		return this.areaConstruccion;
	}

	public void setAreaConstruccion(BigDecimal areaConstruccion) {
		this.areaConstruccion = areaConstruccion;
	}

	public BigDecimal getAreaTerreno() {
		return this.areaTerreno;
	}

	public void setAreaTerreno(BigDecimal areaTerreno) {
		this.areaTerreno = areaTerreno;
	}

	public BigDecimal getAutoavaluoConstruccion() {
		return this.autoavaluoConstruccion;
	}

	public void setAutoavaluoConstruccion(BigDecimal autoavaluoConstruccion) {
		this.autoavaluoConstruccion = autoavaluoConstruccion;
	}

	public BigDecimal getAutoavaluoTerreno() {
		return this.autoavaluoTerreno;
	}

	public void setAutoavaluoTerreno(BigDecimal autoavaluoTerreno) {
		this.autoavaluoTerreno = autoavaluoTerreno;
	}

	public String getCargoFuncionarioEjecutor() {
		return this.cargoFuncionarioEjecutor;
	}

	public void setCargoFuncionarioEjecutor(String cargoFuncionarioEjecutor) {
		this.cargoFuncionarioEjecutor = cargoFuncionarioEjecutor;
	}

	public String getClaseMutacion() {
		return this.claseMutacion;
	}

	public void setClaseMutacion(String claseMutacion) {
		this.claseMutacion = claseMutacion;
	}

	public String getClasificacion() {
		return this.clasificacion;
	}

	public void setClasificacion(String clasificacion) {
		this.clasificacion = clasificacion;
	}

	public String getComisionado() {
		return this.comisionado;
	}

	public void setComisionado(String comisionado) {
		this.comisionado = comisionado;
	}

	public String getDepartamentoCodigo() {
		return this.departamentoCodigo;
	}

	public void setDepartamentoCodigo(String departamentoCodigo) {
		this.departamentoCodigo = departamentoCodigo;
	}

	public String getDocumentacionCompleta() {
		return this.documentacionCompleta;
	}

	public void setDocumentacionCompleta(String documentacionCompleta) {
		this.documentacionCompleta = documentacionCompleta;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Date getFechaInscripcionCatastral() {
		return this.fechaInscripcionCatastral;
	}

	public void setFechaInscripcionCatastral(Date fechaInscripcionCatastral) {
		this.fechaInscripcionCatastral = fechaInscripcionCatastral;
	}

	public Date getFechaLog() {
		return this.fechaLog;
	}

	public void setFechaLog(Date fechaLog) {
		this.fechaLog = fechaLog;
	}

	public Date getFechaRadicacion() {
		return this.fechaRadicacion;
	}

	public void setFechaRadicacion(Date fechaRadicacion) {
		this.fechaRadicacion = fechaRadicacion;
	}

	public String getFuente() {
		return this.fuente;
	}

	public void setFuente(String fuente) {
		this.fuente = fuente;
	}

	public String getFuncionarioEjecutor() {
		return this.funcionarioEjecutor;
	}

	public void setFuncionarioEjecutor(String funcionarioEjecutor) {
		this.funcionarioEjecutor = funcionarioEjecutor;
	}

	public String getFuncionarioRadicador() {
		return this.funcionarioRadicador;
	}

	public void setFuncionarioRadicador(String funcionarioRadicador) {
		this.funcionarioRadicador = funcionarioRadicador;
	}

	public String getFundamentoPeticion() {
		return this.fundamentoPeticion;
	}

	public void setFundamentoPeticion(String fundamentoPeticion) {
		this.fundamentoPeticion = fundamentoPeticion;
	}

	public String getMantieneNumeroPredial() {
		return this.mantieneNumeroPredial;
	}

	public void setMantieneNumeroPredial(String mantieneNumeroPredial) {
		this.mantieneNumeroPredial = mantieneNumeroPredial;
	}

	public String getMarcoJuridico() {
		return this.marcoJuridico;
	}

	public void setMarcoJuridico(String marcoJuridico) {
		this.marcoJuridico = marcoJuridico;
	}

	public String getMunicipioCodigo() {
		return this.municipioCodigo;
	}

	public void setMunicipioCodigo(String municipioCodigo) {
		this.municipioCodigo = municipioCodigo;
	}

	public String getNombreFuncionarioEjecutor() {
		return this.nombreFuncionarioEjecutor;
	}

	public void setNombreFuncionarioEjecutor(String nombreFuncionarioEjecutor) {
		this.nombreFuncionarioEjecutor = nombreFuncionarioEjecutor;
	}

	public String getNombreFuncionarioRadicador() {
		return this.nombreFuncionarioRadicador;
	}

	public void setNombreFuncionarioRadicador(String nombreFuncionarioRadicador) {
		this.nombreFuncionarioRadicador = nombreFuncionarioRadicador;
	}

	public String getNombrePredio() {
		return this.nombrePredio;
	}

	public void setNombrePredio(String nombrePredio) {
		this.nombrePredio = nombrePredio;
	}

	public String getEstadoGdb() {
		return estadoGdb;
	}

	public void setEstadoGdb(String estadoGdb) {
		this.estadoGdb = estadoGdb;
	}

	public String getNumeroPredial() {
		return this.numeroPredial;
	}

	public void setNumeroPredial(String numeroPredial) {
		this.numeroPredial = numeroPredial;
	}

	public String getNumeroRadicacion() {
		return this.numeroRadicacion;
	}

	public void setNumeroRadicacion(String numeroRadicacion) {
		this.numeroRadicacion = numeroRadicacion;
	}

	public String getNumeroRadicacionReferencia() {
		return this.numeroRadicacionReferencia;
	}

	public void setNumeroRadicacionReferencia(String numeroRadicacionReferencia) {
		this.numeroRadicacionReferencia = numeroRadicacionReferencia;
	}

	public String getObservacionesRadicacion() {
		return this.observacionesRadicacion;
	}

	public void setObservacionesRadicacion(String observacionesRadicacion) {
		this.observacionesRadicacion = observacionesRadicacion;
	}

	public BigDecimal getOrdenEjecucion() {
		return this.ordenEjecucion;
	}

	public void setOrdenEjecucion(BigDecimal ordenEjecucion) {
		this.ordenEjecucion = ordenEjecucion;
	}

	public BigDecimal getPredioId() {
		return this.predioId;
	}

	public void setPredioId(BigDecimal predioId) {
		this.predioId = predioId;
	}

	public String getProcesoInstanciaId() {
		return this.procesoInstanciaId;
	}

	public void setProcesoInstanciaId(String procesoInstanciaId) {
		this.procesoInstanciaId = procesoInstanciaId;
	}

	public String getProyeccionAsociada() {
		return this.proyeccionAsociada;
	}

	public void setProyeccionAsociada(String proyeccionAsociada) {
		this.proyeccionAsociada = proyeccionAsociada;
	}

	public String getResolucionContraProcede() {
		return this.resolucionContraProcede;
	}

	public void setResolucionContraProcede(String resolucionContraProcede) {
		this.resolucionContraProcede = resolucionContraProcede;
	}

	public BigDecimal getResultadoDocumentoId() {
		return this.resultadoDocumentoId;
	}

	public void setResultadoDocumentoId(BigDecimal resultadoDocumentoId) {
		this.resultadoDocumentoId = resultadoDocumentoId;
	}

	public String getResumen() {
		return this.resumen;
	}

	public void setResumen(String resumen) {
		this.resumen = resumen;
	}

	public BigDecimal getSolicitudId() {
		return this.solicitudId;
	}

	public void setSolicitudId(BigDecimal solicitudId) {
		this.solicitudId = solicitudId;
	}

	public String getSubtipo() {
		return this.subtipo;
	}

	public void setSubtipo(String subtipo) {
		this.subtipo = subtipo;
	}

	public String getTipoInscripcion() {
		return this.tipoInscripcion;
	}

	public void setTipoInscripcion(String tipoInscripcion) {
		this.tipoInscripcion = tipoInscripcion;
	}

	public String getTipoTramite() {
		return this.tipoTramite;
	}

	public void setTipoTramite(String tipoTramite) {
		this.tipoTramite = tipoTramite;
	}

	public String getUbicacionPredio() {
		return this.ubicacionPredio;
	}

	public void setUbicacionPredio(String ubicacionPredio) {
		this.ubicacionPredio = ubicacionPredio;
	}

	public String getUsuarioLog() {
		return this.usuarioLog;
	}

	public void setUsuarioLog(String usuarioLog) {
		this.usuarioLog = usuarioLog;
	}

	public Tramite getTramite() {
		return this.tramite;
	}

	public void setTramite(Tramite tramite) {
		this.tramite = tramite;
	}

	public List<Tramite> getTramites() {
		return this.tramites;
	}

	public void setTramites(List<Tramite> tramites) {
		this.tramites = tramites;
	}

}
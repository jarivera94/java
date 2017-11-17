package co.gov.igac.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the PREDIO database table.
 * 
 */
@Entity
@Table(schema="SNC_CONSERVACION")
public class Predio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="PREDIO_ID_GENERATOR", sequenceName="PREDIO_ID_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PREDIO_ID_GENERATOR")
	private long id;

	@Column(name="ANIO_ULTIMA_RESOLUCION")
	private BigDecimal anioUltimaResolucion;

	@Column(name="AREA_CONSTRUCCION")
	private BigDecimal areaConstruccion;

	@Column(name="AREA_TERRENO")
	private BigDecimal areaTerreno;

	@Column(name="BARRIO_CODIGO")
	private String barrioCodigo;

	private String chip;

	@Column(name="CIRCULO_REGISTRAL")
	private String circuloRegistral;

	@Column(name="CONDICION_PROPIEDAD")
	private String condicionPropiedad;

	@Column(name="CONSECUTIVO_CATASTRAL")
	private BigDecimal consecutivoCatastral;

	@Column(name="CONSECUTIVO_CATASTRAL_ANTERIOR")
	private BigDecimal consecutivoCatastralAnterior;

	@Column(name="CORREGIMIENTO_CODIGO")
	private String corregimientoCodigo;

	@Column(name="DEPARTAMENTO_CODIGO")
	private String departamentoCodigo;

	private String destino;

	private String edificio;

	private String estado;

	private String este;

	private String estrato;

	@Temporal(TemporalType.DATE)
	@Column(name="FECHA_INSCRIPCION_CATASTRAL")
	private Date fechaInscripcionCatastral;

	@Temporal(TemporalType.DATE)
	@Column(name="FECHA_LOG")
	private Date fechaLog;

	@Temporal(TemporalType.DATE)
	@Column(name="FECHA_REGISTRO")
	private Date fechaRegistro;

	@Column(name="LOCALIDAD_CODIGO")
	private String localidadCodigo;

	@Column(name="MANZANA_CODIGO")
	private String manzanaCodigo;

	@Column(name="MUNICIPIO_CODIGO")
	private String municipioCodigo;

	private String nip;

	private String nombre;

	private String norte;

	@Column(name="NUMERO_PREDIAL")
	private String numeroPredial;

	@Column(name="NUMERO_PREDIAL_ANTERIOR")
	private String numeroPredialAnterior;

	@Column(name="NUMERO_REGISTRO")
	private String numeroRegistro;

	@Column(name="NUMERO_REGISTRO_ANTERIOR")
	private String numeroRegistroAnterior;

	@Column(name="NUMERO_ULTIMA_RESOLUCION")
	private String numeroUltimaResolucion;

	private String piso;

	private String predio;

	@Column(name="SECTOR_CODIGO")
	private String sectorCodigo;

	private String tipo;

	@Column(name="TIPO_AVALUO")
	private String tipoAvaluo;

	@Column(name="TIPO_CATASTRO")
	private String tipoCatastro;

	private String unidad;

	@Column(name="USUARIO_LOG")
	private String usuarioLog;

	@Column(name="ZONA_UNIDAD_ORGANICA")
	private String zonaUnidadOrganica;

	public Predio() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public BigDecimal getAnioUltimaResolucion() {
		return this.anioUltimaResolucion;
	}

	public void setAnioUltimaResolucion(BigDecimal anioUltimaResolucion) {
		this.anioUltimaResolucion = anioUltimaResolucion;
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

	public String getBarrioCodigo() {
		return this.barrioCodigo;
	}

	public void setBarrioCodigo(String barrioCodigo) {
		this.barrioCodigo = barrioCodigo;
	}

	public String getChip() {
		return this.chip;
	}

	public void setChip(String chip) {
		this.chip = chip;
	}

	public String getCirculoRegistral() {
		return this.circuloRegistral;
	}

	public void setCirculoRegistral(String circuloRegistral) {
		this.circuloRegistral = circuloRegistral;
	}

	public String getCondicionPropiedad() {
		return this.condicionPropiedad;
	}

	public void setCondicionPropiedad(String condicionPropiedad) {
		this.condicionPropiedad = condicionPropiedad;
	}

	public BigDecimal getConsecutivoCatastral() {
		return this.consecutivoCatastral;
	}

	public void setConsecutivoCatastral(BigDecimal consecutivoCatastral) {
		this.consecutivoCatastral = consecutivoCatastral;
	}

	public BigDecimal getConsecutivoCatastralAnterior() {
		return this.consecutivoCatastralAnterior;
	}

	public void setConsecutivoCatastralAnterior(BigDecimal consecutivoCatastralAnterior) {
		this.consecutivoCatastralAnterior = consecutivoCatastralAnterior;
	}

	public String getCorregimientoCodigo() {
		return this.corregimientoCodigo;
	}

	public void setCorregimientoCodigo(String corregimientoCodigo) {
		this.corregimientoCodigo = corregimientoCodigo;
	}

	public String getDepartamentoCodigo() {
		return this.departamentoCodigo;
	}

	public void setDepartamentoCodigo(String departamentoCodigo) {
		this.departamentoCodigo = departamentoCodigo;
	}

	public String getDestino() {
		return this.destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}

	public String getEdificio() {
		return this.edificio;
	}

	public void setEdificio(String edificio) {
		this.edificio = edificio;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getEste() {
		return this.este;
	}

	public void setEste(String este) {
		this.este = este;
	}

	public String getEstrato() {
		return this.estrato;
	}

	public void setEstrato(String estrato) {
		this.estrato = estrato;
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

	public Date getFechaRegistro() {
		return this.fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getLocalidadCodigo() {
		return this.localidadCodigo;
	}

	public void setLocalidadCodigo(String localidadCodigo) {
		this.localidadCodigo = localidadCodigo;
	}

	public String getManzanaCodigo() {
		return this.manzanaCodigo;
	}

	public void setManzanaCodigo(String manzanaCodigo) {
		this.manzanaCodigo = manzanaCodigo;
	}

	public String getMunicipioCodigo() {
		return this.municipioCodigo;
	}

	public void setMunicipioCodigo(String municipioCodigo) {
		this.municipioCodigo = municipioCodigo;
	}

	public String getNip() {
		return this.nip;
	}

	public void setNip(String nip) {
		this.nip = nip;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNorte() {
		return this.norte;
	}

	public void setNorte(String norte) {
		this.norte = norte;
	}

	public String getNumeroPredial() {
		return this.numeroPredial;
	}

	public void setNumeroPredial(String numeroPredial) {
		this.numeroPredial = numeroPredial;
	}

	public String getNumeroPredialAnterior() {
		return this.numeroPredialAnterior;
	}

	public void setNumeroPredialAnterior(String numeroPredialAnterior) {
		this.numeroPredialAnterior = numeroPredialAnterior;
	}

	public String getNumeroRegistro() {
		return this.numeroRegistro;
	}

	public void setNumeroRegistro(String numeroRegistro) {
		this.numeroRegistro = numeroRegistro;
	}

	public String getNumeroRegistroAnterior() {
		return this.numeroRegistroAnterior;
	}

	public void setNumeroRegistroAnterior(String numeroRegistroAnterior) {
		this.numeroRegistroAnterior = numeroRegistroAnterior;
	}

	public String getNumeroUltimaResolucion() {
		return this.numeroUltimaResolucion;
	}

	public void setNumeroUltimaResolucion(String numeroUltimaResolucion) {
		this.numeroUltimaResolucion = numeroUltimaResolucion;
	}

	public String getPiso() {
		return this.piso;
	}

	public void setPiso(String piso) {
		this.piso = piso;
	}

	public String getPredio() {
		return this.predio;
	}

	public void setPredio(String predio) {
		this.predio = predio;
	}

	public String getSectorCodigo() {
		return this.sectorCodigo;
	}

	public void setSectorCodigo(String sectorCodigo) {
		this.sectorCodigo = sectorCodigo;
	}

	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getTipoAvaluo() {
		return this.tipoAvaluo;
	}

	public void setTipoAvaluo(String tipoAvaluo) {
		this.tipoAvaluo = tipoAvaluo;
	}

	public String getTipoCatastro() {
		return this.tipoCatastro;
	}

	public void setTipoCatastro(String tipoCatastro) {
		this.tipoCatastro = tipoCatastro;
	}

	public String getUnidad() {
		return this.unidad;
	}

	public void setUnidad(String unidad) {
		this.unidad = unidad;
	}

	public String getUsuarioLog() {
		return this.usuarioLog;
	}

	public void setUsuarioLog(String usuarioLog) {
		this.usuarioLog = usuarioLog;
	}

	public String getZonaUnidadOrganica() {
		return this.zonaUnidadOrganica;
	}

	public void setZonaUnidadOrganica(String zonaUnidadOrganica) {
		this.zonaUnidadOrganica = zonaUnidadOrganica;
	}

}
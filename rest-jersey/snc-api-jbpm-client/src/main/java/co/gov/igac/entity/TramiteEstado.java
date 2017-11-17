package co.gov.igac.entity;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name="TRAMITE_ESTADO",schema="SNC_TRAMITE")
@NamedQueries({@NamedQuery(name="getTramiteEstados", query = "SELECT t FROM TramiteEstado t"),@NamedQuery(name="getTramiteEstado", query = "SELECT t FROM TramiteEstado t WHERE t.id = :id")})
public class TramiteEstado implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TRAMITE_ESTADO_ID_SEQ")
	@SequenceGenerator(name = "TRAMITE_ESTADO_ID_SEQ", sequenceName = "TRAMITE_ESTADO_ID_SEQ", allocationSize = 1)
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
	private long id;

	@Column(name="TRAMITE_ID")
	private BigDecimal tramiteId;

	private String estado;

	@Column(name="FECHA_INICIO")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaInicio;

	private String motivo;

	private String responsable;

	private String observaciones;

	@Column(name="USUARIO_LOG")
	private String usuarioLog;

	@Column(name="FECHA_LOG")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaLog;

	private static final long serialVersionUID = 1L;

	public TramiteEstado() {
		super();
        fechaInicio = new java.util.Date();
        fechaLog = fechaInicio;
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public BigDecimal getTramiteId() {
		return this.tramiteId;
	}

	public void setTramiteId(BigDecimal tramiteId) {
		this.tramiteId = tramiteId;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Date getFechaInicio() {
		return this.fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public String getMotivo() {
		return this.motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public String getResponsable() {
		return this.responsable;
	}

	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}

	public String getObservaciones() {
		return this.observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public String getUsuarioLog() {
		return this.usuarioLog;
	}

	public void setUsuarioLog(String usuarioLog) {
		this.usuarioLog = usuarioLog;
	}

	public Date getFechaLog() {
		return this.fechaLog;
	}

	public void setFechaLog(Date fechaLog) {
		this.fechaLog = fechaLog;
	}

	@Override
	public String toString() {
		return "TramiteEstado [id=" + id + ", tramiteId=" + tramiteId + ", estado="
				+ estado + ", fechaInicio=" + fechaInicio + ", observaciones="
				+ observaciones + ", motivo=" + motivo + ", responsable="
				+ responsable + ", usuarioLog=" + usuarioLog + ", fechaLog="
				+ fechaLog + "]";
	}
	
}

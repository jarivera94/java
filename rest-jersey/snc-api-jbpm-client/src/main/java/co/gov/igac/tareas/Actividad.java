package co.gov.igac.tareas;

public class Actividad {
	
	private Object id;
	private Object tipo;
	private Object nombreDeDespliegue;
	private Object nombre;
	private Object idObjetoNegocio;
	private Object fechaAsignacion;
	private Object fechaExpiracion;
	private Object estado;
	private RutaActividad rutaActividad;
	private Object usuarioOriginador;
	private Object usuarioEjecutor;
	private Object prioridad;
	private Object alerta;
	private Object tipoProcesamiento;
	private Object tipoActividad;
	private Object transiciones;
	private Object roles;
	private Object numeroSolicitud;
	private Object idCorrelacion;
	private Object municipio;
	private Object departamento;
	private Object zona;
	private Object estaReclamada;
	private Object tipoSolicitud;
	private Object tipoTramite;
	private Object numeroRadicacion;
	private Object numeroPredial;
	private Object urlcasoUso;
	private Object reclamada;
	private Object numeroActualizacion;
	private Object numeroCorrelaciones;
	
	
	
	public Object getId() {
		return id;
	}
	public Object getTipo() {
		return tipo;
	}
	public Object getNombreDeDespliegue() {
		return nombreDeDespliegue;
	}
	public Object getNombre() {
		return nombre;
	}
	public Object getIdObjetoNegocio() {
		return idObjetoNegocio;
	}
	public Object getFechaAsignacion() {
		return fechaAsignacion;
	}
	public Object getFechaExpiracion() {
		return fechaExpiracion;
	}
	public Object getEstado() {
		return estado;
	}
	public RutaActividad getRutaActividad() {
		return rutaActividad;
	}
	public Object getUsuarioOriginador() {
		return usuarioOriginador;
	}
	public Object getUsuarioEjecutor() {
		return usuarioEjecutor;
	}
	public Object getPrioridad() {
		return prioridad;
	}
	public Object getAlerta() {
		return alerta;
	}
	public Object getTipoProcesamiento() {
		return tipoProcesamiento;
	}
	public Object getTipoActividad() {
		return tipoActividad;
	}
	public Object getTransiciones() {
		return transiciones;
	}
	public Object getRoles() {
		return roles;
	}
	public Object getNumeroSolicitud() {
		return numeroSolicitud;
	}
	public Object getIdCorrelacion() {
		return idCorrelacion;
	}
	public Object getMunicipio() {
		return municipio;
	}
	public Object getDepartamento() {
		return departamento;
	}
	public Object getZona() {
		return zona;
	}
	public Object getEstaReclamada() {
		return estaReclamada;
	}
	public Object getTipoSolicitud() {
		return tipoSolicitud;
	}
	public Object getUrlcasoUso() {
		return urlcasoUso;
	}
	public Object getReclamada() {
		return reclamada;
	}
	public Object getNumeroActualizacion() {
		return numeroActualizacion;
	}
	public Object getNumeroCorrelaciones() {
		return numeroCorrelaciones;
	}
	public void setId(Object id) {
		this.id = id;
	}
	public void setTipo(Object tipo) {
		this.tipo = tipo;
	}
	public void setNombreDeDespliegue(Object nombreDeDespliegue) {
		this.nombreDeDespliegue = nombreDeDespliegue;
	}
	public void setNombre(Object nombre) {
		this.nombre = nombre;
	}
	public void setIdObjetoNegocio(Object idObjetoNegocio) {
		this.idObjetoNegocio = idObjetoNegocio;
	}
	public void setFechaAsignacion(Object fechaAsignacion) {
		this.fechaAsignacion = fechaAsignacion;
	}
	public void setFechaExpiracion(Object fechaExpiracion) {
		this.fechaExpiracion = fechaExpiracion;
	}
	public void setEstado(Object estado) {
		this.estado = estado;
	}
	public void setRutaActividad(RutaActividad rutaActividad) {
		this.rutaActividad = rutaActividad;
	}
	public void setUsuarioOriginador(Object usuarioOriginador) {
		this.usuarioOriginador = usuarioOriginador;
	}
	public void setUsuarioEjecutor(Object usuarioEjecutor) {
		this.usuarioEjecutor = usuarioEjecutor;
	}
	public void setPrioridad(Object prioridad) {
		this.prioridad = prioridad;
	}
	public void setAlerta(Object alerta) {
		this.alerta = alerta;
	}
	public void setTipoProcesamiento(Object tipoProcesamiento) {
		this.tipoProcesamiento = tipoProcesamiento;
	}
	public void setTipoActividad(Object tipoActividad) {
		this.tipoActividad = tipoActividad;
	}
	public void setTransiciones(Object transiciones) {
		this.transiciones = transiciones;
	}
	public void setRoles(Object roles) {
		this.roles = roles;
	}
	public void setNumeroSolicitud(Object numeroSolicitud) {
		this.numeroSolicitud = numeroSolicitud;
	}
	public void setIdCorrelacion(Object idCorrelacion) {
		this.idCorrelacion = idCorrelacion;
	}
	public void setMunicipio(Object municipio) {
		this.municipio = municipio;
	}
	public void setDepartamento(Object departamento) {
		this.departamento = departamento;
	}
	public void setZona(Object zona) {
		this.zona = zona;
	}
	public void setEstaReclamada(Object estaReclamada) {
		this.estaReclamada = estaReclamada;
	}
	public void setTipoSolicitud(Object tipoSolicitud) {
		this.tipoSolicitud = tipoSolicitud;
	}
	public void setUrlcasoUso(Object urlcasoUso) {
		this.urlcasoUso = urlcasoUso;
	}
	public void setReclamada(Object reclamada) {
		this.reclamada = reclamada;
	}
	public void setNumeroActualizacion(Object numeroActualizacion) {
		this.numeroActualizacion = numeroActualizacion;
	}
	public void setNumeroCorrelaciones(Object numeroCorrelaciones) {
		this.numeroCorrelaciones = numeroCorrelaciones;
	}
	
	public Object getTipoTramite() {
		return tipoTramite;
	}
	public void setTipoTramite(Object tipoTramite) {
		this.tipoTramite = tipoTramite;
	}
	public Object getNumeroRadicacion() {
		return numeroRadicacion;
	}
	public void setNumeroRadicacion(Object numeroRadicacion) {
		this.numeroRadicacion = numeroRadicacion;
	}
	public Object getNumeroPredial() {
		return numeroPredial;
	}
	public void setNumeroPredial(Object numeroPredial) {
		this.numeroPredial = numeroPredial;
	}
	@Override
	public String toString() {
		return "Actividad [id=" + id + ", tipo=" + tipo + ", nombreDeDespliegue=" + nombreDeDespliegue + ", nombre="
				+ nombre + ", idObjetoNegocio=" + idObjetoNegocio + ", fechaAsignacion=" + fechaAsignacion
				+ ", fechaExpiracion=" + fechaExpiracion + ", estado=" + estado + ", rutaActividad=" + rutaActividad
				+ ", usuarioOriginador=" + usuarioOriginador + ", usuarioEjecutor=" + usuarioEjecutor + ", prioridad="
				+ prioridad + ", alerta=" + alerta + ", tipoProcesamiento=" + tipoProcesamiento + ", tipoActividad="
				+ tipoActividad + ", transiciones=" + transiciones + ", roles=" + roles + ", numeroSolicitud="
				+ numeroSolicitud + ", idCorrelacion=" + idCorrelacion + ", municipio=" + municipio + ", departamento="
				+ departamento + ", zona=" + zona + ", estaReclamada=" + estaReclamada + ", tipoSolicitud="
				+ tipoSolicitud + ", urlcasoUso=" + urlcasoUso + ", reclamada=" + reclamada + ", numeroActualizacion="
				+ numeroActualizacion + ", numeroCorrelaciones=" + numeroCorrelaciones + "]";
	}
	
}
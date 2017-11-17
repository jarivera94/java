package co.gov.igac.nucleo.predial;

import java.math.BigDecimal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Usuario {

	@SerializedName("identificacion")
	@Expose
	private BigDecimal identificacion;
	@SerializedName("tipoIdentificacion")
	@Expose
	private Object tipoIdentificacion;
	@SerializedName("primerNombre")
	@Expose
	private String primerNombre;
	@SerializedName("segundoNombre")
	@Expose
	private Object segundoNombre;
	@SerializedName("primerApellido")
	@Expose
	private String primerApellido;
	@SerializedName("segundoApellido")
	@Expose
	private Object segundoApellido;
	@SerializedName("login")
	@Expose
	private String login;
	@SerializedName("email")
	@Expose
	private Object email;
	@SerializedName("contratista")
	@Expose
	private Boolean contratista;
	@SerializedName("codigoUOC")
	@Expose
	private Object codigoUOC;
	@SerializedName("codigoTerritorial")
	@Expose
	private String codigoTerritorial;
	@SerializedName("descripcionUOC")
	@Expose
	private Object descripcionUOC;
	@SerializedName("descripcionTerritorial")
	@Expose
	private String descripcionTerritorial;
	@SerializedName("direccionEstructuraOrganizacional")
	@Expose
	private Object direccionEstructuraOrganizacional;
	@SerializedName("telefonoEstructuraOrganizacional")
	@Expose
	private Object telefonoEstructuraOrganizacional;
	@SerializedName("fechaExpiracion")
	@Expose
	private BigDecimal fechaExpiracion;
	@SerializedName("estado")
	@Expose
	private String estado;
	@SerializedName("sigToken")
	@Expose
	private Object sigToken;
	@SerializedName("codigoEstructuraOrganizacional")
	@Expose
	private String codigoEstructuraOrganizacional;
	@SerializedName("nombreCompleto")
	@Expose
	private String nombreCompleto;
	@SerializedName("uoc")
	@Expose
	private Boolean uoc;
	@SerializedName("primerRol")
	@Expose
	private String primerRol;

	public Object getIdentificacion() {
		return identificacion;
	}

	public void setIdentificacion(BigDecimal identificacion) {
		this.identificacion = identificacion;
	}

	public Object getTipoIdentificacion() {
		return tipoIdentificacion;
	}

	public void setTipoIdentificacion(Object tipoIdentificacion) {
		this.tipoIdentificacion = tipoIdentificacion;
	}

	public String getPrimerNombre() {
		return primerNombre;
	}

	public void setPrimerNombre(String primerNombre) {
		this.primerNombre = primerNombre;
	}

	public Object getSegundoNombre() {
		return segundoNombre;
	}

	public void setSegundoNombre(Object segundoNombre) {
		this.segundoNombre = segundoNombre;
	}

	public String getPrimerApellido() {
		return primerApellido;
	}

	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}

	public Object getSegundoApellido() {
		return segundoApellido;
	}

	public void setSegundoApellido(Object segundoApellido) {
		this.segundoApellido = segundoApellido;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public Object getEmail() {
		return email;
	}

	public void setEmail(Object email) {
		this.email = email;
	}

	public Boolean getContratista() {
		return contratista;
	}

	public void setContratista(Boolean contratista) {
		this.contratista = contratista;
	}

	public Object getCodigoUOC() {
		return codigoUOC;
	}

	public void setCodigoUOC(Object codigoUOC) {
		this.codigoUOC = codigoUOC;
	}

	public String getCodigoTerritorial() {
		return codigoTerritorial;
	}

	public void setCodigoTerritorial(String codigoTerritorial) {
		this.codigoTerritorial = codigoTerritorial;
	}

	public Object getDescripcionUOC() {
		return descripcionUOC;
	}

	public void setDescripcionUOC(Object descripcionUOC) {
		this.descripcionUOC = descripcionUOC;
	}

	public String getDescripcionTerritorial() {
		return descripcionTerritorial;
	}

	public void setDescripcionTerritorial(String descripcionTerritorial) {
		this.descripcionTerritorial = descripcionTerritorial;
	}

	public Object getDireccionEstructuraOrganizacional() {
		return direccionEstructuraOrganizacional;
	}

	public void setDireccionEstructuraOrganizacional(Object direccionEstructuraOrganizacional) {
		this.direccionEstructuraOrganizacional = direccionEstructuraOrganizacional;
	}

	public Object getTelefonoEstructuraOrganizacional() {
		return telefonoEstructuraOrganizacional;
	}

	public void setTelefonoEstructuraOrganizacional(Object telefonoEstructuraOrganizacional) {
		this.telefonoEstructuraOrganizacional = telefonoEstructuraOrganizacional;
	}

	public BigDecimal getFechaExpiracion() {
		return fechaExpiracion;
	}

	public void setFechaExpiracion(BigDecimal fechaExpiracion) {
		this.fechaExpiracion = fechaExpiracion;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Object getSigToken() {
		return sigToken;
	}

	public void setSigToken(Object sigToken) {
		this.sigToken = sigToken;
	}

	public String getCodigoEstructuraOrganizacional() {
		return codigoEstructuraOrganizacional;
	}

	public void setCodigoEstructuraOrganizacional(String codigoEstructuraOrganizacional) {
		this.codigoEstructuraOrganizacional = codigoEstructuraOrganizacional;
	}

	public String getNombreCompleto() {
		return nombreCompleto;
	}

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	public Boolean getUoc() {
		return uoc;
	}

	public void setUoc(Boolean uoc) {
		this.uoc = uoc;
	}

	public String getPrimerRol() {
		return primerRol;
	}

	public void setPrimerRol(String primerRol) {
		this.primerRol = primerRol;
	}

}
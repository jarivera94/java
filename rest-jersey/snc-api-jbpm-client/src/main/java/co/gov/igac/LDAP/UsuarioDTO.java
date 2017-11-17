package co.gov.igac.LDAP;

import java.io.Serializable;
import java.util.Arrays;

public class UsuarioDTO implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2075223982093295565L;
	private String identificacion;
	private String tipoIdentificacion;
	private String primerNombre;
	private String segundoNombre;
	private String primerApellido;
	private String segundoApellido;
	private String login;
	// private String descripcionEstructuraOrganizacional;
	private String[] roles;
	// private String codigoEstructuraOrganizacional;
	private boolean contratista;
	private String codigoUOC;
	private String codigoTerritorial;
	private String descripcionUOC;
	private String descripcionTerritorial;

	private String direccionEstructuraOrganizacional;
	private String telefonoEstructuraOrganizacional;

	public UsuarioDTO() {
		super();
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public boolean isContratista() {
		return contratista;
	}

	public void setContratista(boolean contratista) {
		this.contratista = contratista;
	}

	public String getCodigoEstructuraOrganizacional() {
		if (this.isUoc())
			return codigoUOC;
		else
			return codigoTerritorial;
	}

	/*
	 * public void setCodigoEstructuraOrganizacional( String
	 * codigoEstructuraOrganizacional) { this.codigoEstructuraOrganizacional =
	 * codigoEstructuraOrganizacional; }
	 */

	public String getIdentificacion() {
		return identificacion;
	}

	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}

	public String getPrimerNombre() {
		return primerNombre;
	}

	public void setPrimerNombre(String primerNombre) {
		this.primerNombre = primerNombre;
	}

	public String getSegundoNombre() {
		return segundoNombre;
	}

	public void setSegundoNombre(String segundoNombre) {
		this.segundoNombre = segundoNombre;
	}

	public String getPrimerApellido() {
		return primerApellido;
	}

	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}

	public String getSegundoApellido() {
		return segundoApellido;
	}

	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
	}

	public String getDescripcionEstructuraOrganizacional() {
		if (this.isUoc())
			return this.descripcionUOC;
		else
			return this.descripcionTerritorial;
	}

	/*
	 * public void setDescripcionEstructuraOrganizacional(String
	 * descripcionEstructuraOrganizacional) {
	 * this.descripcionEstructuraOrganizacional =
	 * descripcionEstructuraOrganizacional.toUpperCase(); }
	 */

	/**
	 * Concatenación del nombre completo
	 * 
	 * @return
	 */
	public String getNombreCompleto() {
		String cadena = "";
		if (this.primerNombre != null)
			cadena += primerNombre;
		if (this.segundoNombre != null)
			cadena += " " + this.segundoNombre;
		if (this.primerApellido != null)
			cadena += " " + this.primerApellido;
		if (this.segundoApellido != null)
			cadena += " " + this.segundoApellido;
		return cadena.trim();
	}

	public String[] getRoles() {
		return roles;
	}

	public void setRoles(String[] roles) {
		this.roles = roles;
	}

	public String getRolesCadena() {
		String s = "";
		for (String r : roles) {
			s += r + ", ";
		}
		return s;
	}

	/**
	 * 
	 */
	public String toString() {
		return "UsuarioDTO [identificacion=" + identificacion
		+ ", tipoIdentificacion=" + tipoIdentificacion
		+ ", primerNombre=" + primerNombre + ", segundoNombre="
		+ segundoNombre + ", primerApellido=" + primerApellido
		+ ", segundoApellido=" + segundoApellido + ", login=" + login
		+ ", roles=" + Arrays.toString(roles) + ", contratista="
		+ contratista + ", codigoUOC=" + codigoUOC
		+ ", codigoTerritorial=" + codigoTerritorial
		+ ", descripcionUOC=" + descripcionUOC
		+ ", descripcionTerritorial=" + descripcionTerritorial
		+ ", direccionEstructuraOrganizacional="
		+ direccionEstructuraOrganizacional
		+ ", telefonoEstructuraOrganizacional="
		+ telefonoEstructuraOrganizacional + "]";
	}
	
	

	public void setCodigoUOC(String codigoUOC) {
		this.codigoUOC = codigoUOC;
	}

	public void setCodigoTerritorial(String codigoTerritorial) {
		this.codigoTerritorial = codigoTerritorial;
	}

	public void setDescripcionUOC(String descripcionUOC) {
		this.descripcionUOC = descripcionUOC;
	}

	public void setDescripcionTerritorial(String descripcionTerritorial) {
		this.descripcionTerritorial = descripcionTerritorial;
	}

	public boolean isTerritorial() {
		return !this.isUoc();
	}

	public boolean isUoc() {
		return (this.codigoUOC != null && !this.codigoUOC.equals(""));
	}

	public String getCodigoUOC() {
		return codigoUOC;
	}

	public String getCodigoTerritorial() {
		return codigoTerritorial;
	}

	public String getDescripcionUOC() {
		return descripcionUOC;
	}

	public String getDescripcionTerritorial() {
		return descripcionTerritorial;
	}

	public void copiarEstructuraOrganizacional(UsuarioDTO usr) {
		this.codigoTerritorial = usr.getCodigoTerritorial();
		this.codigoUOC = usr.getCodigoUOC();
		this.descripcionTerritorial = usr.getDescripcionTerritorial();
		this.descripcionUOC = usr.getDescripcionUOC();
	}

	public String getDireccionEstructuraOrganizacional() {
		return direccionEstructuraOrganizacional;
	}

	public void setDireccionEstructuraOrganizacional(String direccionEstructuraOrganizacional) {
		this.direccionEstructuraOrganizacional = direccionEstructuraOrganizacional;
	}

	public String getTelefonoEstructuraOrganizacional() {
		return telefonoEstructuraOrganizacional;
	}

	public void setTelefonoEstructuraOrganizacional(String telefonoEstructuraOrganizacional) {
		this.telefonoEstructuraOrganizacional = telefonoEstructuraOrganizacional;
	}

	public String getPrimerRol() {
		if (this.roles != null && this.roles.length > 0)
			return this.roles[0].toLowerCase();
		else
			return "";
	}

	public String getTipoIdentificacion() {
		return tipoIdentificacion;
	}

	public void setTipoIdentificacion(String tipoIdentificacion) {
		this.tipoIdentificacion = tipoIdentificacion;
	}
	
	
}

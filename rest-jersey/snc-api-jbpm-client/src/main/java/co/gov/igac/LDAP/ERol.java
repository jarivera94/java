package co.gov.igac.LDAP;

import java.io.Serializable;

public enum ERol implements Serializable {
	ABOGADO("Profesional_Abogado","CN=Profesional_Abogado,OU=SIGC,OU=APLICACIONES," + ConfiguracionLdap.DN ),
	CONTROL_DIGITALIZACION("Control_Digitalizacion","CN=Control_Digitalizacion,OU=SIGC,OU=APLICACIONES," + ConfiguracionLdap.DN ),
	COORDINADOR("Coordinador","CN=Coordinador,OU=SIGC,OU=APLICACIONES," + ConfiguracionLdap.DN ),
	COORDINADOR_GIT_AVALUOS("Coordinador_GIT_Avaluos","CN=Coordinador_GIT_Avaluos,OU=SIGC,OU=APLICACIONES," + ConfiguracionLdap.DN ),
	DIGITALIZADOR("Digitalizador","CN=Digitalizadores,OU=SIGC,OU=APLICACIONES," + ConfiguracionLdap.DN ),
	DIRECTOR_TERRITORIAL("Director_Territorial","CN=Director_Territorial,OU=SIGC,OU=APLICACIONES," + ConfiguracionLdap.DN ),
	EJECUTOR_TRAMITE("Ejecutor_Tramite","CN=Ejecutor_Tramite,OU=SIGC,OU=APLICACIONES," + ConfiguracionLdap.DN ),
	FUNCIONARIO_RADICADOR("Funcionario_Radicador","CN=Funcionario_Radicador,OU=SIGC,OU=APLICACIONES," + ConfiguracionLdap.DN ),
	INVESTIGADOR_MERCADO("Investigador_Mercado","CN=Investigador_Mercado,OU=SIGC,OU=APLICACIONES," + ConfiguracionLdap.DN ),
	RECOLECTOR_OFERTA_INMOBILIARIA("Recolector_Oferta_Inmobiliaria","CN=Recolector_Oferta_inmobiliaria,OU=SIGC,OU=APLICACIONES," + ConfiguracionLdap.DN ),
	RESPONSABLE_ACTUALIZACION("Responsable_Actualizacion", "CN=Responsable_Actualizacion,OU=SIGC,OU=APLICACIONES," + ConfiguracionLdap.DN ),
	RESPONSABLE_CONSERVACION ("Responsable_Conservacion","CN=Responsable_Conservacion,OU=SIGC,OU=APLICACIONES," + ConfiguracionLdap.DN ),
	RESPONSABLE_RADICACION_CONSERVACION ("Responsable_Radicacion_Conservacion","CN=Responsable_Radicacion_Conservacion,OU=SIGC,OU=APLICACIONES," + ConfiguracionLdap.DN ),
	RESPONSABLE_SISTEMAS_SUBDIRECCION_CATASTRO("Responsable_Sistemas_Subdireccion_Catastro","CN=Responsable_Sistemas_Subdireccion_Catastro,OU=SIGC,OU=APLICACIONES," + ConfiguracionLdap.DN ),
	RESPONSABLE_UNIDAD_OPERATIVA("Responsable_Unidad_Operativa","CN=Responsable_Unidad_Operativa,OU=SIGC,OU=APLICACIONES," + ConfiguracionLdap.DN ),
    SECRETARIA_CONSERVACION("Secretaria_Conservacion", "CN=Secretaria_Conservacion,OU=SIGC,OU=APLICACIONES," + ConfiguracionLdap.DN ),
	SUBDIRECTOR_CATASTRO("Subdirector_Catastro","CN=Subdirector_Catastro,OU=SIGC,OU=APLICACIONES," + ConfiguracionLdap.DN ),
	CONTROL_CALIDAD_PRODUCTOS("Control_Calidad_Productos","CN=Control_Calidad_Productos,OU=SIGC,OU=APLICACIONES," + ConfiguracionLdap.DN ),
	;
	
	private String rol;
	private String distinguishedName;
	
	ERol (String rol){
		this.rol=rol;
	}
	
	ERol (String rol, String distinguishedName){
		this.rol=rol;
		this.distinguishedName=distinguishedName;
	}
	
	public String getRol() {
		return rol;
	}

	public String getDistinguishedName() {
		return distinguishedName;
	}
	
}
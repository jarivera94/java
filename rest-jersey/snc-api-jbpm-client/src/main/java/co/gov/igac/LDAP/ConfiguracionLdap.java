package co.gov.igac.LDAP;

public class ConfiguracionLdap {
	
	public static final String SERVIDOR = LDapConfigurationReader.getString("servidor");
	public static final String PUERTO = LDapConfigurationReader.getString("puerto");
	public static final String DOMINIO = LDapConfigurationReader.getString("dominio");
	public static final String USUARIO = LDapConfigurationReader.getString("usuario");
	public static final String CONTRASENA = LDapConfigurationReader.getString("contrasena");
	public static final String INITIAL_CONTEXT_FACTORY = LDapConfigurationReader.getString("initial_context_factory");
	public static final String DN = LDapConfigurationReader.getString("dn");	
	public static final String DN_APLICACION_SNC = LDapConfigurationReader.getString("dn_aplicacion_snc");
	public static final String DN_TERRITORIALES = LDapConfigurationReader.getString("dn_territoriales");
	public static final String DN_DELEGADAS = LDapConfigurationReader.getString("dn_delegadas");
}

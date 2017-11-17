package co.gov.igac.configuracion;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class LectorConfiguracionAPI {
	
	public static final String USUARIO_PROP = "apiprocesos.usuario";
	public static final String CONTRASENIA_PROP = "apiprocesos.contrasenia";
	public static final String SERVER_URL = "apiprocesos.serverurl";
	private static final String BUNDLE_NAME = "configuracionAPI";
	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
			.getBundle(BUNDLE_NAME);
	
	private LectorConfiguracionAPI() {
	}
	
	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}

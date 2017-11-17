package co.gov.igac.LDAP;

import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class LDapConfigurationReader {
private static final String BUNDLE_NAME = "configuracionLdap";
	
	/**
	 * Variable para uso de logs
	 */
	private static final Logger LOGGER = Logger.getLogger("co.gov.igac.snc.dao.util.LDapConfigurationReader");
	

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
			.getBundle(BUNDLE_NAME);

	private LDapConfigurationReader() {
	}

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			LOGGER.severe( e.getMessage());
		}
		return null;
	}
}

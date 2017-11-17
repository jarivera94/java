package co.gov.igac.LDAP;

import java.util.logging.Logger;

import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
public class LdapUtil {

	private static final Logger LOGGER = Logger.getLogger("java.util.logging.Logger.LdapUtil");

	/**
	 * 
	 * @param attrs
	 * @return
	 */
	public static boolean extraerContratista(Attributes attrs) {
		boolean isContratista = false;
		try {
			Attribute attrDesc = attrs.get("description");
			if (attrDesc != null) {
				String description = (String) attrDesc.get();
				description = description.trim().toUpperCase();
				if (description.indexOf("CONTRATISTA") > -1) {
					isContratista = true;
				}
			}
		} catch (NamingException e) {
			LOGGER.severe(e.getMessage());
		}
		return isContratista;
	}

}


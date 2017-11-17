package co.gov.igac.LDAP;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;


public class LdapSecurityUtil implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5257541032986214117L;

	public static UsuarioDTO getUserDetails(Attributes attrs) {
		UsuarioDTO usuarioDto = null;
		try {
			String dn = attrs.get("distinguishedName").get(0).toString();
			String lastKnownParent = dn.substring(dn.indexOf(",OU=")+1);
			//System.out.println("getUserDetails   dn: "+dn);
			//System.out.println("getUserDetails   lastKnownParent: "+lastKnownParent);
			String codigos[] = getTerritorial(lastKnownParent);
			//LOGGER.debug("lastKnownParent: " + lastKnownParent);
			//LOGGER.debug("territorial: " + territorial);
			//System.out.println("ARMAR USUARIO0 ");
			usuarioDto = new UsuarioDTO();
			usuarioDto.setLogin(attrs.get("sAMAccountName").get(0).toString());
			
			usuarioDto.setDescripcionTerritorial(codigos[0]);
			usuarioDto.setDescripcionUOC(codigos[1]);
			
			usuarioDto.setPrimerNombre(getPrimerNombre(dn));
			
			if (attrs.get("employeeId")!=null && attrs.get("employeeId").get(0)!=null){
				usuarioDto.setIdentificacion(attrs.get("employeeId").get(0).toString());
			}
			
			if (attrs.get("employeeType")!=null && attrs.get("employeeType").get(0)!=null){
				usuarioDto.setTipoIdentificacion(attrs.get("employeeType").get(0).toString());
			}
			
			///////////////////////////////////////////////////////////////////////////////
			Attribute memberOf = attrs.get("memberOf");
			
			NamingEnumeration<?>  rol  = memberOf.getAll();
			
			List<String> rolesList = new ArrayList<String>();
			while (rol.hasMoreElements()) {
				String rolStr = LdapSecurityUtil.extractRol(rol.next().toString());
				rolesList.add(rolStr);
			}
			String[] rolesArr = new String[rolesList.size()];
			int i = 0;
			for (String rolStr : rolesList) {
				rolesArr[i] = rolStr.toUpperCase();
				i++;
			}
			usuarioDto.setRoles(rolesArr);
			///////////////////////////////////////////////////////////////////////////////
			usuarioDto.setContratista(LdapUtil.extraerContratista(attrs));
		} catch (NamingException e) {
			throw new RuntimeException(e.getMessage());
		}

		return usuarioDto;
	}
	
	private static String[] getTerritorial(String dn) {
		String resultado[]=new String[2];
		// Primer indice territorial
		// segundo indice uoc, si aplica
		if (dn != null) {
			dn=dn.toUpperCase();
			int posTerritoriales=dn.indexOf("OU=TERRITORIALES");
			//System.out.println("ES TERRITORIAL "+posTerritoriales);
			if (posTerritoriales==-1){
				posTerritoriales=dn.indexOf("OU=SUB_CATASTRO");
				//System.out.println("ES SUBCATASTRO "+posTerritoriales);
				if (posTerritoriales!=-1){
					//System.out.println("ES CENTRAL");
					resultado[0]="SEDE_CENTRAL";
					return resultado;
				}
				if (posTerritoriales==-1){
					posTerritoriales=dn.indexOf("OU=EXTERNOS");
					//System.out.println("ES EXTERNO "+posTerritoriales);
				}
			}else{
				//System.out.println("DN TERRITORIAL: "+dn);
				//System.out.println("POS TERRITORIAL: "+posTerritoriales);
				int primerOU=dn.indexOf("OU=");
				int segundoOU=dn.indexOf("OU=", primerOU+1);
				
				//System.out.println("PRIMER OU: "+primerOU);
				//System.out.println("SEGUNDO OU: "+segundoOU);
				if (posTerritoriales==segundoOU){
					//Es una territorial
					//System.out.println("TERRITORIAL-------");
					resultado[0]=dn.substring(primerOU + 3, segundoOU-1);
				}else{
					// Es una UOC
					//System.out.println("UOC-------");
					resultado[1]=dn.substring(primerOU + 3, segundoOU-1);
					resultado[0]=dn.substring(segundoOU + 3, posTerritoriales-1);
				}
			}
		}
		return resultado;
	}

	private static String getPrimerNombre(String dn) {
		return dn.substring(3, dn.indexOf(','));
	}

	private static String extractRol(String authority){
		return authority.substring(authority.indexOf('=') + 1,authority.indexOf(','));
	}
	
}
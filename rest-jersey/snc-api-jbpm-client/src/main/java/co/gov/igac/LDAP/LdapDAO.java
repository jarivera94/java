package co.gov.igac.LDAP;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;


public class LdapDAO implements Serializable {

	private static final long serialVersionUID = 8818724802630149658L;

	/**
	 * Variable para uso de logs
	 */
	private static final java.util.logging.Logger LOGGER = Logger.getLogger("LdapDAO"); 
	
	private static final String PLANTILLA_ESTRUCTURA_TERRITORIAL = "OU={0}";

	private static LdapDAO instance = null;
	
	private LdapContext ctxGC = null;
	
	public synchronized static LdapDAO getInstance(){
		if (instance == null) {
			instance = new LdapDAO();
		}
		return instance;
	}
	
	protected LdapDAO() {		
	}
	
	private LdapContext getLDAPConnection() {
		
		String principal = ConfiguracionLdap.USUARIO + "@" + ConfiguracionLdap.DOMINIO;

		Hashtable<String, String> environment = new Hashtable<String, String>();
		environment.put(Context.INITIAL_CONTEXT_FACTORY, ConfiguracionLdap.INITIAL_CONTEXT_FACTORY);
		environment.put(Context.PROVIDER_URL, "ldap://" + ConfiguracionLdap.SERVIDOR + ":" + ConfiguracionLdap.PUERTO);
		environment.put(Context.SECURITY_AUTHENTICATION, "simple");
		environment.put(Context.SECURITY_PRINCIPAL, principal);
		environment.put(Context.SECURITY_CREDENTIALS, ConfiguracionLdap.CONTRASENA);
		try {
			if(ctxGC == null){
				ctxGC = new InitialLdapContext(environment, null);
			}
		}
        catch (NamingException e) {
			LOGGER.severe("No se pudo obtener conexión con el servidor LDAP : " + e.getMessage());
			e.printStackTrace();
		}
		LOGGER.fine("El usuario: " + principal + " se autenticó satisfactoriamente ante el servidor LDAP");
		
		return ctxGC;
	}
	
	private void closeConnection() {
		try {
			System.out.println("--------------CERRANDO CONEXION LDAP_DAO----------------------");
			if(ctxGC != null){
				ctxGC.close();
			}
		}catch (NamingException e) {
			LOGGER.severe("No se pudo cerrar conexión de LDAP : " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	@Override
	protected void finalize() throws Throwable {
		System.out.println("--------------MATANDO LDAP_DAO----------------------");
		closeConnection();
		
		super.finalize();
	}

	/**
	 * Retorna u usuario para una estructura organizacional(Territorial o Unidad
	 * Operativa) y rol
	 * 
	 * @param estructuraOrganizacional
	 * @param rol
	 */
	public List<Attributes> getUsuariosEstructuraRol(
			String estructuraOrganizacional, ERol rol) {
		LOGGER.fine("LdapDAO#getUsuariosEstructuraRol(" + estructuraOrganizacional + ", " + rol.getRol()+ ") inicio");
	
		List<Attributes> jefes = null;
		try {

			String searchFilter = "(&(objectClass=user)(memberOf=CN="
					+ rol.getRol() + "," + ConfiguracionLdap.DN_APLICACION_SNC
					+ "))";			
			String returnedAtts[] = { "name", "displayName", "mail",
					"memberOf", "cn", "distinguishedName", "lastKnownParent",
					"sAMAccountName" };
			SearchControls searchCtls = new SearchControls();
			searchCtls.setReturningAttributes(returnedAtts);
			searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);

			LOGGER.fine("searchFilter: " + searchFilter);
			NamingEnumeration<SearchResult> answer = getLDAPConnection().search(ConfiguracionLdap.DN, searchFilter, searchCtls);
			Attributes attrs = null;

			jefes = new ArrayList<Attributes>();

			while (answer.hasMoreElements()) {
				SearchResult sr = (SearchResult) answer.next();
				attrs = sr.getAttributes();
				String dn = attrs.get("distinguishedname").get(0).toString();
				if(dn.contains(estructuraOrganizacional)){
					jefes.add(attrs);
				}
				
			}

		} catch (NamingException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		LOGGER.fine("LdapDAO#getUsuariosEstructuraRol(" + estructuraOrganizacional + ", " + rol.getRol()+ ") fin");

		return jefes;
	}
	
	/**
	 * 
	 * @param estructuraOrganizacional
	 * @param rol
	 * @return
	 */
	public List<UsuarioDTO> getUsuariosEstructuraRol2(
			String estructuraOrganizacional, String rolldap) {
		ERol rol = ERol.valueOf(rolldap);
		
		LOGGER.fine("LdapDAO#getUsuariosEstructuraRol2(" + estructuraOrganizacional + ", " + rol.getRol()+ ") inicio");

		List<UsuarioDTO> usuarios = null;
		
		if(estructuraOrganizacional.equals("SEDE_CENTRAL")){
			estructuraOrganizacional = "SUB_CATASTRO";
		}
		
		try {
			String searchFilter = "(&(objectClass=group)(distinguishedName=CN=" + rol.getRol() + "," + ConfiguracionLdap.DN_APLICACION_SNC + "))";
			LOGGER.fine("searchFilter: " + searchFilter);
			String returnedAtts[] = {"member"};
			SearchControls searchCtls = new SearchControls();
			searchCtls.setReturningAttributes(returnedAtts);
			searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
			
			NamingEnumeration<SearchResult> answer = getLDAPConnection().search(ConfiguracionLdap.DN, searchFilter, searchCtls);
			Attributes attrs = null;

			usuarios = new ArrayList<UsuarioDTO>();
			String estructura = null;
			while (answer.hasMoreElements()) {
				SearchResult sr = (SearchResult) answer.next();
				attrs = sr.getAttributes();
				/**
				 * Ajuste por control de calida Redmine 15589
				 * 
				 * @author alejandro.sanchez
				 */
				if ( attrs == null || attrs.size() == 0 )
				{
					continue;
				}
				String member = attrs.get("member").toString();
				if (member.toUpperCase().startsWith("MEMBER")) {
					member = member.substring(8);
				}				
				String cadenas[] = member.split("CN=");				
				int usuariosNoProcesados = cadenas.length;
				for (String cadena: cadenas){
					LOGGER.fine("CADENA LDAP: " + cadena);
					estructura = MessageFormat.format(PLANTILLA_ESTRUCTURA_TERRITORIAL, estructuraOrganizacional);
					if (cadena.toUpperCase().indexOf(estructura) != -1) {
						String distinguisedName = "";
						
						if(usuariosNoProcesados != 1){
							distinguisedName = "CN=".concat(cadena.substring(0, cadena.lastIndexOf(',')));
						} else {
							distinguisedName = "CN=".concat(cadena);
						}
						
						UsuarioDTO usuarioDTO = searchUserByDistinguishedName(distinguisedName);
						usuarios.add(usuarioDTO);					
					}
					usuariosNoProcesados--;
				}
			}
		} catch (NamingException e) {
			e.printStackTrace();
			LOGGER.log(Level.SEVERE, e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
		for (UsuarioDTO usuarioDTO : usuarios) {
			LOGGER.fine(usuarioDTO.toString());
		}
		LOGGER.fine("LdapDAO#getUsuariosEstructuraRol2(" + estructuraOrganizacional + ", " + rol.getRol()+ ") fin");
		
		return usuarios;
	}

	/**
	 * @author fredy.wilches
	 * Este metodo hay que revisarlo porque el usuario fue creado sin coincidir en algo con los usuarios anteriores 
	 * @return
	 */
	public UsuarioDTO getSubdirectorCatastro() {
		return searchUser("subcatastro");
	}
	
	
	/**
	 * 
	 * @param login
	 * @return
	 */
	public UsuarioDTO searchUser(String login) {
		UsuarioDTO user = null;
		try {
			String searchFilter = "(&(objectClass=user)(sAMAccountName="	+ login + "*)" + ")";

			String returnedAtts[] = { "name", "displayName", "mail",
					"memberOf", "cn", "distinguishedName", "lastKnownParent",
					"sAMAccountName", "employeeId", "employeeType" };
			SearchControls searchCtls = new SearchControls();
			searchCtls.setReturningAttributes(returnedAtts);
			searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);

			NamingEnumeration<SearchResult> answer = getLDAPConnection()
					.search(ConfiguracionLdap.DN, searchFilter, searchCtls);

			while (answer.hasMoreElements()) {
				SearchResult sr = (SearchResult) answer.next();
				Attributes attrs = sr.getAttributes();
				user = LdapSecurityUtil.getUserDetails(attrs);
				LOGGER.fine("user:" + user);
				break;
			}
			
		} catch (NamingException e) {
			e.printStackTrace();
			LOGGER.severe(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
		return user;
	}
	
	/**
	 * Busca un usuario en el LDAP por su distinguisedName
	 * 
	 * @author christian.rodriguez
	 * @param distinguishedName
	 *            distinguisedName del usuario que se desea buscar
	 * @return usuario que se encontró en el LDAP
	 */
	public UsuarioDTO searchUserByDistinguishedName(String distinguishedName) {
		UsuarioDTO user = null;
		try {
			String searchFilter = "(&(objectClass=user)(distinguishedName="	+ distinguishedName + ")" + ")";
			
			String returnedAtts[] = { "name", "displayName", "mail",
					"memberOf", "cn", "distinguishedName", "lastKnownParent",
					"sAMAccountName", "employeeId", "employeeType"};//, "givenName", "sn" 
			SearchControls searchCtls = new SearchControls();
			searchCtls.setReturningAttributes(returnedAtts);
			searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);

			NamingEnumeration<SearchResult> answer = getLDAPConnection()
					.search(ConfiguracionLdap.DN, searchFilter, searchCtls);
			
			while (answer.hasMoreElements()) {

				if(answer.hasMoreElements()){
					SearchResult sr = (SearchResult) answer.next();

					Attributes attrs = sr.getAttributes();

					user = LdapSecurityUtil.getUserDetails(attrs);

					LOGGER.fine("user: " + user);
					break;
				}
			}
			
		} catch (NamingException e) {
			e.printStackTrace();
			LOGGER.severe(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
		return user;
	}

	/**
	 * Retorna el jefe de conservacion para una estructura organizacional
	 * (Territorial o Unidad Operativa)
	 * 
	 * @param estructuraOrganizacional
	 */
	public List<Attributes> getUsuariosJefeConservacion(
			String estructuraOrganizacional) {
		return getUsuariosEstructuraRol(estructuraOrganizacional,
				ERol.RESPONSABLE_CONSERVACION);
	}

	/**
	 * 
	 * @param estructuraOrganizacional
	 * @return
	 */
	public List<UsuarioDTO> getJefeConservacion(
			UsuarioDTO usuario) {
		List<Attributes> jefes = this.getUsuariosJefeConservacion(usuario.getDescripcionEstructuraOrganizacional());
		return convertLDAPToDTO(usuario, jefes);
	}

	/**
	 * @author fredy.wilches Retorna los UsuarioDTO de una territorial y rol
	 * @param estructuraOrganizacional
	 * @param rol
	 * @return
	 */
	public List<UsuarioDTO> getUsuariosRolTerritorial(
			String estructuraOrganizacional, String rolLdap) {
		
		return this.getUsuariosEstructuraRol2(
				estructuraOrganizacional, rolLdap);
	}

	/**
	 * @author fredy.wilches Convierte usuarios del LDAP en UsuarioDTO
	 * @param estructuraOrganizacional
	 * @param usuariosLDAP
	 * @return
	 */
	private List<UsuarioDTO> convertLDAPToDTO(
			UsuarioDTO usuarioDto, List<Attributes> usuariosLDAP) {
		ArrayList<UsuarioDTO> usuarios = new ArrayList<UsuarioDTO>();
		for (Attributes attrs : usuariosLDAP) {
			if (attrs != null) {
				UsuarioDTO usuario = new UsuarioDTO();
				try {
					usuario.setLogin(attrs.get("sAMAccountName").get(0)
							.toString());
					usuario.setPrimerNombre(attrs.get("name").get(0).toString());
					usuario.setCodigoUOC(usuarioDto.getCodigoUOC());
					usuario.setCodigoTerritorial(usuarioDto.getCodigoTerritorial());
					usuario.setDescripcionUOC(usuarioDto.getDescripcionUOC());
					usuario.setDescripcionTerritorial(usuarioDto.getDescripcionTerritorial());
					usuarios.add(usuario);
				} catch (NamingException e) {
					LOGGER.severe(e.getMessage());
				}
			}
		}
		return usuarios;
	}

	/**
	 * Convierte usuarios del LDAP en UsuarioDTO
	 * @author fredy.wilches 
	 * @param usuariosLDAP
	 * @return
	 */
	private List<UsuarioDTO> convertLDAPToDTO(List<Attributes> usuariosLDAP) {
		ArrayList<UsuarioDTO> usuarios = new ArrayList<UsuarioDTO>();
		for (Attributes attrs : usuariosLDAP) {
			if (attrs != null) {
				UsuarioDTO usuario = new UsuarioDTO();
				try {
					usuario.setLogin(attrs.get("sAMAccountName").get(0)
							.toString());
					usuario.setPrimerNombre(attrs.get("name").get(0).toString());
					usuarios.add(usuario);
				} catch (NamingException e) {
					LOGGER.severe(e.getMessage());
				}
			}
		}
		return usuarios;
	}
	
	/**
	 * Retorna los atributos LDAP para una territorial y/o UO 
	 * @param territorial
	 * @param uo nombre de la unidad operativa, null en caso de que no se requiera.
	 * @return
	 */
	private List<Attributes> getFuncionarios(
			String territorial, String uo) {
		List<Attributes> funcionarios = null;
		try {

			String searchFilter = "(&(objectClass=user)(!(objectClass=computer)))";
			String returnedAtts[] = {"name", "displayName", "mail", "memberOf", 
					"cn", "distinguishedName", "lastKnownParent", "sAMAccountName"};
			
			String base = 
				(uo != null) 
				? "OU=" + uo + ",OU=" + territorial + "," + ConfiguracionLdap.DN_TERRITORIALES //OU=TERRITORIALES,OU=IGAC,DC=PRUDCIGAC,DC=LOCAL"
			    : "OU=" + territorial + "," + ConfiguracionLdap.DN_TERRITORIALES;//OU=TERRITORIALES,OU=IGAC,DC=PRUDCIGAC,DC=LOCAL";
			
			
			String baseDelegada = "OU=" + territorial + "," + ConfiguracionLdap.DN_DELEGADAS;
						
			SearchControls searchCtls = new SearchControls();
			searchCtls.setReturningAttributes(returnedAtts);
			searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
			NamingEnumeration<SearchResult> answer = null;
			NamingEnumeration<SearchResult> answer1 = null;
			try{
				answer = getLDAPConnection().search(base, searchFilter, searchCtls);

			}catch(NamingException e){
				
			}
			try{
				answer1 = getLDAPConnection().search(baseDelegada, searchFilter, searchCtls);
			}catch(NamingException e){
				
			}
			

			Attributes attrs = null;
			funcionarios = new ArrayList<Attributes>();
			
			if(answer!=null){
				while (answer.hasMoreElements()) {
					SearchResult sr = (SearchResult) answer.next();
					attrs = sr.getAttributes();
					funcionarios.add(attrs);
				}
			}
			
			if(answer1!=null){
				while (answer1.hasMoreElements()) {
					SearchResult sr = (SearchResult) answer1.next();
					attrs = sr.getAttributes();
					funcionarios.add(attrs);
				}
			}

		} catch (NamingException e) {
			e.printStackTrace();
			LOGGER.severe(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
		return funcionarios;
	}	
	
	/**
	 * 
	 * @param territorial
	 * @param uo
	 * @return
	 */
	public List<UsuarioDTO> getFuncionariosDto(String territorial, String uo) {
		return convertLDAPToDTO(getFuncionarios(territorial, uo));
	}
	
	/**
	 * 
	 * @param rol Rol de los usuarios del SNC a consultar.
	 * @return
	 */
	public List<UsuarioDTO> getUsuariosRol(ERol rol) {
		LOGGER.fine("LdapDAO#getUsuariosRol(" + rol.getRol()+ ") inicio");
		List<UsuarioDTO> usuarios = null;
		try {

			String searchFilter = "(&(objectClass=group)(distinguishedName=CN=" + rol.getRol() + "," + ConfiguracionLdap.DN_APLICACION_SNC + "))";
			LOGGER.fine("searchFilter: " + searchFilter);
			String returnedAtts[] = {"member"};
			SearchControls searchCtls = new SearchControls();
			searchCtls.setReturningAttributes(returnedAtts);
			searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
			NamingEnumeration<SearchResult> answer = getLDAPConnection().search(ConfiguracionLdap.DN, searchFilter, searchCtls);
			Attributes attrs = null;
			usuarios = new ArrayList<UsuarioDTO>();
			while (answer.hasMoreElements()) {
				SearchResult sr = (SearchResult) answer.next();
				attrs = sr.getAttributes();
				/**
				 * Ajuste por control de calida Redmine 15589
				 * 
				 * @author alejandro.sanchez
				 */
				if ( attrs == null || attrs.size() == 0 )
				{
					continue;
				}
				String member = attrs.get("member").toString();

				if (member.toUpperCase().startsWith("MEMBER")) {
					member = member.substring(8);
				}				
				String cadenas[] = member.split("CN=");	

				int usuariosNoProcesados = cadenas.length;

				for (String cadena: cadenas){
					LOGGER.fine("CADENA LDAP: " + cadena);

					String distinguisedName = "";
					if(cadena.contains("OU=")){
						if(!cadena.trim().equals("")){
							if(usuariosNoProcesados != 1){
								distinguisedName = "CN=".concat(cadena.substring(0, cadena.lastIndexOf(',')));
							} else {
								distinguisedName = "CN=".concat(cadena);
							}
						
							UsuarioDTO usuarioDTO = searchUserByDistinguishedName(distinguisedName);
														
							usuarios.add(usuarioDTO);	
						}
					}				
					usuariosNoProcesados--;
				}
			}
		} catch (NamingException e) {
			e.printStackTrace();
			LOGGER.log(Level.SEVERE, e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
		for (UsuarioDTO usuarioDTO : usuarios) {
			LOGGER.fine(usuarioDTO.toString());
		}
		LOGGER.fine("LdapDAO#getUsuariosEstructuraRol2(" + rol.getRol()+ ") fin");
		
		return usuarios;
	}

}
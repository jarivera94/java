package co.gov.igac.kier.execution;

import java.math.BigDecimal;
import java.util.List;

import org.kie.server.client.KieServicesClient;
import org.kie.server.client.KieServicesConfiguration;
import org.kie.server.client.KieServicesFactory;

import co.gov.igac.configuracion.LectorConfiguracionAPI;
import co.gov.igac.dao.ManagerDAO;
import co.gov.igac.entity.Users;

public class JbpmConnection {
	
	private static final String URL_KIE_SERVER = LectorConfiguracionAPI.getString(LectorConfiguracionAPI.SERVER_URL);
	
	public ManagerDAO managerdao;
	public KieServicesConfiguration configuration;
	
	public KieServicesClient  getConnection(String user){
	
		
		configuration = KieServicesFactory.newRestConfiguration(URL_KIE_SERVER, user, user).clearExtraClasses();
		configuration.setTimeout(900000L);
		
         
        KieServicesClient kieServicesClient =  KieServicesFactory.newKieServicesClient(configuration);
                
        return kieServicesClient;
	}
	
	public Users autenticaUsuario(String username,String groupname){
		
		Users user= null;
		try {
			managerdao= new ManagerDAO();
			user = managerdao.buscaUsuario(username);
			
			if(user == null){
				List<BigDecimal> grupos = managerdao.busaGrupo(groupname);
				user = new Users(username);
				user  =managerdao.creaUsuario(user,grupos);
			}
		} catch (NullPointerException e) {
			throw new NullPointerException();
		}
		
		return user;
	}
	
	public List<Object[]> consultaDespliegue(String idtarea){
		
		List<Object[]> resultado=null;
		managerdao= new ManagerDAO();
		resultado= managerdao.consultaDespliegue(idtarea);
		return resultado;
		
	} 	
}

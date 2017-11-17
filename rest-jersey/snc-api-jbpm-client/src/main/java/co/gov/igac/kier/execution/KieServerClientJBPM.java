package co.gov.igac.kier.execution;

import java.util.HashMap;
import java.util.List;

import javax.persistence.PersistenceException;

import org.kie.remote.common.rest.KieRemoteHttpRequestException;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.KieServicesException;
import org.kie.server.client.ProcessServicesClient;
import org.kie.server.client.UserTaskServicesClient;

import co.gov.igac.LDAP.LdapDAO;
import co.gov.igac.LDAP.UsuarioDTO;
import co.gov.igac.dao.TareasDao;
import co.gov.igac.logger.impl.ISNCLogger;
import co.gov.igac.logger.impl.SNCLoggerFactory;
import co.gov.igac.nucleo.predial.ObjetoNegocio;
import co.gov.igac.nucleo.predial.PeticionActividad;
import co.gov.igac.nucleo.predial.PeticionAvanzarActividad;
import co.gov.igac.nucleo.predial.SolicitudActualizacion;
import co.gov.igac.nucleo.predial.SolicitudCatastral;
import co.gov.igac.nucleo.predial.SolicitudConservacion;
import co.gov.igac.nucleo.predial.Usuario;
import co.gov.igac.sigc.excepcion.ExcepcionSNC;
import co.gov.igac.tareas.Actividad;
import co.gov.igac.tareas.ConteoACtividad;
import co.gov.igac.utilerias.Calendario;
import co.gov.igac.utilerias.FiltroActividades;
import co.gov.igac.utilerias.FiltroGenerador;
import co.gov.igac.nucleo.predial.TransfeririActividad;

public class KieServerClientJBPM {

	private static final ISNCLogger LOGGER = SNCLoggerFactory.getLogger(KieServerClientJBPM.class);
	private JbpmConnection jbpmConnection;
	private TareasDao tareasDao ;
	
	public KieServerClientJBPM(){
		jbpmConnection= new JbpmConnection();
		tareasDao= new TareasDao();
	}
	
	public String asignaUsuarioLdap(List<FiltroActividades> actividad){
		LdapDAO ldap= LdapDAO.getInstance();
		List<UsuarioDTO> usuarios = ldap.getUsuariosEstructuraRol2(actividad.get(0).getValor().toString(), actividad.get(1).getValor().toString());
		return usuarios.get(0).getLogin().toString();
	}
	
	public void transfiereActividad(TransfeririActividad trasnferirActividad){
		LOGGER.info("Transfiere tarea en  JBPM");
		try {
			String usuario =tareasDao.BuscaUsuarioPorTarea(trasnferirActividad.getActividades().get(0).toString());
			if(usuario=="null"){
				startTaskJBPM(trasnferirActividad.getNuevoUsuario().getLogin().toString(), trasnferirActividad.getActividades().get(0).toString());
			}else {
				transfiereTarea(usuario, trasnferirActividad);
			}
		} catch (IndexOutOfBoundsException e) {
				throw new NullPointerException("Tarea no encontrada");
		} catch (PersistenceException e) {
			throw new PersistenceException("Error de petición");
		}
	}
	
	public void transfiereTarea(String usuario, TransfeririActividad trasnferirActividad){
		LOGGER.info("Delega la tarea en JBPM");
		KieServicesClient kieServicesClient=jbpmConnection.getConnection(usuario);
		UserTaskServicesClient taskClient = kieServicesClient.getServicesClient(UserTaskServicesClient.class);
		List<Object[]>resultado=jbpmConnection.consultaDespliegue(trasnferirActividad.getActividades().get(0).toString());
		taskClient.delegateTask(resultado.get(0)[0].toString(), new Long(trasnferirActividad.getActividades().get(0).toString()), usuario,trasnferirActividad.getNuevoUsuario().getLogin().toString());
	}

	
	public String  asignaTiemposMuertos(List<FiltroActividades> actividad){
		List<Object[]> resultado= tareasDao.BuscaActividad(actividad);
		startTaskJBPM("snc_tiempos_muertos",resultado.get(0)[0].toString());
		return "snc_tiempos_muertos";
	}
	
	@SuppressWarnings("static-access")
	public Long kieStartProcess(HashMap<String, Object> params,SolicitudCatastral solicitudCatastral){
		LOGGER.info("Inicia el proceso catastral en JBPM");
		
		KieServicesClient kieServicesClient =jbpmConnection.getConnection(solicitudCatastral.getActividadesUsuarios().get(0).getUsuarios().get(0).getLogin().toString());
		ProcessServicesClient processClient = kieServicesClient.getServicesClient(ProcessServicesClient.class);
		
		Long processid =processClient.startProcess(solicitudCatastral.ID_CONTAINER,solicitudCatastral.ID_PROCESS,params);
		return processid;
	}
	
	@SuppressWarnings("static-access")
	public Long kieStartProcess(HashMap<String, Object> params,SolicitudActualizacion solicitudActualizacion){
		LOGGER.info("Inicia el proceso catastral en JBPM");
		
		KieServicesClient kieServicesClient =jbpmConnection.getConnection(solicitudActualizacion.getActividadesUsuarios().get(0).getUsuarios().get(0).getLogin().toString());
		ProcessServicesClient processClient = kieServicesClient.getServicesClient(ProcessServicesClient.class);
		
		Long processid =processClient.startProcess(solicitudActualizacion.ID_CONTAINER,solicitudActualizacion.ID_PROCESS,params);
		return processid;
	}
	@SuppressWarnings("static-access")
	public Long kieStartProcess(HashMap<String, Object> params,SolicitudConservacion solicitudConservacion){
		LOGGER.info("Inicia el proceso de cnservacion en JBPM");
		
		KieServicesClient kieServicesClient =jbpmConnection.getConnection(solicitudConservacion.getActividadesUsuarios().get(0).getUsuarios().get(0).getLogin().toString());
		ProcessServicesClient processClient = kieServicesClient.getServicesClient(ProcessServicesClient.class);
		
		Long processid =processClient.startProcess(solicitudConservacion.ID_CONTAINER,solicitudConservacion.ID_PROCESS,params);
		return processid;
	}

	
	public String  actualizaFechaConservacion(SolicitudConservacion solicitudConservacion){
		LOGGER.info("Busca Duracion tareas");
		
		List<Object []> detalleTarea =tareasDao.BuscaTareaPorProcesoC(solicitudConservacion.getIdentificador().toString(),solicitudConservacion.getNumeroSolicitud().toString());
		List<Object []> duracionTarea=tareasDao.BuscaDuracionTarea(detalleTarea);
		String fecha =Calendario.parseDataTime(duracionTarea.get(0)[3].toString());
		tareasDao.actualizaFechaExpiracion(fecha, detalleTarea.get(0)[0].toString());
		
		return detalleTarea.get(0)[0].toString();
	}
	
	public String actaulizaFechaProductosP( String idProceso){
		LOGGER.info("Busca Duracion tareas");
		String idTarea =tareasDao.BuscaTareaPorProcesoP(idProceso);
		String fecha =Calendario.parseDataTime("P1D");
		tareasDao.actualizaFechaExpiracion(fecha, idTarea);
		return idTarea;
	}
	
	public String validarIdentificadorProceso( String identificador){
		LOGGER.info("Validacion del Identificador Proceso");
		String processinstanceid = tareasDao.validacionExisteProceso(identificador);
		return processinstanceid;
	}
	
	public List<String> adaptadorTramites(List<FiltroActividades> tramites){
		return tareasDao.adaptadorTramites(tramites);
	}
	
	public void avanzaActividadJBPM(PeticionAvanzarActividad peticionActividad){
		LOGGER.info("Avanzando la actividad");
		
		try {
			String usuario =tareasDao.BuscaUsuarioPorTarea(peticionActividad.getIdObjeto().toString());
			KieServicesClient kieServicesClient=jbpmConnection.getConnection(usuario);
			UserTaskServicesClient taskClient = kieServicesClient.getServicesClient(UserTaskServicesClient.class);
			HashMap<String, Object > params = new HashMap<>();
			params.put("actividad_", peticionActividad.getObjetoNegocio().getActividadesUsuarios().get(0).getActividad().toString());
			if( peticionActividad.getObjetoNegocio().getTramitesAsociados() != null){
				params.put("tramitesAsociados_", peticionActividad.getObjetoNegocio().getTramitesAsociados());
			}
			List<Object[]>resultado=jbpmConnection.consultaDespliegue(peticionActividad.getIdObjeto().toString());
			
			taskClient.startTask(resultado.get(0)[0].toString(), new Long(peticionActividad.getIdObjeto().toString()), usuario);
			taskClient.completeTask(resultado.get(0)[0].toString(), new Long(peticionActividad.getIdObjeto().toString()), usuario,params);
		} catch (NullPointerException e) {
			throw new NullPointerException("Tarea no encontrada");
		} catch (KieServicesException e) {
			System.out.println(e.getMessage());
			throw new KieServicesException("No se puede completar la tarea");
		} catch (KieRemoteHttpRequestException e) {
			throw new KieRemoteHttpRequestException("Se produjo un error al intentar recuperar el código de respuesta");
		}
	}
	
	public void replanificaActividad(PeticionActividad peticionActividad){
		LOGGER.info("Replanifica la activiadad");
		try {
			String usuario =tareasDao.BuscaUsuarioPorTarea(peticionActividad.getIdActividad().toString());
			KieServicesClient kieServicesClient=jbpmConnection.getConnection(usuario);
			UserTaskServicesClient taskClient = kieServicesClient.getServicesClient(UserTaskServicesClient.class);
			HashMap<String, Object > params = new HashMap<>();
			params.put("tiempoaReplanificar_", peticionActividad.getFechaReanudacion());
			
			List<Object[]>resultado=jbpmConnection.consultaDespliegue(peticionActividad.getIdActividad().toString());
			taskClient.startTask(resultado.get(0)[0].toString(), new Long(peticionActividad.getIdActividad().toString()), usuario);
			taskClient.completeTask(resultado.get(0)[0].toString(), new Long(peticionActividad.getIdActividad().toString()), usuario,params);
		} catch (NullPointerException e) {
			throw new NullPointerException("Tarea no encontrada");
		} catch (KieServicesException e) {
			throw new KieServicesException("No se puede completar la tarea");
		} catch (KieRemoteHttpRequestException e) {
			throw new KieRemoteHttpRequestException("Se produjo un error al intentar recuperar el código de respuesta");
		}
	}
	
	public ObjetoNegocio ConstruyeResultadoAvanzar(PeticionAvanzarActividad actividad){
		ObjetoNegocio objectoNegocio=actividad.getObjetoNegocio();
		objectoNegocio.destruyeUsuarios();
		if(actividad.getObjetoNegocio().getOperacion().toString().equals("productosCatastrales")){
			objectoNegocio.setResultado(tareasDao.BuscaSiguienteTareaProductos(actividad.getIdObjeto().toString()));
		} else if(actividad.getObjetoNegocio().getOperacion().toString().equals("conservacion")){
			objectoNegocio.setResultado(tareasDao.BuscaSiguienteTareaConservacion(actividad.getIdObjeto().toString()));
		}
		
		objectoNegocio.setTransicion(objectoNegocio.getActividadesUsuarios().get(0).getActividad().toString());
		return objectoNegocio;
	}
	
	public void actaulizaFechaProductosT(String idTarea){
		String fecha =Calendario.parseDataTime("P1D");
		tareasDao.actualizaFechaExpiracion(fecha, idTarea);
	}
	public void actaulizaFechaConservacionT(ObjetoNegocio objetoNegocio){
		String tramite = tareasDao.BuscaTramiteActividad(objetoNegocio.getResultado().toString());
		List<Object []> duracion = tareasDao.BuscaDuracionTramiteTarea(objetoNegocio.getActividadesUsuarios().get(0).getActividad().toString(),tramite);
		String fecha =Calendario.parseDataTime(duracion.get(0)[3].toString());
		tareasDao.actualizaFechaExpiracion(fecha, objetoNegocio.getResultado().toString());
	}
	public void reclamaActividad(PeticionActividad peticionActividad){
		LOGGER.info("Reclamando la actividad");
		if(tareasDao.validaReclamar(peticionActividad.getIdActividad().toString())) {
			startTaskJBPM(peticionActividad.getUsuario().getLogin().toString(),peticionActividad.getIdActividad().toString());
		}else {
			throw new NullPointerException("CP41006");
		}
		
	}
	public void reservaPrimerUsuarioCrear(String usuario, String idTarea ){
		LOGGER.info("Reserva siguiente Actividad");
		
		startTaskJBPM(usuario,idTarea);
	}
		
	public void startTaskJBPM(String usuario, String idTarea){
		LOGGER.info("Inicia tarea en JBPM");
		try {
			KieServicesClient kieServicesClient=jbpmConnection.getConnection(usuario);
			UserTaskServicesClient taskClient = kieServicesClient.getServicesClient(UserTaskServicesClient.class);
			List<Object[]>resultado=jbpmConnection.consultaDespliegue(idTarea);
			taskClient.claimTask(resultado.get(0)[0].toString(), new Long(idTarea.toString()), usuario);
		} catch (NullPointerException e) {
			throw new NullPointerException("Error de inicio de Tarea JBPM ");
		} catch (KieServicesException e) {
			throw new KieServicesException("Error Start Task");
		} catch (KieRemoteHttpRequestException e) {
			throw new KieRemoteHttpRequestException("Se produjo un error al intentar recuperar el código de respuesta");
		}
	}

	public List<Actividad> obtenerTareasKie(PeticionActividad peticionActividad) throws ExcepcionSNC{
		List<Actividad> actividades= null;
		
		actividades =tareasDao.retrieveActividades(peticionActividad.getUsuario().getLogin().toString(), peticionActividad.getUsuario().getDescripcionTerritorial().toString(), peticionActividad.getActividad().toString());
		return actividades;
	}
	
	public List<ConteoACtividad> obtenerConteoTareasKie(Usuario usuario){
		List<ConteoACtividad> conteoActividades = null;
		
		conteoActividades= tareasDao.CuentaActividades(usuario);
		return conteoActividades;
	}
	
	public List<Actividad> ObtenerTareasKieFiltro(List<FiltroActividades> filtros){
		List<Actividad> actividades= null;
		FiltroGenerador filtrGenerador= new FiltroGenerador();
		String queryFiltros = filtrGenerador.QueryFiltroTareas(filtros);
		
		
		actividades = tareasDao.retrieveActividadesFiltro(filtros,queryFiltros);
		
		return actividades;
	}
		

}

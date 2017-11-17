package co.gov.igac.dao;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.PersistenceException;

import org.hibernate.loader.plan.exec.process.spi.ReturnReader;
import org.hibernate.loader.plan.spi.Return;

import co.gov.igac.kier.execution.KieServerClientJBPM;
import co.gov.igac.logger.impl.ISNCLogger;
import co.gov.igac.logger.impl.SNCLoggerFactory;
import co.gov.igac.nucleo.predial.Usuario;
import co.gov.igac.sigc.excepcion.ExcepcionSNC;
import co.gov.igac.tareas.Actividad;
import co.gov.igac.tareas.ConteoACtividad;
import co.gov.igac.tareas.RutaActividad;
import co.gov.igac.utilerias.FiltroActividades;

public class TareasDao {
	
	private static final ISNCLogger LOGGER = SNCLoggerFactory.getLogger(KieServerClientJBPM.class);
	
	ManagerDAO managerDAO;
	public TareasDao(){
		managerDAO = new ManagerDAO();
	}
	
	public List<Object[]> asignaUsuarioLdap(List<FiltroActividades> actividad){
		return managerDAO.idTarea(actividad.get(2).getValor().toString());
	}
	
	public List<Object[]> BuscaActividad(List<FiltroActividades> actividad){
		return managerDAO.idTarea(actividad.get(0).getValor().toString());
	} 
	
	public List<String> adaptadorTramites(List<FiltroActividades> tramites){
		return managerDAO.adaptadorServicios(new Long(tramites.get(0).getValor())); 
	}
	
	public String BuscaSiguienteTareaProductos(String idActividad){
		return managerDAO.BuscaActividadProductos(idActividad);
		
	}

	public String BuscaUsuarioPorTarea(String idtarea){
		String resultado = null;
		try {
			resultado = managerDAO.BuscaUsuarioPorTarea(idtarea);
		} catch (NullPointerException e) {
			throw new NullPointerException("Tarea no encontrada");
		} catch (PersistenceException e) {
			throw new PersistenceException("Error de petición");
		}
		return resultado;
	}
	
	
	
	public String BuscaSiguienteTareaConservacion(String idActividad){
		return managerDAO.BuscaActividadConservacion(idActividad);
	}
	
	public List<Object []> BuscaTareaPorProcesoC(String identificador, String numeroSolicitud){
		return managerDAO.BuscaActividadConservacion(identificador, numeroSolicitud);
	}  
	
	public String BuscaTramiteActividad(String idTarea){
		return managerDAO.BuscaActividadTramite(idTarea);
	}
	public List<Object []> BuscaDuracionTramiteTarea(String nombreActividad, String tipoTramite){
		return managerDAO.BuscaDuracionTareaConservacion(nombreActividad, tipoTramite);
	}
	
	public List<Object []> BuscaDuracionTarea(List<Object []> detalleTarea){
		String[] nombreActividad = detalleTarea.get(0)[1].toString().split("\\.");
		return managerDAO.BuscaDuracionTareaConservacion(nombreActividad[2]+"."+nombreActividad[3], detalleTarea.get(0)[3].toString());
	}
	
	public String BuscaTareaPorProcesoP(String idproceso){
		return managerDAO.BuscaActividadProcesoP(idproceso);
	} 
	
	public String validacionExisteProceso(String identificador){
		return managerDAO.validacionExisteProceso(identificador);
	}
	
	public List<ConteoACtividad> CuentaActividades(Usuario usuario){
		List<ConteoACtividad> actividades= null;
		List<Object[]> conteoActividades=null;
		conteoActividades= managerDAO.ConteoActividadesUsuario(usuario.getLogin().toString());
		actividades= ConstruyeConteoActividad(conteoActividades);
		return actividades;
	}
	
	public void actualizaFechaExpiracion(String fecha, String idTarea){
		managerDAO.asignaDuracionTarea(fecha, idTarea);
	} 
	
	public boolean validaReclamar(String idTarea) {
		boolean max = (managerDAO.validarTarea(idTarea).equals("null")) ? true : false;
		return max;
	}
		
	public List<Actividad> retrieveActividadesFiltro(List<FiltroActividades> filtros ,String queryFiltros){
		List<Actividad> actividades= null;
		
		List<Object[]> listadoActividades=null;
		//espacio para capturar error
		listadoActividades= managerDAO.TareasConFiltro(queryFiltros);
		actividades = ConstruyeActividadesConFiltro(listadoActividades);
		
		return actividades;
	}
	
	public List<ConteoACtividad> ConstruyeConteoActividad(List<Object[]>conteoActividades){
		List<ConteoACtividad> actividades= new ArrayList<>();
		ConteoACtividad conteo = null;
		List<Object[]>parametros_tareas;
		for (int i = 0; i < conteoActividades.size(); i++) {
			parametros_tareas= managerDAO.ParametrosTareas(conteoActividades.get(i)[1].toString().split("\\."));
			conteo = new ConteoACtividad();
			conteo.setRutaActividad(conteoActividades.get(i)[1]);
			conteo.setConteo(conteoActividades.get(i)[0]);
			conteo.setUrlCasoDeUso(parametros_tareas.get(0)[0]);
			conteo.setTipoProcesamiento(parametros_tareas.get(0)[1]);
			conteo.setTipoActividad(parametros_tareas.get(0)[2]);
			conteo.setTransiciones(construyeArrayList(parametros_tareas.get(0)[3].toString()));
			actividades.add(conteo);
		}
		
		return actividades;
	}
	
	
	public List<Actividad> retrieveActividades(String usuario, String territorial, String actividad)
			throws ExcepcionSNC {
		
		List<Actividad> actividades = null;
		List<Object[]> listadoActividades=null;		
		try {

			listadoActividades =managerDAO.ListaTareas(usuario, territorial,actividad);
			
			actividades= ConstruyeActividad(listadoActividades);
		}catch(Exception e){
			e.printStackTrace();
		} 
		
		return actividades;
	}
	public List<Actividad> ConstruyeActividadesConFiltro(List<Object[]> listadoActividades){
		LOGGER.info("Construye lista de actividades con filtro");
		List<Actividad> actividades = new ArrayList<>();
		List<Object[]> parametrosTareas=null;
		List<Object[]> variablesProceso=null;
		Actividad actividad =null;
		
		for (int i = 0; i < listadoActividades.size(); i++) {
			try {
				String[] nombreActividad=listadoActividades.get(i)[5].toString().trim().split("\\.");
				parametrosTareas=managerDAO.consultaParametrosTarea(nombreActividad[2]+"."+nombreActividad[3]);
				
				//Construye la actividad
				actividad = new Actividad();
				actividad.setId(listadoActividades.get(i)[0]);
				actividad.setTipo("Instancia Actividad");
				actividad.setNombreDeDespliegue(listadoActividades.get(i)[5]);
				actividad.setNombre(parametrosTareas.get(0)[0]);
				actividad.setRutaActividad(construyeRutaActividad(parametrosTareas));
				if(actividad.getRutaActividad().getProceso().toString().equals("Conservación")){
					//
					System.out.println();
					variablesProceso=managerDAO.ConsultaPrimariosProcesoConservacion(listadoActividades.get(i)[6].toString());
					actividad.setIdObjetoNegocio(variablesProceso.get(0)[0]);
					actividad.setNumeroActualizacion(variablesProceso.get(0)[0]);
					actividad.setNumeroSolicitud(variablesProceso.get(0)[1]);
					actividad.setTipoTramite(variablesProceso.get(0)[2]);
					actividad.setNumeroPredial(variablesProceso.get(0)[3]);
					actividad.setNumeroRadicacion(variablesProceso.get(0)[4]);
				}else{
					variablesProceso=managerDAO.ConsultaPrimariosProcesoCatastral(listadoActividades.get(i)[6].toString());
					actividad.setIdObjetoNegocio(variablesProceso.get(0)[0]);
					actividad.setNumeroActualizacion(variablesProceso.get(0)[0]);
					actividad.setNumeroSolicitud(variablesProceso.get(0)[1]);
					actividad.setTipoSolicitud(variablesProceso.get(0)[2]);
				}
				
				
				// formato de fecha en milisegundos
				DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date = format.parse(listadoActividades.get(i)[1].toString());
				long millisFechAc = date.getTime();
				actividad.setFechaAsignacion(millisFechAc);
				
				if(!(listadoActividades.get(i)[2]== null||listadoActividades.get(i)[2].toString().equals(""))){
					date = null;
					date = format.parse(listadoActividades.get(i)[2].toString());
					millisFechAc = date.getTime();
					actividad.setFechaExpiracion(millisFechAc);
				}
				
				if(listadoActividades.get(i)[3]==null || listadoActividades.get(i)[3].toString().equals("") ){
					actividad.setEstaReclamada(false);
					actividad.setEstado("Por Reclamar");
					actividad.setReclamada(false);
				}else{
					actividad.setEstaReclamada(true);
					actividad.setReclamada(true);
					actividad.setEstado("Reclamada");
				}
				
				
				actividad.setUsuarioOriginador("adminkie");
				actividad.setUsuarioEjecutor(listadoActividades.get(i)[7]);
				actividad.setPrioridad(listadoActividades.get(i)[4]);
				actividad.setAlerta(0);
				actividad.setTipoProcesamiento(parametrosTareas.get(0)[9]);
				actividad.setTipoActividad(parametrosTareas.get(0)[5]);
				actividad.setTransiciones(construyeArrayList(parametrosTareas.get(0)[7].toString()));
				actividad.setRoles(construyeArrayList(parametrosTareas.get(0)[8].toString()));
				
				actividad.setIdCorrelacion(0);
				
				actividad.setUrlcasoUso(parametrosTareas.get(0)[6]);
				
				actividad.setNumeroCorrelaciones(0);

				actividades.add(actividad);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return actividades;
	}
	public List<Actividad> ConstruyeActividad(List<Object[]> listadoActividades){
		
		LOGGER.info("Construye lista de actividades");
		List<Actividad> actividades = new ArrayList<>();
		List<Object[]> parametrosTareas=null;
		List<Object[]> variablesProceso=null;
		Actividad actividad =null;
		for (int i = 0; i < listadoActividades.size(); i++) {
			
			try {
				String[] nombreActividad=listadoActividades.get(i)[5].toString().trim().split("\\.");

				//Consulta los parametros de las tareas
				parametrosTareas=managerDAO.consultaParametrosTarea(nombreActividad[2]+"."+nombreActividad[3]);
				//Consulta los valores primarios del proceso
				
				//Construye la actividad
				actividad = new Actividad();
				actividad.setId(listadoActividades.get(i)[14]);
				actividad.setTipo("Instancia Actividad");
				actividad.setNombreDeDespliegue(listadoActividades.get(i)[7]);
				actividad.setNombre(parametrosTareas.get(0)[0]);
				actividad.setRutaActividad(construyeRutaActividad(parametrosTareas));
				
				if(actividad.getRutaActividad().getProceso().toString().equals("Conservación")){	
					variablesProceso=managerDAO.ConsultaPrimariosProcesoConservacion(listadoActividades.get(i)[11].toString());
					actividad.setIdObjetoNegocio(variablesProceso.get(0)[0]);
					actividad.setNumeroActualizacion(variablesProceso.get(0)[0]);
					actividad.setNumeroSolicitud(variablesProceso.get(0)[1]);
					actividad.setTipoTramite(variablesProceso.get(0)[2]);
					actividad.setNumeroPredial(variablesProceso.get(0)[3]);
					actividad.setNumeroRadicacion(variablesProceso.get(0)[4]);
					
				}else{
					
					variablesProceso=managerDAO.ConsultaPrimariosProcesoCatastral(listadoActividades.get(i)[11].toString());
					actividad.setIdObjetoNegocio(variablesProceso.get(0)[0]);
					actividad.setNumeroActualizacion(variablesProceso.get(0)[0]);
					actividad.setNumeroSolicitud(variablesProceso.get(0)[1]);
					actividad.setTipoSolicitud(variablesProceso.get(0)[2]);
				}
				
				
				DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date = format.parse(listadoActividades.get(i)[0].toString());
				long millisFechAc = date.getTime();

				actividad.setFechaAsignacion(millisFechAc);
				if(!(listadoActividades.get(i)[6]== null||listadoActividades.get(i)[6].toString().equals(""))){
					date = null;
					date = format.parse(listadoActividades.get(i)[6].toString());
					millisFechAc = date.getTime();
					actividad.setFechaExpiracion(millisFechAc);
				}
				
				if(listadoActividades.get(i)[1]==null || listadoActividades.get(i)[1].toString().equals("") ){
					actividad.setEstaReclamada(false);
					actividad.setEstado("Por Reclamar");
					actividad.setReclamada(false);
				}else{
					actividad.setEstaReclamada(true);
					actividad.setReclamada(true);
					actividad.setEstado("Reclamada");
				}
				
				actividad.setUsuarioOriginador("adminkie");
				actividad.setUsuarioEjecutor(listadoActividades.get(i)[1]);
				actividad.setPrioridad(listadoActividades.get(i)[9]);
				actividad.setAlerta(0);
				actividad.setTipoProcesamiento(parametrosTareas.get(0)[9]);
				actividad.setTipoActividad(parametrosTareas.get(0)[5]);
				actividad.setTransiciones(construyeArrayList(parametrosTareas.get(0)[7].toString()));
				actividad.setRoles(construyeArrayList(parametrosTareas.get(0)[8].toString()));
				
				actividad.setIdCorrelacion(0);
				
				actividad.setUrlcasoUso(parametrosTareas.get(0)[6]);
				
				actividad.setNumeroCorrelaciones(0);

				actividades.add(actividad);
			} catch (ParseException e) {
				
				e.printStackTrace();
			}
		}
		
		return actividades;
	}
	
	public RutaActividad construyeRutaActividad(List<Object[]> parametrosTareas){
		
		
		String[] nombreActividad=parametrosTareas.get(0)[0].toString().split("\\.");
		RutaActividad rutaActividad =new RutaActividad();
		rutaActividad.setMacroproceso(parametrosTareas.get(0)[1].toString());
		rutaActividad.setProceso(parametrosTareas.get(0)[2].toString());
		rutaActividad.setSubproceso(parametrosTareas.get(0)[3].toString());
		rutaActividad.setActividad(nombreActividad[1].toString());
		rutaActividad.setMacroprocesoProceso(parametrosTareas.get(0)[4].toString());
		rutaActividad.setSubprocesoActividad(parametrosTareas.get(0)[0].toString());
		
		return rutaActividad;
	}
	
	public ArrayList<String> construyeArrayList(String cadenaTransicione){
		ArrayList<String> transiciones = new ArrayList<String>(Arrays.asList(cadenaTransicione.trim().split(";")));
		return transiciones;
		
	}
	

}

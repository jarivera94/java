package co.gov.igac.client;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.kie.remote.common.rest.KieRemoteHttpRequestException;
import org.kie.server.client.KieServicesException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;

import co.gov.igac.entity.Users;
import co.gov.igac.kier.execution.JbpmConnection;
import co.gov.igac.kier.execution.KieServerClientJBPM;
import co.gov.igac.logger.impl.ISNCLogger;
import co.gov.igac.logger.impl.SNCLoggerFactory;
import co.gov.igac.nucleo.predial.ObjetoNegocio;
import co.gov.igac.nucleo.predial.PeticionActividad;
import co.gov.igac.nucleo.predial.PeticionAvanzarActividad;
import co.gov.igac.nucleo.predial.TransfeririActividad;
import co.gov.igac.nucleo.predial.Usuario;
import co.gov.igac.parametros.ParametrosServicioRest;
import co.gov.igac.sigc.excepcion.ExcepcionSNC;
import co.gov.igac.sigc.excepcion.ManejadorExcepciones;
import co.gov.igac.tareas.Actividad;
import co.gov.igac.tareas.ConteoACtividad;
import co.gov.igac.utilerias.FiltroActividades;
import co.gov.igac.utilerias.ParseData;

@Path(ParametrosServicioRest.PATH_GESTOR_TAREAS)
public class GestorTareasRest {
	private static final String PLANTILLA_RESULTADO = "'{'\"estado\":\"{0}\",\"resultado\":\"{1}\"'}'";
	private static ParseData parseData = null;
	private static String hr = "\n===================================================================";
	private static final ISNCLogger LOGGER = SNCLoggerFactory
			.getLogger(GestorTareasRest.class);
	
	
	public GestorTareasRest() throws ExcepcionSNC {
		super();
	
		if(parseData== null){
			parseData= new ParseData();
		}
	}

	
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.TEXT_PLAIN)
	@Path("/validausuario")
	public String validaUsuario(String avanceActividad) {
		LOGGER.info(avanceActividad);
		JbpmConnection jbpmconnection= new JbpmConnection();
		Users user =jbpmconnection.autenticaUsuario(avanceActividad, "funcionario_radicador");
		return user.toString();
	}

	

	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.TEXT_PLAIN)
	@Path(ParametrosServicioRest.AVANZAR_ACTIVIDAD_CONSERVACION_SIMPLE)
	public String avanzarActividadConservacionSimple(String avanceActividadJson) {
		LOGGER.info(new String (Thread.currentThread().getStackTrace()[1].getMethodName()));
		LOGGER.info(avanceActividadJson);
		LOGGER.info(hr);
		return "";
	}

	
	
	
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.TEXT_PLAIN)
	@Path(ParametrosServicioRest.AVANZAR_ACTIVIDAD_OSMI)
	public String avanzarActividadOsmi(String avanceActividad) {
		LOGGER.info(new String (Thread.currentThread().getStackTrace()[1].getMethodName()));
		String result = null;
		LOGGER.info(hr);
		return "";
	}
	
	
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.TEXT_PLAIN)
	@Path(ParametrosServicioRest.AVANZAR_ACTIVIDAD_OSMI_CONTROL_CALIDAD)
	public String avanzarActividadOsmiControlCalidad(String avanceActividad) {
		LOGGER.info(new String (Thread.currentThread().getStackTrace()[1].getMethodName()));
		String result = null;
		LOGGER.info(hr);
		return "";
	}
	
	///
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.TEXT_PLAIN)
	@Path(ParametrosServicioRest.AVANZAR_ACTIVIDAD_CONSERVACION)
	public String avanzarActividadConservacion(String avanceActividad) {
		LOGGER.info(new String (Thread.currentThread().getStackTrace()[1].getMethodName()));
		LOGGER.info(avanceActividad);
		String result ="";
		try {
			String siguienteUsuario= null;
			String rolsiguienteUsuario=null;
			avanceActividad= parseData.parsePeticion(avanceActividad);
			PeticionAvanzarActividad peticionActividad = parseData.objectToPeticionAvanzar(avanceActividad);
			KieServerClientJBPM kieServerClient = new KieServerClientJBPM();
			int reservar = peticionActividad.getObjetoNegocio().getActividadesUsuarios().get(0).getUsuarios().size();
			if(reservar==1){
				siguienteUsuario=peticionActividad.getObjetoNegocio().getActividadesUsuarios().get(0).getUsuarios().get(0).getLogin();
				rolsiguienteUsuario=peticionActividad.getObjetoNegocio().getActividadesUsuarios().get(0).getUsuarios().get(0).getPrimerRol();
			}
			kieServerClient.avanzaActividadJBPM(peticionActividad);
			ObjetoNegocio objetoNegocio = kieServerClient.ConstruyeResultadoAvanzar(peticionActividad);
			kieServerClient.actaulizaFechaConservacionT(objetoNegocio);
			if(reservar==1){			
				parseData.loginKie(siguienteUsuario,rolsiguienteUsuario);
				kieServerClient.startTaskJBPM(siguienteUsuario, objetoNegocio.getResultado().toString());
			}
			result = MessageFormat.format(PLANTILLA_RESULTADO,"OK", parseData.ObjectToJsonNull(objetoNegocio));
		} catch (JsonParseException e) {			
			ExcepcionSNC exc = ManejadorExcepciones.CP42001
					.getExcepcion(LOGGER, e,
							"avance de actividad conservacion");
			result = MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage()+", "+exc.getMessage());
			
		} catch (NullPointerException e) {
			
			ExcepcionSNC exc = ManejadorExcepciones.CP42001
					.getExcepcion(LOGGER, e,
							"avance de actividad conservacion");
			result = MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage()+", valide la petición"
					+ "de entrada. "+exc.getMessage());
			
		} catch (IndexOutOfBoundsException e) {
			e.getLocalizedMessage();
			ExcepcionSNC exc = ManejadorExcepciones.CP42007
					.getExcepcion(LOGGER, e,
							"avance de actividad conservacion");
			result = MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage()+", "+exc.getMessage());
		} catch (IllegalArgumentException e) {
			
			ExcepcionSNC exc = ManejadorExcepciones.CP42008
					.getExcepcion(LOGGER, e,
							"avance de actividad conservacion");
			result = MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage()+", "+exc.getMessage());
		} catch (KieServicesException e) {
			ExcepcionSNC exc = ManejadorExcepciones.CP44001
					.getExcepcion(LOGGER, e,
							"avance de actividad conservacion");
			result = MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage()+", "+exc.getMessage());
		} catch (PersistenceException e) {
			ExcepcionSNC exc = ManejadorExcepciones.CP45001
					.getExcepcion(LOGGER, e,
							"avance de actividad conservacion");
			result = MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage()+", "+exc.getMessage());
		} catch (KieRemoteHttpRequestException e) {
			ExcepcionSNC exc = ManejadorExcepciones.CP44002
					.getExcepcion(LOGGER, e,
							"avance de actividad conservacion");
			result = MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage()+", "+exc.getMessage());
		}
		LOGGER.info(hr);
		return result ;
	}
	
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.TEXT_PLAIN)
	@Path(ParametrosServicioRest.AVANZAR_ACTIVIDAD_ACTUALIZACION)
	public String avanzarActividadActualizacion(String avanceActividad) {
		LOGGER.info(new String (Thread.currentThread().getStackTrace()[1].getMethodName()));
		LOGGER.info(avanceActividad);
		String result ="";
		try {
			String siguienteUsuario= null;
			String rolsiguienteUsuario=null;
			avanceActividad= parseData.parsePeticion(avanceActividad);
			PeticionAvanzarActividad peticionActividad = parseData.objectToPeticionAvanzar(avanceActividad);
			KieServerClientJBPM kieServerClient = new KieServerClientJBPM();
			int reservar =peticionActividad.getObjetoNegocio().getActividadesUsuarios().get(0).getUsuarios().size();
			if(reservar==1){
				siguienteUsuario=peticionActividad.getObjetoNegocio().getActividadesUsuarios().get(0).getUsuarios().get(0).getLogin();
				rolsiguienteUsuario=peticionActividad.getObjetoNegocio().getActividadesUsuarios().get(0).getUsuarios().get(0).getPrimerRol();
			}
			kieServerClient.avanzaActividadJBPM(peticionActividad);
			ObjetoNegocio objetoNegocio = kieServerClient.ConstruyeResultadoAvanzar(peticionActividad);
			kieServerClient.actaulizaFechaProductosT(objetoNegocio.getResultado().toString());
			if(reservar==1){			
				parseData.loginKie(siguienteUsuario,rolsiguienteUsuario);
				kieServerClient.startTaskJBPM(siguienteUsuario, objetoNegocio.getResultado().toString());
			}
			result = MessageFormat.format(PLANTILLA_RESULTADO,"OK", parseData.ObjectToJsonNull(objetoNegocio));
		} catch (JsonParseException e) {			
			ExcepcionSNC exc = ManejadorExcepciones.CP42001
					.getExcepcion(LOGGER, e,
							"avance de actividad conservacion");
			result = MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage()+", "+exc.getMessage());
			
		} catch (NullPointerException e) {
			
			ExcepcionSNC exc = ManejadorExcepciones.CP42001
					.getExcepcion(LOGGER, e,
							"avance de actividad conservacion");
			result = MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage()+", valide la petición"
					+ "de entrada. "+exc.getMessage());
			
		} catch (IndexOutOfBoundsException e) {
			e.getLocalizedMessage();
			ExcepcionSNC exc = ManejadorExcepciones.CP42007
					.getExcepcion(LOGGER, e,
							"avance de actividad conservacion");
			result = MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage()+", "+exc.getMessage());
		} catch (IllegalArgumentException e) {
			
			ExcepcionSNC exc = ManejadorExcepciones.CP42008
					.getExcepcion(LOGGER, e,
							"avance de actividad conservacion");
			result = MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage()+", "+exc.getMessage());
		} catch (KieServicesException e) {
			ExcepcionSNC exc = ManejadorExcepciones.CP44001
					.getExcepcion(LOGGER, e,
							"avance de actividad conservacion");
			result = MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage()+", "+exc.getMessage());
		} catch (PersistenceException e) {
			ExcepcionSNC exc = ManejadorExcepciones.CP45001
					.getExcepcion(LOGGER, e,
							"avance de actividad conservacion");
			result = MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage()+", "+exc.getMessage());
		} catch (KieRemoteHttpRequestException e) {
			ExcepcionSNC exc = ManejadorExcepciones.CP44002
					.getExcepcion(LOGGER, e,
							"avance de actividad conservacion");
			result = MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage()+", "+exc.getMessage());
		}
		LOGGER.info(hr);
		return result ;
	}

	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.TEXT_PLAIN)
	@Path(ParametrosServicioRest.AVANZAR_ACTIVIDAD_PRODUCTOS)
	public String avanzarActividadProductos(String avanceActividad) {
		LOGGER.info(new String (Thread.currentThread().getStackTrace()[1].getMethodName()));
		LOGGER.info(avanceActividad);
		String result ="";
		try {
			String siguienteUsuario= null;
			String rolsiguienteUsuario=null;
			avanceActividad= parseData.parsePeticion(avanceActividad);
			PeticionAvanzarActividad peticionActividad = parseData.objectToPeticionAvanzar(avanceActividad);
			KieServerClientJBPM kieServerClient = new KieServerClientJBPM();
			int reservar = peticionActividad.getObjetoNegocio().getActividadesUsuarios().get(0).getUsuarios().size();
			if(reservar==1){
				siguienteUsuario=peticionActividad.getObjetoNegocio().getActividadesUsuarios().get(0).getUsuarios().get(0).getLogin();
				rolsiguienteUsuario=peticionActividad.getObjetoNegocio().getActividadesUsuarios().get(0).getUsuarios().get(0).getPrimerRol();
			}
			kieServerClient.avanzaActividadJBPM(peticionActividad);
			ObjetoNegocio objetoNegocio = kieServerClient.ConstruyeResultadoAvanzar(peticionActividad);
			kieServerClient.actaulizaFechaProductosT(objetoNegocio.getResultado().toString());
			if(reservar==1){			
				parseData.loginKie(siguienteUsuario,rolsiguienteUsuario);
				kieServerClient.startTaskJBPM(siguienteUsuario, objetoNegocio.getResultado().toString());
			}
			result = MessageFormat.format(PLANTILLA_RESULTADO,"OK", parseData.ObjectToJson(objetoNegocio));
		} catch (JsonParseException e) {			
			ExcepcionSNC exc = ManejadorExcepciones.CP42001
					.getExcepcion(LOGGER, e,
							"avance de actividad de productos");
			result = MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage()+", "+exc.getMessage());
			
		} catch (NullPointerException e) {
			
			ExcepcionSNC exc = ManejadorExcepciones.CP42001
					.getExcepcion(LOGGER, e,
							"avance de actividad de productos");
			result = MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage()+", valide la petición"
					+ "de entrada. "+exc.getMessage());
			
		} catch (IndexOutOfBoundsException e) {
			e.getLocalizedMessage();
			ExcepcionSNC exc = ManejadorExcepciones.CP42007
					.getExcepcion(LOGGER, e,
							"avance de actividad de productos");
			result = MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage()+", "+exc.getMessage());
		} catch (IllegalArgumentException e) {
			
			ExcepcionSNC exc = ManejadorExcepciones.CP42008
					.getExcepcion(LOGGER, e,
							"avance de actividad de productos");
			result = MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage()+", "+exc.getMessage());
		} catch (KieServicesException e) {
			ExcepcionSNC exc = ManejadorExcepciones.CP44001
					.getExcepcion(LOGGER, e,
							"avance de actividad de productos");
			result = MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage()+", "+exc.getMessage());
		} catch (PersistenceException e) {
			ExcepcionSNC exc = ManejadorExcepciones.CP45001
					.getExcepcion(LOGGER, e,
							"avance de actividad de productos");
			result = MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage()+", "+exc.getMessage());
		} catch (KieRemoteHttpRequestException e) {
			ExcepcionSNC exc = ManejadorExcepciones.CP44002
					.getExcepcion(LOGGER, e,
							"avance de actividad de productos");
			result = MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage()+", "+exc.getMessage());
		}
		LOGGER.info(hr);
		return result ;
	}

	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.TEXT_PLAIN)
	@Path(ParametrosServicioRest.RECLAMAR_ACT_CONSERVACION)
	public String reclamarActividadConservacion(String reclamarActividad) {
		LOGGER.info(new String (Thread.currentThread().getStackTrace()[1].getMethodName()));
		LOGGER.info(reclamarActividad);
		String result = null;		
		try {
			reclamarActividad = parseData.parsePeticion(reclamarActividad);
			PeticionActividad peticionActividad = parseData.objetcPeticionActividad(reclamarActividad);
			parseData.loginKie(peticionActividad.getUsuario().getLogin(), peticionActividad.getUsuario().getPrimerRol());
			KieServerClientJBPM kieServerClient= new KieServerClientJBPM ();
			kieServerClient.reclamaActividad(peticionActividad);
			result = MessageFormat.format(PLANTILLA_RESULTADO, "OK","null");
		} catch (JsonParseException e) {			
			ExcepcionSNC exc = ManejadorExcepciones.CP42001
					.getExcepcion(LOGGER, e,
							"Reclamar actividad Conservacion");
			result = MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage()+", "+exc.getMessage());
			
		} catch (NullPointerException e) {
			if (e.getMessage().equals("CP41006")) {
				ExcepcionSNC exc = ManejadorExcepciones.CP41006
						.getExcepcion(LOGGER, e,
								"Reclamar actividad Conservacion");
				result = MessageFormat.format(PLANTILLA_RESULTADO, e.getMessage(), exc.getMessage());
			}else {
				ExcepcionSNC exc = ManejadorExcepciones.CP42001
						.getExcepcion(LOGGER, e,
								"Reclamar actividad Conservacion");
				result = MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage()+", "+exc.getMessage());
			}
			
		} catch (IndexOutOfBoundsException e) {
			
			ExcepcionSNC exc = ManejadorExcepciones.CP42007
					.getExcepcion(LOGGER, e,
							"Reclamar actividad Conservacion");
			result = MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage()+", "+exc.getMessage());
		} catch (IllegalArgumentException e) {
			
			ExcepcionSNC exc = ManejadorExcepciones.CP42008
					.getExcepcion(LOGGER, e,
							"Reclamar actividad Conservacion");
			result = MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage()+", "+exc.getMessage());
		} catch (KieServicesException e) {
			
			ExcepcionSNC exc = ManejadorExcepciones.CP44001
					.getExcepcion(LOGGER, e,
							"Reclamar actividad Conservacion");
			result = MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage()+", "+exc.getMessage());
		} catch (KieRemoteHttpRequestException e) {
			
			ExcepcionSNC exc = ManejadorExcepciones.CP44002
					.getExcepcion(LOGGER, e,
							"Reclamar actividad Conservacion");
			result = MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage()+", "+exc.getMessage());
		} catch (PersistenceException e) {
			
			ExcepcionSNC exc = ManejadorExcepciones.CP45001
					.getExcepcion(LOGGER, e,
							"Reclamar actividad Conservacion");
			result = MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage()+", "+exc.getMessage());
		}
		LOGGER.info(hr);
		return result;
	}
	
	
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.TEXT_PLAIN)
	@Path(ParametrosServicioRest.TRANSFERIR_ACTIVIDAD)
	public String transferirActividadConservacion(String transActividad) {
		LOGGER.info(new String (Thread.currentThread().getStackTrace()[1].getMethodName()));
		LOGGER.info(transActividad);
		String result = null;
		try {
			transActividad = parseData.parsePeticion(transActividad);
			TransfeririActividad transferirActividad = parseData.objectoTransferirActividad(transActividad);
			KieServerClientJBPM kieServiceClient = new KieServerClientJBPM();
			kieServiceClient.transfiereActividad(transferirActividad);
			result = MessageFormat.format(PLANTILLA_RESULTADO, "OK","true");
		} catch (JsonParseException e) {
			
			ExcepcionSNC exc = ManejadorExcepciones.CP42001
					.getExcepcion(LOGGER, e,
							"Transferir Actividad");
			result = MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage()+" "+exc.getMensajeUsuario());

			
		} catch (NullPointerException e) {
			
			ExcepcionSNC exc = ManejadorExcepciones.CP42006
					.getExcepcion(LOGGER, e,
							"Transferir Actividad");
			result = MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage()+" "+exc.getMensajeUsuario());
			
		} catch (IndexOutOfBoundsException e) {
			
			ExcepcionSNC exc = ManejadorExcepciones.CP42007
					.getExcepcion(LOGGER, e,
							"Transferir Actividad");
			result = MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage()+" "+exc.getMensajeUsuario());
		} catch (IllegalArgumentException e) {
			
			ExcepcionSNC exc = ManejadorExcepciones.CP42008
					.getExcepcion(LOGGER, e,
							"Transferir Actividad");
			result = MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage()+" "+exc.getMensajeUsuario());	
		} catch (PersistenceException e) {
			
			ExcepcionSNC exc = ManejadorExcepciones.CP45001
					.getExcepcion(LOGGER, e,
							"Transferir Actividad");
			result = MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage()+" "+exc.getMensajeUsuario());
		} 
		LOGGER.info(hr);
		return result;
	}
	
	
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.TEXT_PLAIN)
	@Path(ParametrosServicioRest.SUSPENDER_ACTIVIDAD)
	public String suspenderActividad(String suspenderActividadStr) {
		LOGGER.info(new String (Thread.currentThread().getStackTrace()[1].getMethodName()));
		LOGGER.info(suspenderActividadStr);
		String result = null;
		try {
			/*WRITE SERVICE*/
		}catch (JsonParseException e) {			
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} 
		LOGGER.info(hr);
		return "";
	}
	
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.TEXT_PLAIN)
	@Path(ParametrosServicioRest.REPLANIFICAR_ACTIVIDAD)
	public String replanificarActividad(String replanificarActividadStr) {
		LOGGER.info(new String (Thread.currentThread().getStackTrace()[1].getMethodName()));
		LOGGER.info(replanificarActividadStr);
		String result = null;
		replanificarActividadStr = parseData.parsePeticion(replanificarActividadStr);
		PeticionActividad peticion = parseData.objetcPeticionActividad(replanificarActividadStr);
		
		try {
			KieServerClientJBPM kieServiceClient = new KieServerClientJBPM();
			kieServiceClient.replanificaActividad(peticion);
			result = MessageFormat.format(PLANTILLA_RESULTADO, "OK",peticion.getFechaReanudacion());
		}catch (JsonParseException e) {			
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} 
		LOGGER.info(hr);
		return result;
	}
	
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.TEXT_PLAIN)
	@Path(ParametrosServicioRest.REANUDAR_ACTIVIDAD)
	public String reanudarActividad(String idActividad) {
		LOGGER.info(new String (Thread.currentThread().getStackTrace()[1].getMethodName()));
		LOGGER.info(idActividad);
		String result = null;
		try {
			/*WRITE SERVICE*/
		}catch (JsonParseException e) {			
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} 
		LOGGER.info(hr);
		return "";
	}
	
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.TEXT_PLAIN)
	@Path(ParametrosServicioRest.RECLAMAR_ACT_ACTUALIZACION)
	public String reclamarActividadActualizacion(String reclamarActividad) {
		LOGGER.info(new String (Thread.currentThread().getStackTrace()[1].getMethodName()));
		LOGGER.info(reclamarActividad);
		String result = null;
		LOGGER.info(reclamarActividad);
		try {
			/*WRITE SERVICE*/
		}catch (JsonParseException e) {			
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} 
		LOGGER.info(hr);
		return "";
	}
	
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path(ParametrosServicioRest.OBTENER_ACTIVIDADES_CON_USUARIO_FILTRO)
	public String obtenerListaActividadesUsuarioConFiltro(
			@FormParam(ParametrosServicioRest.USUARIO_DTO) String usuarioAsJSon,
			@FormParam(ParametrosServicioRest.FILTRO) String transicion) {
		LOGGER.info(new String (Thread.currentThread().getStackTrace()[1].getMethodName()));
		
		
		LOGGER.info(usuarioAsJSon);
		LOGGER.info(transicion);
		
		usuarioAsJSon= parseData.parsePeticion(usuarioAsJSon);
		Usuario usuario = parseData.objectToUsuario(usuarioAsJSon);
		String result= null;
		try {
			
			List<Actividad> actividades= null;
			List<FiltroActividades> filtros= new ArrayList<FiltroActividades>();
			FiltroActividades filtroUsuario = new FiltroActividades();
			FiltroActividades filtroActividad = new FiltroActividades();
			FiltroActividades filtroEstados = new FiltroActividades();
			
			filtroUsuario.setAtributo("usuarioPotencial");
			filtroUsuario.setValor(usuario.getLogin().toString());
			filtros.add(filtroUsuario);
			
			filtroActividad.setAtributo("estados");
			filtroActividad.setValor("2,8");
			filtros.add(filtroActividad);
			
			filtroEstados.setAtributo("nombreActividad");
			filtroEstados.setValor(transicion);
			filtros.add(filtroEstados);
			
			KieServerClientJBPM kieServiceClient = new KieServerClientJBPM();
			actividades = kieServiceClient.ObtenerTareasKieFiltro(filtros);
			result = MessageFormat.format(PLANTILLA_RESULTADO, "OK",parseData.ObjectToJson(actividades));
			
			
		} catch (JsonParseException e) {			
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info(hr);
		return result;

	}

	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.TEXT_PLAIN)
	@Path(ParametrosServicioRest.OBTENER_ACTIVIDADES_CON_FILTRO)
	public String obtenerTareas(String peticion) {
		LOGGER.info(new String (Thread.currentThread().getStackTrace()[1].getMethodName()));
		LOGGER.info(peticion);
		String result = null;
		try {
			List<Actividad> actividades= null;
			peticion= parseData.parsePeticion(peticion);
			List<FiltroActividades> filtros = parseData.StringToFiltroActividades(peticion);
			KieServerClientJBPM kieServiceClient = new KieServerClientJBPM();
			actividades = kieServiceClient.ObtenerTareasKieFiltro(filtros);
			result = MessageFormat.format(PLANTILLA_RESULTADO, "OK",parseData.ObjectToJson(actividades));
		} catch (JsonParseException e) {			
			ExcepcionSNC exc = ManejadorExcepciones.CP42001
					.getExcepcion(LOGGER, e,
							"Obtener actividades con filtro");
			LOGGER.error("Excepcion ----->"+MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage()+", "+exc.getMessage()));
			
		} catch (NullPointerException e) {
			
			ExcepcionSNC exc = ManejadorExcepciones.CP42001
					.getExcepcion(LOGGER, e,
							"Obtener actividades con filtro");
			LOGGER.error("Excepcion ----->"+MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage()+", "+exc.getMessage()));
		} catch (IndexOutOfBoundsException e) {
			
			ExcepcionSNC exc = ManejadorExcepciones.CP42007
					.getExcepcion(LOGGER, e,
							"Obtener actividades con filtro");
			LOGGER.error(MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage()+", "+exc.getMessage()));
		} catch (IllegalArgumentException e) {
			
			ExcepcionSNC exc = ManejadorExcepciones.CP42008
					.getExcepcion(LOGGER, e,
							"Obtener actividades con filtro");
			LOGGER.error("Excepcion ----->"+MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage()+", "+exc.getMessage()));
		} catch (KieServicesException e) {
			
			ExcepcionSNC exc = ManejadorExcepciones.CP44001
					.getExcepcion(LOGGER, e,
							"Obtener actividades con filtro");
			LOGGER.error(MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage()+", "+exc.getMessage()));
		} catch (KieRemoteHttpRequestException e) {
			
			ExcepcionSNC exc = ManejadorExcepciones.CP44002
					.getExcepcion(LOGGER, e,
							"Obtener actividades con filtro");
			LOGGER.error("Excepcion ----->"+MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage()+", "+exc.getMessage()));
		} catch (PersistenceException e) {
			
			ExcepcionSNC exc = ManejadorExcepciones.CP45001
					.getExcepcion(LOGGER, e,
							"Obtener actividades con filtro");
			LOGGER.error("Excepcion ----->"+MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage()+", "+exc.getMessage()));
		} 	
		LOGGER.info(hr);
		return result;
	}
	
	
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.TEXT_PLAIN)
	@Path(ParametrosServicioRest.OBTENER_CONTEO_ACTIVIDADES_USUARIO)
	public String obtenerConteoActividadesUsuario(String jsonUsuario) {
		LOGGER.info(new String (Thread.currentThread().getStackTrace()[1].getMethodName()));
		LOGGER.info(jsonUsuario);
		String result =null;
		try {
			jsonUsuario = parseData.parsePeticion(jsonUsuario);
			Usuario usuario = parseData.objectUser(jsonUsuario);
			parseData.loginKie(usuario.getLogin(), usuario.getPrimerRol());
			KieServerClientJBPM kieServiceClient = new KieServerClientJBPM();
			List<ConteoACtividad> listaActividades= null;
			listaActividades=kieServiceClient.obtenerConteoTareasKie(usuario);
			result =MessageFormat.format(PLANTILLA_RESULTADO, "OK",parseData.ObjectToJson(listaActividades));
		} catch (JsonParseException e) {			
			ExcepcionSNC exc = ManejadorExcepciones.CP42001
					.getExcepcion(LOGGER, e,
							"obtener conteo actividades usuario");
			LOGGER.error("Excepcion ----->"+MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage()+", "+exc.getMessage()));
			
		} catch (NullPointerException e) {
			
			ExcepcionSNC exc = ManejadorExcepciones.CP42001
					.getExcepcion(LOGGER, e,
							"obtener conteo actividades usuario");
			LOGGER.error("Excepcion ----->"+MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage()+", "+exc.getMessage()));
		} catch (IndexOutOfBoundsException e) {
			
			ExcepcionSNC exc = ManejadorExcepciones.CP42007
					.getExcepcion(LOGGER, e,
							"obtener conteo actividades usuario");
			LOGGER.error(MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage()+", "+exc.getMessage()));
		} catch (IllegalArgumentException e) {
			
			ExcepcionSNC exc = ManejadorExcepciones.CP42008
					.getExcepcion(LOGGER, e,
							"obtener conteo actividades usuarios");
			LOGGER.error("Excepcion ----->"+MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage()+", "+exc.getMessage()));
		} catch (KieServicesException e) {
			
			ExcepcionSNC exc = ManejadorExcepciones.CP44001
					.getExcepcion(LOGGER, e,
							"obtener conteo actividades usuario");
			LOGGER.error(MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage()+", "+exc.getMessage()));
		} catch (KieRemoteHttpRequestException e) {
			
			ExcepcionSNC exc = ManejadorExcepciones.CP44002
					.getExcepcion(LOGGER, e,
							"obtener conteo actividades usuario");
			LOGGER.error("Excepcion ----->"+MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage()+", "+exc.getMessage()));
		} catch (PersistenceException e) {
			
			ExcepcionSNC exc = ManejadorExcepciones.CP45001
					.getExcepcion(LOGGER, e,
							"obtener conteo actividades usuario");
			LOGGER.error("Excepcion ----->"+MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage()+", "+exc.getMessage()));
		} 	
		LOGGER.info(hr);
		return result;
	}

	
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.TEXT_PLAIN)
	@Path(ParametrosServicioRest.OBTENER_TAREAS_POR_USUARIO)
	public String obtenerTareasPorUsuario(String peticion_user) {
		LOGGER.info(new String (Thread.currentThread().getStackTrace()[1].getMethodName()));
		LOGGER.info(peticion_user);
		String result = null;
		try {
			peticion_user=parseData.parsePeticion(peticion_user);
			PeticionActividad peticionActividad = parseData.objetcPeticionActividad(peticion_user);
			parseData.loginKie(peticionActividad.getUsuario().getLogin(), peticionActividad.getUsuario().getPrimerRol());
			KieServerClientJBPM kieServiceClient = new KieServerClientJBPM();
			List<Actividad> actividades= null;		
			actividades = kieServiceClient.obtenerTareasKie(peticionActividad);
			Gson gson = new GsonBuilder().serializeNulls().create();
			result = MessageFormat.format(PLANTILLA_RESULTADO, "OK", gson.toJson(actividades));
		} catch (ExcepcionSNC e) {
			ExcepcionSNC exc = ManejadorExcepciones.CP41001
					.getExcepcion(LOGGER, e,
							"Obtener tareas por usuario");
			LOGGER.error("Excepcion ----->"+MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage()+", "+exc.getMessage()));
		} catch (JsonParseException e) {			
			ExcepcionSNC exc = ManejadorExcepciones.CP41001
					.getExcepcion(LOGGER, e,
							"Obtener tareas por usuario");
			LOGGER.error("Excepcion ----->"+MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage()+", "+exc.getMessage()));
			
		} catch (NullPointerException e) {
			
			ExcepcionSNC exc = ManejadorExcepciones.CP41001
					.getExcepcion(LOGGER, e,
							"Obtener tareas por usuario");
			LOGGER.error("Excepcion ----->"+MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage()+", "+exc.getMessage()));
		} catch (IndexOutOfBoundsException e) {
			
			ExcepcionSNC exc = ManejadorExcepciones.CP42007
					.getExcepcion(LOGGER, e,
							"Obtener tareas por usuario");
			LOGGER.error(MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage()+", "+exc.getMessage()));
		} catch (IllegalArgumentException e) {
			
			ExcepcionSNC exc = ManejadorExcepciones.CP42008
					.getExcepcion(LOGGER, e,
							"Obtener tareas por usuario");
			LOGGER.error("Excepcion ----->"+MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage()+", "+exc.getMessage()));
		} catch (KieServicesException e) {
			
			ExcepcionSNC exc = ManejadorExcepciones.CP44001
					.getExcepcion(LOGGER, e,
							"Obtener tareas por usuario");
			LOGGER.error(MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage()+", "+exc.getMessage()));
		} catch (KieRemoteHttpRequestException e) {
			
			ExcepcionSNC exc = ManejadorExcepciones.CP44002
					.getExcepcion(LOGGER, e,
							"Obtener tareas por usuario");
			LOGGER.error("Excepcion ----->"+MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage()+", "+exc.getMessage()));
		} catch (PersistenceException e) {
			
			ExcepcionSNC exc = ManejadorExcepciones.CP45001
					.getExcepcion(LOGGER, e,
							"Obtener tareas por usuario");
			LOGGER.error("Excepcion ----->"+MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage()+", "+exc.getMessage()));
		}	
		LOGGER.info(hr);
		return result;
	}
	
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.TEXT_PLAIN)
	@Path(ParametrosServicioRest.OBTENER_ACTIVIDADES_ID_OBJ_NEGOCIO)
	public String obtenerActividadesPorIdObjetoNegocio(String peticion) {
		LOGGER.info(new String (Thread.currentThread().getStackTrace()[1].getMethodName()));	
		LOGGER.info(peticion);
		try {
			
		} catch (JsonParseException e) {			
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info(hr);
		return "";
	}

	

	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path(ParametrosServicioRest.OBTENER_CONTEO_ACTIVIDADES_Y_FILTRO)
	public String obtenerConteoActividadesConFiltro(
			@FormParam(ParametrosServicioRest.USUARIO_DTO) String usuarioAsJSon,
			@FormParam(ParametrosServicioRest.FILTRO) String filtroAsJson) {
		LOGGER.info(new String (Thread.currentThread().getStackTrace()[1].getMethodName()));
		String result = null;
		try {
			
			/*WRITE SERVICE*/
			
		} catch (JsonParseException e) {			
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info(hr);
		return "";

	}

	

	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.TEXT_PLAIN)
	@Path(ParametrosServicioRest.OBTENER_ESTADO_ACTIVIDAD)
	public String obtenerEstadoActividad(String idActividad) {
		LOGGER.info(new String (Thread.currentThread().getStackTrace()[1].getMethodName()));
		String result = null;
		LOGGER.info(idActividad);
		try {
			
			/*WRITE SERVICE*/
			
		} catch (JsonParseException e) {			
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info(hr);
		return "";
	}

	/*@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.TEXT_PLAIN)
	@Path(ParametrosServicioRest.OBTENER_OBJ_NEGOCIO_CONSERVACION)
	public String obtenerObjetoNegocioConservacion(String peticion)
			throws ExcepcionSNC {
		LOGGER.info(peticion);		
		String result = null;
		return "";
	}*/

	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.TEXT_PLAIN)
	@Path(ParametrosServicioRest.OBTENER_OBJ_NEGOCIO_CONSERVACION)
	public String obtenerObjetoNegocioActualizacion(String peticion) {
		LOGGER.info(new String (Thread.currentThread().getStackTrace()[1].getMethodName()));
		LOGGER.info(peticion);
		String result = null;
		try {
			
			/*WRITE SERVICE*/
			
		} catch (JsonParseException e) {			
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		LOGGER.info(hr);
		return "";
	}
	
	
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.TEXT_PLAIN)
	@Path(ParametrosServicioRest.OBTENER_OBJ_NEGOCIO_OFERTAS_INMOBILIARIAS)
	public String obtenerObjetoNegocioOfertasInmobiliarias(String peticion)
			throws ExcepcionSNC {
		LOGGER.info(new String (Thread.currentThread().getStackTrace()[1].getMethodName()));
		LOGGER.info(peticion);
		String result = null;
		try {
			
			/*WRITE SERVICE*/
			
		} catch (JsonParseException e) {			
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info(hr);
		return "";
	}

	
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.TEXT_PLAIN)
	@Path(ParametrosServicioRest.OBTENER_OBJ_NEGOCIO_CONTROL_CALIDAD)
	public String obtenerObjetoNegocioControlCalidad(String peticion)
			throws ExcepcionSNC {
		LOGGER.info(new String (Thread.currentThread().getStackTrace()[1].getMethodName()));
		LOGGER.info(peticion);
		String result = null;
		try {
			
			/*WRITE SERVICE*/
			
		} catch (JsonParseException e) {			
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info(hr);
		return "";
	}
	
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.TEXT_PLAIN)
	@Path(ParametrosServicioRest.OBTENER_USUARIOS_ACTIVIDAD)
	public String obtenerUsuariosActividad(String idActividad) {
		LOGGER.info(new String (Thread.currentThread().getStackTrace()[1].getMethodName()));
		LOGGER.info(idActividad);
		String result = null;
		try {
			
			/*WRITE SERVICE*/
			
		} catch (JsonParseException e) {			
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info(hr);
		return "";
	}
	
	

}

package co.gov.igac.client;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.hibernate.HibernateException;
import org.kie.remote.common.rest.KieRemoteHttpRequestException;
import org.kie.server.client.KieServicesException;

import com.google.gson.JsonParseException;

import ch.qos.logback.classic.Logger;
import co.gov.igac.dao.ManagerDAO;
import co.gov.igac.entity.Predio;
import co.gov.igac.entity.Tramite;
import co.gov.igac.kier.execution.KieServerClientJBPM;
import co.gov.igac.logger.impl.ISNCLogger;
import co.gov.igac.logger.impl.SNCLoggerFactory;
import co.gov.igac.nucleo.predial.SolicitudActualizacion;
import co.gov.igac.nucleo.predial.SolicitudCatastral;
import co.gov.igac.nucleo.predial.SolicitudConservacion;
import co.gov.igac.parametros.ParametrosServicioRest;
import co.gov.igac.sigc.excepcion.ExcepcionSNC;
import co.gov.igac.sigc.excepcion.ManejadorExcepciones;
import co.gov.igac.utilerias.Calendario;
import co.gov.igac.utilerias.FiltroActividades;
import co.gov.igac.utilerias.ParseData;

@Path(ParametrosServicioRest.PATH_GESTOR_PROCESOS)
public class GestorProcesosRest {
	private static final ISNCLogger LOGGER = SNCLoggerFactory.getLogger(GestorProcesosRest.class);

	private static ParseData parseData = null;

	private static final String PLANTILLA_RESULTADO = "'{'\"estado\":\"{0}\",\"resultado\":\"{1}\"'}'";

	private static final String hr = "\n===================================================================";

	public GestorProcesosRest() {
		super();
		if (parseData == null) {
			parseData = new ParseData();
		}
	}

	// EXCEPTION
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.TEXT_PLAIN)
	@Path(ParametrosServicioRest.ADAPTADOR_SERVICIOS)
	public String adaptadorDeServicios(String tramitesDatas) {
		LOGGER.info(new String(Thread.currentThread().getStackTrace()[1].getMethodName()));
		LOGGER.info(tramitesDatas);
		String result = null;
		try {
			tramitesDatas = parseData.parsePeticion(tramitesDatas);
			List<FiltroActividades> tramites = parseData.StringToFiltroActividades(tramitesDatas);
			KieServerClientJBPM kieServerClient = new KieServerClientJBPM();
			result = kieServerClient.adaptadorTramites(tramites).toString();
		} catch (JsonParseException e) {

			ExcepcionSNC exc = ManejadorExcepciones.CP42001.getExcepcion(LOGGER, e, "adaptador de servicios");
			LOGGER.error(MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage()));

		} catch (NullPointerException e) {

			ExcepcionSNC exc = ManejadorExcepciones.CP42006.getExcepcion(LOGGER, e, "adaptador de servicios");
			LOGGER.error(MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage()));

		} catch (IndexOutOfBoundsException e) {

			ExcepcionSNC exc = ManejadorExcepciones.CP42007.getExcepcion(LOGGER, e, "adaptador de servicios");
			LOGGER.error(MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage()));
		} catch (IllegalArgumentException e) {

			ExcepcionSNC exc = ManejadorExcepciones.CP42008.getExcepcion(LOGGER, e, "adaptador de servicios");
			LOGGER.error(MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage()));
		} catch (KieServicesException e) {

			ExcepcionSNC exc = ManejadorExcepciones.CP44001.getExcepcion(LOGGER, e, "adaptador de servicios");
			LOGGER.error(MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage()));
		}
		LOGGER.info(hr);
		return result;
	}

	@POST
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
	@Path(ParametrosServicioRest.ADAPTADOR_TRAMITES)
	public String adaptadorTramites(String tramitesDatas) {
		LOGGER.info(new String(Thread.currentThread().getStackTrace()[1].getMethodName()));
		LOGGER.info(tramitesDatas);
		String result = null;
		try {
			tramitesDatas = parseData.parsePeticion(tramitesDatas);
			List<FiltroActividades> tramites = parseData.StringToFiltroActividades(tramitesDatas);
			ManagerDAO managerdao = new ManagerDAO();
			HashMap<String, Object> salida = new HashMap<>();
			String resultado = managerdao.adaptadorTramites(new Long(tramites.get(0).getValor().toString()));
			if (resultado == "error") {
				salida.put("resultado", resultado);

			} else {
				salida.put("resultado", "OK");
				salida.put("tipoTramite", resultado);
				Long idpredio = (long) 0;
				try {
					Tramite tramite = managerdao
							.tramitePorId(new Long(new Long(tramites.get(0).getValor().toString())));
					idpredio = tramite.getPredioId().longValue();
					salida.put("numeroRadicacion", tramite.getNumeroRadicacion());
				} catch (HibernateException e) {
					salida.put("resultado", "error");
					salida.put("Observaciones", "Imposible obtener el tipo de trámite de id: "
							+ new Long(tramites.get(0).getValor().toString()));
					// e.printStackTrace();
				}
				if (idpredio != 0) {
					try {
						Predio predio = managerdao.predioPorId(idpredio);
						salida.put("numeroPredial", predio.getNumeroPredial());
					} catch (Exception e) {
						salida.put("resultado", "error");
						salida.put("Observaciones", "Imposible obtener el numero predial de trámite de id: "
								+ new Long(tramites.get(0).getValor().toString()));
					}
				}
			}
			result = salida.toString();
		} catch (JsonParseException e) {

			ExcepcionSNC exc = ManejadorExcepciones.CP42001.getExcepcion(LOGGER, e, "adaptador de tramites");
			LOGGER.error(MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(),
					e.getMessage() + " " + exc.getMensajeUsuario()));

		} catch (NullPointerException e) {

			ExcepcionSNC exc = ManejadorExcepciones.CP42006.getExcepcion(LOGGER, e, "adaptador de tramites");
			LOGGER.error(MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(),
					e.getMessage() + " " + exc.getMensajeUsuario()));

		} catch (IndexOutOfBoundsException e) {

			ExcepcionSNC exc = ManejadorExcepciones.CP42007.getExcepcion(LOGGER, e, "adaptador de tramites");
			LOGGER.error(MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(),
					e.getMessage() + " " + exc.getMensajeUsuario()));
		} catch (IllegalArgumentException e) {

			ExcepcionSNC exc = ManejadorExcepciones.CP42008.getExcepcion(LOGGER, e, "adaptador de tramites");
			LOGGER.error(MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(),
					e.getMessage() + " " + exc.getMensajeUsuario()));
		} catch (KieServicesException e) {

			ExcepcionSNC exc = ManejadorExcepciones.CP44001.getExcepcion(LOGGER, e, "adaptador de tramites");
			LOGGER.error(MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(),
					e.getMessage() + " " + exc.getMensajeUsuario()));
		}
		LOGGER.info(hr);
		return result;
	}

	@POST
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
	@Path(ParametrosServicioRest.ASIGNA_USUARIO_LDAP)
	public String LdapUsuarios(String request) {
		LOGGER.info(new String(Thread.currentThread().getStackTrace()[1].getMethodName()));
		LOGGER.info(request);
		String result = null;
		try {
			request = parseData.parsePeticion(request);
			List<FiltroActividades> actividad = parseData.StringToFiltroActividades(request);
			KieServerClientJBPM kieServerClient = new KieServerClientJBPM();
			result = kieServerClient.asignaUsuarioLdap(actividad);
		} catch (JsonParseException e) {

			ExcepcionSNC exc = ManejadorExcepciones.CP42001.getExcepcion(LOGGER, e, "Asignar Usuario LDAP");
			LOGGER.error(MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(),
					e.getMessage() + " " + exc.getMensajeUsuario()));

		} catch (NullPointerException e) {

			ExcepcionSNC exc = ManejadorExcepciones.CP42006.getExcepcion(LOGGER, e, "Asignar Usuario LDAP");
			LOGGER.error(MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(),
					e.getMessage() + " " + exc.getMensajeUsuario()));

		} catch (IndexOutOfBoundsException e) {

			ExcepcionSNC exc = ManejadorExcepciones.CP42007.getExcepcion(LOGGER, e, "Asignar Usuario LDAP");
			LOGGER.error(MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(),
					e.getMessage() + " " + exc.getMensajeUsuario()));
		} catch (IllegalArgumentException e) {

			ExcepcionSNC exc = ManejadorExcepciones.CP42008.getExcepcion(LOGGER, e, "Asignar Usuario LDAP");
			LOGGER.error(MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(),
					e.getMessage() + " " + exc.getMensajeUsuario()));
		} catch (KieServicesException e) {

			ExcepcionSNC exc = ManejadorExcepciones.CP44001.getExcepcion(LOGGER, e, "Asignar Usuario LDAP");
			LOGGER.error(MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(),
					e.getMessage() + " " + exc.getMensajeUsuario()));
		}
		LOGGER.info(hr);
		return result;

	}

	@POST
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
	@Path(ParametrosServicioRest.ASIGNA_USUARIO_TIEMPOS_MUERTOS)
	public String tiemposMuertos(String request) {
		LOGGER.info(new String(Thread.currentThread().getStackTrace()[1].getMethodName()));
		LOGGER.info(request);
		String result = "";
		try {
			request = parseData.parsePeticion(request);
			List<FiltroActividades> actividad = parseData.StringToFiltroActividades(request);
			KieServerClientJBPM kieServerClient = new KieServerClientJBPM();
			result = kieServerClient.asignaTiemposMuertos(actividad);
		} catch (JsonParseException e) {

			ExcepcionSNC exc = ManejadorExcepciones.CP42001.getExcepcion(LOGGER, e, "ASIGNA_USUARIO_TIEMPOS_MUERTOS");
			LOGGER.error(MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(),
					e.getMessage() + " " + exc.getMensajeUsuario()));

		} catch (NullPointerException e) {

			ExcepcionSNC exc = ManejadorExcepciones.CP42006.getExcepcion(LOGGER, e, "ASIGNA_USUARIO_TIEMPOS_MUERTOS");
			LOGGER.error(MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(),
					e.getMessage() + " " + exc.getMensajeUsuario()));

		} catch (IndexOutOfBoundsException e) {

			ExcepcionSNC exc = ManejadorExcepciones.CP42007.getExcepcion(LOGGER, e, "ASIGNA_USUARIO_TIEMPOS_MUERTOS");
			LOGGER.error(MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(),
					e.getMessage() + " " + exc.getMensajeUsuario()));
		} catch (IllegalArgumentException e) {

			ExcepcionSNC exc = ManejadorExcepciones.CP42008.getExcepcion(LOGGER, e, "ASIGNA_USUARIO_TIEMPOS_MUERTOS");
			LOGGER.error(MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(),
					e.getMessage() + " " + exc.getMensajeUsuario()));
		}
		LOGGER.info(hr);
		return result;

	}

	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.TEXT_PLAIN)
	@Path(ParametrosServicioRest.OBTENER_CALENDARIO)
	public String obtenerCalendarioHabil(String cuerpoFiltroActividad) {
		LOGGER.info(new String(Thread.currentThread().getStackTrace()[1].getMethodName()));
		LOGGER.info(cuerpoFiltroActividad);
		String fechaFinal = null;
		try {
			cuerpoFiltroActividad = parseData.parsePeticion(cuerpoFiltroActividad);
			List<FiltroActividades> actividad = parseData.StringToFiltroActividades(cuerpoFiltroActividad);
			fechaFinal = Calendario.getTimeActivity(actividad.get(0).getValor().toString());
		} catch (JsonParseException e) {
			ExcepcionSNC exc = ManejadorExcepciones.CP42001.getExcepcion(LOGGER, e, "Obtención de Calendario");
			fechaFinal = exc.getCodigoExcepcion() + " - " + e.getMessage() + ", " + exc.getMessage();

		} catch (NullPointerException e) {

			ExcepcionSNC exc = ManejadorExcepciones.CP42001.getExcepcion(LOGGER, e, "Obtención de Calendario");

			fechaFinal = exc.getCodigoExcepcion() + " - " + e.getMessage() + ", " + exc.getMessage();

		} catch (IndexOutOfBoundsException e) {
			e.getLocalizedMessage();
			ExcepcionSNC exc = ManejadorExcepciones.CP42007.getExcepcion(LOGGER, e, "Obtención de Calendario");
			fechaFinal = exc.getCodigoExcepcion() + " - " + e.getMessage() + ", " + exc.getMessage();
		} catch (IllegalArgumentException e) {

			ExcepcionSNC exc = ManejadorExcepciones.CP42008.getExcepcion(LOGGER, e, "Obtención de Calendario");
			fechaFinal = exc.getCodigoExcepcion() + " - " + e.getMessage() + ", " + exc.getMessage();
		} catch (KieServicesException e) {
			ExcepcionSNC exc = ManejadorExcepciones.CP44001.getExcepcion(LOGGER, e, "Obtención de Calendario");
			fechaFinal = exc.getCodigoExcepcion() + " - " + e.getMessage() + ", " + exc.getMessage();
		} catch (PersistenceException e) {
			ExcepcionSNC exc = ManejadorExcepciones.CP45001.getExcepcion(LOGGER, e, "Obtención de Calendario");
			fechaFinal = exc.getCodigoExcepcion() + " - " + e.getMessage() + ", " + exc.getMessage();
		} catch (KieRemoteHttpRequestException e) {
			ExcepcionSNC exc = ManejadorExcepciones.CP44002.getExcepcion(LOGGER, e, "Obtención de Calendario");
			fechaFinal = exc.getCodigoExcepcion() + " - " + e.getMessage() + ", " + exc.getMessage();
		}
		LOGGER.info(hr);
		return fechaFinal;
	}

	@POST
	@Consumes(MediaType.TEXT_PLAIN)
	@Path(ParametrosServicioRest.CREAR_PROCESOS_CONSERVACION)
	public void crearProcesosConservacion(String jsonSolicitudesCatastrales) {
		LOGGER.info(new String(Thread.currentThread().getStackTrace()[1].getMethodName()));
		LOGGER.info(jsonSolicitudesCatastrales);
		LOGGER.info(hr);
	}

	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.TEXT_PLAIN)
	@Path(ParametrosServicioRest.CREAR_SUSPENDER_PROCESO_CONSERVACION)
	public String crearYSuspenderProcesoConservacion(String jsonSolicitudCatastral) {
		LOGGER.info(new String(Thread.currentThread().getStackTrace()[1].getMethodName()));
		LOGGER.info(jsonSolicitudCatastral);
		String result = null;
		try {

		} catch (JsonParseException e) {

			ExcepcionSNC exc = ManejadorExcepciones.CP42001.getExcepcion(LOGGER, e,
					"la suspencion proceso conservación");
			result = MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(),
					e.getMessage() + " " + exc.getMensajeUsuario());

		} catch (NullPointerException e) {

			ExcepcionSNC exc = ManejadorExcepciones.CP42006.getExcepcion(LOGGER, e,
					"la suspencion proceso conservación");
			result = MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(),
					e.getMessage() + " " + exc.getMensajeUsuario());

		} catch (IndexOutOfBoundsException e) {

			ExcepcionSNC exc = ManejadorExcepciones.CP42007.getExcepcion(LOGGER, e,
					"la suspencion proceso conservación");
			result = MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(),
					e.getMessage() + " " + exc.getMensajeUsuario());
		} catch (IllegalArgumentException e) {

			ExcepcionSNC exc = ManejadorExcepciones.CP42008.getExcepcion(LOGGER, e,
					"la suspencion proceso conservación");
			result = MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(),
					e.getMessage() + " " + exc.getMensajeUsuario());

		}
		LOGGER.info(hr);
		return jsonSolicitudCatastral.toString();
	}

	@POST
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
	@Path(ParametrosServicioRest.CREAR_PROCESOS_ACTUALIZACION_EXPRESS)
	public String crearProcesosActualizacionExpress(String solicitudActualizacion) {
		LOGGER.info(new String(Thread.currentThread().getStackTrace()[1].getMethodName()));
		LOGGER.info(solicitudActualizacion);
		String result = null;
		try {

		} catch (JsonParseException e) {

			ExcepcionSNC exc = ManejadorExcepciones.CP42001.getExcepcion(LOGGER, e,
					"la creación de proceso de actualización express");
			result = MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(),
					e.getMessage() + " " + exc.getMensajeUsuario());

		} catch (NullPointerException e) {

			ExcepcionSNC exc = ManejadorExcepciones.CP42006.getExcepcion(LOGGER, e,
					"la creación de proceso de actualización express");
			result = MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(),
					e.getMessage() + " " + exc.getMensajeUsuario());

		} catch (IndexOutOfBoundsException e) {

			ExcepcionSNC exc = ManejadorExcepciones.CP42007.getExcepcion(LOGGER, e,
					"la creación de proceso de actualización express");
			result = MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(),
					e.getMessage() + " " + exc.getMensajeUsuario());
		} catch (IllegalArgumentException e) {

			ExcepcionSNC exc = ManejadorExcepciones.CP42008.getExcepcion(LOGGER, e,
					"la creación de proceso de actualización express");
			result = MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(),
					e.getMessage() + " " + exc.getMensajeUsuario());
		}
		LOGGER.info(hr);
		return result;
	}

	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.TEXT_PLAIN)
	@Path(ParametrosServicioRest.REANUDAR_PROCESO)
	public String reanudarProceso(String idProceso) {
		LOGGER.info(new String(Thread.currentThread().getStackTrace()[1].getMethodName()));
		LOGGER.info(idProceso);
		String result = null;
		try {

		} catch (JsonParseException e) {

			ExcepcionSNC exc = ManejadorExcepciones.CP42001.getExcepcion(LOGGER, e, "reanudando proceso");
			result = MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(),
					e.getMessage() + " " + exc.getMensajeUsuario());

		} catch (NullPointerException e) {

			ExcepcionSNC exc = ManejadorExcepciones.CP42006.getExcepcion(LOGGER, e, "reanudando proceso");
			result = MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(),
					e.getMessage() + " " + exc.getMensajeUsuario());

		} catch (IndexOutOfBoundsException e) {

			ExcepcionSNC exc = ManejadorExcepciones.CP42007.getExcepcion(LOGGER, e, "reanudando proceso");
			result = MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(),
					e.getMessage() + " " + exc.getMensajeUsuario());
		} catch (IllegalArgumentException e) {

			ExcepcionSNC exc = ManejadorExcepciones.CP42008.getExcepcion(LOGGER, e, "reanudando proceso");
			result = MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(),
					e.getMessage() + " " + exc.getMensajeUsuario());

		}
		LOGGER.info(hr);
		return "";
	}

	@POST
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
	@Path(ParametrosServicioRest.REANUDAR_PROCESOS_ACTUALIZACION_EXPRESS)
	public String reanudarProcesosActualizacionExpress(String solicitudActualizacion) {
		LOGGER.info(new String(Thread.currentThread().getStackTrace()[1].getMethodName()));
		LOGGER.info(solicitudActualizacion);
		String result = null;
		try {

		} catch (JsonParseException e) {

			ExcepcionSNC exc = ManejadorExcepciones.CP42001.getExcepcion(LOGGER, e,
					"reanudar procesos actualizacion express");
			result = MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(),
					e.getMessage() + " " + exc.getMensajeUsuario());

		} catch (NullPointerException e) {

			ExcepcionSNC exc = ManejadorExcepciones.CP42006.getExcepcion(LOGGER, e,
					"reanudar procesos actualizacion express");
			result = MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(),
					e.getMessage() + " " + exc.getMensajeUsuario());

		} catch (IndexOutOfBoundsException e) {

			ExcepcionSNC exc = ManejadorExcepciones.CP42007.getExcepcion(LOGGER, e,
					"reanudar procesos actualizacion express");
			result = MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(),
					e.getMessage() + " " + exc.getMensajeUsuario());
		} catch (IllegalArgumentException e) {

			ExcepcionSNC exc = ManejadorExcepciones.CP42008.getExcepcion(LOGGER, e,
					"reanudar procesos actualizacion express");
			result = MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(),
					e.getMessage() + " " + exc.getMensajeUsuario());

		}
		LOGGER.info(hr);
		return "";
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path(ParametrosServicioRest.ESTADO_SERVICIOS)
	public String verificarServicios() throws ExcepcionSNC {
		LOGGER.info(new String(Thread.currentThread().getStackTrace()[1].getMethodName()));
		String result = null;
		try {

		} catch (JsonParseException e) {

			ExcepcionSNC exc = ManejadorExcepciones.CP42001.getExcepcion(LOGGER, e, "estado de servicios");
			result = MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage());

		} catch (NullPointerException e) {

			ExcepcionSNC exc = ManejadorExcepciones.CP42006.getExcepcion(LOGGER, e, "estado de servicios");
			result = MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage());

		} catch (IndexOutOfBoundsException e) {

			ExcepcionSNC exc = ManejadorExcepciones.CP42007.getExcepcion(LOGGER, e, "estado de servicios");
			result = MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage());
		} catch (IllegalArgumentException e) {

			ExcepcionSNC exc = ManejadorExcepciones.CP42008.getExcepcion(LOGGER, e, "estado de servicios");
			result = MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage());
		} catch (KieServicesException e) {

			ExcepcionSNC exc = ManejadorExcepciones.CP44001.getExcepcion(LOGGER, e, "estado de servicios");
			result = MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage());
		}
		LOGGER.info(hr);
		return "";
	}

	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.TEXT_PLAIN)
	@Path(ParametrosServicioRest.CREAR_PROCESO_ACTUALIZACION)
	public String crearProcesoActualizacion(String jsonactualizacionCatastral) {
		LOGGER.info(new String(Thread.currentThread().getStackTrace()[1].getMethodName()));
		LOGGER.info(jsonactualizacionCatastral);
		String result = null;
		try {
			jsonactualizacionCatastral = parseData.parsePeticion(jsonactualizacionCatastral);
			SolicitudActualizacion solicitudActaulizacion = parseData
					.objetcSolicitudActualizacion(jsonactualizacionCatastral); // Create object Catastral Json to Gson
			KieServerClientJBPM kieServerClient = new KieServerClientJBPM();
			String identificador = kieServerClient
					.validarIdentificadorProceso(solicitudActaulizacion.getIdentificador().toString());
			if (identificador.equals("null")) {
				HashMap<String, Object> params = new HashMap<>();
				params.put("identificador", solicitudActaulizacion.getIdentificador());
				params.put("numeroSolicitud", solicitudActaulizacion.getNumeroSolicitud());
				params.put("territorial", solicitudActaulizacion.getActividadesUsuarios().get(0).getUsuarios().get(0)
						.getDescripcionTerritorial());
				params.put("tipoSolicitud", solicitudActaulizacion.getTipoSolicitud());
				params.put("actividad", solicitudActaulizacion.getActividadesUsuarios().get(0).getActividad());
				params.put("fechaSolicitud", solicitudActaulizacion.getFechaSolicitud());
				parseData.loginKie(
						solicitudActaulizacion.getActividadesUsuarios().get(0).getUsuarios().get(0).getLogin(),
						solicitudActaulizacion.getActividadesUsuarios().get(0).getUsuarios().get(0).getPrimerRol());
				String idproceso = kieServerClient.kieStartProcess(params, solicitudActaulizacion).toString();
				String idTarea = kieServerClient.actaulizaFechaProductosP(idproceso);
				if (solicitudActaulizacion.getActividadesUsuarios().get(0).getUsuarios().size() == 1) {
					kieServerClient.reservaPrimerUsuarioCrear(solicitudActaulizacion.getActividadesUsuarios().get(0)
							.getUsuarios().get(0).getLogin().toString(), idTarea);
				}
				result = MessageFormat.format(PLANTILLA_RESULTADO, "OK", idproceso);
			} else {
				result = MessageFormat.format(PLANTILLA_RESULTADO, "OK", identificador);
			}
		} catch (JsonParseException e) {

			ExcepcionSNC exc = ManejadorExcepciones.CP42001.getExcepcion(LOGGER, e,
					"la creación de proceso actualización");
			result = MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage());

		} catch (NullPointerException e) {

			ExcepcionSNC exc = ManejadorExcepciones.CP42006.getExcepcion(LOGGER, e,
					"la creación de proceso actualización");
			result = MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage());

		} catch (IndexOutOfBoundsException e) {

			ExcepcionSNC exc = ManejadorExcepciones.CP42007.getExcepcion(LOGGER, e,
					"la creación de proceso actualización");
			result = MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage());
		} catch (IllegalArgumentException e) {

			ExcepcionSNC exc = ManejadorExcepciones.CP42008.getExcepcion(LOGGER, e,
					"la creación de proceso actualización");
			result = MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage());
		} catch (KieServicesException e) {
			ExcepcionSNC exc = ManejadorExcepciones.CP44001.getExcepcion(LOGGER, e,
					"la creación de proceso actualización");
			result = MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage());
		}
		LOGGER.info(hr);
		return result;
	}

	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.TEXT_PLAIN)
	@Path(ParametrosServicioRest.CREAR_PROCESO_OSMI)
	public String crearProcesoOsmi(String jsonOfertasInmobiliarias) {
		LOGGER.info(new String(Thread.currentThread().getStackTrace()[1].getMethodName()));
		LOGGER.info(jsonOfertasInmobiliarias);
		String result = null;
		try {

		} catch (JsonParseException e) {

			ExcepcionSNC exc = ManejadorExcepciones.CP42001.getExcepcion(LOGGER, e, "la creación de proceso OSMI");
			result = MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage());

		} catch (NullPointerException e) {

			ExcepcionSNC exc = ManejadorExcepciones.CP42006.getExcepcion(LOGGER, e, "la creación de proceso OSMI");
			result = MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage());

		} catch (IndexOutOfBoundsException e) {

			ExcepcionSNC exc = ManejadorExcepciones.CP42007.getExcepcion(LOGGER, e, "la creación de proceso OSMI");
			result = MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage());
		} catch (IllegalArgumentException e) {

			ExcepcionSNC exc = ManejadorExcepciones.CP42008.getExcepcion(LOGGER, e, "la creación de proceso OSMI");
			result = MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage());
		} catch (KieServicesException e) {

			ExcepcionSNC exc = ManejadorExcepciones.CP44001.getExcepcion(LOGGER, e, "la creación de proceso OSMI");
			result = MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage());
		}
		LOGGER.info(hr);
		return "";
	}

	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.TEXT_PLAIN)
	@Path(ParametrosServicioRest.CREAR_PROCESO_OSMI_CONTROL_CALIDAD)
	public String crearProcesoOsmiControlCalidad(String jsonControlCalidad) {
		LOGGER.info(new String(Thread.currentThread().getStackTrace()[1].getMethodName()));
		LOGGER.info(jsonControlCalidad);
		String result = null;
		try {

		} catch (JsonParseException e) {

			ExcepcionSNC exc = ManejadorExcepciones.CP42001.getExcepcion(LOGGER, e,
					"la creación de proceso control de calidad");
			result = MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage());

		} catch (NullPointerException e) {

			ExcepcionSNC exc = ManejadorExcepciones.CP42006.getExcepcion(LOGGER, e,
					"la creación de proceso control de calidad");
			result = MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage());

		} catch (IndexOutOfBoundsException e) {

			ExcepcionSNC exc = ManejadorExcepciones.CP42007.getExcepcion(LOGGER, e,
					"la creación de proceso control de calidad");
			result = MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage());
		} catch (IllegalArgumentException e) {

			ExcepcionSNC exc = ManejadorExcepciones.CP42008.getExcepcion(LOGGER, e,
					"la creación de proceso control de calidad");
			result = MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage());
		} catch (KieServicesException e) {

			ExcepcionSNC exc = ManejadorExcepciones.CP44001.getExcepcion(LOGGER, e,
					"la creación de proceso control de calidad");
			result = MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage());
		}
		LOGGER.info(hr);
		return "";
	}

	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.TEXT_PLAIN)
	@Path(ParametrosServicioRest.CREAR_PROCESO_CONSERVACION)
	public String crearProcesoConservacion(String jsonSolicitudConservacion) {
		LOGGER.info(new String(Thread.currentThread().getStackTrace()[1].getMethodName()));
		LOGGER.info(jsonSolicitudConservacion);
		String result = null;
		try {
			jsonSolicitudConservacion = parseData.parsePeticion(jsonSolicitudConservacion);
			SolicitudConservacion solicitudConservacion = parseData.objectToConservacion(jsonSolicitudConservacion);
			KieServerClientJBPM kieServerClient = new KieServerClientJBPM();
			String identificador = kieServerClient
					.validarIdentificadorProceso(solicitudConservacion.getIdentificador().toString());
			if (identificador.equals("null")) {
				HashMap<String, Object> params = new HashMap<>();
				params.put("identificador", solicitudConservacion.getIdentificador());
				params.put("numeroPredial", solicitudConservacion.getNumeroPredial());
				params.put("numeroRadicacion", solicitudConservacion.getNumeroRadicacion());
				params.put("numeroSolicitud", solicitudConservacion.getNumeroSolicitud());
				params.put("observaciones", solicitudConservacion.getObservaciones());
				params.put("territorial", solicitudConservacion.getActividadesUsuarios().get(0).getUsuarios().get(0)
						.getDescripcionTerritorial());
				params.put("tipoTramite", solicitudConservacion.getTipoTramite());
				params.put("actividad", solicitudConservacion.getActividadesUsuarios().get(0).getActividad());
				parseData.loginKie(
						solicitudConservacion.getActividadesUsuarios().get(0).getUsuarios().get(0).getLogin(),
						solicitudConservacion.getActividadesUsuarios().get(0).getUsuarios().get(0).getPrimerRol());
				String idproceso = kieServerClient.kieStartProcess(params, solicitudConservacion).toString();
				String idTarea = kieServerClient.actualizaFechaConservacion(solicitudConservacion);
				
				 if
				 (solicitudConservacion.getActividadesUsuarios().get(0).getUsuarios().size()
				 == 1) {
				 kieServerClient.reservaPrimerUsuarioCrear(solicitudConservacion.getActividadesUsuarios().get(0)
				 .getUsuarios().get(0).getLogin().toString(), idTarea);
				 }
				result = MessageFormat.format(PLANTILLA_RESULTADO, "OK", idproceso);
			} else {
				result = MessageFormat.format(PLANTILLA_RESULTADO, "OK", identificador);
			}
		} catch (JsonParseException e) {

			ExcepcionSNC exc = ManejadorExcepciones.CP42001.getExcepcion(LOGGER, e,
					"la creación de proceso de conservación");
			result = MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage());

		} catch (NullPointerException e) {

			ExcepcionSNC exc = ManejadorExcepciones.CP42006.getExcepcion(LOGGER, e,
					"la creación de proceso de conservación");
			result = MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage());

		} catch (IndexOutOfBoundsException e) {

			ExcepcionSNC exc = ManejadorExcepciones.CP42007.getExcepcion(LOGGER, e,
					"la creación de proceso de conservación");
			result = MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage());
		} catch (IllegalArgumentException e) {

			ExcepcionSNC exc = ManejadorExcepciones.CP42008.getExcepcion(LOGGER, e,
					"la creación de proceso de conservación");
			result = MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage());
		}
		LOGGER.info(hr);
		return result;
	}

	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.TEXT_PLAIN)
	@Path(ParametrosServicioRest.CREAR_PROCESO_PRODUCTOS_CATASTRALES)
	public String crearProcesoProductosCatastrales(String jsonSolicitudProductoCatastral) {
		LOGGER.info(new String(Thread.currentThread().getStackTrace()[1].getMethodName()));
		LOGGER.info(jsonSolicitudProductoCatastral);
		String result = null;
		try {
			jsonSolicitudProductoCatastral = parseData.parsePeticion(jsonSolicitudProductoCatastral);
			SolicitudCatastral solicitudCatastral = parseData.objetcSolicitudCatastral(jsonSolicitudProductoCatastral); // Create
																														// object
																														// Catastral
																														// Json
																														// to
																														// Gson
			KieServerClientJBPM kieServerClient = new KieServerClientJBPM();
			String identificador = kieServerClient
					.validarIdentificadorProceso(solicitudCatastral.getIdentificador().toString());
			if (identificador.equals("null")) {
				HashMap<String, Object> params = new HashMap<>();
				params.put("identificador", solicitudCatastral.getIdentificador());
				params.put("numeroSolicitud", solicitudCatastral.getNumeroSolicitud());
				params.put("territorial", solicitudCatastral.getActividadesUsuarios().get(0).getUsuarios().get(0)
						.getDescripcionTerritorial());
				params.put("tipoSolicitud", solicitudCatastral.getTipoSolicitud());
				params.put("actividad", solicitudCatastral.getActividadesUsuarios().get(0).getActividad());
				params.put("fechaSolicitud", solicitudCatastral.getFechaSolicitud());

				parseData.loginKie(solicitudCatastral.getActividadesUsuarios().get(0).getUsuarios().get(0).getLogin(),
						solicitudCatastral.getActividadesUsuarios().get(0).getUsuarios().get(0).getPrimerRol());

				String idproceso = kieServerClient.kieStartProcess(params, solicitudCatastral).toString();
				String idTarea = kieServerClient.actaulizaFechaProductosP(idproceso);
				if (solicitudCatastral.getActividadesUsuarios().get(0).getUsuarios().size() == 1) {
					kieServerClient.reservaPrimerUsuarioCrear(solicitudCatastral.getActividadesUsuarios().get(0)
							.getUsuarios().get(0).getLogin().toString(), idTarea);
				}
				result = MessageFormat.format(PLANTILLA_RESULTADO, "OK", idproceso);
			} else {
				result = MessageFormat.format(PLANTILLA_RESULTADO, "OK", identificador);
			}
		} catch (JsonParseException e) {

			ExcepcionSNC exc = ManejadorExcepciones.CP42001.getExcepcion(LOGGER, e,
					"la creación de proceso de catastral");
			result = MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(),
					e.getMessage() + ", " + exc.getMessage());

		} catch (NullPointerException e) {

			ExcepcionSNC exc = ManejadorExcepciones.CP42006.getExcepcion(LOGGER, e,
					"la creación de proceso de catastral");
			result = MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(),
					e.getMessage() + ", " + exc.getMessage());

		} catch (IndexOutOfBoundsException e) {

			ExcepcionSNC exc = ManejadorExcepciones.CP42007.getExcepcion(LOGGER, e,
					"la creación de proceso de catastral");
			result = MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(),
					e.getMessage() + ", " + exc.getMessage());
		} catch (IllegalArgumentException e) {

			ExcepcionSNC exc = ManejadorExcepciones.CP42008.getExcepcion(LOGGER, e,
					"la creación de proceso de catastral");
			result = MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(),
					e.getMessage() + ", " + exc.getMessage());
		} catch (KieServicesException e) {

			ExcepcionSNC exc = ManejadorExcepciones.CP44001.getExcepcion(LOGGER, e,
					"la creación de proceso de catastral");
			result = MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(),
					e.getMessage() + ", " + exc.getMessage());
		}
		LOGGER.info(hr);
		return result;
	}

	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.TEXT_PLAIN)
	@Path(ParametrosServicioRest.OBTENER_ESTADO_PROCESO)
	public String obtenerEstadoProceso(String idProceso) {
		LOGGER.info(new String(Thread.currentThread().getStackTrace()[1].getMethodName()));
		LOGGER.info(idProceso);
		String result = null;
		try {

			/* WRITE SERVICE */

		} catch (JsonParseException e) {

			ExcepcionSNC exc = ManejadorExcepciones.CP42001.getExcepcion(LOGGER, e,
					"la creación de proceso de conservación");
			result = MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage());

		} catch (NullPointerException e) {

			ExcepcionSNC exc = ManejadorExcepciones.CP42006.getExcepcion(LOGGER, e,
					"la obtención del estado de proceso");
			result = MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage());

		} catch (IndexOutOfBoundsException e) {

			ExcepcionSNC exc = ManejadorExcepciones.CP42007.getExcepcion(LOGGER, e,
					"la obtención del estado de proceso");
			result = MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage());
		} catch (IllegalArgumentException e) {

			ExcepcionSNC exc = ManejadorExcepciones.CP42008.getExcepcion(LOGGER, e,
					"la obtención del estado de proceso");
			result = MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage());
		} catch (KieServicesException e) {

			ExcepcionSNC exc = ManejadorExcepciones.CP44001.getExcepcion(LOGGER, e,
					"la obtención del estado de proceso");
			result = MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage());
		}
		LOGGER.info(hr);
		return "";
	}

	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.TEXT_PLAIN)
	@Path(ParametrosServicioRest.OBTENER_ACTIVIDADES_PROCESO)
	public String obtenerActividadesProceso(String idProceso) throws ExcepcionSNC {
		LOGGER.info(new String(Thread.currentThread().getStackTrace()[1].getMethodName()));
		LOGGER.info(idProceso);
		String result = null;
		try {

			/* WRITE SERVICE */

		} catch (JsonParseException e) {

			ExcepcionSNC exc = ManejadorExcepciones.CP42001.getExcepcion(LOGGER, e,
					"la obtención de actividades proceso");
			result = MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage());

		} catch (NullPointerException e) {

			ExcepcionSNC exc = ManejadorExcepciones.CP42006.getExcepcion(LOGGER, e,
					"la obtención de actividades proceso");
			result = MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage());

		} catch (IndexOutOfBoundsException e) {

			ExcepcionSNC exc = ManejadorExcepciones.CP42007.getExcepcion(LOGGER, e,
					"la obtención de actividades proceso");
			result = MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage());
		} catch (IllegalArgumentException e) {

			ExcepcionSNC exc = ManejadorExcepciones.CP42008.getExcepcion(LOGGER, e,
					"la obtención de actividades proceso");
			result = MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage());

		} catch (KieServicesException e) {

			ExcepcionSNC exc = ManejadorExcepciones.CP44001.getExcepcion(LOGGER, e,
					"la obtención de actividades proceso");
			result = MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage());
		}
		LOGGER.info(hr);
		return "";
	}

	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.TEXT_PLAIN)
	@Path(ParametrosServicioRest.OBTENER_OBJ_NEGOCIO_PROCESO)
	public String obtenerObjetoNegocioProceso(String peticionStr) throws ExcepcionSNC {
		LOGGER.info(new String(Thread.currentThread().getStackTrace()[1].getMethodName()));
		String result = null;
		LOGGER.info(peticionStr);
		try {

			/* WRITE SERVICE */

		} catch (JsonParseException e) {

			ExcepcionSNC exc = ManejadorExcepciones.CP42001.getExcepcion(LOGGER, e,
					"la obtención del objeto del negocio");
			result = MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage());

		} catch (NullPointerException e) {

			ExcepcionSNC exc = ManejadorExcepciones.CP42006.getExcepcion(LOGGER, e,
					"la obtención del objeto del negocio");
			result = MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage());

		} catch (IndexOutOfBoundsException e) {

			ExcepcionSNC exc = ManejadorExcepciones.CP42007.getExcepcion(LOGGER, e,
					"la obtención del objeto del negocio");
			result = MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage());
		} catch (IllegalArgumentException e) {

			ExcepcionSNC exc = ManejadorExcepciones.CP42008.getExcepcion(LOGGER, e,
					"la obtención del objeto del negocio");
			result = MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage());

		} catch (KieServicesException e) {

			ExcepcionSNC exc = ManejadorExcepciones.CP44001.getExcepcion(LOGGER, e,
					"la obtención del objeto del negocio");
			result = MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage());
		}
		LOGGER.info(hr);
		return "";
	}

	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.TEXT_PLAIN)
	@Path(ParametrosServicioRest.CANCELAR_POR_ID_PROCESO)
	public String cancelarProceso(String peticionCancelarProcesoJson) {
		LOGGER.info(new String(Thread.currentThread().getStackTrace()[1].getMethodName()));
		LOGGER.info(peticionCancelarProcesoJson);
		String result = null;
		try {

			/* WRITE SERVICE */

		} catch (JsonParseException e) {

			ExcepcionSNC exc = ManejadorExcepciones.CP42001.getExcepcion(LOGGER, e, "la cancelación del proceso ID");
			result = MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage());

		} catch (NullPointerException e) {

			ExcepcionSNC exc = ManejadorExcepciones.CP42006.getExcepcion(LOGGER, e, "la cancelación del proceso ID");
			result = MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage());

		} catch (IndexOutOfBoundsException e) {

			ExcepcionSNC exc = ManejadorExcepciones.CP42007.getExcepcion(LOGGER, e, "la cancelación del proceso ID");
			result = MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage());
		} catch (IllegalArgumentException e) {

			ExcepcionSNC exc = ManejadorExcepciones.CP42008.getExcepcion(LOGGER, e, "la cancelación del proceso ID");
			result = MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage());
		} catch (KieServicesException e) {

			ExcepcionSNC exc = ManejadorExcepciones.CP44001.getExcepcion(LOGGER, e, "la cancelación del proceso ID");
			result = MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage());
		}
		LOGGER.info(hr);
		return "";
	}

	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.TEXT_PLAIN)
	@Path(ParametrosServicioRest.CANCELAR_POR_ID_TRAMITE)
	public String consultarProcesoCancelado(String idProceso) {
		LOGGER.info(new String(Thread.currentThread().getStackTrace()[1].getMethodName()));
		String result = null;
		try {

			/* WRITE SERVICE */

		} catch (JsonParseException e) {

			ExcepcionSNC exc = ManejadorExcepciones.CP42001.getExcepcion(LOGGER, e, "cancelación por tramite ID");
			result = MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage());

		} catch (NullPointerException e) {

			ExcepcionSNC exc = ManejadorExcepciones.CP42006.getExcepcion(LOGGER, e, "cancelación por tramite ID");
			result = MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage());

		} catch (IndexOutOfBoundsException e) {

			ExcepcionSNC exc = ManejadorExcepciones.CP42007.getExcepcion(LOGGER, e, "cancelación por tramite ID");
			result = MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage());
		} catch (IllegalArgumentException e) {

			ExcepcionSNC exc = ManejadorExcepciones.CP42008.getExcepcion(LOGGER, e, "cancelación por tramite ID");
			result = MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage());

		} catch (KieServicesException e) {

			ExcepcionSNC exc = ManejadorExcepciones.CP44001.getExcepcion(LOGGER, e, "cancelación por tramite ID");
			result = MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage());
		}
		LOGGER.info(hr);
		return "";
	}

	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.TEXT_PLAIN)
	@Path(ParametrosServicioRest.OBTENER_FLUJO_EJECUCION_PROCESO)
	public String obtenerFlujoEjecucionProceso(String peticionFlujoProceso) {
		LOGGER.info(new String(Thread.currentThread().getStackTrace()[1].getMethodName()));
		LOGGER.info(peticionFlujoProceso);
		String result = null;
		try {

			/* WRITE SERVICE */

		} catch (JsonParseException e) {

			ExcepcionSNC exc = ManejadorExcepciones.CP42001.getExcepcion(LOGGER, e,
					"obtención de flujo de ejecución proceso");
			result = MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage());

		} catch (NullPointerException e) {

			ExcepcionSNC exc = ManejadorExcepciones.CP42006.getExcepcion(LOGGER, e,
					"obtención de flujo de ejecución proceso");
			result = MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage());

		} catch (IndexOutOfBoundsException e) {

			ExcepcionSNC exc = ManejadorExcepciones.CP42007.getExcepcion(LOGGER, e,
					"obtención de flujo de ejecución proceso");
			result = MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage());
		} catch (IllegalArgumentException e) {

			ExcepcionSNC exc = ManejadorExcepciones.CP42008.getExcepcion(LOGGER, e,
					"obtención de flujo de ejecución proceso");
			result = MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage());

		} catch (KieServicesException e) {

			ExcepcionSNC exc = ManejadorExcepciones.CP44001.getExcepcion(LOGGER, e,
					"obtención de flujo de ejecución proceso");
			result = MessageFormat.format(PLANTILLA_RESULTADO, exc.getCodigoExcepcion(), e.getMessage());
		}
		LOGGER.info(hr);
		return "";
	}

}

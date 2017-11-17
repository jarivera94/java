package co.gov.igac.parametros;

public class ParametrosServicioRest {
	//PARAMETROS DE ENTRADA/SALIDA
		public static final String ID_ACTIVIDAD = "idActividad";
		public static final String SOL_CATASTRAL = "solicitudCatastral";
		public static final String SOL_PROD_CATASTRAL = "solicitudProductoCatastral";
		public static final String ACT_MUNICIPIO = "actualizacionMunicipio";
		public static final String USUARIO_DTO = "usuario";
		public static final String DURACION = "duracion";
		public static final String FILTRO = "filtro";
		public static final String TRANSICION = "transicion";
		public static final String OBSERVACION = "observacion";
		
		
		//PATHS CLASES PROCESO
		public static final String PATH_GESTOR_PROCESOS = "/gestorProcesosRest";
		public static final String PATH_GESTOR_TAREAS = "/gestorTareasRest";
		
		//Verificación de estado de los servicios
		public static final String ESTADO_SERVICIOS = "/estadoServiciosProcesos";

		//PARAMETROS DE MÉTODOS POST
		//Proceso
		public static final String OBTENER_ESTADO_PROCESO = "/obtenerEstadoProceso";
		public static final String CREAR_PROCESO_ACTUALIZACION = "/crearProcesoActualizacion";
		public static final String CREAR_PROCESO_CONSERVACION = "/crearProcesoConservacion";
		public static final String CREAR_PROCESOS_CONSERVACION = "/crearProcesosConservacion";
		public static final String CREAR_SUSPENDER_PROCESO_CONSERVACION = "/crearSuspenderProcesoConservacion";
		public static final String CREAR_PROCESOS_ACTUALIZACION_EXPRESS = "/crearProcesosActualizacionExpress";
		public static final String REANUDAR_PROCESO = "/reanudarProceso";
		public static final String REANUDAR_PROCESOS_ACTUALIZACION_EXPRESS = "/reanudarProcesosActualizacionExpress";
		public static final String CREAR_PROCESO_OSMI = "/crearProcesoOsmi";
		public static final String CREAR_PROCESO_PRODUCTOS_CATASTRALES = "/crearProcesoProductosCatastrales";
		public static final String CREAR_PROCESO_OSMI_CONTROL_CALIDAD = "/crearProcesoOsmiControlCalidad";
		public static final String OBTENER_ACTIVIDADES_PROCESO = "/obtenerActividadesProceso";
		public static final String CANCELAR_POR_ID_PROCESO = "/cancelarProcesoPorIdProceso";
		public static final String CANCELAR_POR_ID_TRAMITE = "/cancelarProcesoPorIdTramite";
		
		public static final String OBTENER_CALENDARIO = "/obtenerCalendarioActividad";
		public static final String ADAPTADOR_SERVICIOS = "/adaptadorServicios";
		public static final String ADAPTADOR_TRAMITES = "/adaptadorTramites";
		public static final String ASIGNA_USUARIO_LDAP = "/ldapUsuarios";
		public static final String ASIGNA_USUARIO_TIEMPOS_MUERTOS = "/tiemposMuertos";
		//Actividades
		public static final String AVANZAR_ACTIVIDAD_ACTUALIZACION = "/avanzarActividadActualizacion";
		public static final String AVANZAR_ACTIVIDAD_ACTUALIZACION_SIMPLE = "/avanzarActividadActualizacionSimple";
		public static final String AVANZAR_ACTIVIDAD_CONSERVACION = "/avanzarActividadConservacion";
		public static final String AVANZAR_ACTIVIDAD_CONSERVACION_SIMPLE = "/avanzarActividadConservacionSimple";
		public static final String AVANZAR_ACTIVIDAD_OSMI = "/avanzarActividadOsmi";
		public static final String AVANZAR_ACTIVIDAD_PRODUCTOS = "/avanzarActividadProductos";
		
		public static final String AVANZAR_ACTIVIDAD_OSMI_CONTROL_CALIDAD = "/avanzarActividadOsmiControlCalidad";
		public static final String AVANZAR_ACTIVIDAD_OSMI_SIMPLE = "/avanzarActividadOsmiSimple";
		public static final String OBTENER_ACTIVIDADES_CON_FILTRO = "/obtenerListaActividadesConFiltro";
		public static final String OBTENER_ACTIVIDADES_CON_USUARIO_FILTRO = "/obtenerListaActividadesUsuarioConFiltro";
		public static final String OBTENER_ACTIVIDADES_ID_OBJ_NEGOCIO = "/obtenerActividadesPorIdObjNegocio";
		public static final String OBTENER_CONTEO_ACTIVIDADES_USUARIO = "/obtenerConteoActividadesUsuario";
		public static final String OBTENER_CONTEO_ACTIVIDADES_Y_FILTRO = "/obtenerConteoActividadesConFiltro";
		public static final String OBTENER_ESTADO_ACTIVIDAD = "/obtenerEstadoActividad";
		public static final String OBTENER_OBJ_NEGOCIO_ACTUALIZACION = "/obtenerObjetoNegocioActualizacion";
		public static final String OBTENER_OBJ_NEGOCIO_CONSERVACION = "/obtenerObjetoNegocioConservacion";
		public static final String OBTENER_OBJ_NEGOCIO_CONTROL_CALIDAD = "/obtenerObjetoNegocioControlCalidad";
		public static final String OBTENER_OBJ_NEGOCIO_OFERTAS_INMOBILIARIAS = "/obtenerObjetoNegocioOfertasInmobiliarias";
		public static final String OBTENER_OBJ_NEGOCIO_PROCESO = "/obtenerObjNegocioProceso";
		public static final String OBTENER_TAREAS_POR_USUARIO = "/obtenerTareasPorUsuario";
		public static final String RECLAMAR_ACT_ACTUALIZACION = "/reclamarActividadActualizacion";
		public static final String RECLAMAR_ACT_CONSERVACION = "/reclamarActividadConservacion"; 
		public static final String REANUDAR_ACTIVIDAD = "/reanudarActividad"; 
		public static final String SUSPENDER_ACTIVIDAD = "/suspenderActividad";
		public static final String REPLANIFICAR_ACTIVIDAD = "/replanificarActividad";
		public static final String TRANSFERIR_ACTIVIDAD = "/transferirActividad";
		public static final String OBTENER_USUARIOS_ACTIVIDAD = "/obtenerUsuariosActividad";
		 
		
		//Diagrama proceso
		public static final String OBTENER_FLUJO_EJECUCION_PROCESO = "/obtenerFlujoEjecucionProceso";
		
		//URLs SERVICIO REST
		public static final String SNC_APIPROCESOS_PROCESOS_REST_URL = "snc.apiprocesos.procesos.rest.url";
		public static final String SNC_APIPROCESOS_TAREAS_REST_URL = "snc.apiprocesos.tareas.rest.url";

}

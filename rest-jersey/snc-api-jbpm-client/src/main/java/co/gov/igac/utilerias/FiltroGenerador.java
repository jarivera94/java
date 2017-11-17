package co.gov.igac.utilerias;

import java.text.MessageFormat;
import java.util.List;

public class FiltroGenerador {
	
	private String SQL_TAREAS_FILTRO="select t.ID, t.ACTIVATIONTIME, t.EXPIRATIONTIME, "
			+ " t.STATUS , t.PRIORITY, t.DESCRIPTION, t.processinstanceid , t.ACTUALOWNER_ID  "
			+ " from TASK t where 1=1 ";
	private String SQL_FILTRO_TERRITORIAL=" and t.PROCESSINSTANCEID in "
			+ " (select PROCESSINSTANCEID from VARIABLEINSTANCELOG v "
			+ " where  v.VARIABLEINSTANCEID ='territorial' and v.VALUE =''{0}'')";
	private String SQL_FILTRO_OWNER=" and t.ACTUALOWNER_ID=''{0}''";
	private String SQL_FILTRO_USUARIO_POTENCIAL=" and (t.ID IN "
			+ " (select t.id from TASK t, TASKVARIABLEIMPL tv where "
			+ " tv.name =''GroupId'' and t.id =tv.taskid  and tv.value LIKE "
			+ " (select CONCAT(''%'',CONCAT(g.GROUPNAME,''%'')) from Groups g, UserGroups ug , Users u  "
			+ " where u.username = ''{0}'' AND u.id = ug.idusername and "
			+ " ug.idgroupname = g.id  and g.GROUPNAME not in (''rest-all'',''kie-server'')) and  (t.ACTUALOWNER_ID =''{1}'' or  "
			+ " t.ACTUALOWNER_ID is null)))";
	private String SQL_FILTROS_ESTADOS=" and t.STATUS in (''{0}'')";
	private String SQL_FILTROS_ID_TAREA=" and t.id =''{0}''";
	private String SQL_FILTROS_ID_PROCESO =" and t.PROCESSINSTANCEID = ''{0}''";
	private String SQL_FILTROS_IDS_PROCESOS =" and t.PROCESSINSTANCEID in (''{0}'') ";
	private String SQL_FILTROS_NUMERO_PREDIAL =" and t.PROCESSINSTANCEID in "
			+ " (select PROCESSINSTANCEID from VARIABLEINSTANCELOG v where  "
			+ " v.VARIABLEINSTANCEID =''numeroPredial'' and v.VALUE =''{0}'')";
	private String SQL_FILTROS_NUMERO_RADICACION =" and t.PROCESSINSTANCEID in "
			+ "(select PROCESSINSTANCEID from VARIABLEINSTANCELOG v where  "
			+ "v.VARIABLEINSTANCEID =''numeroRadicacion'' and v.VALUE =''{0}'')";
	private String SQL_FILTROS_NUMERO_SOLICITUD=" and t.PROCESSINSTANCEID in "
			+ " (select PROCESSINSTANCEID from VARIABLEINSTANCELOG v where"
			+ "  v.VARIABLEINSTANCEID =''numeroSolicitud'' and v.VALUE =''{0}'')";
	private String SQL_FILTROS_FECHA_SOLICITUD =" and t.PROCESSINSTANCEID in (select PROCESSINSTANCEID from VARIABLEINSTANCELOG v where  v.VARIABLEINSTANCEID =''fechaSolicitud'' and v.VALUE =''{0}'')";
	private String SQL_FILTROS_TIPO__SOLICITUD =" and t.PROCESSINSTANCEID in (select PROCESSINSTANCEID from VARIABLEINSTANCELOG v where  v.VARIABLEINSTANCEID =''tipoSolicitud'' and v.VALUE =''{0}'')";
	private String SQL_FILTROS_TIPO__TRAMITE =" and t.PROCESSINSTANCEID in (select PROCESSINSTANCEID from VARIABLEINSTANCELOG v where  v.VARIABLEINSTANCEID =''tipoTramite'' and v.VALUE =''{0}'')";
	
	private String SQL_FILTROS_NOMBRE_ACTIVIDAD=" and t.PROCESSINSTANCEID in (select PROCESSINSTANCEID from VARIABLEINSTANCELOG v where  v.VARIABLEINSTANCEID =''actividad'' and v.VALUE =''{0}'')";
	private String SQL_FILTROS_ULTIMA_ACTIVIVAD=" and  t.ID = (select max (id) from "
			+ "task where PROCESSINSTANCEID in"
			+ " (select PROCESSINSTANCEID from VARIABLEINSTANCELOG v"
			+ " where  v.VARIABLEINSTANCEID =''identificador'' and v.VALUE =''{0}''))";
	private String SQL_FILTROS_IDENTIFICADOR_OBJETO = " and t.PROCESSINSTANCEID in "
			+ " (select PROCESSINSTANCEID from VARIABLEINSTANCELOG v where  "
			+ " v.VARIABLEINSTANCEID =''identificador'' and v.VALUE =''{0}'')";
	
	private String TAREAS_VIGENTES = "0,2,8";
	
	private String SQL_NOMBRE_TAREA=" and t.DESCRIPTION like ''%{0}%''";
	/**
	 * Corresponde a tareas que ya fueron realizadas en el proceso de negocio.
	 */
	
	private String TAREAS_REALIZADAS = "5";

	/**
	 * Corresponde a tareas de procesos que terminaron antes de que dichas
	 * actividades fueran realizadas. Un ejemplo de este caso es cuando un
	 * trámite no procede.
	 */
	private String TAREAS_TERMINADAS = "7";

	/**
	 * Corresponden a tareas que pueden ser realizadas por alguno de los
	 * usuarios de un rol. Por ejemplo, el rol de control de digitalización, el
	 * cual tiene varios usuarios por territorial y cualquiera de ellos puede
	 * realizar la tarea.
	 * 
	 */
	private String TAREAS_LISTAS = "2";

	/**
	 * Corresponden a tareas que pueden ser ejecutadas por un usuario en
	 * particular (generalmente cuando un rol tiene un sólo usuario, por
	 * ejemplo: Un sólo Responsable de Conservación en una territorial).
	 * 
	 */
	private String TAREAS_RECLAMADAS = "8";

	/**
	 * Corresponde a todos los estados soportados por las tareas de las
	 * instancias de procesos de negocio del Sistema Nacional Catastral.
	 */
	private String TAREAS_VIGENTES_REALIZADAS_FINALIZADAS_TERMINADAS = "0,2,8,5,7";

	/**
	 * Corresponde a todos los estados soportados por las actividades de los
	 * procesos de negocio del Sistema Nacional Catastral.
	 */
	private String ACTIVIDADES_VIGENTES_REALIZADAS_FINALIZADAS_TERMINADAS = "0,2,8,5,7";

	private String ACTIVIDADES_SUSPENDIDAS = "1";

	private String ACTIVIDADES_VIGENTES = "0";

	private String ACTIVIDADES_VIGENTES_Y_SUSPENDIDAS = "0,1";
	
	public  final String QueryFiltroTareas(List<FiltroActividades> filtros){
		
		String queryFiltros=SQL_TAREAS_FILTRO;
		
		for (int i = 0; i < filtros.size(); i++) {
			if(filtros.get(i).getAtributo().toString().equals("usuarioPotencial")) 
				queryFiltros+= MessageFormat.format(SQL_FILTRO_USUARIO_POTENCIAL,filtros.get(i).getValor().toString(),filtros.get(i).getValor().toString());
			
			if(filtros.get(i).getAtributo().toString().equals("estados"))				
				queryFiltros+= MessageFormat.format(SQL_FILTROS_ESTADOS,RetornaEstados(filtros.get(i).getValor().toString()));
			if(filtros.get(i).getAtributo().toString().equals("numeroSolicitud"))
				queryFiltros+= MessageFormat.format(SQL_FILTROS_NUMERO_SOLICITUD,filtros.get(i).getValor().toString());
			if(filtros.get(i).getAtributo().toString().equals("ID_PRO")||filtros.get(i).getAtributo().toString().equals("ID_CONSERVACION"))
				queryFiltros+= MessageFormat.format(SQL_FILTROS_IDENTIFICADOR_OBJETO,filtros.get(i).getValor().toString());
			if(filtros.get(i).getAtributo().toString().equals("ultimaActividad"))
				queryFiltros+= MessageFormat.format(SQL_FILTROS_ULTIMA_ACTIVIVAD,filtros.get(i).getValor().toString());
			if(filtros.get(i).getAtributo().toString().equals("nombreActividad"))
				queryFiltros+= MessageFormat.format(SQL_NOMBRE_TAREA,filtros.get(i).getValor().toString());
		}
		
		System.out.println(queryFiltros);
		return queryFiltros;
	}
	public  String RetornaEstados(String estado){
		String[] estados = estado.split(",");
		String resultadoEstados="',''"; 
		for (int i = 0; i < estados.length; i++) {
			if(estados[i].equals("5"))resultadoEstados+=",'Completed'";
			if(estados[i].equals("7"))resultadoEstados+=",'Completed'";
			if(estados[i].equals("2"))resultadoEstados+=",'Ready'";
			if(estados[i].equals("8"))resultadoEstados+=",'InProgress', 'Reserved'";
		} 
		
		return resultadoEstados+",'";
	}
	public String getSQL_TAREAS_FILTRO() {
		return SQL_TAREAS_FILTRO;
	}
	public void setSQL_TAREAS_FILTRO(String sQL_TAREAS_FILTRO) {
		SQL_TAREAS_FILTRO = sQL_TAREAS_FILTRO;
	}
	public String getSQL_FILTRO_TERRITORIAL() {
		return SQL_FILTRO_TERRITORIAL;
	}
	public void setSQL_FILTRO_TERRITORIAL(String sQL_FILTRO_TERRITORIAL) {
		SQL_FILTRO_TERRITORIAL = sQL_FILTRO_TERRITORIAL;
	}
	public String getSQL_FILTRO_OWNER() {
		return SQL_FILTRO_OWNER;
	}
	public void setSQL_FILTRO_OWNER(String sQL_FILTRO_OWNER) {
		SQL_FILTRO_OWNER = sQL_FILTRO_OWNER;
	}
	public String getSQL_FILTRO_USUARIO_POTENCIAL() {
		return SQL_FILTRO_USUARIO_POTENCIAL;
	}
	public void setSQL_FILTRO_USUARIO_POTENCIAL(String sQL_FILTRO_USUARIO_POTENCIAL) {
		SQL_FILTRO_USUARIO_POTENCIAL = sQL_FILTRO_USUARIO_POTENCIAL;
	}
	public String getSQL_FILTROS_ESTADOS() {
		return SQL_FILTROS_ESTADOS;
	}
	public void setSQL_FILTROS_ESTADOS(String sQL_FILTROS_ESTADOS) {
		SQL_FILTROS_ESTADOS = sQL_FILTROS_ESTADOS;
	}
	public String getSQL_FILTROS_ID_TAREA() {
		return SQL_FILTROS_ID_TAREA;
	}
	public void setSQL_FILTROS_ID_TAREA(String sQL_FILTROS_ID_TAREA) {
		SQL_FILTROS_ID_TAREA = sQL_FILTROS_ID_TAREA;
	}
	public String getSQL_FILTROS_ID_PROCESO() {
		return SQL_FILTROS_ID_PROCESO;
	}
	public void setSQL_FILTROS_ID_PROCESO(String sQL_FILTROS_ID_PROCESO) {
		SQL_FILTROS_ID_PROCESO = sQL_FILTROS_ID_PROCESO;
	}
	public String getSQL_FILTROS_IDS_PROCESOS() {
		return SQL_FILTROS_IDS_PROCESOS;
	}
	public void setSQL_FILTROS_IDS_PROCESOS(String sQL_FILTROS_IDS_PROCESOS) {
		SQL_FILTROS_IDS_PROCESOS = sQL_FILTROS_IDS_PROCESOS;
	}
	public String getSQL_FILTROS_NUMERO_PREDIAL() {
		return SQL_FILTROS_NUMERO_PREDIAL;
	}
	public void setSQL_FILTROS_NUMERO_PREDIAL(String sQL_FILTROS_NUMERO_PREDIAL) {
		SQL_FILTROS_NUMERO_PREDIAL = sQL_FILTROS_NUMERO_PREDIAL;
	}
	public String getSQL_FILTROS_NUMERO_RADICACION() {
		return SQL_FILTROS_NUMERO_RADICACION;
	}
	public void setSQL_FILTROS_NUMERO_RADICACION(String sQL_FILTROS_NUMERO_RADICACION) {
		SQL_FILTROS_NUMERO_RADICACION = sQL_FILTROS_NUMERO_RADICACION;
	}
	public String getSQL_FILTROS_NUMERO_SOLICITUD() {
		return SQL_FILTROS_NUMERO_SOLICITUD;
	}
	public void setSQL_FILTROS_NUMERO_SOLICITUD(String sQL_FILTROS_NUMERO_SOLICITUD) {
		SQL_FILTROS_NUMERO_SOLICITUD = sQL_FILTROS_NUMERO_SOLICITUD;
	}
	public String getSQL_FILTROS_FECHA_SOLICITUD() {
		return SQL_FILTROS_FECHA_SOLICITUD;
	}
	public void setSQL_FILTROS_FECHA_SOLICITUD(String sQL_FILTROS_FECHA_SOLICITUD) {
		SQL_FILTROS_FECHA_SOLICITUD = sQL_FILTROS_FECHA_SOLICITUD;
	}
	public String getSQL_FILTROS_TIPO__SOLICITUD() {
		return SQL_FILTROS_TIPO__SOLICITUD;
	}
	public void setSQL_FILTROS_TIPO__SOLICITUD(String sQL_FILTROS_TIPO__SOLICITUD) {
		SQL_FILTROS_TIPO__SOLICITUD = sQL_FILTROS_TIPO__SOLICITUD;
	}
	public String getSQL_FILTROS_TIPO__TRAMITE() {
		return SQL_FILTROS_TIPO__TRAMITE;
	}
	public void setSQL_FILTROS_TIPO__TRAMITE(String sQL_FILTROS_TIPO__TRAMITE) {
		SQL_FILTROS_TIPO__TRAMITE = sQL_FILTROS_TIPO__TRAMITE;
	}
	public String getSQL_FILTROS_IDENTIFICADOR_OBJETO() {
		return SQL_FILTROS_IDENTIFICADOR_OBJETO;
	}
	public void setSQL_FILTROS_IDENTIFICADOR_OBJETO(String sQL_FILTROS_IDENTIFICADOR_OBJETO) {
		SQL_FILTROS_IDENTIFICADOR_OBJETO = sQL_FILTROS_IDENTIFICADOR_OBJETO;
	}
	public String getSQL_FILTROS_NOMBRE_ACTIVIDAD() {
		return SQL_FILTROS_NOMBRE_ACTIVIDAD;
	}
	public void setSQL_FILTROS_NOMBRE_ACTIVIDAD(String sQL_FILTROS_NOMBRE_ACTIVIDAD) {
		SQL_FILTROS_NOMBRE_ACTIVIDAD = sQL_FILTROS_NOMBRE_ACTIVIDAD;
	}
	public String getTAREAS_VIGENTES() {
		return TAREAS_VIGENTES;
	}
	public void setTAREAS_VIGENTES(String tAREAS_VIGENTES) {
		TAREAS_VIGENTES = tAREAS_VIGENTES;
	}
	public String getTAREAS_REALIZADAS() {
		return TAREAS_REALIZADAS;
	}
	public void setTAREAS_REALIZADAS(String tAREAS_REALIZADAS) {
		TAREAS_REALIZADAS = tAREAS_REALIZADAS;
	}
	public String getTAREAS_TERMINADAS() {
		return TAREAS_TERMINADAS;
	}
	public void setTAREAS_TERMINADAS(String tAREAS_TERMINADAS) {
		TAREAS_TERMINADAS = tAREAS_TERMINADAS;
	}
	public String getTAREAS_LISTAS() {
		return TAREAS_LISTAS;
	}
	public void setTAREAS_LISTAS(String tAREAS_LISTAS) {
		TAREAS_LISTAS = tAREAS_LISTAS;
	}
	public String getTAREAS_RECLAMADAS() {
		return TAREAS_RECLAMADAS;
	}
	public void setTAREAS_RECLAMADAS(String tAREAS_RECLAMADAS) {
		TAREAS_RECLAMADAS = tAREAS_RECLAMADAS;
	}
	public String getTAREAS_VIGENTES_REALIZADAS_FINALIZADAS_TERMINADAS() {
		return TAREAS_VIGENTES_REALIZADAS_FINALIZADAS_TERMINADAS;
	}
	public void setTAREAS_VIGENTES_REALIZADAS_FINALIZADAS_TERMINADAS(
			String tAREAS_VIGENTES_REALIZADAS_FINALIZADAS_TERMINADAS) {
		TAREAS_VIGENTES_REALIZADAS_FINALIZADAS_TERMINADAS = tAREAS_VIGENTES_REALIZADAS_FINALIZADAS_TERMINADAS;
	}
	public String getACTIVIDADES_VIGENTES_REALIZADAS_FINALIZADAS_TERMINADAS() {
		return ACTIVIDADES_VIGENTES_REALIZADAS_FINALIZADAS_TERMINADAS;
	}
	public void setACTIVIDADES_VIGENTES_REALIZADAS_FINALIZADAS_TERMINADAS(
			String aCTIVIDADES_VIGENTES_REALIZADAS_FINALIZADAS_TERMINADAS) {
		ACTIVIDADES_VIGENTES_REALIZADAS_FINALIZADAS_TERMINADAS = aCTIVIDADES_VIGENTES_REALIZADAS_FINALIZADAS_TERMINADAS;
	}
	public String getACTIVIDADES_SUSPENDIDAS() {
		return ACTIVIDADES_SUSPENDIDAS;
	}
	public void setACTIVIDADES_SUSPENDIDAS(String aCTIVIDADES_SUSPENDIDAS) {
		ACTIVIDADES_SUSPENDIDAS = aCTIVIDADES_SUSPENDIDAS;
	}
	public String getACTIVIDADES_VIGENTES() {
		return ACTIVIDADES_VIGENTES;
	}
	public void setACTIVIDADES_VIGENTES(String aCTIVIDADES_VIGENTES) {
		ACTIVIDADES_VIGENTES = aCTIVIDADES_VIGENTES;
	}
	public String getACTIVIDADES_VIGENTES_Y_SUSPENDIDAS() {
		return ACTIVIDADES_VIGENTES_Y_SUSPENDIDAS;
	}
	public void setACTIVIDADES_VIGENTES_Y_SUSPENDIDAS(String aCTIVIDADES_VIGENTES_Y_SUSPENDIDAS) {
		ACTIVIDADES_VIGENTES_Y_SUSPENDIDAS = aCTIVIDADES_VIGENTES_Y_SUSPENDIDAS;
	}
}

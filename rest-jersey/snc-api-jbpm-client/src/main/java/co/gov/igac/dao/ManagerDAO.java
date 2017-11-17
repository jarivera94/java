package co.gov.igac.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import co.gov.igac.entity.Predio;
import co.gov.igac.entity.Tramite;
import co.gov.igac.entity.TramiteEstado;
import co.gov.igac.entity.Users;
import co.gov.igac.logger.impl.ISNCLogger;
import co.gov.igac.logger.impl.SNCLoggerFactory;

public class ManagerDAO {

	private static final ISNCLogger LOGGER = SNCLoggerFactory.getLogger(ManagerDAO.class);
	public static SessionFactory factory;
	public static SessionFactory factorysnc;
	private Transaction tx;
	public String CFGDJBPM = "hibernate.jbpm.cfg.xml";
	public String CFGSNC = "hibernate.snc.cfg.xml";

	private static final String SQL_PARAMETROS_TAREAS = "select p.NOMBREACTIVIDAD ,p.macroproceso,p.proceso,p.subprocesoactividad,p.macroprocesoproceso, p.TIPO,p.URLCASODEUSO,p.TRANCISIONES , r.ROL,p.TIPOPROCESAMIENTO from PARAMETROSTAREAS p, ROLPORTAREA r "
			+ " where p.NOMBREACTIVIDAD = r.NOMBRETAREA and p.NOMBREACTIVIDAD=?";
	private static final String ARCHIVAR_TRAMITE = "UPDATE tramite SET estado = ?, archivado = ? WHERE id = ?";
	/** Expresión SQL para encontrar el nombre de un trámite. */
	private static final String SQL_NOMBRE_TRAMITE = "SELECT "
			+ " FNC_OBTENER_VALOR_DOMINIO_NOM('TRAMITE_TIPO_TRAMITE', TRAMITE.TIPO_TRAMITE) || "
			+ " CASE WHEN FNC_OBTENER_VALOR_DOMINIO_NOM('MUTACION_CLASE',TRAMITE.CLASE_MUTACION) IN ('No encontrado', 'Error') THEN '' "
			+ " ELSE ' ' || FNC_OBTENER_VALOR_DOMINIO_NOM('MUTACION_CLASE',TRAMITE.CLASE_MUTACION) END || "
			+ " CASE WHEN INITCAP(TRAMITE.SUBTIPO) IS NULL THEN '' ELSE ' ' || INITCAP(TRAMITE.SUBTIPO) END "
			+ " FROM TRAMITE WHERE TRAMITE.ID = ?";
	private static final String SQL_VARIABLE_LOG_CATASTRAL = "select vi.VALUE identificador,vn.VALUE numerosolicitud , vt.VALUE tiposolicitud "
			+ " from (select  v.VALUE ,v.PROCESSINSTANCEID from "
			+ " VARIABLEINSTANCELOG v where v.VARIABLEINSTANCEID='identificador' )"
			+ " vi,(select  v.VALUE ,v.PROCESSINSTANCEID from VARIABLEINSTANCELOG v "
			+ " where v.VARIABLEINSTANCEID='numeroSolicitud' ) vn ,"
			+ " (select  v.VALUE ,v.PROCESSINSTANCEID from VARIABLEINSTANCELOG v "
			+ " where v.VARIABLEINSTANCEID='tipoSolicitud' or v.VARIABLEINSTANCEID='tipoTramite' ) vt "
			+ " where vi.PROCESSINSTANCEID=vn.PROCESSINSTANCEID and "
			+ " vn.PROCESSINSTANCEID=vt.PROCESSINSTANCEID and vi.PROCESSINSTANCEID=?";

	private static final String SQL_VARIABLE_LOG_CONSERVACION=" select vi.VALUE identificador,vn.VALUE numerosolicitud , vt.VALUE tipoTramite,"
			+ " vp.VALUE numeroPredial, vr.VALUE numeroRadicacion"
			+ " from (select  v.VALUE ,v.PROCESSINSTANCEID from  VARIABLEINSTANCELOG v"
			+ " where v.VARIABLEINSTANCEID='identificador' ) vi,(select  v.VALUE ,v.PROCESSINSTANCEID"
			+ " from VARIABLEINSTANCELOG v  where v.VARIABLEINSTANCEID='numeroSolicitud' ) vn , "
			+ " (select  v.VALUE ,v.PROCESSINSTANCEID from VARIABLEINSTANCELOG v"
			+ " where  v.VARIABLEINSTANCEID='tipoTramite'"
			+ " ) vt  ,(select  v.VALUE ,v.PROCESSINSTANCEID from VARIABLEINSTANCELOG v"
			+ " where  v.VARIABLEINSTANCEID='numeroPredial') vp,"
			+ " (select  v.VALUE ,v.PROCESSINSTANCEID from VARIABLEINSTANCELOG v"
			+ " where  v.VARIABLEINSTANCEID='numeroRadicacion') vr"
			+ " where vi.PROCESSINSTANCEID=vn.PROCESSINSTANCEID and "
			+ " vn.PROCESSINSTANCEID=vt.PROCESSINSTANCEID and vt.PROCESSINSTANCEID= vp.PROCESSINSTANCEID"
			+ " and vp.PROCESSINSTANCEID=vr.PROCESSINSTANCEID and vi.PROCESSINSTANCEID=?";

	private static final String SQL_LISTA_TAREAS = "select ati.* from ("
			+ " select t.activationTime, t.actualOwner, t.createdBy, "
			+ " t.createdOn, t.deploymentId, t.description, t.dueDate, t.name, t.parentId, "
			+ " t.priority, t.processId, t.processInstanceId, t.processSessionId, t.status, t.taskId,"
			+ " t.workItemId,oe.id,  CASE WHEN v.variableInstanceId = 'territorial' THEN v.value "// territorial
																									// del
																									// usuario
			+ " ELSE NULL END AS territorial from AuditTaskImpl t left join VariableInstanceLog v"
			+ " on v.processInstanceId = t.processInstanceId, PeopleAssignments_PotOwners po, OrganizationalEntity oe"
			+ " where t.taskId = po.task_id and po.entity_id = oe.id  and (t.actualOwner = ? or t.actualOwner  is null )" // usuario
																															// que
																															// ingresa
			+ " and oe.id in (select ooe.id from OrganizationalEntity ooe where ooe.id in "
			+ " (select g.GROUPNAME from Groups g, UserGroups ug , Users u "
			+ " where u.username = ? AND u.id = ug.idusername and ug.idgroupname = g.id))" // usuario
																							// que
																							// ingresa
			+ " and t.status in ('InProgress','Ready','Reserved')order by t.taskId desc) ati"
			+ " where ati.territorial = ? and ati.description=? group by ati.taskId,ati.territorial,ati.activationTime," // territorial
																															// del
																															// usuario
			+ " ati.actualOwner, ati.createdBy, ati.createdOn, ati.deploymentId, ati.description, ati.dueDate,"
			+ " ati.name, ati.parentId, ati.priority, ati.processId, ati.processInstanceId, ati.processSessionId, ati.status,"
			+ " ati.workItemId,ati.id order by ati.taskId desc";

	private static final String SQL_CONTEO_ACTIVIDADES = "select count(*) cantidad_tareas,t.DESCRIPTION"
			+ " from TASK t inner join TASKVARIABLEIMPL tv on t.ID=tv.TASKID where tv.NAME ='GroupId' and  "
			+ " tv.VALUE LIKE (select CONCAT( '%',CONCAT(g.groupname ,'%')) "
			+ " from USERS u inner join USERGROUPS us on u.ID =  us.IDUSERNAME inner join GROUPS g on us.IDGROUPNAME=g.ID"
			+ " where u.username = ? and  g.GROUPNAME not in ('rest-all','kie-server')) and (t.actualowner_id =? or t.actualowner_id  is null )  "
			+ " and t.status in ('InProgress','Ready','Reserved') group by t.DESCRIPTION ";

	private static final String SQL_TRAE_DURACION_HABIL_TAREA = "SELECT timeout FROM DuracionTareas WHERE nombretarea =?";
	private static final String SQL_PARAMETROS_TAREAS_INDIVIDUAL = "select p.URLCASODEUSO, p.TIPOPROCESAMIENTO,p.TIPO,p.TRANCISIONES from PARAMETROSTAREAS p where p.actividad =?";
	private static final String SQL_BUSCA_OWNER_TAREA = "select ACTUALOWNER_ID from task where id =?";
	private static final String SQL_BUSCA_SIGUIENTE_ACTIVIDAD_PRODUCTOS = "select MAX(id) from TASK where PROCESSINSTANCEID =(select PROCESSINSTANCEID from task where id =?) and status ='Ready'";
	private static final String SQL_BUSCA_ACTIVIDAD_PROCESO = "select MAX(id) from TASK where PROCESSINSTANCEID =? and status ='Ready'";
	private static final String SQL_ASIGNA_EXPIRACION_TAREA = "UPDATE TASK tt SET tt.EXPIRATIONTIME =to_date(?,'yyyy/mm/dd hh24:mi:ss')  WHERE ID =?";
	private static final String SQL_CALCULA_DIA_HABIL = "SELECT count(*)  FROM CalendarioFeriado where fecha between to_timestamp (?,'YYYY-MM-DD') and to_timestamp(?,'YYYY-MM-DD') and activo =1";
	private static final String SQL_ACTUALIZA_AUDITASK = "update AUDITTASKIMPL a set a.DUEDATE =to_date(?,'yyyy/mm/dd hh24:mi:ss')  WHERE a.taskid=?";
	private static final String SQL_BUSCA_SIGUIENTE_ACTIVIDAD_CONSERVACION = "select t.ID, t.DESCRIPTION, t.PROCESSINSTANCEID,v.VALUE from task t, VARIABLEINSTANCELOG v "
			+ " where t.PROCESSINSTANCEID=v.PROCESSINSTANCEID and  "
			+ " t.PROCESSINSTANCEID =(SELECT MAX(instanceid) FROM PROCESSINSTANCEINFO p where INSTANCEID in"
			+ " (select vi.PROCESSINSTANCEID " + " from (select v.PROCESSINSTANCEID from VARIABLEINSTANCELOG v"
			+ " where v.VARIABLEID='identificador' and v.value =?) vi,"
			+ " (select v.PROCESSINSTANCEID from VARIABLEINSTANCELOG v "
			+ " where v.VARIABLEID='numeroSolicitud' and v.value =?) vs "
			+ " where vi.PROCESSINSTANCEID=VS.PROCESSINSTANCEID) and  p.STATE=1) and v.VARIABLEID='tipoTramite' and status='Ready'";

	private static final String SQL_BUSCA_DURACION_TAREA_CONSERVACION = "select * from TIEMPOSTAREAS where NOMBREACTIVIDAD=? and TIPOTRAMITE=?";
	private static final String SQL_SIGUIENTE_ACTIVIDAD_CONSERVACION = "select max(t.ID) from task t, VARIABLEINSTANCELOG v "
			+ " where t.PROCESSINSTANCEID=v.PROCESSINSTANCEID and "
			+ " t.PROCESSINSTANCEID =(SELECT MAX(instanceid) FROM PROCESSINSTANCEINFO p where INSTANCEID in "
			+ " (select vi.PROCESSINSTANCEID  " + " from (select v.PROCESSINSTANCEID from VARIABLEINSTANCELOG v "
			+ " where v.VARIABLEID='identificador' and v.value =(select v.value from TASK t inner join VARIABLEINSTANCELOG v on  "
			+ " t.PROCESSINSTANCEID = v.PROCESSINSTANCEID  " + " where t.id =? and v.VARIABLEID='identificador')) vi, "
			+ " (select v.PROCESSINSTANCEID from VARIABLEINSTANCELOG v  "
			+ " where v.VARIABLEID='numeroSolicitud' and v.value =(select v.value from TASK t inner join VARIABLEINSTANCELOG v on  "
			+ " t.PROCESSINSTANCEID = v.PROCESSINSTANCEID  "
			+ " where t.id =? and v.VARIABLEID='numeroSolicitud')) vs  "
			+ " where vi.PROCESSINSTANCEID=VS.PROCESSINSTANCEID) and  p.STATE=1)  "
			+ " and status='Ready' and v.VARIABLEID='tipoTramite'";

	private static final String SQL_BUSCA_TRAMITE_ACTIVIDAD = "select v.value from TASK t inner join VARIABLEINSTANCELOG v on "
			+ " t.PROCESSINSTANCEID = v.PROCESSINSTANCEID" + " where t.id =? and v.VARIABLEID='tipoTramite'";
	private static final String SQL_BUSCA_ACTIVIDA_POR_PROCESO="select max(id),PROCESSID, DEPLOYMENTID from Task where PROCESSINSTANCEID=? and STATUS='Ready' group by PROCESSID, DEPLOYMENTID";
	private static final String SQL_VALIDACION_IDENTIFICADOR_CREAR = "SELECT MIN(PROCESSINSTANCEID) FROM VARIABLEINSTANCELOG WHERE VARIABLEID = 'identificador' AND VALUE  = ?";
	private static final String SQL_VALIDACION_RECLAMAR_TAREA = "SELECT ACTUALOWNER_ID FROM task where id = ?";
	
	public ManagerDAO() {
		if (factory == null) {
			try {
				factory = new Configuration().configure(CFGDJBPM).buildSessionFactory();
			} catch (Throwable ex) {
				LOGGER.info("No es posible conectarse a JBPM");
				throw new ExceptionInInitializerError(ex);
			}
		}
		if (factorysnc == null) {
			try {
				factorysnc = new Configuration().configure(CFGSNC).buildSessionFactory();
			} catch (Throwable ex) {
				LOGGER.info("No es posible conectarse a SNC");
				throw new ExceptionInInitializerError(ex);
			}

		}
	}

	@SuppressWarnings("unchecked")
	public int countHolidays(String startDate, String lastDate) {
		Session session = factory.openSession();
		List<Object> resultquery = null;
		int holidays = 0;

		try {
			tx = session.beginTransaction();

			TypedQuery<?> query = session.createNativeQuery(SQL_CALCULA_DIA_HABIL);
			query.setParameter(1, startDate);
			query.setParameter(2, lastDate);
			resultquery = (List<Object>) query.getResultList();
			holidays = Integer.parseInt(resultquery.get(0).toString());

		} catch (HibernateException e) {
			if (tx == null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

		return holidays;
	}


	@SuppressWarnings("unchecked")
	public List<Object[]> BuscaDuracionTareaConservacion(String nombreActividad, String tipoTramite) {
		List<Object[]> result = null;
		Session session = factory.openSession();
		try {

			tx = session.beginTransaction();
			TypedQuery<?> query = session.createNativeQuery(SQL_BUSCA_DURACION_TAREA_CONSERVACION);
			query.setParameter(1, nombreActividad);
			query.setParameter(2, tipoTramite);
			result = (List<Object[]>) query.getResultList();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public int getHolidays(String nameactivity) {
		List<Object> resultquery = null;
		int holidays = 0;
		Session session = factory.openSession();
		try {
			tx = session.beginTransaction();
			TypedQuery<?> query = session.createNativeQuery(SQL_TRAE_DURACION_HABIL_TAREA);
			query.setParameter(1, nameactivity);
			resultquery = (List<Object>) query.getResultList();
			holidays = Integer.parseInt(resultquery.get(0).toString());
		} catch (NoResultException e) {
			holidays = 0;
			if (tx == null) tx.rollback();
		} catch (IndexOutOfBoundsException e) {
			throw new IndexOutOfBoundsException("Tarea No encontrada");
		} catch (PersistenceException e) {
			throw new PersistenceException("ERROR de consulta BD.");
		} finally {
			session.close();
		}

		return holidays;
	}

	public void asignaDuracionTarea(String fecha, String idTarea) {
		Session session = factory.openSession();
		try {
			tx = session.beginTransaction();
			TypedQuery<?> query = session.createNativeQuery(SQL_ASIGNA_EXPIRACION_TAREA);
			query.setParameter(1, fecha);
			query.setParameter(2, idTarea);
			query.executeUpdate();

			query = session.createNativeQuery(SQL_ACTUALIZA_AUDITASK);
			query.setParameter(1, fecha);
			query.setParameter(2, idTarea);
			query.executeUpdate();
			tx.commit();
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> BuscaActividadConservacion(String identificador, String numeroSolicitud) {
		List<Object[]> result = null;
		Session session = factory.openSession();
		try {
			tx = session.beginTransaction();
			TypedQuery<?> query = session.createNativeQuery(SQL_BUSCA_SIGUIENTE_ACTIVIDAD_CONSERVACION);
			query.setParameter(1, identificador);
			query.setParameter(2, numeroSolicitud);
			result = (List<Object[]>) query.getResultList();
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public String BuscaActividadProductos(String idTarea) {
		String result = null;
		List<Object> resultquery = null;
		Session session = factory.openSession();
		try {
			tx = session.beginTransaction();
			TypedQuery<?> query = session.createNativeQuery(SQL_BUSCA_SIGUIENTE_ACTIVIDAD_PRODUCTOS);
			query.setParameter(1, idTarea);
			resultquery =(List<Object>)query.getResultList();
			result=resultquery.get(0).toString();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public String BuscaActividadTramite(String idTarea) {
		String result = null;
		List<Object> resultquery = null;
		Session session = factory.openSession();
		try {
			tx = session.beginTransaction();
			TypedQuery<?> query = session.createNativeQuery(SQL_BUSCA_TRAMITE_ACTIVIDAD);
			query.setParameter(1, idTarea);
			resultquery = (List<Object>) query.getResultList();
			result = resultquery.get(0).toString();
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public String BuscaActividadConservacion(String idTarea) {
		String result = null;
		List<Object> resultquery = null;
		Session session = factory.openSession();
		try {
			tx = session.beginTransaction();
			TypedQuery<?> query = session.createNativeQuery(SQL_SIGUIENTE_ACTIVIDAD_CONSERVACION);
			query.setParameter(1, idTarea);
			query.setParameter(2, idTarea);
			resultquery = (List<Object>) query.getResultList();
			result = resultquery.get(0).toString();
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public String BuscaActividadProcesoP(String idProceso) {
		String result = null;
		List<Object> resultquery = null;
		Session session = factory.openSession();
		try {
			tx = session.beginTransaction();
			TypedQuery<?> query = session.createNativeQuery(SQL_BUSCA_ACTIVIDAD_PROCESO);
			query.setParameter(1, idProceso);
			resultquery = (List<Object>) query.getResultList();
			result = resultquery.get(0).toString();
		} catch (IndexOutOfBoundsException e) {
			if (result == null) {
				throw new NullPointerException("Tarea no encontrada");
			}
		} catch (PersistenceException e) {
			throw new PersistenceException("Error de petición");
		}
		finally {
			session.close();
		}
		return result ;
	}

	@SuppressWarnings("unchecked")
	public String validacionExisteProceso(String identificador) {
		String result = null;
		List<Object> resultquery = null;
		Session session = factory.openSession();
		try {
			tx = session.beginTransaction();
			TypedQuery<?> query = session.createNativeQuery(SQL_VALIDACION_IDENTIFICADOR_CREAR);
			query.setParameter(1, identificador);
			resultquery = (List<Object>) query.getResultList();
			result = String.valueOf(resultquery.get(0));
		} catch (IndexOutOfBoundsException e) {
			if (result == null) {
				throw new NullPointerException("Registro no encontrada");
			}
		} catch (PersistenceException e) {
			throw new PersistenceException("Error de petición de campos Json Request");
		}
		finally {
			session.close();
		}
		return result ;
	}

	@SuppressWarnings("unchecked")
	public String validarTarea(String idTarea) {
		String result = null;
		List<Object> resultquery = null;
		Session session = factory.openSession();
		try {
			tx = session.beginTransaction();
			TypedQuery<?> query = session.createNativeQuery(SQL_VALIDACION_RECLAMAR_TAREA);
			query.setParameter(1, idTarea);
			resultquery = (List<Object>) query.getResultList();
			result = String.valueOf(resultquery.get(0));
		} catch (IndexOutOfBoundsException e) {
			if (result == null) {
				throw new NullPointerException("Registro no encontrado");
			}
		} catch (PersistenceException e) {
			throw new PersistenceException("Error de petición de campos Json Request");
		}
		finally {
			session.close();
		}
		return result ;
	}

	@SuppressWarnings("unchecked")
	public String BuscaUsuarioPorTarea(String idTarea) {
		String result = null;
		List<Object[]> resultquery = null;
		Session session = factory.openSession();
		try {
			tx = session.beginTransaction();
			TypedQuery<?> query = session.createNativeQuery(SQL_BUSCA_OWNER_TAREA);
			query.setParameter(1, idTarea);
			resultquery = (List<Object[]>) query.getResultList();
			result = String.valueOf(resultquery.get(0));
		} catch (IndexOutOfBoundsException e) {
			if (result == null) {
				throw new NullPointerException("Tarea no encontrada");
			}
		} catch (PersistenceException e) {
			throw new PersistenceException("Error de petición");
		} finally {
			session.close();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> TareasConFiltro(String queryFiltros) {
		List<Object[]> result = null;
		Session session = factory.openSession();
		try {
			tx = session.beginTransaction();
			TypedQuery<?> query = session.createNativeQuery(queryFiltros);
			result = (List<Object[]>) query.getResultList();
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> ParametrosTareas(String[] descripcion) {
		List<Object[]> result = null;
		Session session = factory.openSession();
		try {
			tx = session.beginTransaction();
			TypedQuery<?> query = session.createNativeQuery(SQL_PARAMETROS_TAREAS_INDIVIDUAL);
			query.setParameter(1, descripcion[3]);
			result = (List<Object[]>) query.getResultList();
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> ConteoActividadesUsuario(String usuario) {
		List<Object[]> result = null;
		Session session = factory.openSession();
		try {
			tx = session.beginTransaction();
			TypedQuery<?> query = session.createNativeQuery(SQL_CONTEO_ACTIVIDADES);
			query.setParameter(1, usuario);
			query.setParameter(2, usuario);
			result = (List<Object[]>) query.getResultList();
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return result;

	}

	@SuppressWarnings("unchecked")
	public List<Object[]> ConsultaPrimariosProcesoCatastral(String idProceso) {
		List<Object[]> result = null;
		Session session = factory.openSession();
		try {
			tx = session.beginTransaction();
			TypedQuery<?> query = session.createNativeQuery(SQL_VARIABLE_LOG_CATASTRAL);
			query.setParameter(1, idProceso);
			result = (List<Object[]>) query.getResultList();

		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> ConsultaPrimariosProcesoConservacion(String idProceso) {
		List<Object[]> result = null;
		Session session = factory.openSession();
		try {
			tx = session.beginTransaction();
			TypedQuery<?> query = session.createNativeQuery(SQL_VARIABLE_LOG_CONSERVACION);
			query.setParameter(1, idProceso);
			result = (List<Object[]>) query.getResultList();

		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return result;
	}


	@SuppressWarnings("unchecked")
	public List<Object[]> ListaTareas(String usuario, String territorial, String description) {

		List<Object[]> result = null;
		Session session = factory.openSession();
		try {
			tx = session.beginTransaction();
			TypedQuery<?> query = session.createNativeQuery(SQL_LISTA_TAREAS);

			query.setParameter(1, usuario);
			query.setParameter(2, usuario);
			query.setParameter(3, territorial);
			query.setParameter(4, description);
			result = (List<Object[]>) query.getResultList();
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> consultaDespliegue(String idtarea) {
		List<Object[]> result = null;
		Session session = factory.openSession();
		try {
			session.beginTransaction();

			TypedQuery<?> query = session.createNativeQuery("select DEPLOYMENTID,PROCESSID from TASK WHERE ID=?");
			query.setParameter(1, idtarea);
			result = (List<Object[]>) query.getResultList();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> consultaParametrosTarea(String nombreActividad) {

		List<Object[]> result = null;
		Session session = factory.openSession();

		try {
			tx = session.beginTransaction();
			TypedQuery<?> query = session.createNativeQuery(SQL_PARAMETROS_TAREAS);
			query.setParameter(1, nombreActividad);
			result = (List<Object[]>) query.getResultList();

		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return result;
	}

	public Users buscaUsuario(String username) {
		Session session = factory.openSession();
		Users user = null;
		try {
			tx = session.beginTransaction();
			TypedQuery<?> query = session.getNamedQuery("Users.findByUsername");
			query.setParameter("username", username);
			user = (Users) query.getSingleResult();

		} catch (NoResultException e) {
			LOGGER.info("Se procede a crear el usuario");
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return user;

	}

	@SuppressWarnings("unchecked")
	public List<BigDecimal> busaGrupo(String groupname) {

		Session session = factory.openSession();
		List<BigDecimal> grupos = null;
		try {
			tx = session.beginTransaction();
			TypedQuery<?> query = session.getNamedQuery("Groups.findByGroupname");
			query.setParameter("groupname", groupname);
			grupos = (List<BigDecimal>) query.getResultList();

		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return grupos;
	}


	public Users creaUsuario(Users user, List<BigDecimal> grupos) {

		Session session = factory.openSession();
		try {
			tx = session.beginTransaction();
			TypedQuery<?> query = session.createNativeQuery("INSERT INTO USERS VALUES(NULL,?,?)");
			query.setParameter(1, user.getUsername().toString());
			query.setParameter(2, user.getUsername().toString());

			if (query.executeUpdate() == 1) {
				query = session.createNativeQuery("SELECT MAX(ID) FROM USERS");
				user.setId((BigDecimal) query.getSingleResult());
				for (int i = 0; i < grupos.size(); i++) {
					query = session.createNativeQuery("INSERT INTO USERGROUPS VALUES(?,?)");
					query.setParameter(1, user.getId().toString());
					query.setParameter(2, grupos.get(i).toString());
					query.executeUpdate();

				}
			}
			tx.commit();

		} catch (HibernateException e) {
			e.printStackTrace();

		} finally {
			session.close();
		}

		return user;
	}

	public List<String> adaptadorServicios(Long idTramite) {
		List<String> salida = new ArrayList<>();

		Session session = factorysnc.openSession();

		try {
			tx = session.beginTransaction();
			TypedQuery<?> sql = session.createNativeQuery(ARCHIVAR_TRAMITE);
			sql.setParameter(1, "ARCHIVADO1");
			sql.setParameter(2, "si");
			sql.setParameter(3, idTramite);
			int resultado = sql.executeUpdate();

			if (resultado == 1) {

				TramiteEstado tramiteEstado = new TramiteEstado();
				tramiteEstado.setEstado("ARCHIVADO1");
				tramiteEstado.setFechaInicio(new Date());
				tramiteEstado.setFechaLog(new Date());
				tramiteEstado.setMotivo("ARCHIVADO");
				tramiteEstado.setObservaciones("Creado automáticamente por Api Server JBPM");
				tramiteEstado.setResponsable("JBPM");
				tramiteEstado.setTramiteId(new BigDecimal(idTramite));
				tramiteEstado.setUsuarioLog("snc_tiempos_muertos");
				try {
					session.save(tramiteEstado);
					tx.commit();
					salida.add("ok");
				} catch (HibernateException e) {
					salida.add("error");
					salida.add("Imposible obtener el tipo de trámite de id:" + idTramite);
					e.printStackTrace();

					LOGGER.info("Imposible crear el registro en Tramite_Estado");
				}

			} else {
				salida.add("error");
				salida.add("Imposible obtener el tipo de trámite de id:" + idTramite);
				LOGGER.info("Imposible modificar el registro en Tramite de id: " + idTramite);
			}
		} catch (HibernateException e) {
			salida.add("error");
			salida.add("Imposible obtener el tipo de trámite de id:" + idTramite);
		} finally {
			session.close();
		}
		return salida;
	}

	public String adaptadorTramites(Long id) {

		String resultado = null;
		Session session = factorysnc.openSession();
		tx = session.beginTransaction();
		try {
			TypedQuery<?> query = session.createNativeQuery(SQL_NOMBRE_TRAMITE);
			query.setParameter(1, id);
			resultado = query.getSingleResult().toString();
			resultado.replaceAll("\\s+", " ");
			tx.commit();
		} catch (NoResultException e) {

			// e.printStackTrace();
			resultado = "error";

		} finally {
			session.close();
		}
		return resultado;
	}

	public Tramite tramitePorId(long idTramite) {
		Session session = factorysnc.openSession();
		Tramite tramite = session.find(Tramite.class, idTramite);
		session.close();
		return tramite;
	}

	public Predio predioPorId(long idPredio) {
		Session session = factorysnc.openSession();
		Predio predio = session.find(Predio.class, idPredio);
		session.close();
		return predio;
	}
	@SuppressWarnings("unchecked")
	public List<Object[]> idTarea(String idproceso) {

		Session session = factory.openSession();
		tx = session.beginTransaction();
		List<Object[]> resultado=null;
		try {

			TypedQuery<?> query = session.createNativeQuery(SQL_BUSCA_ACTIVIDA_POR_PROCESO);

			query.setParameter(1, idproceso);

			resultado= (List<Object[]>) query.getResultList();

		} catch (NoResultException e) {

			e.printStackTrace();

		}finally {
			session.close();
		}

		return resultado;
	}

	public Transaction getTx() {
		return tx;
	}

	public void setTx(Transaction tx) {
		this.tx = tx;
	}
}

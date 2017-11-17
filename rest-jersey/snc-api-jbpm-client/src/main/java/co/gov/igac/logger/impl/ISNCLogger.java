package co.gov.igac.logger.impl;

import co.gov.igac.entity.Users;

/**
 * Interfaz de especificación de la gestión de logging de los artefactos del
 * Sistema Nacional Catastral. Debido a que la librería slf4j no tiene asociado
 * el nivel de log fatal, se define esta interfaz con los mismos niveles de log
 * y se agrega este nivel necesario para especificar errores como los de bases de
 * datos.
 * 
 * La interfaz está basada en la especificada en la clase Logger especificada en la
 * librería slf4j 
 * 
 * 
 * @author alejandro.sanchez
 *
 */
public interface ISNCLogger {
	
	/**
	 * Niveles de log manejados en el Sistema Nacional Catastral.
	 * 
	 */
	public enum ESNCLoggerLevel {
		TRACE,
		DEBUG, 
		INFO,
		WARN,
		ERROR,
		FATAL;		
	}
	
	/**
	 * Escribe mensaje con nivel TRACE en el log.
	 * 
	 * @param msg Mensaje que se registrará en el log del artefacto u aplicación.
	 */
	public void trace(String msg);
	
	/**
	 *  Registra mensaje con nivel TRACE en el log.
	 * 
	 * @param msg Mensaje que se registrará en el log del artefacto u aplicación.
	 * @param usuario Usuario al que pertenece la sesión de aplicación.
	 * @param id Identificador del trámite catastral con el que se produce este registro de log. 
	 */
	public void trace(String msg, Users usuario, long id);

	/**
	 * 
	 * Registra una excepción (throwable) con nivel TRACE en el log junto con un 
	 * mensaje.
	 * 
	 * @param msg Mensaje que se registrará en el log del artefacto u aplicación.
	 *            the message accompanying the exception
	 * @param t Excepción nativa o de negocio (throwable) to log
	 * 
	 */
	public void trace(String msg, Throwable t);
		
	/**
	 * 
	 * Registra una excepción (throwable) con nivel TRACE en el log junto con un 
	 * mensaje.
	 * 
	 * @param msg Mensaje que se registrará en el log del artefacto u aplicación.
	 *            the message accompanying the exception
	 * @param t Excepción nativa o de negocio (throwable) to log.	  
	 * @param usuario Usuario al que pertenece la sesión de aplicación.	 
	 * @param id Identificador del trámite catastral con el que se produce este registro de log. 
	 * 
	 */
	public void trace(String msg, Throwable t, Users usuario, long id);
	
	/**
	 * Escribe mensaje con nivel DEBUG en el log.
	 * 
	 * @param msg Mensaje que se registrará en el log del artefacto u aplicación.
	 */
	public void debug(String msg);
	
	/**
	 *  Registra mensaje con nivel DEBUG en el log.
	 * 
	 * @param msg Mensaje que se registrará en el log del artefacto u aplicación.
	 * @param usuario Usuario al que pertenece la sesión de aplicación.
	 * @param id Identificador del trámite catastral con el que se produce este registro de log. 
	 */
	public void debug(String msg, Users usuario, long id);

	/**
	 * 
	 * Registra una excepción (throwable) con nivel DEBUG en el log junto con un 
	 * mensaje.
	 * 
	 * @param msg Mensaje que se registrará en el log del artefacto u aplicación.
	 *            the message accompanying the exception
	 * @param t Excepción nativa o de negocio (throwable) to log
	 * 
	 */
	public void debug(String msg, Throwable t);
	
	/**
	 * 
	 * Registra una excepción (throwable) con nivel DEBUG en el log junto con un 
	 * mensaje.
	 * 
	 * @param msg Mensaje que se registrará en el log del artefacto u aplicación.
	 *            the message accompanying the exception
	 * @param t Excepción nativa o de negocio (throwable) to log.	  
	 * @param usuario Usuario al que pertenece la sesión de aplicación.	 
	 * @param id Identificador del trámite catastral con el que se produce este registro de log. 
	 * 
	 */
	public void debug(String msg, Throwable t, Users usuario, long id);

	/**
	 * Escribe mensaje con nivel INFO en el log.
	 * 
	 * @param msg Mensaje que se registrará en el log del artefacto u aplicación.
	 */
	public void info(String msg);
	
	/**
	 *  Registra mensaje con nivel INFO en el log.
	 * 
	 * @param msg Mensaje que se registrará en el log del artefacto u aplicación.
	 * @param usuario Usuario al que pertenece la sesión de aplicación.
	 * @param id Identificador del trámite catastral con el que se produce este registro de log. 
	 */	
	public void info(String msg, Users usuario, long id);

	/**
	 * 
	 * Registra una excepción (throwable) con nivel INFO en el log junto con un 
	 * mensaje.
	 * 
	 * @param msg Mensaje que se registrará en el log del artefacto u aplicación.
	 *            the message accompanying the exception
	 * @param t Excepción nativa o de negocio (throwable) to log
	 * 
	 */
	public void info(String msg, Throwable t);
	
	
	/**
	 * 
	 * Registra una excepción (throwable) con nivel INFO en el log junto con un 
	 * mensaje.
	 * 
	 * @param msg Mensaje que se registrará en el log del artefacto u aplicación.
	 *            the message accompanying the exception
	 * @param t Excepción nativa o de negocio (throwable) to log.	  
	 * @param usuario Usuario al que pertenece la sesión de aplicación.	 
	 * @param id Identificador del trámite catastral con el que se produce este registro de log. 
	 * 
	 */
	public void info(String msg, Throwable t, Users usuario, long id);

	/**
	 * Escribe mensaje con niveL WARNING en el log.
	 * 
	 * @param msg Mensaje que se registrará en el log del artefacto u aplicación.
	 */
	public void warn(String msg);
	
	/**
	 *  Registra mensaje con nivel WARNING en el log.
	 * 
	 * @param msg Mensaje que se registrará en el log del artefacto u aplicación.
	 * @param usuario Usuario al que pertenece la sesión de aplicación.
	 * @param id Identificador del trámite catastral con el que se produce este registro de log. 
	 */		
	public void warn(String msg, Users usuario, long id);

	/**
	 * 
	 * Registra una excepción (throwable) con nivel WARNING en el log junto con un 
	 * mensaje.
	 * 
	 * @param msg Mensaje que se registrará en el log del artefacto u aplicación.
	 *            the message accompanying the exception
	 * @param t Excepción nativa o de negocio (throwable) to log
	 */
	public void warn(String msg, Throwable t);
	
	/**
	 * 
	 * Registra una excepción (throwable) con nivel WARNING en el log junto con un 
	 * mensaje.
	 * 
	 * @param msg Mensaje que se registrará en el log del artefacto u aplicación.
	 *            the message accompanying the exception
	 * @param t Excepción nativa o de negocio (throwable) to log.	  
	 * @param usuario Usuario al que pertenece la sesión de aplicación.	 
	 * @param id Identificador del trámite catastral con el que se produce este registro de log. 
	 * 
	 */
	public void warn(String msg, Throwable t, Users usuario, long id);

	/**
	 * Escribe mensaje con niveL ERROR en el log.
	 * 
	 * @param msg Mensaje que se registrará en el log del artefacto u aplicación.
	 */
	public void error(String msg);
	
	/**
	 *  Registra mensaje con nivel ERROR en el log.
	 * 
	 * @param msg Mensaje que se registrará en el log del artefacto u aplicación.
	 * @param usuario Usuario al que pertenece la sesión de aplicación.
	 * @param id Identificador del trámite catastral con el que se produce este registro de log. 
	 */		
	public void error(String msg, Users usuario, long id);

	/**
	 * 
	 * Registra una excepción (throwable) con nivel ERROR en el log junto con un 
	 * mensaje.
	 * 
	 * @param msg Mensaje que se registrará en el log del artefacto u aplicación.
	 *            the message accompanying the exception
	 * @param t Excepción nativa o de negocio (throwable) to log
	 */
	public void error(String msg, Throwable t);
	
	/**
	 * 
	 * Registra una excepción (throwable) con nivel ERROR en el log junto con un 
	 * mensaje.
	 * 
	 * @param msg Mensaje que se registrará en el log del artefacto u aplicación.
	 *            the message accompanying the exception
	 * @param t Excepción nativa o de negocio (throwable) to log.	  
	 * @param usuario Usuario al que pertenece la sesión de aplicación.	 
	 * @param id Identificador del trámite catastral con el que se produce este registro de log. 
	 * 
	 */
	public void error(String msg, Throwable t, Users usuario, long id);
	
	/**
	 * Escribe mensaje con niveL FATAL en el log.
	 * 
	 * @param msg Mensaje que se registrará en el log del artefacto u aplicación.
	 */
	public void fatal(String msg);
	
	/**
	 *  Registra mensaje con nivel FATAL en el log.
	 * 
	 * @param msg Mensaje que se registrará en el log del artefacto u aplicación.
	 * @param usuario Usuario al que pertenece la sesión de aplicación.
	 * @param id Identificador del trámite catastral con el que se produce este registro de log. 
	 */		
	public void fatal(String msg, Users usuario, long id);
	
	
	/**
	 * 
	 * Registra una excepción (throwable) con nivel FATAL en el log junto con un 
	 * mensaje.
	 * 
	 * @param msg Mensaje que se registrará en el log del artefacto u aplicación.
	 *            the message accompanying the exception
	 * @param t Excepción nativa o de negocio (throwable) to log
	 */
	public void fatal(String msg, Throwable t);
	
	/**
	 * 
	 * Registra una excepción (throwable) con nivel FATAL en el log junto con un 
	 * mensaje.
	 * 
	 * @param msg Mensaje que se registrará en el log del artefacto u aplicación.
	 *            the message accompanying the exception
	 * @param t Excepción nativa o de negocio (throwable) to log.	  
	 * @param usuario Usuario al que pertenece la sesión de aplicación.	 
	 * @param id Identificador del trámite catastral con el que se produce este registro de log. 
	 * 
	 */
	public void fatal(String msg, Throwable t, Users usuario, long id);
	
}

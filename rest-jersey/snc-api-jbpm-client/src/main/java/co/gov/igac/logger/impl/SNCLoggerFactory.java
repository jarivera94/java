package co.gov.igac.logger.impl;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.log4j.LogManager;




/**
 * Fabrica que permite crear instancias del Logger del SNC.
 * 
 * 
 * @author alejandro.sanchez
 *
 */
public class SNCLoggerFactory {
	
	private static ConcurrentMap<String, SNCSlf4jLog4jLogger> LOGGER_MAP = new ConcurrentHashMap<String, SNCSlf4jLog4jLogger>();
	
	/**
	 * Implementaciones de logging soportadas
	 * 
	 * 
	 */
	public enum ESNCLoggerType
	{
		SLF4J_LOG4J
	};
	
	/**
	 * Obtiene una instancia del logger a partir del nombre de la clase especificado.
	 * 
	 * @param name Un nombre de clase.
	 * 
	 * @return Una instancia del logger.
	 */
	public static ISNCLogger getLogger(String name)
	{
		SNCSlf4jLog4jLogger slf4jLogger = LOGGER_MAP.get(name);
	    if (slf4jLogger != null)
	      return slf4jLogger;
	    org.apache.log4j.Logger log4jLogger;
	    if (name.equalsIgnoreCase("ROOT"))
	      log4jLogger = LogManager.getRootLogger();
	    else {
	      log4jLogger = LogManager.getLogger(name);
	    }
	    SNCSlf4jLog4jLogger newInstance = new SNCSlf4jLog4jLogger(log4jLogger);
	    SNCSlf4jLog4jLogger oldInstance = LOGGER_MAP.putIfAbsent(name, newInstance);
	    return oldInstance == null ? newInstance : oldInstance;
	}
	
	/**
	 * Obtiene una instancia del logger a partir del nombre de la clase especificado.
	 * 
	 * @param clase 
	 * 
	 * @return Una instancia del logger.
	 */
	public static ISNCLogger getLogger(Class<?> clase)
	{
		return getLogger(clase.getName());
	}
	
	/**
	 * Obtiene una instancia del logger a partir del nombre de la clase especificado.
	 * 
	 * @param name Un nombre de clase.
	 * @param tipoLogger Tipo de implementación de logger. Sólo soporta SLF4J_LOG4J.
	 * @return Una instancia del logger.
	 */
	public static ISNCLogger getLogger(String name, ESNCLoggerType tipoLogger)
	{
		ISNCLogger logger = null;
		switch (tipoLogger)
		{
		case SLF4J_LOG4J:
			logger = getLogger(name);
			break;			
		}
		return logger;
	}
	
	/**
	 * 
	 * Obtiene una instancia del logger a partir del nombre de la clase especificado.
	 * 
	 * @param clase Una clase.
	 * @param tipoLogger Tipo de implementación de logger. Sólo soporta SLF4J_LOG4J.
	 * @return
	 */
	public static ISNCLogger getLogger(Class<?> clase, ESNCLoggerType tipoLogger)
	{
		ISNCLogger logger = null;
		switch (tipoLogger)
		{
		case SLF4J_LOG4J:
			logger = getLogger(clase.getName());
			break;			
		}
		return logger;
	}
}

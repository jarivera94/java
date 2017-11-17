package co.gov.igac.sigc.excepcion;

import java.io.Serializable;

import co.gov.igac.logger.impl.ISNCLogger;

public interface ISNCExceptionHandler extends Serializable{
	
	public static final String PLANTILLA_MENSAJE_PROCESO = " del proceso de tipo {0} con identificador de negocio {1}";

	/**
	 * Registra en el log la excepción y retorna una excepción de negocio.
	 * 
	 * @param logger
	 *            logger de la clase que lanza la excepción.
	 * @param params
	 *            parámetros necesarios para construir el mensaje.
	 * 
	 * @return excepción de negocio creada a partir de los parámetros
	 *         especificados.
	 */
	public ExcepcionSNC getExcepcion(ISNCLogger logger, Object... params);

	/**
	 * Registra en el log la excepción y retorna una excepción de negocio.
	 * 
	 * @param logger
	 *            logger de la clase que lanza la excepción.
	 * 
	 * @param excepcionNativa
	 *            excepción nativa o de una librería de terceros.
	 * 
	 * @param params
	 *            parámetros necesarios para construir el mensaje.
	 * 
	 * @return excepción de negocio creada a partir de los parámetros
	 *         especificados.
	 */
	public ExcepcionSNC getExcepcion(ISNCLogger logger, Exception excepcionNativa,
			Object... params);

	/**
	 * Registra en el log la excepción y retorna una excepción de negocio.
	 * 
	 * @param logger
	 *            logger de la clase que lanza la excepción.
	 * 
	 * @param throwable
	 *            throwable nativo o de una librería de terceros. .
	 * 
	 * @param params
	 *            parámetros necesarios para construir el mensaje.
	 * 
	 * @return excepción de negocio creada a partir de los parámetros
	 *         especificados.
	 */
	public ExcepcionSNC getExcepcion(ISNCLogger logger, Throwable throwable,
			Object... params);

	/**
	 * Registra en el log la excepción y retorna una excepción de negocio.
	 * 
	 * @param logger
	 *            logger de la clase que lanza la excepción.
	 * 
	 * @param excepcionNativa
	 *            excepción nativa de negocio.
	 * 
	 * @param params
	 *            parámetros necesarios para construir el mensaje.
	 * 
	 * @return excepción de negocio creada a partir de los parámetros
	 *         especificados.
	 */
	public ExcepcionSNC getExcepcion(ISNCLogger logger,
			ExcepcionSNC excepcionNativa, Object... params);
}

package co.gov.igac.sigc.excepcion;

import java.io.Serializable;
import java.text.MessageFormat;

import co.gov.igac.logger.impl.ISNCLogger;




public final class SNCExceptionFactory implements Serializable {

	/**
	 * Identificador único para garantizar serialización de la clase.
	 */
	private static final long serialVersionUID = -3855838847778932297L;

	/**
	 * Registra en el log la excepción y retorna una excepción de negocio.
	 * 
	 * @param plantillaExcepcionSNC
	 *            Objeto con el código de excepción, la severidad y la plantilla
	 *            del mensaje de excepción.
	 * @param logger
	 *            logger de la clase que lanza la excepción.
	 * @param params
	 *            parámetros necesarios para construir el mensaje.
	 * 
	 * @return excepción de negocio creada a partir de los parámetros
	 *         especificados.
	 */
	public static ExcepcionSNC getExcepcion(ExcepcionSNC plantillaExcepcionSNC, ISNCLogger logger, Object... params) {
		String mensaje = construirMensaje(plantillaExcepcionSNC, params);
//		LoggerDelegator.log(logger, plantillaExcepcionSNC.getSeveridadExcepcion(), mensaje);
		return new ExcepcionSNC(plantillaExcepcionSNC.getCodigoExcepcion(),
				plantillaExcepcionSNC.getSeveridadExcepcion(), mensaje);
	}

	/**
	 * Registra en el log la excepción y retorna una excepción de negocio.
	 * 
	 * @param plantillaExcepcionSNC
	 *            Objeto con el código de excepción, la severidad y la plantilla
	 *            del mensaje de excepción.
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
	public static ExcepcionSNC getExcepcion(ExcepcionSNC plantillaExcepcionSNC, ISNCLogger logger,
			Exception excepcionNativa, Object... params) {
		String mensaje = construirMensaje(plantillaExcepcionSNC, params);
//		LoggerDelegator.log(logger, plantillaExcepcionSNC.getSeveridadExcepcion(), mensaje, excepcionNativa);
		return new ExcepcionSNC(plantillaExcepcionSNC.getCodigoExcepcion(),
				plantillaExcepcionSNC.getSeveridadExcepcion(), mensaje, excepcionNativa);
	}

	/**
	 * Registra en el log la excepción y retorna una excepción de negocio.
	 * 
	 * @param plantillaExcepcionSNC
	 *            Objeto con el código de excepción, la severidad y la plantilla
	 *            del mensaje de excepción.
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
	public static ExcepcionSNC getExcepcion(ExcepcionSNC plantillaExcepcionSNC, ISNCLogger logger, Throwable throwable,
			Object... params) {
		String mensaje = construirMensaje(plantillaExcepcionSNC, params);
//		LoggerDelegator.log(logger, plantillaExcepcionSNC.getSeveridadExcepcion(), mensaje, throwable);
		return new ExcepcionSNC(plantillaExcepcionSNC.getCodigoExcepcion(),
				plantillaExcepcionSNC.getSeveridadExcepcion(), mensaje, throwable);
	}

	/**
	 * Registra en el log la excepción y retorna una excepción de negocio.
	 * 
	 * @param plantillaExcepcionSNC
	 *            Objeto con el código de excepción, la severidad y la plantilla
	 *            del mensaje de excepción.
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
	public static ExcepcionSNC getExcepcion(ExcepcionSNC plantillaExcepcionSNC, ISNCLogger logger,
			ExcepcionSNC excepcionNativa, Object... params) {
		String mensaje = construirMensaje(plantillaExcepcionSNC, params);
//		LoggerDelegator.log(logger, plantillaExcepcionSNC.getSeveridadExcepcion(), mensaje, excepcionNativa);
		return new ExcepcionSNC(plantillaExcepcionSNC.getCodigoExcepcion(),
				plantillaExcepcionSNC.getSeveridadExcepcion(), mensaje, excepcionNativa);
	}

	/**
	 * Construye el mensaje de la excepción incluyendo los parámetros
	 * especificados.
	 * 
	 * @param params
	 *            Valores para los parámetros que recibe el mensaje de error.
	 * 
	 * @return Mensaje de la excepción personalizado con los valores
	 *         especificados.
	 */
	private static String construirMensaje(ExcepcionSNC plantillaExcepcionSNC, final Object... params) {
		return params != null ? MessageFormat.format(plantillaExcepcionSNC.getMessage(), params)
				: plantillaExcepcionSNC.getMessage();
	}

}

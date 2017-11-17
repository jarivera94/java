package co.gov.igac.sigc.excepcion;

import javax.ejb.ApplicationException;


/**
 * @author alejandro.sanchez
 * @version sigc-comun 1.0
 * @created 31-Mayo-2011 9:10:11 AM
 * @modified 25-Marzo-2014 4:07:15 AM
 * 
 * <br>
 *          @ApplicationException</br> Esta anotación indica que esta excepción
 *          puede utilizarse en la interfaz de un EJB. Un RuntimeException se
 *          manejará como una excepción normal, es decir, provocaría una
 *          finalización prematura en un mótodo pero no se tendrán más
 *          consecuencias.
 * 
 *          rollback=true indica que una transacción activa es revocada al
 *          producirse esta excepción; un caso práctico es cuando se debe hacer
 *          una inserción en varias tablas de una base de datos,en este caso si
 *          se produce una excepción, se esperaría que no agregaran registros a
 *          las tablas de la base de datos.
 * 
 *          Por defecto rollback es <br>
 *          false</br>.
 *
 * Extiende RuntimeException para que no haya que cambiar los métodos para decir que lanzan esa
 * excepción
 */
@ApplicationException(rollback=true)
public class ExcepcionTransaccionSNC extends ExcepcionSNC{

	private static final long serialVersionUID = -1571309021814315845L;
	
	/**
	 * 
	 * @param codigoExcepcion
	 * @param severidadDeExcepcion
	 * @param mensaje
	 */
	protected ExcepcionTransaccionSNC(String codigoExcepcion,
			String severidadDeExcepcion, String mensaje) {
		super(codigoExcepcion, severidadDeExcepcion, mensaje);
	}
	
	/**
	 * 
	 * @param codigoExcepcion
	 * @param severidadDeExcepcion
	 * @param mensaje
	 */
	protected ExcepcionTransaccionSNC(String codigoExcepcion,
			String severidadDeExcepcion, String mensaje, Exception excepcion) {
		super(codigoExcepcion, severidadDeExcepcion, mensaje, excepcion);
	}
	
	/**
	 * 
	 * @param codigoExcepcion
	 * @param severidadDeExcepcion
	 * @param mensaje
	 */
	protected ExcepcionTransaccionSNC(String codigoExcepcion,
			String severidadDeExcepcion, String mensaje, Throwable throvable) {
		super(codigoExcepcion, severidadDeExcepcion, mensaje, throvable);
	}
	
	protected ExcepcionTransaccionSNC(String codigoExcepcion,
			String severidadDeExcepcion, String mensaje, String mensajeUsuario) {
		super(codigoExcepcion, severidadDeExcepcion, mensaje, mensajeUsuario);
	}
}

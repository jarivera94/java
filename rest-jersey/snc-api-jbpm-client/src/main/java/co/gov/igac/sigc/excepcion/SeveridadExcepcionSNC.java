package co.gov.igac.sigc.excepcion;


/**
 * @author alejandro.sanchez
 * @version sigc-comun 1.0
 * @created 31-Mayo-2010 9:10:11 AM
 * TODO Debe cambiarse el nombre de la clase a SeveridadExcepcionSNC.
 */
public class SeveridadExcepcionSNC {
	
	/**
	 * En el caso que la excepción no cause fallos en el componente del sistema y siga funcionando sin
	 * problemas. Esta severidad debe usarse en caso que la excepción presentada pueda afectar el 
	 * resultado final que el usuario o cliente del sistema está esperando.
	 */
	public static final String ADVERTENCIA = "ADVERTENCIA";
	/**
	 * En el caso que la excepción no cause fallos en el componente del sistema y siga funcionando sin
	 * problemas. Esta severidad debe usarse en caso que la excepción presentada afecte  considerablemente 
	 * el resultado final que el usuario o cliente del sistema está esperando.
	 */
	public static final String ERROR = "ERROR";
	
	/**
	 * En el caso que la excepción sea reflejo de un fallo en el componente del sistema, y no sea capaz
	 * de dar el resultado esperado no sólo a la petición actual, sino a otras peticiones.
	 */
	public static final String FATAL = "FATAL";
	
	
	
}
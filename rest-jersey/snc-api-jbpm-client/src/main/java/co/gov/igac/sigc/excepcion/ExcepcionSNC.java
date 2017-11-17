package co.gov.igac.sigc.excepcion;

import java.io.Serializable;
import java.text.MessageFormat;

public class ExcepcionSNC extends Exception implements Serializable {

	/**
	 * Identificar único para la clase <class>ExcepcionSNC</class>
	 */
	private static final long serialVersionUID = -1195105817814223485L;

	/**
	 * Define la severidad de la excepción, las tipos de severidad se encuentran
	 * definidos en {@link SeveridadExcepcionSNC}.
	 */
	private String severidadDeExcepcion;

	private static final String FORMATO_MENSAJE = "{0}";

	/**
	 * Corresponde a una cadena que permite identificar de manera inequívoca el
	 * lugar en el código donde se produjo la excepción. De acuerdo a la
	 * siguiente convención:
	 * 
	 * Caracteres 1 y 2: Literal que representa el compontente o capa del
	 * sistema (A-Z). Caracteres 3 y 4: Numeral que representa el módulo del
	 * sistema (01 a 99). Caracteres 5 a 8: Numeral que representa la
	 * localización del error.
	 * 
	 * Por ejemplo:
	 * 
	 * CP110001: Capa o componente de procesos, API procesos (0001 representa el
	 * lugar donde se produce la primera excepción agregada en el API de
	 * procesos). CP120001: Capa o componente de procesos, API tareas (0001
	 * representa el lugar donde se produce la primera excepción agregada en el
	 * API de tareas). CP200003: Capa o componente de procesos, API tareas (0001
	 * representa el lugar donde se produce la tercera excepción agregada en el
	 * proceso de Conservación implementado en el WID de IBM).
	 */
	protected String codigoExcepcion;
	
	/**
     * Define el mensaje que debe mostrarse al usuario en la capa de presentación.
     * 
     * 
     * Este mensaje debe definirse en un lenguaje de fácil entendimiento para los usuarios. Se recomienda definirlo en la capa de presentación o en la capa de mayor nivel donde se presente la excepción.
     */
    private String mensajeUsuario;

	/**
	 * Constructor para creación de una excepción de negocio que no tiene
	 * asociada una excepción nativa.
	 * 
	 * @param codigoExcepcion
	 *            Código de la excepción que permite identificar la capa o
	 *            componente y el módulo donde se produjo la excepción.
	 * @param severidadDeExcepcion
	 *            Severidad de la excepción de acuerdo a las convenciones
	 *            definidas en {@link SeveridadExcepcionSNC}
	 * @param mensaje
	 *            Mensaje asociado a la excepción. Este mensaje puede requerir
	 *            formato, de ser así entonces debe usarse el méto
	 *            {@link getExcepcion} con formato, es decir: getExcepcion(
	 *            Logger logger, Exception excepcionNativa, Object... params )
	 */
	protected ExcepcionSNC(String codigoExcepcion,
			String severidadDeExcepcion, String mensaje) {
		super(mensaje);
		this.severidadDeExcepcion = severidadDeExcepcion;
		this.codigoExcepcion = codigoExcepcion;
	}
	
	protected ExcepcionSNC(String codigoExcepcion,
			String severidadDeExcepcion, String mensaje, String mensajeUsuario) {
		super(mensaje);
		this.severidadDeExcepcion = severidadDeExcepcion;
		this.codigoExcepcion = codigoExcepcion;
		this.mensajeUsuario = mensajeUsuario;
	}

	protected ExcepcionSNC(String codigoExcepcion,
			String severidadDeExcepcion, String mensaje,
			Exception excepcion) {
		super(mensaje, excepcion);		
		this.severidadDeExcepcion = severidadDeExcepcion;
		this.codigoExcepcion = codigoExcepcion;		
	}
	
	protected ExcepcionSNC(String codigoExcepcion,
			String severidadDeExcepcion, String mensaje,
			Throwable throvable) {
		super(mensaje, throvable);		
		this.severidadDeExcepcion = severidadDeExcepcion;
		this.codigoExcepcion = codigoExcepcion;		
	}

	/**
	 * Retorna el código de la excepción, el .
	 * 
	 * @return
	 */
	public String getCodigoExcepcion() {
		return codigoExcepcion;
	}

	/**
	 * Retorna el código que indica la severidad de la excepción
	 * {@link SeveridadExcepcionSNC}
	 * 
	 * @return Severidad de la excepción.
	 */
	public String getSeveridadExcepcion() {
		return severidadDeExcepcion;
	}

	@Override
	public String getMessage() {
		return MessageFormat.format(FORMATO_MENSAJE, super.getMessage());
	}

	/**
	 * Obtiene el mensaje de usuario que será mostrado al momento de presentarse el error.
	 * 
	 * @return Mensaje de error para el usuario
	 */
	public String getMensajeUsuario() {
		return mensajeUsuario;
	}

	/**
	 * Establece el mensaje de usuario que será desplegado en la capa de presentación.
	 * 
	 * @param mensajeUsuario Mensaje que se mostrará al usuario.
	 */
	public void setMensajeUsuario(String mensajeUsuario) {
		this.mensajeUsuario = mensajeUsuario;
	}		
}
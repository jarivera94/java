package co.gov.igac.sigc.excepcion;

public final class SNCExceptionTemplateFactory {
	
	public enum ESNCExceptionLevel
	{
		ADVERTENCIA(SeveridadExcepcionSNC.ADVERTENCIA), 
		ERROR(SeveridadExcepcionSNC.ERROR),
		FATAL(SeveridadExcepcionSNC.FATAL);
		
		String severidad;		
		ESNCExceptionLevel(String severidad)
		{
			this.severidad = severidad;
		}
		public String getSeveridad() {
			return severidad;
		}		
	}

	public static ExcepcionSNC getExcepcionSNC(String codigoExcepcion,
			ESNCExceptionLevel severidadDeExcepcion, String mensaje) {
		return new ExcepcionSNC(codigoExcepcion, severidadDeExcepcion.toString(), mensaje);		
	}
	
	public static ExcepcionSNC getExcepcionSNC(String codigoExcepcion,
			ESNCExceptionLevel severidadDeExcepcion, String mensaje, String mensajeUsuario) {
		return new ExcepcionSNC(codigoExcepcion, severidadDeExcepcion.toString(), mensaje, mensajeUsuario);
	}

	public static ExcepcionSNC getExcepcionSNC(String codigoExcepcion,
			ESNCExceptionLevel severidadDeExcepcion, String mensaje,
			Exception excepcion) {
		return new ExcepcionSNC(codigoExcepcion, severidadDeExcepcion.toString(), mensaje, excepcion);
	}
	
	public static ExcepcionSNC getExcepcionSNC(String codigoExcepcion,
			ESNCExceptionLevel severidadDeExcepcion, String mensaje,
			Throwable throwable) {
		return new ExcepcionSNC(codigoExcepcion, severidadDeExcepcion.toString(), mensaje, throwable);
	}
	
	public static ExcepcionTransaccionSNC getExcepcionTransaccionSNC(String codigoExcepcion,
			ESNCExceptionLevel severidadDeExcepcion, String mensaje) {
		return new ExcepcionTransaccionSNC(codigoExcepcion, severidadDeExcepcion.toString(), mensaje);		
	}
	
	public static ExcepcionTransaccionSNC getExcepcionTransaccionSNC(String codigoExcepcion,
			ESNCExceptionLevel severidadDeExcepcion, String mensaje, String mensajeUsuario) {
		return new ExcepcionTransaccionSNC(codigoExcepcion, severidadDeExcepcion.toString(), mensaje, mensajeUsuario);
	}

	public static ExcepcionTransaccionSNC getExcepcionTransaccionSNC(String codigoExcepcion,
			String severidadDeExcepcion, String mensaje,
			Exception excepcion) {
		return new ExcepcionTransaccionSNC(codigoExcepcion, severidadDeExcepcion.toString(), mensaje, excepcion);
	}
	
	public static ExcepcionTransaccionSNC getExcepcionTransaccionSNC(String codigoExcepcion,
			ESNCExceptionLevel severidadDeExcepcion, String mensaje,
			Throwable throwable) {
		return new ExcepcionTransaccionSNC(codigoExcepcion, severidadDeExcepcion.toString(), mensaje, throwable);
	}
}

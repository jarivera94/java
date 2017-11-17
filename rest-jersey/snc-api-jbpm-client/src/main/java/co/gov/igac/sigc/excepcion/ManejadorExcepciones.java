package co.gov.igac.sigc.excepcion;


import co.gov.igac.logger.impl.ISNCLogger;
import co.gov.igac.sigc.excepcion.ExcepcionSNC;
import co.gov.igac.sigc.excepcion.ISNCExceptionHandler;
import co.gov.igac.sigc.excepcion.SNCExceptionFactory;
import co.gov.igac.sigc.excepcion.SNCExceptionTemplateFactory;
import co.gov.igac.sigc.excepcion.SNCExceptionTemplateFactory.ESNCExceptionLevel;

/**
 * Se debe crear una clase similar a esta que será la que maneje las excepciones del sistema
 * 
 */
public enum ManejadorExcepciones implements ISNCExceptionHandler {
	
	//Gestor tareas
	CP41001(  SNCExceptionTemplateFactory.getExcepcionSNC( "CP41001", ESNCExceptionLevel.ERROR, "Ocurrió un error en la interpretación de la petición JSON para {0}." )),
	CP41002(  SNCExceptionTemplateFactory.getExcepcionSNC( "CP41002", ESNCExceptionLevel.ERROR, "Ocurrió un error en el mapeo de la petición JSON para {0}." )),
	CP41003(  SNCExceptionTemplateFactory.getExcepcionSNC( "CP41003", ESNCExceptionLevel.ERROR, "Ocurrió un error en la serialización de la petición JSON para {0}." )),
	CP41004(  SNCExceptionTemplateFactory.getExcepcionSNC( "CP41004", ESNCExceptionLevel.ERROR, "Parámetro invalido: {0}" )),
	CP41005(  SNCExceptionTemplateFactory.getExcepcionSNC( "CP41005", ESNCExceptionLevel.ERROR, "Funcionalidad para {0} no está disponible en el API de procesos." )),
	CP41006(  SNCExceptionTemplateFactory.getExcepcionSNC( "CP41006", ESNCExceptionLevel.ERROR, " La tarea fue previamente reclamada. Para este caso, debe transferirse la actividad. {0}" )),
	//Gestor procesos
	CP42001(  SNCExceptionTemplateFactory.getExcepcionSNC( "CP42001", ESNCExceptionLevel.ERROR, "Ocurrió un error en la interpretación de la petición JSON para {0}." )),
	CP42002(  SNCExceptionTemplateFactory.getExcepcionSNC( "CP42002", ESNCExceptionLevel.ERROR, "Ocurrió un error en el mapeo de la petición JSON para {0}." )),
	CP42003(  SNCExceptionTemplateFactory.getExcepcionSNC( "CP42003", ESNCExceptionLevel.ERROR, "Ocurrió un error en la serialización de la petición JSON para {0}." )),
	CP42004(  SNCExceptionTemplateFactory.getExcepcionSNC( "CP42004", ESNCExceptionLevel.ERROR, "Parámetro invalido: {0}" )),
	CP42005(  SNCExceptionTemplateFactory.getExcepcionSNC( "CP42005", ESNCExceptionLevel.ERROR, "Funcionalidad para {0} no está disponible en el API de procesos." )),
	CP42006(  SNCExceptionTemplateFactory.getExcepcionSNC( "CP42006", ESNCExceptionLevel.ERROR, "Ocurrió un error en la serializacion del objecto GSON para {0}" )),
	CP42007(  SNCExceptionTemplateFactory.getExcepcionSNC( "CP42007", ESNCExceptionLevel.ERROR, "Error en el Index y Size del Object GSON para {0}" )),
	CP42008(  SNCExceptionTemplateFactory.getExcepcionSNC( "CP42008", ESNCExceptionLevel.ERROR, "Ocurrió un error de expresiones ilegales en Json to Gson para {0}" )),
	
	//Cliente REST servicios procesos
	//org.codehaus.jackson.JsonParseException
	CP43001(  SNCExceptionTemplateFactory.getExcepcionSNC( "CP43001", ESNCExceptionLevel.ERROR, "El formato de la petición no cumple la sintaxis requerida para {0}." )),
	//org.codehaus.jackson.map.JsonMappingException
	CP43002(  SNCExceptionTemplateFactory.getExcepcionSNC( "CP43002", ESNCExceptionLevel.ERROR, "Ocurrió un error en el mapeo de la petición JSON para {0}." )),
	//com.sun.jersey.api.client.ClientHandlerException
	CP43003(  SNCExceptionTemplateFactory.getExcepcionSNC( "CP43003", ESNCExceptionLevel.ERROR, "Ocurrió un error en el procesamiento de la petición o respuesta HTTP para {0}." )),
	//com.sun.jersey.api.client.UniformInterfaceException
	CP43004(  SNCExceptionTemplateFactory.getExcepcionSNC( "CP43004", ESNCExceptionLevel.ERROR, "Ocurrió un error en el procesamiento de la petición HTTP para {0}." )),
	//java.io.IOException
	CP43005( SNCExceptionTemplateFactory.getExcepcionSNC( "CP43005", ESNCExceptionLevel.ERROR, "Ocurrió un error inesperado en el procesamiento de la petición para {0}." )),
	//org.codehaus.jackson.JsonGenerationException
	CP43006( SNCExceptionTemplateFactory.getExcepcionSNC( "CP43006", ESNCExceptionLevel.ERROR, "Ocurrió un error en la generación de la petición JSON para {0}." )),
	CP43007( SNCExceptionTemplateFactory.getExcepcionSNC( "CP43007", ESNCExceptionLevel.FATAL, "Los servicios del API de procesos del SNC no están disponibles." )),
	
	 //Excepciones por lectura de observaciones
	CP40008( SNCExceptionTemplateFactory.getExcepcionSNC( "CP40008", ESNCExceptionLevel.ERROR, "Ocurrió un error en la generación de las observaciones en formato JSON." )),
	CP40009( SNCExceptionTemplateFactory.getExcepcionSNC( "CP40009", ESNCExceptionLevel.ERROR, "Ocurrió un error en el mapeo de las observaciones en formato JSON." )),
	CP40010( SNCExceptionTemplateFactory.getExcepcionSNC( "CP40010", ESNCExceptionLevel.ERROR, "Ocurrió un error en la serialización de las observaciones en formato JSON." )),
    
	//kieServiceClient KieServicesException
	CP44001(  SNCExceptionTemplateFactory.getExcepcionSNC( "CP44001", ESNCExceptionLevel.ERROR, "Ocurrió un error con los datos de entrada KieServiceClient para {0}." )),
	CP44002(  SNCExceptionTemplateFactory.getExcepcionSNC( "CP44002", ESNCExceptionLevel.ERROR, "Error de response Server KieRemoteHttpRequestException para {0}." )),
	
	//BDJbpmp
	CP45001(  SNCExceptionTemplateFactory.getExcepcionSNC( "CP45001", ESNCExceptionLevel.ADVERTENCIA, "Advertencia, no se presenta instancia en la base de datos {0}" ));
	
	
	
	;
    
    /**
	 * Corresponde a una excepción de negocio en el componente de procesos.
	 */
	/**
	 * Corresponde a una excepción de negocio en el componente de procesos.
	 */
	private ExcepcionSNC excepcionSNC;
	
	
	
	static {
		for ( ManejadorExcepciones excepcionProcesos: ManejadorExcepciones.values() )
		{
			assert(excepcionProcesos.toString() ==  excepcionProcesos.excepcionSNC.getCodigoExcepcion());
		}
	}
	
	/**
	 * Constructor de una excepción de negocio en el componente de procesos
	 * 
	 * @param excepcionSNC
	 */
	private ManejadorExcepciones(ExcepcionSNC excepcionSNC) {
		this.excepcionSNC = excepcionSNC;
	}
	
	public ExcepcionSNC getExcepcionSNC()
	{		
		return excepcionSNC;
	}	
	
	public ExcepcionSNC getExcepcion(ISNCLogger logger, Object... params) {
		return SNCExceptionFactory.getExcepcion(excepcionSNC, logger, params);
	}


	public ExcepcionSNC getExcepcion(ISNCLogger logger, Exception excepcionNativa, Object... params) {
		return SNCExceptionFactory.getExcepcion(excepcionSNC, logger, excepcionNativa, params);
	}

	public ExcepcionSNC getExcepcion(ISNCLogger logger, Throwable throwable,	Object... params) {
		return SNCExceptionFactory.getExcepcion(excepcionSNC, logger, throwable, params);
	}

	public ExcepcionSNC getExcepcion(ISNCLogger logger, ExcepcionSNC excepcionNativa, Object... params) {
		return SNCExceptionFactory.getExcepcion(excepcionSNC, logger, excepcionNativa, params);
	}	
}


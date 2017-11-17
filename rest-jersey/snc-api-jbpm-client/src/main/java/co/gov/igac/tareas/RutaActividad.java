package co.gov.igac.tareas;

public class RutaActividad {

	private String proceso;
	private String subproceso;
	private String actividad;
	private String separador=".";
	private String macroprocesoProceso;
	private String subprocesoActividad;
	
	private String macroproceso;
	public String getMacroproceso() {
		return macroproceso;
	}
	public String getProceso() {
		return proceso;
	}
	public String getSubproceso() {
		return subproceso;
	}
	public String getActividad() {
		return actividad;
	}
	public String getSeparador() {
		return separador;
	}
	public String getMacroprocesoProceso() {
		return macroprocesoProceso;
	}
	public String getSubprocesoActividad() {
		return subprocesoActividad;
	}
	public void setMacroproceso(String macroproceso) {
		this.macroproceso = macroproceso;
	}
	public void setProceso(String proceso) {
		this.proceso = proceso;
	}
	public void setSubproceso(String subproceso) {
		this.subproceso = subproceso;
	}
	public void setActividad(String actividad) {
		this.actividad = actividad;
	}
	public void setSeparador(String separador) {
		this.separador = separador;
	}
	public void setMacroprocesoProceso(String macroprocesoProceso) {
		this.macroprocesoProceso = macroprocesoProceso;
	}
	public void setSubprocesoActividad(String subprocesoActividad) {
		this.subprocesoActividad = subprocesoActividad;
	}
	
}

package co.gov.igac.tareas;

import java.util.ArrayList;

public class ConteoACtividad {
	
	private Object rutaActividad;
	
	private Object conteo;
	
	private Object urlCasoDeUso;
	
	private Object tipoProcesamiento;
	
	private Object tipoActividad;
	
	private ArrayList<String> transiciones;

	public Object getRutaActividad() {
		return rutaActividad;
	}

	public void setRutaActividad(Object rutaActividad) {
		this.rutaActividad = rutaActividad;
	}

	public Object getConteo() {
		return conteo;
	}

	public void setConteo(Object conteo) {
		this.conteo = conteo;
	}

	public Object getUrlCasoDeUso() {
		return urlCasoDeUso;
	}

	public void setUrlCasoDeUso(Object urlCasoDeUso) {
		this.urlCasoDeUso = urlCasoDeUso;
	}

	public Object getTipoProcesamiento() {
		return tipoProcesamiento;
	}

	public void setTipoProcesamiento(Object tipoProcesamiento) {
		this.tipoProcesamiento = tipoProcesamiento;
	}

	public Object getTipoActividad() {
		return tipoActividad;
	}

	public void setTipoActividad(Object tipoActividad) {
		this.tipoActividad = tipoActividad;
	}

	public ArrayList<String> getTransiciones() {
		return transiciones;
	}

	public void setTransiciones(ArrayList<String> transiciones) {
		this.transiciones = transiciones;
	}

	@Override
	public String toString() {
		return "ConteoACtividad [rutaActividad=" + rutaActividad + ", conteo=" + conteo + ", urlCasoDeUso="
				+ urlCasoDeUso + ", tipoProcesamiento=" + tipoProcesamiento + ", tipoActividad=" + tipoActividad
				+ ", transiciones=" + transiciones + "]";
	}
	
	
}

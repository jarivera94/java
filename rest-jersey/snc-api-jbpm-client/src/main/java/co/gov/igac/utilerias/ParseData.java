package co.gov.igac.utilerias;

import java.util.List;

import org.kie.server.client.KieServicesException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import co.gov.igac.entity.Users;
import co.gov.igac.kier.execution.JbpmConnection;
import co.gov.igac.nucleo.predial.PeticionActividad;
import co.gov.igac.nucleo.predial.PeticionAvanzarActividad;
import co.gov.igac.nucleo.predial.SolicitudActualizacion;
import co.gov.igac.nucleo.predial.SolicitudCatastral;
import co.gov.igac.nucleo.predial.SolicitudConservacion;
import co.gov.igac.nucleo.predial.TransfeririActividad;
import co.gov.igac.nucleo.predial.Usuario;

public class ParseData {
	
	Gson gson = null;
	Gson gsonserialize= null;
	public ParseData(){
		gson = new Gson ();
		gsonserialize = new GsonBuilder().serializeNulls().create();
	}
	
	public PeticionAvanzarActividad objectToPeticionAvanzar(String peticion){
		
		
		PeticionAvanzarActividad peticionAvanzaActividad = gson.fromJson(peticion, PeticionAvanzarActividad.class);
		
		return peticionAvanzaActividad;
	}
	
	public TransfeririActividad objectoTransferirActividad(String peticion ){
		TransfeririActividad transferirActividad = gson.fromJson(peticion, TransfeririActividad.class);
		return transferirActividad;
	}

	
	public List<FiltroActividades> StringToFiltroActividades(String peticion){
		List<FiltroActividades> filtroActividades= null;
		Gson gson = new Gson ();
		filtroActividades= gson.fromJson(peticion,  new TypeToken<List<FiltroActividades>>(){}.getType());
		return filtroActividades;
	}	
	public PeticionActividad objetcPeticionActividad(String peitcionActividad) {
		Gson gson = new Gson();
		PeticionActividad objPeticionActividad = gson.fromJson(peitcionActividad, PeticionActividad.class);
		return objPeticionActividad;
	}
	
	public String objectoJsonPeticionActividad(PeticionActividad peticionActividad) {
		
		String resultado = gsonserialize.toJson(peticionActividad);
		return resultado ;
	}
	
	public SolicitudCatastral objetcSolicitudCatastral(String JsonCatastral) {
		SolicitudCatastral objCatastral = gson.fromJson(JsonCatastral, SolicitudCatastral.class);
		return objCatastral;
	}
	
	public SolicitudActualizacion objetcSolicitudActualizacion(String JsonCatastral) {
		SolicitudActualizacion objCatastral = gson.fromJson(JsonCatastral, SolicitudActualizacion.class);
		return objCatastral;
	}
	public String objectoJsonSolicitudCatastral(SolicitudCatastral solicitud) {
		String resultado = gsonserialize.toJson(solicitud);
		return resultado ;
	}
	public String ObjectToJson(Object objecto){
		
		String resultado = gsonserialize.toJson(objecto);
		return resultado ;
	}
	public String ObjectToJsonNull(Object objecto){
		Gson gson = new Gson();
		String resultado = gson.toJson(objecto);
		return resultado ;
	}
	
	public Usuario objectUser(String usuario){
		
		Gson gson = new Gson();
		Usuario user= gson.fromJson(usuario, Usuario.class);
		return user;
	}
	
	public Usuario objectToUsuario(String usuarioS){
		Usuario usuario = gson.fromJson( usuarioS,Usuario.class);
		return usuario;
	}
	public SolicitudConservacion objectToConservacion(String solicitud){
		
		SolicitudConservacion solicitudConservacion = gson.fromJson(solicitud, SolicitudConservacion.class);
		return solicitudConservacion;
	}
	
	@SuppressWarnings("unused")
	public void loginKie(String username,String groupname){
		try {
			JbpmConnection jbpmconnection= new JbpmConnection();
			Users user =jbpmconnection.autenticaUsuario(username, groupname);
		} catch (KieServicesException e) {
			throw new KieServicesException("Error LoginKie conexion Jbpm");
		}
	} 

	
	public String parsePeticion(String data){
		data=data.replace("\\", "");
		data=data.replace("\"{","{" );
		data=data.replace("}\"","}" );
		return data;
	}
}

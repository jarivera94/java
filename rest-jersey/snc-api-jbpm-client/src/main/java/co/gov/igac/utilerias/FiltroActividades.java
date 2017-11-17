package co.gov.igac.utilerias;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FiltroActividades {

	@SerializedName("atributo")
	@Expose
	private String atributo;
	@SerializedName("valor")
	@Expose
	private String valor;

	public String getAtributo() {
	return atributo;
	}

	public void setAtributo(String atributo) {
	this.atributo = atributo;
	}

	public String getValor() {
	return valor;
	}

	public void setValor(String valor) {
	this.valor = valor;
	}
}

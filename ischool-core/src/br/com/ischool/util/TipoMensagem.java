package br.com.ischool.util;

/**
 * @author Daniel Souza de lima e-mail:daniesouza@gmail.com
 * 
 */

public enum TipoMensagem {

	INFO("INFO"),
	ALERTA("ALERTA"),
	ERRO("ERRO");
	
	private String label;
	
	private TipoMensagem(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
	
}

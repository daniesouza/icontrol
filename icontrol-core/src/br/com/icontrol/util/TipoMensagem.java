package br.com.icontrol.util;

/**
 * @author Icontrol
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

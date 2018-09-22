package br.com.icontrol.util;

import java.util.ResourceBundle;

/**
 * @author Icontrol
 * 
 */

public class Propriedades {

	
	private static String PATH_PROPERTIES = "br.com.icontrol.messages.error"; 
	
	private static ResourceBundle instancia;
	
	public static ResourceBundle getInstancia() {
		if (instancia == null) {
			instancia =  ResourceBundle.getBundle(PATH_PROPERTIES);
		}
		return instancia;
	}
		
	public static String obterValor(String chave) {
		String retorno;
		String valor = null;
		try{
			valor = getInstancia().getString(chave);
		}catch(Exception e){
			
		}
		
		if (valor == null) {
			retorno = chave;
		} else {
			retorno = getInstancia().getString(chave); 
		}
		return retorno;
	}
	
}
package br.com.ischool.util;

import java.util.ResourceBundle;

/**
 * @author Daniel Souza de lima e-mail:daniesouza@gmail.com
 * 
 */

public class Propriedades {

	
	private static String PATH_PROPERTIES = "br.com.ischool.messages.error"; 
	
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
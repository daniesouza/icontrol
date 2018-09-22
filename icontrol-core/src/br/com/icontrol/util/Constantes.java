package br.com.icontrol.util;

/**
 * @author Icontrol
 * 
 */
public class Constantes {

	public static final int    TAMANHO_BUFFER = 2048; // 2 Kb
	public static final String ADMINISTRADOR	= "ROLE_ADMIN";
	public static final String CLIENT			= "ROLE_CLIENT";
	public static final String ADMIN_CLIENTE	= "ROLE_ADMIN_CLIENT";
	
	public static final int TIPO_COMANDO_INFRA_VERMELHO = 1;
	public static final int TIPO_COMANDO_ANALOGICO		= 2;
	
	public static final int COMANDO_RECEBIDO = 1;
	public static final int COMANDO_NAO_RECONHECIDO = 0;
	
	public static final String COMANDO_STATUS_DESCONHECIDO = "D";
	public static final String COMANDO_STATUS_CONHECIDO = "C";
	
	// PORTAS DO ARDUINO
	
	public static final int A0 = 14;
	public static final int A1 = 15;
	public static final int A2 = 16;
	public static final int A3 = 17;
	public static final int A4 = 18;
	public static final int A5 = 19;
	
	
	
	public static final int INCLUSAO  = 1;
	public static final int ALTERACAO = 2;
	public static final int EXCLUSAO  = 3;
	public static final int COMANDO   = 4;
	public static final int LOGIN  	  = 5;
	
	
	// TEMA
	
	public static final String DEFAULT_TEMA = "glass-x";
	
}

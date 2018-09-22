package br.com.icontrol.exceptions;

import java.util.ArrayList;
import java.util.List;

import br.com.icontrol.util.DadosUtil;

/**
 * @author Icontrol
 * 
 */

public class DAOException extends Exception{


	

	/**
	 * 
	 */
	private static final long serialVersionUID = -7957545972774971175L;

	public DAOException(Throwable cause){
		
		super(cause);
	}
		
	private List<String> mensagens = new ArrayList<String>();
	
	public DAOException() {
		super("DAOException - Erro");
	}
	
	public DAOException(Exception ex){
		
		super(ex);
	}
	
	@Override
	public String getMessage() {
		if(!existeErro()) {
			return super.getMessage();			
		} else {
			return obterMensagens();
		}
	}
	
	public DAOException(String str) {
		super(str);
		mensagens.add(str);
	}
	
	public void adicionarMensagem(String mensagem) {
		this.mensagens.add(mensagem);
	}

	public List<String> getMensagens() {
		return mensagens;
	}
	
	public boolean existeErro() {
		return !DadosUtil.isEmpty(mensagens);
	}
	
	public String obterMensagens() {
		StringBuilder sb = new StringBuilder();
		for (String msg : mensagens) {
			sb.append(msg);
			sb.append(";");
		}
		return sb.substring(0, sb.length()-1).toString();
	}

}

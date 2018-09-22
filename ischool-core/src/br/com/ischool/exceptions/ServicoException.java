package br.com.ischool.exceptions;

import java.util.ArrayList;
import java.util.List;

import br.com.ischool.util.DadosUtil;

/**
 * @author Daniel Souza de lima e-mail:daniesouza@gmail.com
 * 
 */

public class ServicoException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5606506990286597937L;
	

	public ServicoException(Throwable cause){
		
		super(cause);
	}
		
	private List<String> mensagens = new ArrayList<String>();
	
	public ServicoException() {
		super("ServicoException - Erro");
	}
	
	public ServicoException(Exception ex){
		
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
	
	public ServicoException(String str) {
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

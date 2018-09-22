package br.com.ischool.util;

import java.io.Serializable;

/**
 * @author Daniel Souza de lima e-mail:daniesouza@gmail.com
 * 
 */

public class Mensagem implements Serializable {

	private static final long serialVersionUID = 1709434477883334412L;
	
	private String mensagem;
	
	private TipoMensagem tipoMensagem;
	
	public Mensagem() {
	}

	public Mensagem(String mensagem, TipoMensagem tipoMensagem) {
		super();
		this.mensagem = mensagem;
		this.tipoMensagem = tipoMensagem;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public TipoMensagem getTipoMensagem() {
		return tipoMensagem;
	}

	public void setTipoMensagem(TipoMensagem tipoMensagem) {
		this.tipoMensagem = tipoMensagem;
	}
	
}

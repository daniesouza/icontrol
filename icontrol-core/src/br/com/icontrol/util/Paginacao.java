package br.com.icontrol.util;

/**
 * @author Icontrol
 * 
 */

public class Paginacao {

	private int qtdeRegistroPorPagina;
	
	private int paginaAtual;
	
	private int totalRegistros;
	
	private int inicio;

	public Paginacao(int inicio) {
		super();
		this.inicio = inicio;
	}	
	
	public Paginacao(int inicio, int qtdeRegistroPorPagina) {
		super();
		this.qtdeRegistroPorPagina = qtdeRegistroPorPagina;
		this.inicio = inicio;
	}

	public int getQtdeRegistroPorPagina() {
		return qtdeRegistroPorPagina;
	}

	public void setQtdeRegistroPorPagina(int qtdeRegistroPorPagina) {
		this.qtdeRegistroPorPagina = qtdeRegistroPorPagina;
	}

	public int getPaginaAtual() {
		return paginaAtual;
	}

	public void setPaginaAtual(int paginaAtual) {
		this.paginaAtual = paginaAtual;
	}

	public int getTotalRegistros() {
		return totalRegistros;
	}

	public void setTotalRegistros(int totalRegistros) {
		this.totalRegistros = totalRegistros;
	}
	
	public int getInicio() {
//		return (getPaginaAtual()-1) * getQtdeRegistroPorPagina();
		return inicio;
	}

	public void setInicio(int inicio) {
		this.inicio = inicio;
	}
		
}

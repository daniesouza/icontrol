package br.com.ischool.business;

import java.util.Collection;

import br.com.ischool.entity.EventoExecutado;
import br.com.ischool.exceptions.ServicoException;
import br.com.ischool.util.Paginacao;

/**
 * @author Daniel Souza de lima e-mail:daniesouza@gmail.com
 * 
 */
public interface EventoExecutadoServiceLocal {

	public void salvarEventoExecutado(EventoExecutado eventoExecutado) throws ServicoException;
	
	public void alterarEventoExecutado(EventoExecutado eventoExecutado) throws ServicoException;
	
	public Collection<EventoExecutado> listarEventoExecutados() throws ServicoException;
	
	public Collection<EventoExecutado> listarEventoExecutados(EventoExecutado filtro,Paginacao paginacao) throws ServicoException;
	
	public void excluirEventoExecutado(EventoExecutado eventoExecutado) throws ServicoException;

	public EventoExecutado selectById(Long id) throws ServicoException;

}

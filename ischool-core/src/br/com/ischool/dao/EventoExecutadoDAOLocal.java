package br.com.ischool.dao;

import java.util.Collection;

import br.com.ischool.entity.EventoExecutado;
import br.com.ischool.exceptions.DAOException;
import br.com.ischool.util.Paginacao;

/**
 * @author Daniel Souza de lima e-mail:daniesouza@gmail.com
 * 
 */
public interface EventoExecutadoDAOLocal {

	public void salvarEventoExecutado(EventoExecutado eventoExecutado) throws DAOException;
	
	public void alterarEventoExecutado(EventoExecutado eventoExecutado) throws DAOException;
	
	public Collection<EventoExecutado> listarEventoExecutados() throws DAOException;
	
	public Collection<EventoExecutado> listarEventoExecutados(EventoExecutado filtro,Paginacao paginacao) throws DAOException;
	
	public void excluirEventoExecutado(EventoExecutado eventoExecutado) throws DAOException;

	int consultarQtde(EventoExecutado filtro) throws DAOException;
	
	public EventoExecutado selectById(Long id) throws DAOException;

}

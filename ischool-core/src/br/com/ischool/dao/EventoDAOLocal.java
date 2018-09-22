package br.com.ischool.dao;

import java.util.Collection;

import br.com.ischool.entity.Evento;
import br.com.ischool.exceptions.DAOException;
import br.com.ischool.util.Paginacao;

/**
 * @author Daniel Souza de lima e-mail:daniesouza@gmail.com
 * 
 */
public interface EventoDAOLocal {

	public void salvarEvento(Evento evento) throws DAOException;
	
	public void alterarEvento(Evento evento) throws DAOException;
	
	public Collection<Evento> listarEventos() throws DAOException;
	
	public Collection<Evento> listarEventos(Evento filtro,Paginacao paginacao) throws DAOException;
	
	public void excluirEvento(Evento evento) throws DAOException;

	int consultarQtde(Evento filtro) throws DAOException;
	
	public Evento selectById(Long id) throws DAOException;
}

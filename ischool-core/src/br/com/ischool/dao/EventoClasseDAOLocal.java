package br.com.ischool.dao;

import java.util.Collection;

import br.com.ischool.entity.EventoClasse;
import br.com.ischool.exceptions.DAOException;
import br.com.ischool.util.Paginacao;

/**
 * @author Daniel Souza de lima e-mail:daniesouza@gmail.com
 * 
 */
public interface EventoClasseDAOLocal {

	public void salvarEventoClasse(EventoClasse eventoClasse) throws DAOException;
	
	public void alterarEventoClasse(EventoClasse eventoClasse) throws DAOException;
	
	public Collection<EventoClasse> listarEventoClasses() throws DAOException;
	
	public Collection<EventoClasse> listarEventoClasses(EventoClasse filtro,Paginacao paginacao) throws DAOException;
	
	public void excluirEventoClasse(EventoClasse eventoClasse) throws DAOException;

	int consultarQtde(EventoClasse filtro) throws DAOException;
	
	public EventoClasse selectById(Long id) throws DAOException;

	public void limparEventosClasse(Long idClasse) throws DAOException;
}

package br.com.icontrol.dao;

import java.util.Collection;

import br.com.icontrol.entity.Componente;
import br.com.icontrol.exceptions.DAOException;
import br.com.icontrol.util.Paginacao;

/**
 * @author Icontrol
 * 
 */
public interface ComponenteDAOLocal {

	public void salvarComponente(Componente componente) throws DAOException;
	
	public void alterarComponente(Componente componente) throws DAOException;
	
	public Collection<Componente> listarComponentes() throws DAOException;
	
	public Collection<Componente> listarComponentes(Componente filtro,Paginacao paginacao) throws DAOException;
	
	public void excluirComponente(Componente componente) throws DAOException;
	
	public Componente selectById(Long id) throws DAOException;
	
	public int consultarQtde(Componente componente) throws DAOException;
}

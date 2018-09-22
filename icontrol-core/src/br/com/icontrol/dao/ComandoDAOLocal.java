package br.com.icontrol.dao;

import java.util.Collection;

import br.com.icontrol.entity.Comando;
import br.com.icontrol.exceptions.DAOException;
import br.com.icontrol.util.Paginacao;

/**
 * @author Icontrol
 * 
 */
public interface ComandoDAOLocal {

	public void salvarComando(Comando comando) throws DAOException;
	
	public void alterarComando(Comando comando) throws DAOException;
	
	public Collection<Comando> listarComandos() throws DAOException;
	
	public Collection<Comando> listarComandos(Comando filtro,Paginacao paginacao) throws DAOException;
	
	public void excluirComando(Comando comando) throws DAOException;
	
	public Comando selectById(Long id) throws DAOException;
	
	public int consultarQtde(Comando comando) throws DAOException;
}
package br.com.icontrol.dao;

import java.util.Collection;

import br.com.icontrol.entity.Log;
import br.com.icontrol.exceptions.DAOException;
import br.com.icontrol.util.Paginacao;

/**
 * @author Icontrol
 * 
 */
public interface LogDAOLocal {

	public void salvarLog(Log log) throws DAOException;
	
	public void alterarLog(Log log) throws DAOException;
	
	public Collection<Log> listarLogs() throws DAOException;
	
	public Collection<Log> listarLogs(Log filtro,Paginacao paginacao) throws DAOException;
	
	public void excluirLog(Log log) throws DAOException;
	
	public Log selectById(Long id) throws DAOException;

}
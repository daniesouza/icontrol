package br.com.icontrol.business;

import java.util.Collection;

import br.com.icontrol.entity.Log;
import br.com.icontrol.exceptions.ServicoException;
import br.com.icontrol.util.Paginacao;

/**
 * @author Icontrol
 * 
 */
public interface LogServiceLocal {

	public void salvarLog(Log Log) throws ServicoException;
	
	public void alterarLog(Log Log) throws ServicoException;
	
	public Collection<Log> listarLogs() throws ServicoException;
	
	public Collection<Log> listarLogs(Log filtro,Paginacao paginacao) throws ServicoException;
	
	public void excluirLog(Log Log) throws ServicoException;
}

package br.com.icontrol.business;

import java.util.Collection;

import br.com.icontrol.entity.Comando;
import br.com.icontrol.exceptions.ServicoException;
import br.com.icontrol.util.Paginacao;

/**
 * @author Icontrol
 * 
 */
public interface ComandoServiceLocal {

	public void salvarComando(Comando comando) throws ServicoException;
	
	public void alterarComando(Comando comando) throws ServicoException;
	
	public Collection<Comando> listarComandos() throws ServicoException;
	
	public Collection<Comando> listarComandos(Comando filtro,Paginacao paginacao) throws ServicoException;
	
	public void excluirComando(Comando comando) throws ServicoException;
}

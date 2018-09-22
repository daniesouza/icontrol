package br.com.icontrol.business;

import java.util.Collection;

import br.com.icontrol.entity.Componente;
import br.com.icontrol.exceptions.ServicoException;
import br.com.icontrol.util.Paginacao;

/**
 * @author Icontrol
 * 
 */
public interface ComponenteServiceLocal {

	public void salvarComponente(Componente componente) throws ServicoException;
	
	public void alterarComponente(Componente componente) throws ServicoException;
	
	public Collection<Componente> listarComponentes() throws ServicoException;
	
	public Collection<Componente> listarComponentes(Componente filtro,Paginacao paginacao) throws ServicoException;
		
	public void excluirComponente(Componente componente) throws ServicoException;
}

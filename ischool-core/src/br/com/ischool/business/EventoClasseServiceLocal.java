package br.com.ischool.business;

import java.util.Collection;

import br.com.ischool.entity.EventoClasse;
import br.com.ischool.exceptions.ServicoException;
import br.com.ischool.util.Paginacao;

/**
 * @author Daniel Souza de lima e-mail:daniesouza@gmail.com
 * 
 */
public interface EventoClasseServiceLocal {

	public void salvarEventoClasse(EventoClasse eventoClasse) throws ServicoException;
	
	public void alterarEventoClasse(EventoClasse eventoClasse) throws ServicoException;
	
	public Collection<EventoClasse> listarEventoClasses() throws ServicoException;
	
	public Collection<EventoClasse> listarEventoClasses(EventoClasse filtro,Paginacao paginacao) throws ServicoException;
	
	public void excluirEventoClasse(EventoClasse eventoClasse) throws ServicoException;
	
	public void atualizarEventoClasse(Collection<EventoClasse> eventosClasse,Long idClasse) throws ServicoException;

	public EventoClasse selectById(Long id) throws ServicoException;
	

}

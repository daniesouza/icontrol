package br.com.ischool.business;

import java.util.Collection;
import br.com.ischool.entity.Evento;
import br.com.ischool.exceptions.ServicoException;
import br.com.ischool.util.Paginacao;

/**
 * @author Daniel Souza de lima e-mail:daniesouza@gmail.com
 * 
 */
public interface EventoServiceLocal {

	public void salvarEvento(Evento evento) throws ServicoException;
	
	public void alterarEvento(Evento evento) throws ServicoException;
	
	public Collection<Evento> listarEventos() throws ServicoException;
	
	public Collection<Evento> listarEventos(Evento filtro,Paginacao paginacao) throws ServicoException;
	
	public void excluirEvento(Evento evento) throws ServicoException;

	public Evento selectById(Long id) throws ServicoException;

}

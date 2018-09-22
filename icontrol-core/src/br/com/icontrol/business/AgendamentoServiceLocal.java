package br.com.icontrol.business;

import java.util.Collection;

import br.com.icontrol.entity.Agendamento;
import br.com.icontrol.exceptions.ServicoException;
import br.com.icontrol.util.Paginacao;

/**
 * @author Icontrol
 * 
 */
public interface AgendamentoServiceLocal {

	public void salvarAgendamento(Agendamento Agendamento) throws ServicoException;
	
	public void alterarAgendamento(Agendamento Agendamento) throws ServicoException;
	
	public Collection<Agendamento> listarAgendamentos() throws ServicoException;
	
	public Collection<Agendamento> listarAgendamentos(Agendamento filtro,Paginacao paginacao) throws ServicoException;
	
	public void excluirAgendamento(Agendamento Agendamento) throws ServicoException;
	
}


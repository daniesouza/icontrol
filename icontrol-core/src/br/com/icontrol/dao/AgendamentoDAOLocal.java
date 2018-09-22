package br.com.icontrol.dao;

import java.util.Collection;

import br.com.icontrol.entity.Agendamento;
import br.com.icontrol.exceptions.DAOException;
import br.com.icontrol.util.Paginacao;

/**
 * @author Icontrol
 * 
 */
public interface AgendamentoDAOLocal {

	public void salvarAgendamento(Agendamento agendamento) throws DAOException;
	
	public void alterarAgendamento(Agendamento agendamento) throws DAOException;
	
	public Collection<Agendamento> listarAgendamentos() throws DAOException;
	
	public Collection<Agendamento> listarAgendamentos(Agendamento filtro,Paginacao paginacao) throws DAOException;
		
	public void excluirAgendamento(Agendamento agendamento) throws DAOException;

	public Agendamento selectById(Long id) throws DAOException;
	
	public int consultarQtde(Agendamento agendamento) throws DAOException;

}

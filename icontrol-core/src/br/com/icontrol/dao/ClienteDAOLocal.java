package br.com.icontrol.dao;

import java.util.Collection;

import br.com.icontrol.entity.Cliente;
import br.com.icontrol.exceptions.DAOException;
import br.com.icontrol.util.Paginacao;

/**
 * @author Icontrol
 * 
 */
public interface ClienteDAOLocal {

	public void salvarCliente(Cliente cliente) throws DAOException;
	
	public void alterarCliente(Cliente cliente) throws DAOException;
	
	public Collection<Cliente> listarClientes() throws DAOException;
	
	public Collection<Cliente> listarClientes(Cliente filtro,Paginacao paginacao) throws DAOException;
	
	public void excluirCliente(Cliente cliente) throws DAOException;

	int consultarQtde(Cliente filtro) throws DAOException;
	
	public Cliente selectById(Long id) throws DAOException;
}

package br.com.ischool.business;

import java.util.Collection;
import java.util.List;

import br.com.ischool.entity.Cliente;
import br.com.ischool.entity.Usuario;
import br.com.ischool.exceptions.ServicoException;
import br.com.ischool.util.Paginacao;

/**
 * @author Daniel Souza de lima e-mail:daniesouza@gmail.com
 * 
 */
public interface ClienteServiceLocal {

	public void salvarCliente(Cliente Cliente,List<Usuario> listaRemoverUsuarios) throws ServicoException;
	
	public void alterarCliente(Cliente Cliente,List<Usuario> listaRemoverUsuarios) throws ServicoException;
	
	public Collection<Cliente> listarClientes() throws ServicoException;
	
	public Collection<Cliente> listarClientes(Cliente filtro,Paginacao paginacao) throws ServicoException;
	
	public void excluirCliente(Cliente Cliente) throws ServicoException;
	
}

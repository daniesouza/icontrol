package br.com.icontrol.business;

import java.util.Collection;
import java.util.List;

import br.com.icontrol.entity.Arduino;
import br.com.icontrol.entity.Cliente;
import br.com.icontrol.entity.Usuario;
import br.com.icontrol.exceptions.ServicoException;
import br.com.icontrol.util.Paginacao;

/**
 * @author Icontrol
 * 
 */
public interface ClienteServiceLocal {

	public void salvarCliente(Cliente Cliente,List<Usuario> listaRemoverUsuarios,List<Arduino> listaRemoverArduino) throws ServicoException;
	
	public void alterarCliente(Cliente Cliente,List<Usuario> listaRemoverUsuarios,List<Arduino> listaRemoverArduino) throws ServicoException;
	
	public Collection<Cliente> listarClientes() throws ServicoException;
	
	public Collection<Cliente> listarClientes(Cliente filtro,Paginacao paginacao) throws ServicoException;
	
	public void excluirCliente(Cliente Cliente) throws ServicoException;
	
}

package br.com.icontrol.controller.datamodel;

import java.util.List;

import br.com.icontrol.business.ClienteServiceLocal;
import br.com.icontrol.entity.Cliente;
import br.com.icontrol.exceptions.ServicoException;
import br.com.icontrol.util.Paginacao;

public class ClienteDataModel extends GenericDataModel<Cliente> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2575105380004396272L;
	
	private ClienteServiceLocal clienteLocal;
	
	@Override
	public List<Cliente> obterResultados(Cliente filtro, Paginacao paginacao) throws ServicoException {

		List<Cliente> listaCliente = (List<Cliente>) clienteLocal.listarClientes(filtro, paginacao);
			
		return listaCliente;

	}

	public ClienteServiceLocal getClienteLocal() {
		return clienteLocal;
	}



	public void setClienteLocal(ClienteServiceLocal clienteLocal) {
		this.clienteLocal = clienteLocal;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	

}

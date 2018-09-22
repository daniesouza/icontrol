package br.com.icontrol.controller.datamodel;

import java.util.List;

import br.com.icontrol.business.AgendamentoServiceLocal;
import br.com.icontrol.entity.Agendamento;
import br.com.icontrol.entity.Cliente;
import br.com.icontrol.entity.Usuario;
import br.com.icontrol.exceptions.ServicoException;
import br.com.icontrol.util.FacesUtil;
import br.com.icontrol.util.Paginacao;

public class AgendamentoDataModel extends GenericDataModel<Agendamento> {


	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2630736418801546685L;
	
	private AgendamentoServiceLocal agendamentoLocal;
	
	@Override
	public List<Agendamento> obterResultados(Agendamento filtro, Paginacao paginacao) throws ServicoException {
		
		Usuario usuario = FacesUtil.recuperarUsuarioSessao();
		
		// SE O USUARIO FOR ADMIN GERAL NAO TERA UM CLIENTE ASSOCIADO
		if(usuario.getCliente() != null){
			
			if(filtro == null){
				filtro = new Agendamento();
				filtro.setCliente(new Cliente());
				filtro.setUsuario(new Usuario());
			}
			filtro.getCliente().setCpfCnpj(usuario.getCliente().getCpfCnpj());
			filtro.getUsuario().setId(usuario.getId());
		}
		
		List<Agendamento> listaAgendamento = (List<Agendamento>) agendamentoLocal.listarAgendamentos(filtro, paginacao);
			
		return listaAgendamento;

	}

	public AgendamentoServiceLocal getAgendamentoLocal() {
		return agendamentoLocal;
	}



	public void setAgendamentoLocal(AgendamentoServiceLocal agendamentoLocal) {
		this.agendamentoLocal = agendamentoLocal;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	

}

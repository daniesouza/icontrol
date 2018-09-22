package br.com.icontrol.controller.datamodel;

import java.util.List;

import br.com.icontrol.business.UsuarioServiceLocal;
import br.com.icontrol.entity.Cliente;
import br.com.icontrol.entity.Usuario;
import br.com.icontrol.exceptions.ServicoException;
import br.com.icontrol.util.DadosUtil;
import br.com.icontrol.util.FacesUtil;
import br.com.icontrol.util.Paginacao;



public class UsuarioDataModel extends GenericDataModel<Usuario> {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 4714236311597872095L;
	private UsuarioServiceLocal usuarioLocal;
	
	@Override
	public List<Usuario> obterResultados(Usuario filtro, Paginacao paginacao) throws ServicoException {

		
		Usuario usuario = FacesUtil.recuperarUsuarioSessao();
		
		// SE O USUARIO FOR ADMIN GERAL NAO TERA UM CLIENTE ASSOCIADO
		if(!DadosUtil.isEmpty(usuario.getCliente())){	
			
			if(DadosUtil.isEmpty(filtro)){
				filtro = new Usuario();
				filtro.setCliente(new Cliente());
			}
			filtro.getCliente().setCpfCnpj(usuario.getCliente().getCpfCnpj());
		}
		
		List<Usuario> listaUsuario = (List<Usuario>) usuarioLocal.listarUsuarios(filtro, paginacao);
			
		return listaUsuario;


	}

	public UsuarioServiceLocal getUsuarioLocal() {
		return usuarioLocal;
	}

	public void setUsuarioLocal(UsuarioServiceLocal usuarioLocal) {
		this.usuarioLocal = usuarioLocal;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	
}

package br.com.icontrol.controller.datamodel;

import java.util.List;

import br.com.icontrol.business.GrupoServiceLocal;
import br.com.icontrol.entity.Cliente;
import br.com.icontrol.entity.Grupo;
import br.com.icontrol.entity.Usuario;
import br.com.icontrol.exceptions.ServicoException;
import br.com.icontrol.util.FacesUtil;
import br.com.icontrol.util.Paginacao;

public class GrupoDataModel extends GenericDataModel<Grupo> {
	

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -689629716996431626L;
	private GrupoServiceLocal GrupoLocal;
	
	@Override
	public List<Grupo> obterResultados(Grupo filtro, Paginacao paginacao) throws ServicoException {

		Usuario usuario = FacesUtil.recuperarUsuarioSessao();
		
		// SE O USUARIO FOR ADMIN GERAL NAO TERA UM CLIENTE ASSOCIADO
		if(usuario.getCliente() != null){
			
			if(filtro == null){
				filtro = new Grupo();
				filtro.setCliente(new Cliente());
			}
			filtro.getCliente().setCpfCnpj(usuario.getCliente().getCpfCnpj());
		}
		
		List<Grupo> listaGrupo = (List<Grupo>) GrupoLocal.listarGrupos(filtro, paginacao);
			
		return listaGrupo;
	}

	public GrupoServiceLocal getGrupoLocal() {
		return GrupoLocal;
	}

	public void setGrupoLocal(GrupoServiceLocal GrupoLocal) {
		this.GrupoLocal = GrupoLocal;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}

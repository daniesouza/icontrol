package br.com.icontrol.controller.datamodel;

import java.util.List;

import br.com.icontrol.business.AtalhoServiceLocal;
import br.com.icontrol.entity.Cliente;
import br.com.icontrol.entity.Atalho;
import br.com.icontrol.entity.Usuario;
import br.com.icontrol.exceptions.ServicoException;
import br.com.icontrol.util.FacesUtil;
import br.com.icontrol.util.Paginacao;

public class AtalhoDataModel extends GenericDataModel<Atalho> {
	


	/**
	 * 
	 */
	private static final long serialVersionUID = 6483695971188433966L;
	
	private AtalhoServiceLocal atalhoLocal;
	
	@Override
	public List<Atalho> obterResultados(Atalho filtro, Paginacao paginacao) throws ServicoException {

		Usuario usuario = FacesUtil.recuperarUsuarioSessao();
		
		// SE O USUARIO FOR ADMIN GERAL NAO TERA UM CLIENTE ASSOCIADO
		if(usuario.getCliente() != null){
			
			if(filtro == null){
				filtro = new Atalho();
				filtro.setCliente(new Cliente());
				filtro.setUsuario(new Usuario());
			}
			filtro.getCliente().setCpfCnpj(usuario.getCliente().getCpfCnpj());
			filtro.getUsuario().setId(usuario.getId());
		}
		
		List<Atalho> listaAtalho = (List<Atalho>) atalhoLocal.listarAtalhos(filtro, paginacao);
			
		return listaAtalho;
	}

	public AtalhoServiceLocal getAtalhoLocal() {
		return atalhoLocal;
	}

	public void setAtalhoLocal(AtalhoServiceLocal AtalhoLocal) {
		this.atalhoLocal = AtalhoLocal;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}

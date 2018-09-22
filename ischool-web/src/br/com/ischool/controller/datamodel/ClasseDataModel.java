package br.com.ischool.controller.datamodel;

import java.util.List;

import br.com.ischool.business.ClasseServiceLocal;
import br.com.ischool.entity.Classe;
import br.com.ischool.entity.Cliente;
import br.com.ischool.entity.Usuario;
import br.com.ischool.exceptions.ServicoException;
import br.com.ischool.util.FacesUtil;
import br.com.ischool.util.Paginacao;

public class ClasseDataModel extends GenericDataModel<Classe> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7624444062873031697L;
	
	private ClasseServiceLocal classeLocal;
	
	@Override
	public List<Classe> obterResultados(Classe filtro, Paginacao paginacao) throws ServicoException {
		
		Usuario usuario = FacesUtil.recuperarUsuarioSessao();
		
		// SE O USUARIO FOR ADMIN GERAL NAO TERA UM CLIENTE ASSOCIADO
		if(usuario.getCliente() != null){
			
			if(filtro == null){
				filtro = new Classe();
				filtro.setCliente(new Cliente());
			}
			filtro.getCliente().setIdCliente(usuario.getCliente().getIdCliente());
		}

		List<Classe> listaClasse = (List<Classe>) classeLocal.listarClasses(filtro, paginacao);
			
		return listaClasse;

	}

	public ClasseServiceLocal getClasseLocal() {
		return classeLocal;
	}



	public void setClasseLocal(ClasseServiceLocal classeLocal) {
		this.classeLocal = classeLocal;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	

}

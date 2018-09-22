package br.com.icontrol.controller.datamodel;

import java.util.List;

import br.com.icontrol.business.ComponenteServiceLocal;
import br.com.icontrol.entity.Componente;
import br.com.icontrol.exceptions.ServicoException;
import br.com.icontrol.util.Paginacao;

public class ComponenteDataModel extends GenericDataModel<Componente> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4244971828979633295L;
	
	private ComponenteServiceLocal componenteLocal;
	
	@Override
	public List<Componente> obterResultados(Componente filtro, Paginacao paginacao) throws ServicoException {

		List<Componente> listaComponente = (List<Componente>) componenteLocal.listarComponentes(filtro, paginacao);
			
		return listaComponente;
	}

	public ComponenteServiceLocal getComponenteLocal() {
		return componenteLocal;
	}

	public void setComponenteLocal(ComponenteServiceLocal componenteLocal) {
		this.componenteLocal = componenteLocal;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}

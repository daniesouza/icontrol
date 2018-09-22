package br.com.icontrol.controller.datamodel;

import java.util.List;

import br.com.icontrol.business.ComandoServiceLocal;
import br.com.icontrol.entity.Comando;
import br.com.icontrol.exceptions.ServicoException;
import br.com.icontrol.util.Paginacao;

public class ComandoDataModel extends GenericDataModel<Comando> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6516792638584439219L;
	
	private ComandoServiceLocal comandoLocal;
	
	@Override
	public List<Comando> obterResultados(Comando filtro, Paginacao paginacao) throws ServicoException {

		List<Comando> listaComando = (List<Comando>) comandoLocal.listarComandos(filtro, paginacao);
			
		return listaComando;

	}

	public ComandoServiceLocal getComandoLocal() {
		return comandoLocal;
	}



	public void setComandoLocal(ComandoServiceLocal comandoLocal) {
		this.comandoLocal = comandoLocal;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	

}

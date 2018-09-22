package br.com.icontrol.controller.datamodel;

import java.util.List;

import br.com.icontrol.business.LogServiceLocal;
import br.com.icontrol.entity.Cliente;
import br.com.icontrol.entity.Log;
import br.com.icontrol.entity.Usuario;
import br.com.icontrol.exceptions.ServicoException;
import br.com.icontrol.util.DadosUtil;
import br.com.icontrol.util.FacesUtil;
import br.com.icontrol.util.Paginacao;

public class LogDataModel extends GenericDataModel<Log> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1821508850652343416L;
	
	private LogServiceLocal logLocal;
	
	@Override
	public List<Log> obterResultados(Log filtro, Paginacao paginacao) throws ServicoException {
		
		Usuario usuario = FacesUtil.recuperarUsuarioSessao();
	
		// SE O USUARIO FOR ADMIN GERAL NAO TERA UM CLIENTE ASSOCIADO
		if(!DadosUtil.isEmpty(usuario.getCliente())){					
				
			if(DadosUtil.isEmpty(filtro)){
				filtro = new Log();
				filtro.setUsuario(new Usuario());
				filtro.getUsuario().setCliente(new Cliente());
			}			
								
			filtro.getUsuario().getCliente().setId(usuario.getCliente().getId());
		}
		

		List<Log> listaLog = (List<Log>) logLocal.listarLogs(filtro, paginacao);
			
		return listaLog;

	}

	public LogServiceLocal getLogLocal() {
		return logLocal;
	}



	public void setLogLocal(LogServiceLocal LogLocal) {
		this.logLocal = LogLocal;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	

}

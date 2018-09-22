package br.com.icontrol.controller;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.icontrol.business.LogServiceLocal;
import br.com.icontrol.controller.datamodel.LogDataModel;
import br.com.icontrol.entity.Cliente;
import br.com.icontrol.entity.Log;
import br.com.icontrol.entity.Usuario;
import br.com.icontrol.exceptions.ServicoException;
import br.com.icontrol.util.DadosUtil;
import br.com.icontrol.util.FacesUtil;

/**
 * @author Icontrol
 *      
 */

@ManagedBean
@ViewScoped
public class LogBean extends AbstractViewHelper<Log> implements Serializable{



	/**
	 * 
	 */
	private static final long serialVersionUID = -5300994858688793669L;

	@EJB
	private LogServiceLocal logService;
	
	private LogDataModel logDataModel = new LogDataModel();

			
	
	@Override
	public void inicializar() {
		
		filtro  = new Log();
		filtro.setUsuario(new Usuario());
		
		if(!DadosUtil.isEmpty(FacesUtil.recuperarUsuarioSessao().getCliente())){			
			filtro.getUsuario().setCliente(FacesUtil.recuperarUsuarioSessao().getCliente());
		}else{
			filtro.getUsuario().setCliente(new Cliente());
		}
		
		
		logDataModel.setLogLocal(logService);
		
	}

	@Override
	public void pesquisar() {
		
		logDataModel.setFiltro(filtro);
		
	}

	@Override
	public void reset() {
		setEntidade(new Log());	
	}
		


	@Override
	public void salvarImpl() throws ServicoException {
		
		// NAO SERA IMPLEMENTADO

	}
	

	@Override
	public void alterarImpl() throws ServicoException {
		
		// NAO SERA IMPLEMENTADO

	}

	@Override
	public void excluirImpl() throws ServicoException {
		
		// NAO SERA IMPLEMENTADO
		
	}

	public LogServiceLocal getLogService() {
		return logService;
	}

	public void setLogService(LogServiceLocal LogService) {
		this.logService = LogService;
	}

	public LogDataModel getLogDataModel() {
		return logDataModel;
	}

	public void setLogDataModel(LogDataModel LogDataModel) {
		this.logDataModel = LogDataModel;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

}

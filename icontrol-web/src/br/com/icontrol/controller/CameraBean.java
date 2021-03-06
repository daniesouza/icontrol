package br.com.icontrol.controller;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.icontrol.business.CameraServiceLocal;
import br.com.icontrol.controller.datamodel.CameraDataModel;
import br.com.icontrol.entity.Camera;
import br.com.icontrol.exceptions.ServicoException;
import br.com.icontrol.util.Constantes;

/**
 * @author Icontrol
 *      
 */

@ManagedBean
@ViewScoped
public class CameraBean extends AbstractViewHelper<Camera> implements Serializable{



	/**
	 * 
	 */
	private static final long serialVersionUID = -5300994858688793669L;

	@EJB
	private CameraServiceLocal cameraService;
	
	private CameraDataModel cameraDataModel = new CameraDataModel();

			
	
	@Override
	public void inicializar() {
		
		filtro  = new Camera();
		cameraDataModel.setCameraLocal(cameraService);
		
	}

	@Override
	public void pesquisar() {
		
		cameraDataModel.setFiltro(filtro);
		
	}

	@Override
	public void reset() {
		setEntidade(new Camera());	
	}

	@Override
	public void salvarImpl() throws ServicoException {
		       
		cameraService.salvarCamera(entidade);
		reset();		   
		
		gravarLog("Camera "+entidade.getCodigo(),Constantes.INCLUSAO);
	}

	@Override
	public void alterarImpl() throws ServicoException {
		
		cameraService.alterarCamera(entidade);
		reset();	
		
		gravarLog("Camera "+entidade.getCodigo(),Constantes.ALTERACAO);
	}

	@Override
	public void excluirImpl() throws ServicoException {
		
		cameraService.excluirCamera(entidade);		
		reset();
		
		gravarLog("Camera "+entidade.getCodigo(),Constantes.EXCLUSAO);
	}
	
	public void novo(){
		reset();
	}

	public CameraServiceLocal getCameraService() {
		return cameraService;
	}

	public void setCameraService(CameraServiceLocal CameraService) {
		this.cameraService = CameraService;
	}

	public CameraDataModel getCameraDataModel() {
		return cameraDataModel;
	}

	public void setCameraDataModel(CameraDataModel CameraDataModel) {
		this.cameraDataModel = CameraDataModel;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

}

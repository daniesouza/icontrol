package br.com.icontrol.controller.datamodel;

import java.util.List;

import br.com.icontrol.business.CameraServiceLocal;
import br.com.icontrol.entity.Camera;
import br.com.icontrol.exceptions.ServicoException;
import br.com.icontrol.util.Paginacao;

public class CameraDataModel extends GenericDataModel<Camera> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6516792638584439219L;
	
	private CameraServiceLocal cameraLocal;
	
	@Override
	public List<Camera> obterResultados(Camera filtro, Paginacao paginacao) throws ServicoException {

		List<Camera> listaCamera = (List<Camera>) cameraLocal.listarCameras(filtro, paginacao);
			
		return listaCamera;

	}

	public CameraServiceLocal getCameraLocal() {
		return cameraLocal;
	}



	public void setCameraLocal(CameraServiceLocal CameraLocal) {
		this.cameraLocal = CameraLocal;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	

}

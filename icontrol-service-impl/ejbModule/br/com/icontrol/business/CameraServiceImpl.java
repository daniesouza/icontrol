package br.com.icontrol.business;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import br.com.icontrol.dao.CameraDAOLocal;
import br.com.icontrol.entity.Camera;
import br.com.icontrol.exceptions.DAOException;
import br.com.icontrol.exceptions.ServicoException;
import br.com.icontrol.util.DadosUtil;
import br.com.icontrol.util.Paginacao;

/**
 * @author Icontrol
 * Session Bean implementation class CameraServiceImpl
 */
@Stateless
@Local(value=CameraServiceLocal.class)
public class CameraServiceImpl implements CameraServiceLocal {

    /**
     * Default constructor. 
     */
    public CameraServiceImpl() {
        // TODO Auto-generated constructor stub
    }
    
	@EJB
	private CameraDAOLocal cameraDAO;
	
    @PostConstruct
    public void carregarInformacoes()
    {
    	System.out.println("CARREGADO OS RECURSOS DO EJB "+this.getClass().getName());
    }
    
    @PreDestroy
    public void clear()
    {
    	System.out.println("LIBERANDO OS RECURSOS DO EJB "+this.getClass().getName());
    }
	
	@Override
	public void salvarCamera(Camera Camera) throws ServicoException {
		
		try {
			validarCamera(Camera);
			
			boolean existeCamera = existeCamera(Camera);
			if (existeCamera) {
				throw new ServicoException("Camera_EXISTENTE");
			}
			
			cameraDAO.salvarCamera(Camera);
			
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServicoException(e.getMessage());
		}
			
	}

	@Override
	public void alterarCamera(Camera Camera) throws ServicoException {
		

		try {
			validarCamera(Camera);
			
			boolean existeGrupo = existeCamera(Camera);
			if (existeGrupo) {
				throw new ServicoException("CAMERA_EXISTENTE");
			}
			
			cameraDAO.alterarCamera(Camera);
			
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServicoException(e.getMessage());
		}
			
			
	}

	@Override
	public Collection<Camera> listarCameras() throws ServicoException {
		
		try {
			
			return cameraDAO.listarCameras();
			
		} catch (DAOException e) {
			
			throw new ServicoException(e);			
		}
	}

	@Override
	public Collection<Camera> listarCameras(Camera filtro,Paginacao paginacao) throws ServicoException {

		try {
			
			return cameraDAO.listarCameras(filtro,paginacao);
			
		} catch (DAOException e) {
			
			throw new ServicoException(e);			
		}
	}


	@Override
	public void excluirCamera(Camera Camera) throws ServicoException {
		
		try {
			
			cameraDAO.excluirCamera(Camera);
			
		} catch (DAOException e) {
			
			throw new ServicoException(e);		
		} 
		
	}
	
	
	private void validarCamera(Camera camera) throws ServicoException {
		ServicoException se = new ServicoException();

		if (DadosUtil.isEmpty(camera.getCodigo())) {
			se.adicionarMensagem("CODIGO_NAO_VAZIO");
		}
		if (DadosUtil.isEmpty(camera.getNome())) {
			se.adicionarMensagem("NOME_NAO_VAZIO");
		}
		if (DadosUtil.isEmpty(camera.getMarca())) {
			se.adicionarMensagem("MARCA_NAO_VAZIO");
		}
		
		if(camera.isAutenticacao()){
			
			if (DadosUtil.isEmpty(camera.getUsuario())) {
				se.adicionarMensagem("USUARIO_NAO_VAZIO");
			}
			
			if (DadosUtil.isEmpty(camera.getSenha())) {
				se.adicionarMensagem("SENHA_NAO_VAZIO");
			}
			
		}
		
		if (DadosUtil.isEmpty(camera.getIp())) {
			se.adicionarMensagem("URL_NAO_VAZIO");
		}else if(!isValidURL(camera.getIp())){
			se.adicionarMensagem("URL_INVALIDA");
		}

		if (se.existeErro()) {
			throw se;
		}
	}
	
	public boolean isValidURL(String url) {  

	    URL u = null;

	    try {  
	        u = new URL(url);  
	    } catch (MalformedURLException e) {  
	        return false;  
	    }

	    try {  
	        u.toURI();  
	    } catch (URISyntaxException e) {  
	        return false;  
	    }  

	    return true;  
	}
	
	private boolean existeCamera(Camera camera) throws ServicoException {
		try {
			if (!DadosUtil.isEmpty(camera.getIdCamera())) {
				Camera cameraAux = cameraDAO.selectById(camera.getIdCamera());
				if (cameraAux.getCodigo().equalsIgnoreCase(camera.getCodigo())) {
					return false;
				}
			}

			Camera filtro = new Camera();
			filtro.setCodigo(camera.getCodigo());

			int qtdeClientes = cameraDAO.consultarQtde(filtro);
			return qtdeClientes > 0;

		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServicoException(e.getMessage());
		}
	}

	@Override
	public Collection<Camera> listarCamerasSemArduino() throws ServicoException {
		
		try {
			
			return cameraDAO.listarCamerasSemArduino();
			
		} catch (DAOException e) {
			
			throw new ServicoException(e);			
		}
	}

}

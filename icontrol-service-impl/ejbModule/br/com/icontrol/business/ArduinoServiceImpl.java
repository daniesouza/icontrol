package br.com.icontrol.business;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import br.com.icontrol.dao.ArduinoDAOLocal;
import br.com.icontrol.entity.Arduino;
import br.com.icontrol.entity.Camera;
import br.com.icontrol.entity.Cliente;
import br.com.icontrol.entity.Comando;
import br.com.icontrol.entity.Componente;
import br.com.icontrol.entity.Grupo;
import br.com.icontrol.entity.Usuario;
import br.com.icontrol.exceptions.DAOException;
import br.com.icontrol.exceptions.ServicoException;
import br.com.icontrol.util.DadosUtil;
import br.com.icontrol.util.Paginacao;
import br.com.icontrol.util.PermissaoUtil;

/**
 * @author Icontrol
 * Session Bean implementation class ArduinoServiceImpl
 */
@Stateless
@Local(value=ArduinoServiceLocal.class)
public class ArduinoServiceImpl implements ArduinoServiceLocal {

    /**
     * Default constructor. 
     */
    public ArduinoServiceImpl() {
        // TODO Auto-generated constructor stub
    }
    
	@EJB
	private ArduinoDAOLocal arduinoDAO;
	@EJB
	private GenericServiceLocal servicoGenerico;
	
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
	
    
	private void validarArduino(Arduino arduino) throws ServicoException {
		ServicoException se = new ServicoException();

		if (DadosUtil.isEmpty(arduino.getCodigo())) {
			se.adicionarMensagem("CODIGO_NAO_VAZIO");
		}
		if (DadosUtil.isEmpty(arduino.getNome())) {
			se.adicionarMensagem("NOME_NAO_VAZIO");
		}
		
		if (arduino.getAndar() == null) {
			se.adicionarMensagem("ANDAR_NAO_VAZIO");
		}
		
		if (DadosUtil.isEmpty(arduino.getIp())) {
			se.adicionarMensagem("IP_NAO_VAZIO");
		}
		
		if (DadosUtil.isEmpty(arduino.getPorta())) {
			se.adicionarMensagem("PORTA_NAO_VAZIO");
		}

		if (se.existeErro()) {
			throw se;
		}
	}
	
	private boolean existeArduino(Arduino arduino) throws ServicoException {
		try {
			if (!DadosUtil.isEmpty(arduino.getIdArduino())) {
				Arduino arduinoAux = arduinoDAO.selectById(arduino.getIdArduino());
				if (arduinoAux.getCodigo().equalsIgnoreCase(arduino.getCodigo())) {
					return false;
				}
			}

			Arduino filtro = new Arduino();
			filtro.setCodigo(arduino.getCodigo());
			int qtdeArduinos = arduinoDAO.consultarQtde(filtro);
			return qtdeArduinos > 0;

		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServicoException(e.getMessage());
		}
	}
    

	@Override
	public void salvarArduino(Arduino arduino,List<Camera> listaRemoverCameras) throws ServicoException {
		
		try {
			validarArduino(arduino);
			
			boolean existeArduino = existeArduino(arduino);
			if (existeArduino) {
				throw new ServicoException("ARDUINO_EXISTENTE");
			}
									
			arduinoDAO.salvarArduino(arduino);
			
			for(Camera camera:arduino.getCameras()){
				
				camera.setArduino(arduino);
				
				servicoGenerico.atualizar(camera);

			}
			
			for(Camera camera:listaRemoverCameras){
				camera = servicoGenerico.obterEntidade(camera.getId(), Camera.class);	
				camera.setArduino(null);
				servicoGenerico.atualizar(arduino);
			}
				
			} catch (DAOException e) {
				
				throw new ServicoException(e);				
			} 
		
			
	}

	@Override
	public void alterarArduino(Arduino arduino,List<Camera> listaRemoverCameras) throws ServicoException {
		

		try {
			
			validarArduino(arduino);
			
			boolean existeArduino = existeArduino(arduino);
			if (existeArduino) {
				throw new ServicoException("ARDUINO_EXISTENTE");
			}
										
			arduinoDAO.alterarArduino(arduino);
			
			for(Camera camera:arduino.getCameras()){
				
				camera.setArduino(arduino);
				
				servicoGenerico.atualizar(camera);
			}
			
			for(Camera camera:listaRemoverCameras){
				camera = servicoGenerico.obterEntidade(camera.getId(), Camera.class);	
				camera.setArduino(null);
				servicoGenerico.atualizar(arduino);
			}
			
		} catch (DAOException e) {
			
			throw new ServicoException(e);
			
		}
			
	}

	@Override
	public Collection<Arduino> listarArduinos() throws ServicoException {
		
		try {
			
			return arduinoDAO.listarArduinos();
			
		} catch (DAOException e) {
			
			throw new ServicoException(e);
			
		}
	}
	
	@Override
	public Collection<Arduino> listarArduinosSemCliente() throws ServicoException {
		
		try {
			
			return arduinoDAO.listarArduinosSemCliente();
			
		} catch (DAOException e) {
			
			throw new ServicoException(e);
			
		}
	}
	

	@Override
	public Collection<Arduino> listarArduinos(Arduino filtro,Paginacao paginacao) throws ServicoException {

		try {
			
			return arduinoDAO.listarArduinos(filtro,paginacao);
			
		} catch (DAOException e) {
			
			throw new ServicoException(e);
			
		}
	}


	@Override
	public void excluirArduino(Arduino arduino) throws ServicoException {
		
		try {
			
			arduinoDAO.excluirArduino(arduino);
			
		} catch (DAOException e) {
			
			throw new ServicoException(e);
			
		} 
		
	}

	@Override
	public Arduino selectById(Long id) throws ServicoException {
		
		try {
			
			return arduinoDAO.selectById(id);
			
		} catch (DAOException e) {
			
			throw new ServicoException(e);
			
		} 
	}
	
	@Override
	public Collection<Arduino> obterArduinosPermissaoAdmin(Cliente cliente) throws ServicoException {

		if (DadosUtil.isEmpty(cliente)) {
			throw new ServicoException("CLIENTE_NAO_VAZIO");
		}
		
		Collection<Arduino> listaArduinosAcesso = new ArrayList<Arduino>();
		
		Arduino filtro = new Arduino();
		filtro.setCliente(new Cliente());
		filtro.getCliente().setId(cliente.getId());

		try {
			
			listaArduinosAcesso = arduinoDAO.listarArduinos(filtro,new Paginacao(0));
			
			for(Arduino ard:listaArduinosAcesso){
				
				Arduino arduino = servicoGenerico.obterListaLazy(ard, Arduino.class,"cameras");
				
				ard.setCameras(arduino.getCameras());
			}
			
			List<Arduino> listaRemoverArd = new ArrayList<Arduino>();
			
			for(Arduino ard:listaArduinosAcesso){
				
				Arduino arduino = servicoGenerico.obterListaLazy(ard, Arduino.class,"componentes");
				
				List<Componente> listaRemover = new ArrayList<Componente>();
				
				for(Componente comp:arduino.getComponentes()){
					
					Componente componente = servicoGenerico.obterListaLazy(comp, Componente.class,"comandos");
					
					if(!componente.getComandos().isEmpty()){
						comp.setComandos(componente.getComandos());
					}else{
						listaRemover.add(comp);
					}
				}
				
				if(!listaRemover.isEmpty()){
					arduino.getComponentes().removeAll(listaRemover);
				}
				
				if(!arduino.getComponentes().isEmpty()){					
					ard.setComponentes(arduino.getComponentes());
				}else{
					listaRemoverArd.add(arduino);
				}
				
			}
			
			if(!listaRemoverArd.isEmpty()){
				listaArduinosAcesso.removeAll(listaRemoverArd);
			}
			
		
		} catch (DAOException e) {
			
			throw new ServicoException(e);
			
		} 
		
		return listaArduinosAcesso;

	}

	@Override
	public Collection<Arduino> obterArduinosPermissoesPorUsuario(Usuario usuario) throws ServicoException {
		
		
		Set<Arduino>  arduinosAcesso = new HashSet<Arduino>();
		Collection<Arduino> listaArduinosAcesso = new ArrayList<Arduino>();
		
		try {
			usuario = servicoGenerico.obterListaLazy(usuario, Usuario.class, "grupos");
			

			
			for(Grupo grupo:usuario.getGrupos()){
				
				if(DadosUtil.isEmpty(grupo.getArduinos())){
					continue;
				}
				
				for(Arduino arduino:grupo.getArduinos()){
					arduinosAcesso.add(arduino);
				}
			}
			
			if(arduinosAcesso.isEmpty()){
				return listaArduinosAcesso;
			}
			
			Iterator<Arduino> it = arduinosAcesso.iterator();

			
			while (it.hasNext()) {
				Arduino arduino = (Arduino) it.next();
				
				if(DadosUtil.isEmpty(arduino.getComponentes())){
					continue;
				}
				
				List<Componente> listaRemoverComp = new ArrayList<Componente>();
				
				for(Componente comp:arduino.getComponentes()){
													
					List<Comando> listaRemoverCom = new ArrayList<Comando>();
					
					if(DadosUtil.isEmpty(comp.getComandos())){
						continue;
					}
					
					for(Comando comando:comp.getComandos()){
						if(!PermissaoUtil.existePermissao(usuario,arduino,comp, comando)){
							
							listaRemoverCom.add(comando);
						}
					}
					
					servicoGenerico.detach(comp);
					comp.getComandos().removeAll(listaRemoverCom);			
					
					if(comp.getComandos().isEmpty()){
						listaRemoverComp.add(comp);
					}
				}
				
				if(!listaRemoverComp.isEmpty()){
					arduino.getComponentes().removeAll(listaRemoverComp);
				}
				
				listaArduinosAcesso.add(arduino);
			}
			
			for(Arduino ard:listaArduinosAcesso){
				
				Arduino arduino = servicoGenerico.obterListaLazy(ard, Arduino.class,"cameras");
				
				ard.setCameras(arduino.getCameras());
			}
			
			return listaArduinosAcesso;
		
		} catch (ServicoException e) {
			throw e;
		}
	}

}

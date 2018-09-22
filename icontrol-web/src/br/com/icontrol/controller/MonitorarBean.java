package br.com.icontrol.controller;

import java.io.IOException;
import java.io.Serializable;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.icontrol.business.ArduinoServiceLocal;
import br.com.icontrol.business.AtalhoServiceLocal;
import br.com.icontrol.business.ClienteServiceLocal;
import br.com.icontrol.entity.Arduino;
import br.com.icontrol.entity.Atalho;
import br.com.icontrol.entity.AtalhoArdCompComando;
import br.com.icontrol.entity.Cliente;
import br.com.icontrol.entity.Comando;
import br.com.icontrol.entity.Componente;
import br.com.icontrol.entity.Monitor;
import br.com.icontrol.entity.Usuario;
import br.com.icontrol.exceptions.ServicoException;
import br.com.icontrol.exceptions.WebException;
import br.com.icontrol.util.ClienteSocket;
import br.com.icontrol.util.Constantes;
import br.com.icontrol.util.DadosUtil;
import br.com.icontrol.util.FacesUtil;
import br.com.icontrol.util.Paginacao;

/**
 * @author Icontrol
 *      
 */

@ViewScoped
@ManagedBean
public class MonitorarBean extends AbstractViewHelper<Monitor> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6147860517223993075L;

	
	@EJB
	private ArduinoServiceLocal arduinoService;
	
	@EJB
	private ClienteServiceLocal clienteService;
	
	@EJB
	private AtalhoServiceLocal atalhoService;
	
	private List<Arduino> arduinosAcesso;
	
	private List<Cliente> listaClientes;
	
	private List<Atalho> listaAtalhos;
	
	private String andar;
	
	private String loginPass = "user=admin&pwd=admin";
	private String caracterEspecial = "&";

	

	@Override
	public void inicializar() throws WebException {
		
		setEntidade(new Monitor());
		
		Usuario usuario = FacesUtil.recuperarUsuarioSessao();

		// SE O USUARIO FOR ADMIN GERAL NAO TERA UM CLIENTE ASSOCIADO
		if(!DadosUtil.isEmpty(usuario.getCliente())){				
			iniciarMonitoramentoCliente();	
			iniciarAtalhos();
		}else{
			
			try {	
				listaClientes = (List<Cliente>) clienteService.listarClientes();
			} catch (ServicoException e) {				
				throw new WebException(e);
			}
		}

	}
	
	public void iniciarMonitoramentoCliente() throws WebException{
		
		try {
			Usuario usuario = FacesUtil.recuperarUsuarioSessao();
			arduinosAcesso = (List<Arduino>) arduinoService.obterArduinosPermissoesPorUsuario(usuario);
			conectarArduinos();
			
		} catch (ServicoException e) {			
			throw new WebException(e);
		}
	}
	
	public void iniciarAtalhos() throws WebException{	
		try {
			Atalho filtro = new Atalho();
			Usuario usuario = FacesUtil.recuperarUsuarioSessao();
			
			filtro.setUsuario(new Usuario());
			filtro.getUsuario().setId(usuario.getId());		
			listaAtalhos = (List<Atalho>) atalhoService.listarAtalhos(filtro, new Paginacao(0));
		} catch (ServicoException e) {			
			throw new WebException(e);
		}
	}
	
	
	public void iniciarMonitoramentoAdmin(){
		
		FacesMessage msg = null;
		
		try {
			if(!DadosUtil.isEmpty(entidade.getCliente())){
				arduinosAcesso = (List<Arduino>) arduinoService.obterArduinosPermissaoAdmin(entidade.getCliente());
				conectarArduinos();	
			}
			
		} catch (ServicoException e) {
			msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "FALHA AO INICIAR MONITORAMENTO ", e.getMessage());
			e.printStackTrace();
		}
		
		if( msg != null ) {
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	
	public void filtrarArduino(){
				
		for(Arduino arduino:arduinosAcesso){
			
			if(andar.equals("")){
				arduino.setHide(false);
			}				
			else if(Integer.parseInt(andar) == arduino.getAndar()){
				arduino.setHide(false);
			}else{
				arduino.setHide(true);
			}
		}
		
	}
	
	public void conectarArduinos(){
				
		for(Arduino arduino:arduinosAcesso){			
			arduino.setClienteSocket(new ClienteSocket());
		}
			
	}
	
	public void executarAtalho(Atalho atalho){
	
		FacesMessage msg = null;
		
		try{
			
		     List<AtalhoArdCompComando> listaSemPermissao = new ArrayList<AtalhoArdCompComando>();
			 List<AtalhoArdCompComando> list = atalhoService.obterAtalhoArdCompComando(atalho.getId());
			 
			 
			 for(AtalhoArdCompComando atalhoArdCompComando:list){
				 
				 boolean possuiPermissao = false;
				 
				 for(Arduino arduino:arduinosAcesso){
					 
					 if(arduino.equals(atalhoArdCompComando.getArduino())){
						 
						 for(Componente comp:arduino.getComponentes()){
							 
							 if(comp.equals(atalhoArdCompComando.getComponente())){
								 
								 for(Comando comando:comp.getComandos()){
									 
									 if(comando.equals(atalhoArdCompComando.getComando())){
										 possuiPermissao = true;
																			 
										 break;
									 }
								 }
								 
								 break;
							 }
						 }
						 
						 break;
					 }
				 }
				 
				 if(!possuiPermissao){
					 listaSemPermissao.add(atalhoArdCompComando);
				 }
			 }
			 
			 if(!listaSemPermissao.isEmpty()){
				 
				 list.removeAll(listaSemPermissao);
			 }
			 

			 for(AtalhoArdCompComando atalhoArdCompComando:list){
				 
				 atalhoArdCompComando.getArduino().setClienteSocket(new ClienteSocket());
				 
				 executarComando(atalhoArdCompComando.getComando(), atalhoArdCompComando.getArduino(),atalhoArdCompComando.getComponente());
				 
				 for(Arduino arduino:arduinosAcesso){
					 
					 if(arduino.equals(atalhoArdCompComando.getArduino())){
						 
						 for(Componente comp:arduino.getComponentes()){
							 
							 if(comp.equals(atalhoArdCompComando.getComponente())){
								 
								 comp.setStatusPorta(atalhoArdCompComando.getComponente().getStatusPorta());
								 break;
							 }
						 }
						 
						 break;
					 }
				 }
			 }
			 
		} catch (ServicoException e) {			
			
			msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERRO AO EXECUTAR ATALHO "+atalho.getNome(), e.getMessage());
			e.printStackTrace();
		}
		
		if( msg != null ) {
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	
	
	public synchronized void executarComando(Comando comando,Arduino arduino,Componente comp){
		
		FacesMessage msg = null;
		
		try {
			
			arduino.getClienteSocket().abrirConexao(arduino.getIp(), arduino.getPorta());
			
			arduino.getClienteSocket().enviarMensagem("exec;"+comp.getPortaArduino()+";"+comando.getCodigo()+";"+comando.getStatus()+";"+comando.getTipo()+";"+comando.getCodificacao());
			
			String mensagem = arduino.getClienteSocket().receberMensagem();

			int codigoMensagem = Integer.parseInt(mensagem);
				
			if(codigoMensagem == Constantes.COMANDO_RECEBIDO){
				//msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "COMANDO "+comando.getNome() +" ENVIADO!","ARDUINO "+arduino.getNome());
				comp.setStatusPorta(comando.getStatus());
				gravarLog("Comando: "+comando.getCodigo()+" | Componente: "+comp.getCodigo()+" - "+comp.getNome() +" | Arduino: "+arduino.getCodigo() +" - "+arduino.getNome(),Constantes.COMANDO);
			}else if(codigoMensagem == Constantes.COMANDO_NAO_RECONHECIDO){
				comp.setStatusPorta("DESCONHECIDO");
				throw new IOException("COMANDO NÃO RECONHECIDO!");
			}	
			
			arduino.getClienteSocket().fecharConexao();
			
		} catch (UnknownHostException e) {
			msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERRO AO EXECUTAR COMANDO "+comando.getNome() +" NO ARDUINO "+arduino.getNome(), e.getMessage());
		//	e.printStackTrace();
		} catch (IOException e) {
			msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERRO AO EXECUTAR COMANDO "+comando.getNome() +" NO ARDUINO "+arduino.getNome(), e.getMessage());
		//	e.printStackTrace();
		} catch (Exception e) {
			msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERRO AO EXECUTAR COMANDO "+comando.getNome() +" NO ARDUINO "+arduino.getNome(), e.getMessage());
		//	e.printStackTrace();
		}
		
		if( msg != null ) {
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		
	}
	
	
	public void checkStatus(Arduino arduino){
		
		try{
			
			arduino.getClienteSocket().abrirConexao(arduino.getIp(), arduino.getPorta());
			String[] statusPortas = arduino.getClienteSocket().checkStatus();
		
			
			for(int index=0;index<statusPortas.length;index++){
				
				for(Componente comp:arduino.getComponentes()){
					
					if(index == comp.getPortaArduino()){
						
						if(statusPortas[index] != null && !statusPortas[index].equals("") && !statusPortas[index].equals(Constantes.COMANDO_STATUS_DESCONHECIDO)){
							comp.setStatusPorta(statusPortas[index]);
						}else{
							comp.setStatusPorta("DESCONHECIDO");
						}												
					}
									
				}
				
			}
			
			arduino.getClienteSocket().fecharConexao();						
			
		} catch (UnknownHostException e) {
			System.out.println("Arduino "+arduino.getNome() +" Fora do ar " + e.getMessage());
		} catch (IOException e) {
			System.out.println("Arduino "+arduino.getNome() +" Fora do ar " + e.getMessage());
		}catch (Exception e) {
			System.out.println("Arduino "+arduino.getNome() +" Fora do ar " + e.getMessage());
		}
		
	}


	@Override
	public void salvarImpl() throws ServicoException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void alterarImpl() throws ServicoException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void excluirImpl() throws ServicoException {
	
	}


	@Override
	public void pesquisar() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}


	public List<Arduino> getArduinosAcesso() {
		return arduinosAcesso;
	}


	public void setArduinosAcesso(List<Arduino> arduinosAcesso) {
		this.arduinosAcesso = arduinosAcesso;
	}

	public List<Cliente> getListaClientes() {
		return listaClientes;
	}

	public void setListaClientes(List<Cliente> listaClientes) {
		this.listaClientes = listaClientes;
	}

	public String getAndar() {
		return andar;
	}

	public void setAndar(String andar) {
		this.andar = andar;
	}

	public String getLoginPass() {
		return loginPass;
	}

	public void setLoginPass(String loginPass) {
		this.loginPass = loginPass;
	}

	public String getCaracterEspecial() {
		return caracterEspecial;
	}

	public void setCaracterEspecial(String caracterEspecial) {
		this.caracterEspecial = caracterEspecial;
	}

	public List<Atalho> getListaAtalhos() {
		return listaAtalhos;
	}

	public void setListaAtalhos(List<Atalho> listaAtalhos) {
		this.listaAtalhos = listaAtalhos;
	}
	
	

}

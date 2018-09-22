package br.com.icontrol.business;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Timer;
import javax.ejb.TimerService;

import br.com.icontrol.entity.Agendamento;
import br.com.icontrol.entity.Arduino;
import br.com.icontrol.entity.Atalho;
import br.com.icontrol.entity.AtalhoArdCompComando;
import br.com.icontrol.entity.Comando;
import br.com.icontrol.entity.Componente;
import br.com.icontrol.entity.Log;
import br.com.icontrol.entity.Usuario;
import br.com.icontrol.exceptions.ServicoException;
import br.com.icontrol.util.ClienteSocket;
import br.com.icontrol.util.Constantes;
import br.com.icontrol.util.Paginacao;

@Singleton
public class TimerSessionImpl{
  
	@Resource
    TimerService timerService;
	
	
	@EJB
	private AgendamentoServiceLocal agendamentoService;
	
	@EJB
	private ArduinoServiceLocal arduinoService;
	
	@EJB
	private AtalhoServiceLocal atalhoService;
	
	@EJB
	protected LogServiceLocal  logService;
	
	
  
    
//    public void setTimer(long intervalDuration) {
//        System.out.println("Setting a programmatic timeout for "
//                + intervalDuration + " milliseconds from now.");
//        timerService.createTimer(intervalDuration,"Created new programmatic timer");
//    }
//    
//    @Timeout
//    public void programmaticTimeout(Timer timer) {
//        System.out.println("Programmatic timeout occurred.");
//    }

    @Schedule(minute="*/5", hour="*",second="0")
    public void automaticTimeout() {
       
    	
    	executarAgendamentos();
    }
    
    @PreDestroy
    public void stop() {
        for(Object obj : timerService.getTimers()) {
            Timer t = (Timer)obj;
            t.cancel();
           
        }
    }
    
    private void executarAgendamentos(){
    	
    	Agendamento filtro = new Agendamento();
    	
    	Calendar calendario = Calendar.getInstance();  
        switch (calendario.get(Calendar.DAY_OF_WEEK)) {  
        case 1:  
            //DOMINGO-FEIRA  
        	filtro.setDomingo(true);
            break;  
        case 2:  
            //SEGUNDA-FEIRA  
        	filtro.setSegunda(true);
            break;  
        case 3:  
            //TERCA-FEIRA  
        	filtro.setTerca(true);  
            break;  
        case 4:  
            //QUARTA-FEIRA  
        	filtro.setQuarta(true);  
            break;  
        case 5:  
            //QUINTA-FEIRA  
        	filtro.setQuinta(true); 
            break;  
        case 6:  
            //SEXTA-FEIRA  
        	filtro.setSexta(true); 
            break;  
        case 7:  
            //SABADO  
        	filtro.setSabado(true);  
            break;  
  
        } 
        
        calendario.set(Calendar.DAY_OF_MONTH, 1);
        calendario.set(Calendar.MONTH, 0);
        calendario.set(Calendar.YEAR, 1970);
        calendario.set(Calendar.SECOND, 0);
        
        filtro.setHorario(calendario.getTime());
    		
    	
    	try { 
			List<Agendamento> lista = (List<Agendamento>) agendamentoService.listarAgendamentos(filtro, new Paginacao(0));
			
			for(Agendamento agendamento:lista){
				
				for(Atalho atalho:agendamento.getAtalhos()){
					
					List<Arduino> arduinosAcesso = (List<Arduino>) arduinoService.obterArduinosPermissoesPorUsuario(agendamento.getUsuario());
					
					
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
						 
						 executarComando(atalhoArdCompComando.getComando(), atalhoArdCompComando.getArduino(),atalhoArdCompComando.getComponente(),agendamento.getUsuario());
						 
					 }
	
				}
			}
						
		} catch (ServicoException e) {
			e.printStackTrace();
		}
    }
    
	public synchronized void executarComando(Comando comando,Arduino arduino,Componente comp,Usuario usuario){
		
		
		try {
			
			arduino.getClienteSocket().abrirConexao(arduino.getIp(), arduino.getPorta());
			
			arduino.getClienteSocket().enviarMensagem("exec;"+comp.getPortaArduino()+";"+comando.getCodigo()+";"+comando.getStatus()+";"+comando.getTipo()+";"+comando.getCodificacao());
			
			String mensagem = arduino.getClienteSocket().receberMensagem();

			int codigoMensagem = Integer.parseInt(mensagem);
				
			if(codigoMensagem == Constantes.COMANDO_RECEBIDO){
				//msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "COMANDO "+comando.getNome() +" ENVIADO!","ARDUINO "+arduino.getNome());
				comp.setStatusPorta(comando.getStatus());
				gravarLog("Comando: "+comando.getCodigo()+" | Componente: "+comp.getCodigo()+" - "+comp.getNome() +" | Arduino: "+arduino.getCodigo() +" - "+arduino.getNome(),Constantes.COMANDO,usuario);
			}else if(codigoMensagem == Constantes.COMANDO_NAO_RECONHECIDO){
				comp.setStatusPorta("DESCONHECIDO");
				throw new IOException("COMANDO NÃO RECONHECIDO!");
			}	
			
			arduino.getClienteSocket().fecharConexao();
			
		} catch (UnknownHostException e) {
			System.out.println("ERRO AO EXECUTAR COMANDO "+comando.getNome() +" NO ARDUINO "+arduino.getNome()+" "+e.getMessage());
		//	e.printStackTrace();
		} catch (IOException e) {
			System.out.println("ERRO AO EXECUTAR COMANDO "+comando.getNome() +" NO ARDUINO "+arduino.getNome()+" "+e.getMessage());
		//	e.printStackTrace();
		} catch (Exception e) {
			System.out.println("ERRO AO EXECUTAR COMANDO "+comando.getNome() +" NO ARDUINO "+arduino.getNome()+" "+e.getMessage());
		//	e.printStackTrace();
		}
		
	}
	
	
	public void gravarLog(String descricao,int tipo,Usuario usuario){
		
		Log log = new Log();
		
		log.setTipo(tipo);
		log.setDescricao(descricao);
		log.setDtCad(new Date());
		log.setUsuario(usuario);
		
		try {
			logService.salvarLog(log);
		} catch (ServicoException e) {
			e.printStackTrace();
		}
	}

}

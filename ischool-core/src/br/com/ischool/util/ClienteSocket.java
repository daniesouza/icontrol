package br.com.ischool.util;

import java.io.*;
import java.net.*;

/**
 * @author Daniel Souza de lima e-mail:daniesouza@gmail.com
 *      
 */
public class ClienteSocket implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1302189189223070108L;
	private Socket requestSocket;
	private PrintStream out = null; 
	private BufferedReader in=null; 

	public ClienteSocket() {
	}

	public void abrirConexao(String ip,int porta) throws UnknownHostException, IOException{
		
	
		requestSocket = new Socket(ip, porta);
		System.out.println("Conectado a "+ip+" na porta "+porta);
		
		out = new PrintStream(requestSocket.getOutputStream());				
		
	}
	
	public void fecharConexao() throws IOException{
		enviarMensagem("desconectar");
		in.close();
		out.close();
		requestSocket.close();
	}
	
	public String receberMensagem() throws IOException{
		
		if(in == null){
			in = new BufferedReader(new InputStreamReader(requestSocket.getInputStream()));		
		}
		
		return in.readLine();
	}

	public void enviarMensagem(String msg) throws IOException {
		out.println(msg);
		out.flush();

	}

	public PrintStream getOut() {
		return out;
	}

	public void setOut(PrintStream out) {
		this.out = out;
	}
	
	

}

package br.com.icontrol.util;

import java.io.*;
import java.net.*;

/**
 * @author Icontrol
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
	private boolean connected;

	public ClienteSocket() {
	}

	public synchronized void abrirConexao(String ip,int porta) throws UnknownHostException,IOException,Exception{

		try {
			requestSocket = new Socket(ip, porta);
			System.out.println("Conectado a "+ip+" na porta "+porta);
			
			requestSocket.setSoTimeout(5000);
			
			out = new PrintStream(requestSocket.getOutputStream());		
			
			connected = true;
		}catch(UnknownHostException e){
			connected = false;
			throw e;
		}catch(IOException e){
			connected = false;
			throw e;
		}
		catch(Exception e){
			connected = false;
			throw e;
		}

	}
	
	public synchronized void fecharConexao() throws IOException{
				
			if(in != null){
				in.close();
			}
			
			if(out != null){
				out.close();
			}

			requestSocket.close();

			in = null;
			out = null;
			requestSocket = null;
	}
	
	public synchronized String receberMensagem() throws IOException{
		

		in = new BufferedReader(new InputStreamReader(requestSocket.getInputStream()));		

		
		return in.readLine();
	}

	public synchronized void enviarMensagem(String msg) throws IOException {
				
			out.println(msg);
			out.flush();

	}

	public PrintStream getOut() {
		return out;
	}

	public void setOut(PrintStream out) {
		this.out = out;
	}

	
	public synchronized String[] checkStatus() throws IOException,Exception{					
		
		String status="";
		
		try {

				enviarMensagem("STATUS");
				
				status = receberMensagem();
				
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
	
				connected = true;				
				
		} catch (IOException e) {
			connected = false;
			throw e;
		} catch (Exception e) {
			connected = false;
			throw e;
		}			
		
		return status.split(";");
	}

	public boolean isConnected() {
		return connected;
	}

	public void setConnected(boolean connected) {
		this.connected = connected;
	}



}

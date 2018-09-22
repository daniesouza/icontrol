package br.com.ischool.util;
import java.io.*;
import java.net.*;

/**
 * @author Daniel Souza de lima e-mail:daniesouza@gmail.com
 *      
 */
public class Servidor implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2471272228197215875L;
	public ServerSocket providerSocket;
	public Socket connection = null;
	public PrintStream out   = null; 
	public BufferedReader in = null; 
	public String mensagem;

	public void run()
	{
		try{
			//1. Criando Server Socket
			providerSocket = new ServerSocket(2004, 10);
			//2. Wait for connection
			System.out.println("AGUARDANDO CONEXAO....");
			connection = providerSocket.accept();
			System.out.println("CONEXAO RECEBIDA DE " + connection.getInetAddress().getHostName());
			//3. get Input and Output streams
			out = new PrintStream(connection.getOutputStream());
			
			enviarMensagem("CONEXAO ESTABELECIDA!");
			
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

			do{

				mensagem = in.readLine();
				System.out.println("client>" + mensagem);
	
				enviarMensagem("Comando "+mensagem+" Recebido");

			}while(!mensagem.equals("desconectar"));
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		}
		finally{
			try{
				in.close();
				out.close();
				providerSocket.close();
			}
			catch(IOException ioException){
				ioException.printStackTrace();
			}
		}
	}
	public void enviarMensagem(String msg)
	{
		out.println(msg);
		out.flush();
	}
	public static void main(String args[])
	{
		Servidor server = new Servidor();
		while(true){
			server.run();
		}
	}
}
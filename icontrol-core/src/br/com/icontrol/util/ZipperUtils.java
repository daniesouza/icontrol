package br.com.icontrol.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * @author Icontrol
 * 
 */

public class ZipperUtils {
	
	private File arquivoZipAtual;
	
	public void extrairZip(File diretorio) throws ZipException, IOException {
		extrairZip(arquivoZipAtual, diretorio);
	}

	public void extrairZip(File arquivoZip, File diretorio)	throws ZipException, IOException {
		
		ZipFile zip = null;
		File arquivo = null;
		InputStream is = null;
		OutputStream os = null;
		byte[] buffer = new byte[Constantes.TAMANHO_BUFFER];
		try {
			// cria diretório informado, caso não exista
			if (!diretorio.exists()) {
				diretorio.mkdirs();
			}
			if (!diretorio.exists() || !diretorio.isDirectory()) {
				throw new IOException("Informe um diretório válido");
			}
			
			zip = new ZipFile(arquivoZip);
			
			Enumeration<?> e = zip.entries();
			
			while (e.hasMoreElements()) {
				
				ZipEntry entrada = (ZipEntry) e.nextElement();
				
				arquivo = new File(diretorio, entrada.getName());
				
				// se for diretório inexistente, cria a estrutura
				// e pula pra próxima entrada
				
				if (entrada.isDirectory() && !arquivo.exists()) {
					arquivo.mkdirs();
					continue;
				}
				// se a estrutura de diretórios não existe, cria
				if (!arquivo.getParentFile().exists()) {
					arquivo.getParentFile().mkdirs();
				}
				
				try {
					// lê o arquivo do zip e grava em disco
					is = zip.getInputStream(entrada);
					os = new FileOutputStream(arquivo);
					int bytesLidos = 0;
					if (is == null) {
						throw new ZipException("Erro ao ler a entrada do zip: "
								+ entrada.getName());
					}
					while ((bytesLidos = is.read(buffer)) > 0) {
						os.write(buffer, 0, bytesLidos);
					}
				} finally {
					if (is != null) {
						try {
							is.close();
						} catch (Exception ex) {
						}
					}
					if (os != null) {
						try {
							os.close();
						} catch (Exception ex) {
						}
					}
				}
			}
		} finally {
			if (zip != null) {
				try {
					zip.close();
				} catch (Exception e) {
				}
			}
		}
	}
	
	
	public static byte[] criarZip(Map<String,byte[]> files) throws Exception {
		
		  
	                
	    try {  
	    	
	    	ByteArrayOutputStream output = new ByteArrayOutputStream();
	    	
	        ZipOutputStream out = new ZipOutputStream(output);   

	        // Ajusta modo de compressão  
	        out.setLevel(Deflater.DEFAULT_COMPRESSION);  
	  
	        Iterator<String> iterator = files.keySet().iterator();
	       
	        
	        while (iterator.hasNext()) {
	        	
				String nomeArquivo = (String) iterator.next();
				
				byte[] bytes = files.get(nomeArquivo);
			
	          out.putNextEntry(new ZipEntry(nomeArquivo));  
 
	          out.write(bytes);  
	           
	          out.closeEntry();  
	        }

	        output.close();
	        out.close(); 
	        
	        return  output.toByteArray();

	  
	    } catch (IllegalArgumentException iae) {  
	  
	        throw new Exception(iae);  
	  
	    } catch (Exception e) {  
	  
	        throw new Exception(e);  
	  
	    }
	      
	   
	
	}

	/** 
	* Metodo que compacta o arquivo csv gerado 
	*  
	*/  
	public static String criarZip(String diretorio,String nomeArqZip, File[] listaArquivos) throws Exception {  
	  
	    byte[] buffer = new byte[Constantes.TAMANHO_BUFFER];  
	  

	      
	    String zipCaminho = diretorio +"\\"+nomeArqZip+".zip";
	  
	                
	    try {  
	        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipCaminho));   

	        // Ajusta modo de compressão  
	        out.setLevel(Deflater.DEFAULT_COMPRESSION);  
	  
	        for(File file : listaArquivos) {  
	  
	         InputStream in = new FileInputStream(file);  
	                            
	          out.putNextEntry(new ZipEntry(file.getName()));  
	          int len;  
	          while ((len = in.read(buffer)) > 0) {  
	            out.write(buffer, 0, len);  
	          }  
	          out.closeEntry();  
	          in.close();  
	                     }    
	        out.close();  
	  
	    } catch (IllegalArgumentException iae) {  
	  
	        throw new Exception(iae);  
	  
	    } catch (Exception e) {  
	  
	        throw new Exception(e);  
	  
	    }  
	      
	    return zipCaminho; 

	}
	
	
}
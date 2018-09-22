package br.com.icontrol.exceptions;

/**
 * @author Icontrol
 *      
 */
public class WebException extends Exception {

	public WebException(Throwable e) {
		super(e);
	}

	public WebException(String str) {
		super(str);
	}	

	private static final long serialVersionUID = 1L;

}

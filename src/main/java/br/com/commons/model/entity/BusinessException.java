package br.com.commons.model.entity;

/**
 * Representa um erro ocorrido em uma classe de neg�cio. 
 */
public class BusinessException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Quando tiver uma mensagem a ser acrescentada no Stacktrace
	 * 
	 * @param msg
	 *            Mensagem que deve ser acrescentada no Stacktrace
	 * @param e
	 *            Exce��o causadora
	 */
	public BusinessException(String msg, Exception e) {
		super(msg, e);
	}
	/**
	 * Quando ocorrer um erro de neg�cio, sem ter ocorrido uma exce��o
	 * 
	 * @param msg
	 *            Mensagem que deve ser acrescentada no Stacktrace
	 */
	public BusinessException(String msg) {
		super(msg, null);
	}
	
	/**
	 * Quando a exce��o n�o for esperada ou n�o tiver mensagem espec�fica para ela
	 * 
	 * @param e
	 *            Exce��o causadora
	 */
	public BusinessException(Exception e) {
		super(e);
	}
}

package br.com.commons.model.entity;

/**
 * Representa um erro ocorrido em uma classe de negócio. 
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
	 *            Exceção causadora
	 */
	public BusinessException(String msg, Exception e) {
		super(msg, e);
	}
	/**
	 * Quando ocorrer um erro de negócio, sem ter ocorrido uma exceção
	 * 
	 * @param msg
	 *            Mensagem que deve ser acrescentada no Stacktrace
	 */
	public BusinessException(String msg) {
		super(msg, null);
	}
	
	/**
	 * Quando a exceção não for esperada ou não tiver mensagem específica para ela
	 * 
	 * @param e
	 *            Exceção causadora
	 */
	public BusinessException(Exception e) {
		super(e);
	}
}

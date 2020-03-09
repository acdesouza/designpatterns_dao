package br.com.commons.model.dao;

/**
 * Informar um erro originado na classe de implementação da persistência.
 * 
 * @author AC de Souza
 */
public class DAOException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Anexar uma causa ao erro ocorrido.
	 * @param e
	 */
	public DAOException(Exception e){
		super(e);
	}
	
	/**
	 * Anexar uma mensagem e uma causa ao erro ocorrido.
	 * 
	 * @param msg
	 *            Mensagem que deve ser acrescentada no Stacktrace
	 * @param e
	 *            Causa do erro
	 */
	public DAOException(String msg, Exception e) {
		super(msg, e);
	}
	
	/**
	 * Anexar uma mensagem ao erro ocorrido.
	 * 
	 * @param msg
	 *            Mensagem que deve ser acrescentada no Stacktrace
	 */
	public DAOException(String msg) {
		super(msg, null);
	}
}
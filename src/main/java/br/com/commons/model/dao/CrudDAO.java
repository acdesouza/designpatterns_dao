package br.com.commons.model.dao;

import java.util.List;


/**
 * Interface para facilitar a implementa��o dos metodos CRUD nos DAOs.
 * Ao herdar os m�todos desta interface, as DAOs das entidade t�m 4(quatro) m�todos � menos para declarar.
 * 
 * @param <Entidade>
 *            A classe da entidade a ser mapeada pelo DAO.
 * @param <Key>
 *            A classe do identificador da entidade a ser mapeada pelo DAO.
 * @author AC de Souza
 */

public interface CrudDAO<Entidade, Key> {

	/**
	 * Persistir uma entidade nova ou edita os dados de uma entidade persistente, previamente criada, do reposit�rio.
	 * 
	 * A decis�o � tomada, se cria ou edita, baseado no preenchimento do identificador do objeto.
	 * Caso esteja preenchido � feito uma edi��o, caso n�o esteja, � criado um novo.
	 * 
	 * @param 
	 * 		unsaved Entidade nova
	 * @throws DAOException Qualquer erro que ocorrer na grava��o dos dados.
	 */
	public void save(Entidade entidade) throws DAOException;

	/**
	 * Remover uma entidade persistente, previamente criada, do reposit�rio.
	 * 
	 * @param oid 
	 * 		identificador da entidade.
	 */
	public void remove(Key oid);

	/**
	 * Recuperar todas as entidades persistentes no reposit�rio.
	 * 
	 * @return Todas as entidades persistentes no reposit�rio.
	 */
	public List<Entidade> find();

	/**
	 * Recuperar a entidade persistente no reposit�rio que est� associada ao identificador passado como par�metro.  
	 * 
	 * @param key Identificador associado a entidade persistente no reposit�rio que se deseja recuperar.
	 * @return Entidade persistente no reposit�rio que est� associada ao identificador passado como par�metro.
	 */
	public Entidade find(Key key);
}
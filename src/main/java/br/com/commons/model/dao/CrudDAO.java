package br.com.commons.model.dao;

import java.util.List;


/**
 * Interface para facilitar a implementação dos metodos CRUD nos DAOs.
 * Ao herdar os métodos desta interface, as DAOs das entidade têm 4(quatro) métodos à menos para declarar.
 * 
 * @param <Entidade>
 *            A classe da entidade a ser mapeada pelo DAO.
 * @param <Key>
 *            A classe do identificador da entidade a ser mapeada pelo DAO.
 * @author AC de Souza
 */

public interface CrudDAO<Entidade, Key> {

	/**
	 * Persistir uma entidade nova ou edita os dados de uma entidade persistente, previamente criada, do repositório.
	 * 
	 * A decisão é tomada, se cria ou edita, baseado no preenchimento do identificador do objeto.
	 * Caso esteja preenchido é feito uma edição, caso não esteja, é criado um novo.
	 * 
	 * @param 
	 * 		unsaved Entidade nova
	 * @throws DAOException Qualquer erro que ocorrer na gravação dos dados.
	 */
	public void save(Entidade entidade) throws DAOException;

	/**
	 * Remover uma entidade persistente, previamente criada, do repositório.
	 * 
	 * @param oid 
	 * 		identificador da entidade.
	 */
	public void remove(Key oid);

	/**
	 * Recuperar todas as entidades persistentes no repositório.
	 * 
	 * @return Todas as entidades persistentes no repositório.
	 */
	public List<Entidade> find();

	/**
	 * Recuperar a entidade persistente no repositório que está associada ao identificador passado como parâmetro.  
	 * 
	 * @param key Identificador associado a entidade persistente no repositório que se deseja recuperar.
	 * @return Entidade persistente no repositório que está associada ao identificador passado como parâmetro.
	 */
	public Entidade find(Key key);
}
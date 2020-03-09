/**
 * 
 */
package br.com.commons.model.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.commons.model.dao.CrudDAO;
import br.com.commons.model.dao.DAOException;
import br.com.commons.model.entity.CrudBaseEntity;


/**
 * Implementa��o base para um DAO que disponibiliza as opera��es de persist�ncia em bancos de dados relacionais, usando a JPA.
 * Gra�as as implementa��es feitas nesta classe os DAOs das entidades s� precisar�o implementar m�todos de busca com filtros espec�ficos.
 * 
 * @author AC de Souza
 */
public abstract class JpaBaseDAO<T extends CrudBaseEntity<T, K>, K> implements CrudDAO<T, K>{
	/**Jpa
	 * Usado para executar as opera��es com o banco de dados.
	 */
	protected final EntityManager manager;

	/**
	 * Este contrutor serve para receber o {@link EntityManager} criado pela f�brica concreta de DAOs.
	 * 
	 * @param manager {@link EntityManager} criado pela f�brica concreta para o reposit�rio, isto �, a implementa��o da f�brica de DAOs usando a JPA.
	 */
	public JpaBaseDAO(EntityManager manager) {
		this.manager = manager;
	}
	
	/**
	 * Usado nos m�todos de busca. 
	 * Solu��o para a impossibilidade de recuperar a classe de um par�metro Generics.
	 * 
	 * @return A classe Class da entidade que o DAO mapeia.
	 */
	public abstract Class<T> getEntityClass();

	/**
	 * Utiliza o m�todo {@link CrudBaseEntity#getId()} para verificar se � para criar uma entidade nova ou atualizar uma existente.
	 * 
	 * Antes eu usava, somente, o merge. Que realizava um select para colocar a entidade no estado "managed", no {@link EntityManager}. 
	 * Com a abordagem da verifica��o se o id est� nulo eu elimino esse select nos casos de inclus�o. E � por isso que eu fa�o as entidades 
	 * implementarem a interface {@link CrudBaseEntity}.
	 * 
	 * @see br.com.commons.model.dao.CrudDAO#save(java.lang.Object)
	 * @throws DAOException Para traduzir qualquer {@link RuntimeException} que tenha ocorrido durante a opera��o.
	 */
	public void save(T unsaved) throws DAOException{
		try{
			if(unsaved.getId() == null){
				manager.persist(unsaved);
			}else{
				manager.merge(unsaved);
			}
		}catch (RuntimeException e){
			throw new DAOException(e);
		}
	}

	/**
	 * 
	 * @see br.com.commons.model.dao.CrudDAO#remove(java.lang.Object)
	 * @throws DAOException Para traduzir qualquer {@link RuntimeException} que tenha ocorrido durante a opera��o. 
	 */
	public void remove(K id) throws DAOException{
		try{
			manager.remove( getReference(id) );
		}catch (RuntimeException e){
			throw new DAOException(e);
		}
	}

	/**
	 * Usado pelo m�todo {@link #remove(K)}.
	 * Serve para recuperar uma referencia a entidade, associada ao id informado, e coloc�-la com no estado de "managed", no {@link EntityManager}.
	 * A vantagem sobre o find � que o m�todo {@link EntityManager}{@link #getReference(Object)} n�o percorre as associa��es.  
	 * 
	 * @param id Identificador da entidade, a qual se deseja uma inst�ncia no {@link EntityManager}. 
	 * @return A entidade associada ao id informado.
	 */
	protected T getReference(K id){
		return manager.getReference(getEntityClass(), id);
	}

	/**
	 * @see br.com.commons.model.dao.CrudDAO#find()
	 * @throws DAOException Para traduzir qualquer {@link RuntimeException} que tenha ocorrido durante a opera��o.
	 */
	@SuppressWarnings("unchecked")
	public List<T> find() throws DAOException {
		try{
			return manager.createQuery("select e from "+ getEntityClass().getName() +" e").getResultList();
		}catch (RuntimeException e){
			throw new DAOException("Erro ao tentar listar todos as entidades "+ getEntityClass().getName() +" persistentes.", e);
		}
	}

	/**
	 * @see br.com.commons.model.dao.CrudDAO#find(K)
	 * @throws DAOException Para traduzir qualquer {@link RuntimeException} que tenha ocorrido durante a opera��o.
	 */
	public T find(K id) throws DAOException {
		try{
			return manager.find(getEntityClass(), id);
		}catch (RuntimeException e){
			throw new DAOException("Erro ao tentar recuperar a entidade "+ getEntityClass().getName() +" com o identificador: "+ id +".", e);
		}
	}
}
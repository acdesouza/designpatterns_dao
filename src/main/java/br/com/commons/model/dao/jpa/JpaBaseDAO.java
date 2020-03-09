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
 * Implementação base para um DAO que disponibiliza as operações de persistência em bancos de dados relacionais, usando a JPA.
 * Graças as implementações feitas nesta classe os DAOs das entidades só precisarão implementar métodos de busca com filtros específicos.
 * 
 * @author AC de Souza
 */
public abstract class JpaBaseDAO<T extends CrudBaseEntity<T, K>, K> implements CrudDAO<T, K>{
	/**Jpa
	 * Usado para executar as operações com o banco de dados.
	 */
	protected final EntityManager manager;

	/**
	 * Este contrutor serve para receber o {@link EntityManager} criado pela fábrica concreta de DAOs.
	 * 
	 * @param manager {@link EntityManager} criado pela fábrica concreta para o repositório, isto é, a implementação da fábrica de DAOs usando a JPA.
	 */
	public JpaBaseDAO(EntityManager manager) {
		this.manager = manager;
	}
	
	/**
	 * Usado nos métodos de busca. 
	 * Solução para a impossibilidade de recuperar a classe de um parâmetro Generics.
	 * 
	 * @return A classe Class da entidade que o DAO mapeia.
	 */
	public abstract Class<T> getEntityClass();

	/**
	 * Utiliza o método {@link CrudBaseEntity#getId()} para verificar se é para criar uma entidade nova ou atualizar uma existente.
	 * 
	 * Antes eu usava, somente, o merge. Que realizava um select para colocar a entidade no estado "managed", no {@link EntityManager}. 
	 * Com a abordagem da verificação se o id está nulo eu elimino esse select nos casos de inclusão. E é por isso que eu faço as entidades 
	 * implementarem a interface {@link CrudBaseEntity}.
	 * 
	 * @see br.com.commons.model.dao.CrudDAO#save(java.lang.Object)
	 * @throws DAOException Para traduzir qualquer {@link RuntimeException} que tenha ocorrido durante a operação.
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
	 * @throws DAOException Para traduzir qualquer {@link RuntimeException} que tenha ocorrido durante a operação. 
	 */
	public void remove(K id) throws DAOException{
		try{
			manager.remove( getReference(id) );
		}catch (RuntimeException e){
			throw new DAOException(e);
		}
	}

	/**
	 * Usado pelo método {@link #remove(K)}.
	 * Serve para recuperar uma referencia a entidade, associada ao id informado, e colocá-la com no estado de "managed", no {@link EntityManager}.
	 * A vantagem sobre o find é que o método {@link EntityManager}{@link #getReference(Object)} não percorre as associações.  
	 * 
	 * @param id Identificador da entidade, a qual se deseja uma instância no {@link EntityManager}. 
	 * @return A entidade associada ao id informado.
	 */
	protected T getReference(K id){
		return manager.getReference(getEntityClass(), id);
	}

	/**
	 * @see br.com.commons.model.dao.CrudDAO#find()
	 * @throws DAOException Para traduzir qualquer {@link RuntimeException} que tenha ocorrido durante a operação.
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
	 * @throws DAOException Para traduzir qualquer {@link RuntimeException} que tenha ocorrido durante a operação.
	 */
	public T find(K id) throws DAOException {
		try{
			return manager.find(getEntityClass(), id);
		}catch (RuntimeException e){
			throw new DAOException("Erro ao tentar recuperar a entidade "+ getEntityClass().getName() +" com o identificador: "+ id +".", e);
		}
	}
}
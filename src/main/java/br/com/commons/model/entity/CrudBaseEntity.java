/**
 * 
 */
package br.com.commons.model.entity;

import java.io.Serializable;

import br.com.commons.model.dao.jpa.JpaBaseDAO;

/**
 * Interface das entidades com suporte as operações de persistência CRUD:
 * <dl>
 * 	<dt>Create
 * 		<dd>Persistir uma entidade nova.
 * 	<dt>Retrieve
 * 		<dd>Recuperar uma entidade persistente, previamente criada, do repositório.
 * 	<dt>Update
 * 		<dd>Editar os dados de uma entidade persistente, previamente criada, do repositório.
 * 	<dt>Delete
 * 		<dd>Remover uma entidade persistente, previamente criada, do repositório.
 * 	</dd>
 * </dl>
 * 
 * Usada, principalmente, no {@link JpaBaseDAO} para disponibilizar um método {@link #getId()}
 * 
 * @author AC de Souza
 */
public interface CrudBaseEntity<T, K> extends Serializable{

	/**
	 * Recuperar o identificador da entidade.
	 * 
	 * @return oid da entidade.
	 */
	public K getId();

	/**
	 * Gravar a entidade no repositório.
	 * Caso a entidade já exista, alterar os dados gravado.
	 * 
	 * @throws BusinessException Para violação de uma regra de negócio que proíba a inclusão ou alteração da entidade com os dados atuais.
	 */
	public void save() throws BusinessException;

	/**
	 * Apagar a instancia da entidade
	 * 
	 * @throws BusinessException Para violação de uma regra de negócio que proíba a exclusão da entidade.
	 */
	public void remove() throws BusinessException;
}
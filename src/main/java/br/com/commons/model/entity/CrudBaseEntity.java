/**
 * 
 */
package br.com.commons.model.entity;

import java.io.Serializable;

import br.com.commons.model.dao.jpa.JpaBaseDAO;

/**
 * Interface das entidades com suporte as opera��es de persist�ncia CRUD:
 * <dl>
 * 	<dt>Create
 * 		<dd>Persistir uma entidade nova.
 * 	<dt>Retrieve
 * 		<dd>Recuperar uma entidade persistente, previamente criada, do reposit�rio.
 * 	<dt>Update
 * 		<dd>Editar os dados de uma entidade persistente, previamente criada, do reposit�rio.
 * 	<dt>Delete
 * 		<dd>Remover uma entidade persistente, previamente criada, do reposit�rio.
 * 	</dd>
 * </dl>
 * 
 * Usada, principalmente, no {@link JpaBaseDAO} para disponibilizar um m�todo {@link #getId()}
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
	 * Gravar a entidade no reposit�rio.
	 * Caso a entidade j� exista, alterar os dados gravado.
	 * 
	 * @throws BusinessException Para viola��o de uma regra de neg�cio que pro�ba a inclus�o ou altera��o da entidade com os dados atuais.
	 */
	public void save() throws BusinessException;

	/**
	 * Apagar a instancia da entidade
	 * 
	 * @throws BusinessException Para viola��o de uma regra de neg�cio que pro�ba a exclus�o da entidade.
	 */
	public void remove() throws BusinessException;
}
/**
 * 
 */
package br.com.exemplodao.model.dao.jpa;

import javax.persistence.EntityManager;

import br.com.commons.model.dao.jpa.JpaBaseDAO;
import br.com.exemplodao.model.dao.ClienteDAO;
import br.com.exemplodao.model.entity.Cliente;


/**
 * Implementação, com a tecnologia JPA, da interface DAO responsável pelo mapeamento da entidade {@link Cliente}.
 * 
 * @author AC de Souza
 */
public class JpaClienteDAO extends JpaBaseDAO<Cliente, Long> implements ClienteDAO{

	/**
	 * Contrutor recebendo o {@link EntityManager} criado pela fábrica concreta e repassando ao {@link JpaBaseDAO}.
	 * 
	 * @param manager
	 */
	public JpaClienteDAO(EntityManager manager) {
		super(manager);
	}

	/**
	 * @see br.com.commons.model.dao.jpa.JpaBaseDAO#getEntityClass()
	 */
	@Override
	public Class<Cliente> getEntityClass() {
		return Cliente.class;
	}
}
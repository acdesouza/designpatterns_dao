/**
 * 
 */
package br.com.exemplodao.model.dao;

import br.com.commons.model.dao.CrudDAO;
import br.com.exemplodao.model.entity.Cliente;

/**
 * Interface DAO responsável pelo mapeamento da entidade {@link Cliente}.
 * 
 * Não possui métodos porque ele já foram declarados na interface CrudDAO.
 * 
 * @author AC de Souza
 *
 */
public interface ClienteDAO extends CrudDAO<Cliente, Long>{

}

/**
 * 
 */
package br.com.exemplodao.model.dao;

import br.com.commons.model.dao.CrudDAO;
import br.com.exemplodao.model.entity.Cliente;

/**
 * Interface DAO respons�vel pelo mapeamento da entidade {@link Cliente}.
 * 
 * N�o possui m�todos porque ele j� foram declarados na interface CrudDAO.
 * 
 * @author AC de Souza
 *
 */
public interface ClienteDAO extends CrudDAO<Cliente, Long>{

}

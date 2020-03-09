package br.com.exemplodao.model.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import br.com.commons.model.dao.DAOException;
import br.com.commons.model.entity.BusinessException;
import br.com.commons.model.entity.CrudBaseEntity;
import br.com.exemplodao.model.dao.ClienteDAO;
import br.com.exemplodao.model.dao.DAOFactory;

/**
 * Classe que representa o conceito de {@link Cliente} para o sistema.
 * 
 * @author AC de Souza
 */
@Entity
public class Cliente implements CrudBaseEntity<Cliente, Long> {
	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue
	private Long id;
	private String nome;
	private String endereco;
	private String telefone;

	/**
	 * @see br.com.commons.model.entity.CrudBaseEntity#save()
	 */
	public void save() throws BusinessException {
		DAOFactory factory = DAOFactory.getInstance();
		try{
			factory.txBegin();
			ClienteDAO dao = factory.getDao(ClienteDAO.class);

			dao.save(this);

			factory.txCommit();
		}catch (DAOException e){
			factory.txRollback();
			throw new BusinessException(e);
		}finally{
			factory.shutdown();
		}
	}

	public static Cliente find(long oid){
		DAOFactory factory = DAOFactory.getInstance();
		ClienteDAO dao = factory.getDao(ClienteDAO.class);

		return dao.find(oid);
	}
	public static List<Cliente> find(){
		DAOFactory factory = DAOFactory.getInstance();
		ClienteDAO dao = factory.getDao(ClienteDAO.class);

		return dao.find();
	}

	/**
	 * @see br.com.commons.model.entity.CrudBaseEntity#remove()
	 */
	public void remove() throws BusinessException {
		DAOFactory factory = DAOFactory.getInstance();
		ClienteDAO dao = factory.getDao(ClienteDAO.class);
		
		try{
			factory.txBegin();

			dao.remove(this.getId());

			factory.txCommit();
		}catch (DAOException e){
			factory.txRollback();
			throw new BusinessException(e);
		}finally{
			factory.shutdown();
		}
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + (int) (id ^ (id >>> 32));
		return result;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Cliente other = (Cliente) obj;
		if (id != other.id)
			return false;
		return true;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the endereco
	 */
	public String getEndereco() {
		return endereco;
	}

	/**
	 * @param endereco the endereco to set
	 */
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @return the telefone
	 */
	public String getTelefone() {
		return telefone;
	}

	/**
	 * @param telefone the telefone to set
	 */
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
}

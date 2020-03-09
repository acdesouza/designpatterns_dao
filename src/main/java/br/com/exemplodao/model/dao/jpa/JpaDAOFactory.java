package br.com.exemplodao.model.dao.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import br.com.exemplodao.model.dao.DAOFactory;

/**
 * 
 * @author AC de Souza
 */
public class JpaDAOFactory extends DAOFactory {

	private EntityManager manager;
	private static final EntityManagerFactory factory = Persistence.createEntityManagerFactory("exemplo_dao");
	private EntityTransaction tx;

	public JpaDAOFactory() {
		if(manager == null || !manager.isOpen()){
			manager = factory.createEntityManager();
		}
	}

	public void txBegin() {
		if (tx == null) {
			tx = manager.getTransaction();
			tx.begin();
		}
	}

	public void txCommit() {
		if (tx != null && tx.isActive()){
			tx.commit();
		}
	}

	public void txRollback() {
		if (tx != null)
			tx.rollback();
	}

	@Override
	protected String getPrefixoImpl() {
		return "jpa";
	}
	@Override
	protected Object[] getRepositoryArgs() {
		return new Object[]{
			manager
		};
	}
	
	@Override
	protected Class[] getRepositoryTypes() {
		return new Class[]{
			EntityManager.class
		};
	}

	public void shutdown() {
		manager.clear();
		manager.close();
	}

}

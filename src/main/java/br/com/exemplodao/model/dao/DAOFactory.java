package br.com.exemplodao.model.dao;

import java.lang.reflect.InvocationTargetException;

import org.apache.log4j.Logger;

import br.com.commons.model.dao.CrudDAO;
import br.com.commons.model.dao.DAOException;
import br.com.exemplodao.model.dao.jpa.JpaDAOFactory;

/**
 * Esta � a implementa��o da f�brica de DAOs usando a estrat�gia de AbstractFactory apresentada no <a href="http://java.sun.com/blueprints/corej2eepatterns/Patterns/DataAccessObject.html">cat�logo de padr�o J2EE</a>.
 * Por�m, esta vers�o foi modificada para que n�o seja necess�rio a cria��o de um m�todo para cada DAO criado pela f�brica.
 * 
 * @author AC de Souza
 */
public abstract class DAOFactory {
	private final static Logger logger = Logger.getLogger(DAOFactory.class);

	public static final int JPA = 1;
	private static final int DEFAULT_REPOSITORY = JPA;

	/**
	 * Instanciar a implementa��o padr�o, definida pela constante DEFAULT_REPOSITORY, da {@link DAOFactory}
	 * 
	 * @return A implementa��o padr�o da {@link DAOFactory}
	 */
	public static DAOFactory getInstance(){
		return getInstance(DEFAULT_REPOSITORY);
	}

	public static DAOFactory getInstance(int repository) throws DAOException{
		logger.debug("Implementa��o para o reposit�rio: "+ repository);
		switch (repository){
			case JPA: 
				return new JpaDAOFactory();
			default: 
				throw new DAOException("O reposit�rio selecionado n�o possui implementa��o.");
		}
			
	}
	
	/**
	 * Iniciar uma transa��o.
	 * 
	 * @throws DAOException Problemas de comunica��o com o reposit�rio de dados.
	 */
	public abstract void txBegin() throws DAOException;
	
	/**
	 * Confirmar as altera��es feitas na transa��ao atual.
	 * 
	 * @throws DAOException Problemas de comunica��o com o reposit�rio de dados.
	 */
	public abstract void txCommit() throws DAOException;
	
	/**
	 * Desfazer as altera��es feitas na transa��o atual.
	 * 
	 * @throws DAOException Problemas de comunica��o com o reposit�rio de dados.
	 */
	public abstract void txRollback() throws DAOException;

	/**
	 * Terminar a transa��o liberando os recursos necess�rios para as opera��es
	 * 
	 * @throws DAOException Problemas de comunica��o com o reposit�rio de dados.
	 */
	public abstract void shutdown() throws DAOException;

	/**
	 * Informar o prefixo usado na implementa��o do reposit�rio.
	 * Ser� usado para definir o pacote e o nome da classe que implementa a interface DAO.
	 * 
	 * @return Prefixo da implementa��o
	 */
	protected abstract String getPrefixoImpl();

	/**
	 * Informar a lista de Class usadas no contrutor dos DAOs 
	 * @return Tipos de par�metros do contrutor
	 */
	protected abstract Class[] getRepositoryTypes();

	/**
	 * Informar os par�metros a serem passados no contrutor dos DAOs 
	 * @return Par�metros do constutor
	 */
	protected abstract Object[] getRepositoryArgs();

	//DAOFactory.getDao(ClienteDAO.class)
	@SuppressWarnings("unchecked")
	public <T extends CrudDAO> T getDao(Class<T> daoInterface) throws DAOException{
		T dao = null;
		
		String daoPackage = daoInterface.getPackage().getName() + "." + getPrefixoImpl();
		String daoInterfaceName = daoInterface.getSimpleName();
		String daoClassSimpleName = getPrefixoImpl().substring(0, 1).toUpperCase().concat( getPrefixoImpl().substring(1) ) + daoInterfaceName;
		String daoClassName = daoPackage + "." + daoClassSimpleName;
		logger.debug("Implementa��o solicitada para o DAO: "+ daoClassName);
		
		try {
			Class daoClass = Class.forName( daoClassName);
			dao = (T) daoClass.getConstructor(getRepositoryTypes()).newInstance(getRepositoryArgs());
		} catch (IllegalArgumentException e) {
			throw new DAOException(e);
		} catch (SecurityException e) {
			throw new DAOException(e);
		} catch (InstantiationException e) {
			throw new DAOException(e);
		} catch (IllegalAccessException e) {
			throw new DAOException(e);
		} catch (InvocationTargetException e) {
			throw new DAOException(e);
		} catch (NoSuchMethodException e) {
			throw new DAOException(e);
		} catch (ClassNotFoundException e) {
			throw new DAOException(e);
		}
		
		return dao;
	}
}

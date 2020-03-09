package br.com.exemplodao.model.dao;

import java.lang.reflect.InvocationTargetException;

import org.apache.log4j.Logger;

import br.com.commons.model.dao.CrudDAO;
import br.com.commons.model.dao.DAOException;
import br.com.exemplodao.model.dao.jpa.JpaDAOFactory;

/**
 * Esta é a implementação da fábrica de DAOs usando a estratégia de AbstractFactory apresentada no <a href="http://java.sun.com/blueprints/corej2eepatterns/Patterns/DataAccessObject.html">catálogo de padrão J2EE</a>.
 * Porém, esta versão foi modificada para que não seja necessário a criação de um método para cada DAO criado pela fábrica.
 * 
 * @author AC de Souza
 */
public abstract class DAOFactory {
	private final static Logger logger = Logger.getLogger(DAOFactory.class);

	public static final int JPA = 1;
	private static final int DEFAULT_REPOSITORY = JPA;

	/**
	 * Instanciar a implementação padrão, definida pela constante DEFAULT_REPOSITORY, da {@link DAOFactory}
	 * 
	 * @return A implementação padrão da {@link DAOFactory}
	 */
	public static DAOFactory getInstance(){
		return getInstance(DEFAULT_REPOSITORY);
	}

	public static DAOFactory getInstance(int repository) throws DAOException{
		logger.debug("Implementação para o repositório: "+ repository);
		switch (repository){
			case JPA: 
				return new JpaDAOFactory();
			default: 
				throw new DAOException("O repositório selecionado não possui implementação.");
		}
			
	}
	
	/**
	 * Iniciar uma transação.
	 * 
	 * @throws DAOException Problemas de comunicação com o repositório de dados.
	 */
	public abstract void txBegin() throws DAOException;
	
	/**
	 * Confirmar as alterações feitas na transaçãao atual.
	 * 
	 * @throws DAOException Problemas de comunicação com o repositório de dados.
	 */
	public abstract void txCommit() throws DAOException;
	
	/**
	 * Desfazer as alterações feitas na transação atual.
	 * 
	 * @throws DAOException Problemas de comunicação com o repositório de dados.
	 */
	public abstract void txRollback() throws DAOException;

	/**
	 * Terminar a transação liberando os recursos necessários para as operações
	 * 
	 * @throws DAOException Problemas de comunicação com o repositório de dados.
	 */
	public abstract void shutdown() throws DAOException;

	/**
	 * Informar o prefixo usado na implementação do repositório.
	 * Será usado para definir o pacote e o nome da classe que implementa a interface DAO.
	 * 
	 * @return Prefixo da implementação
	 */
	protected abstract String getPrefixoImpl();

	/**
	 * Informar a lista de Class usadas no contrutor dos DAOs 
	 * @return Tipos de parâmetros do contrutor
	 */
	protected abstract Class[] getRepositoryTypes();

	/**
	 * Informar os parâmetros a serem passados no contrutor dos DAOs 
	 * @return Parâmetros do constutor
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
		logger.debug("Implementação solicitada para o DAO: "+ daoClassName);
		
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

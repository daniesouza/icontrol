package br.com.ischool.dao;

import java.io.Serializable;
import java.util.List;

import br.com.ischool.exceptions.DAOException;
import br.com.ischool.util.Paginacao;

/**
 * @author Daniel Souza de lima e-mail:daniesouza@gmail.com
 * 
 */
public interface GenericDAOLocal<Entity, PK extends Serializable> {
	
	Entity save(Entity entity) throws DAOException;
	Entity update(Entity entity) throws DAOException;
	Entity find(Class<Entity> entityClass,PK id) throws DAOException; 	
	void detach(Entity entity)throws DAOException;
    void delete(Entity entity) throws DAOException;
	List<Entity> getAll(String classe) throws DAOException;
	Entity loadLazyRelationship(Object myEntity, String classe,String relacionamento) throws DAOException;
	int count(Object myEntity, String classe, String relacionamento) throws DAOException;	
	public List<Entity> getAll(String classe, Paginacao paginacao, String ordenacao) throws DAOException; 
    
}
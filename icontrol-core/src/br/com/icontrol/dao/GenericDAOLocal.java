package br.com.icontrol.dao;

import java.io.Serializable;
import java.util.List;

import br.com.icontrol.exceptions.DAOException;
import br.com.icontrol.util.Paginacao;

/**
 * @author Icontrol
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
package br.com.ischool.business;

import java.util.Collection;

import br.com.ischool.entity.Entidade;
import br.com.ischool.exceptions.ServicoException;
import br.com.ischool.util.Paginacao;
/**
 * @author Daniel Souza de lima e-mail:daniesouza@gmail.com
 * 
 */
public interface GenericServiceLocal {

	<T> void remover(Collection<T> objetos) throws ServicoException;
	
	<T> void remover(T objeto) throws ServicoException;
	
	<T extends Entidade> void salvar(T objeto) throws ServicoException;	
	
	<T extends Entidade> void salvar(Collection<T> objetos) throws ServicoException;	
	
	<T extends Entidade> void atualizar(Collection<T> objetos) throws ServicoException;
	
	<T> void atualizar(T objeto) throws ServicoException;	
	
	<T> void detach(T objeto)throws ServicoException;
	
	@SuppressWarnings("rawtypes")
	<T> T obterEntidade(Long id, Class clazz) throws ServicoException;
	
	@SuppressWarnings("rawtypes")
	<T> Collection<T> obterEntidades(Class clazz) throws ServicoException;
	
	@SuppressWarnings("rawtypes")
	<T> Collection<T> obterEntidades(Class clazz, Paginacao paginacao, String ordenacao) throws ServicoException;	
	
	@SuppressWarnings("rawtypes")
	<T> T obterListaLazy(Object objeto, Class clazz, String relacionamento) throws ServicoException;
	
	@SuppressWarnings("rawtypes")
	int count(Object objeto, Class clazz, String relacionamento) throws ServicoException;	
	
}

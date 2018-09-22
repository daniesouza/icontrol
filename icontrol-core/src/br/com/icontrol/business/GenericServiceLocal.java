package br.com.icontrol.business;

import java.util.List;

import br.com.icontrol.entity.Entidade;
import br.com.icontrol.exceptions.ServicoException;
import br.com.icontrol.util.Paginacao;
/**
 * @author Icontrol
 * 
 */
public interface GenericServiceLocal {

	<T> void remover(List<T> objetos) throws ServicoException;
	
	<T> void remover(T objeto) throws ServicoException;
	
	<T extends Entidade> void salvar(T objeto) throws ServicoException;	
	
	<T> void atualizar(T objeto) throws ServicoException;	
	
	<T> void detach(T objeto)throws ServicoException;
	
	@SuppressWarnings("rawtypes")
	<T> T obterEntidade(Long id, Class clazz) throws ServicoException;
	
	@SuppressWarnings("rawtypes")
	<T> List<T> obterEntidades(Class clazz) throws ServicoException;
	
	@SuppressWarnings("rawtypes")
	<T> List<T> obterEntidades(Class clazz, Paginacao paginacao, String ordenacao) throws ServicoException;	
	
	@SuppressWarnings("rawtypes")
	<T> T obterListaLazy(Object objeto, Class clazz, String relacionamento) throws ServicoException;
	
	@SuppressWarnings("rawtypes")
	int count(Object objeto, Class clazz, String relacionamento) throws ServicoException;	
	
}

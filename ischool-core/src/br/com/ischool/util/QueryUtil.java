package br.com.ischool.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * @author Daniel Souza de lima e-mail:daniesouza@gmail.com
 * 
 */

public class QueryUtil {
	
	private StringBuilder hql;
	private Map<String, Object> parametros = new HashMap<String, Object>();
	private String concatenadorFiltro;
	private byte contadorFiltro = 0;
	private Paginacao paginacao;
	private boolean count = false;

	public QueryUtil(String hql, boolean possuiFiltro, boolean count) {
		this.count = count;
		this.hql = new StringBuilder(hql);
		this.concatenadorFiltro = (possuiFiltro ? " AND " : " WHERE ");
	}

	public void adicionarFiltroAproximado(String filtro, Object valor) {
		this.adicionarFiltro(filtro + " LIKE ", "%" + valor.toString() + "%");
	}
	
	public void adicionarFiltroIn(String filtro, Object valor) {
		this.adicionarFiltro(filtro + " IN ", valor);
	}	

	public void adicionarFiltroExato(String filtro, Object valor) {
		this.adicionarFiltro(filtro + " = ", valor);
	}
	
	public void adicionarFiltroEntre(String filtro, Object valor1,Object valor2) {
		
		int contadorPrimeiroParam = ++this.contadorFiltro;
		int contadorSegundoParam = ++this.contadorFiltro;
		
		this.hql.append(this.concatenadorFiltro + filtro + " BETWEEN " + (":parametro"+contadorPrimeiroParam + " AND " + ":parametro" + (contadorSegundoParam)));
		this.concatenadorFiltro = " AND ";
		this.parametros.put(("parametro" + contadorPrimeiroParam), valor1);
		this.parametros.put(("parametro" + contadorSegundoParam), valor2);
	}
	
	public void adicionarFiltroExatoOuNulo(String filtro, Object valor) {
		this.hql.append(this.concatenadorFiltro + "(" + filtro + "= :parametro" + (++this.contadorFiltro)+" OR "+filtro + " is null)");
		this.concatenadorFiltro = " AND ";
		this.parametros.put("parametro" + (this.contadorFiltro), valor);
	}	

	private void adicionarFiltro(String filtro, Object valor) {
		this.hql.append(this.concatenadorFiltro + filtro + (":parametro" + (++this.contadorFiltro)));
		this.concatenadorFiltro = " AND ";
		this.parametros.put(("parametro" + this.contadorFiltro), valor);
	}

	public Query obterQuery(EntityManager entityManager) {
		return this.obterQuery(entityManager, "");
	}

	public Query obterQuery(EntityManager entityManager, String finalHql) {
		if(this.count) {
			this.hql = new StringBuilder(criarQueryContadora(this.hql.toString()));
		}
		Query query = entityManager.createQuery(this.hql.toString() +" "+finalHql);

		for(Entry<String, Object> chaveValor : this.parametros.entrySet()) {
			query.setParameter(chaveValor.getKey(), chaveValor.getValue());
		}

		if(isPaginacao() && !this.count)
		{
			query.setFirstResult(paginacao.getInicio());
			query.setMaxResults(paginacao.getQtdeRegistroPorPagina());
		}

		return query;
	}
	
		
	public static String criarQueryContadora(String query) {
	    Pattern pattern = Pattern.compile("SELECT(.+)FROM");
	    Matcher matcher = pattern.matcher(query);
	    matcher.find();
	    String group0 = matcher.group(0).trim();
	    String group1 = matcher.group(1).trim();
	    String countSelect = group0.replace(group1, "COUNT(" + group1 + ")");
	    String countQuery = query.replace(group0, countSelect);
	    return countQuery;
	}

	public boolean isPaginacao()
	{
		return (paginacao != null && paginacao.getInicio() > -1);			
	}

	public void setPaginacao(Paginacao paginacao) {
		this.paginacao = paginacao;
	}

	public void appendHql(String str) {
		this.hql.append(str);
	}

}
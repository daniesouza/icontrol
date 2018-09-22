package br.com.icontrol.business;

import java.util.Collection;
import java.util.List;

import br.com.icontrol.entity.Atalho;
import br.com.icontrol.entity.AtalhoArdCompComando;
import br.com.icontrol.exceptions.ServicoException;
import br.com.icontrol.util.Paginacao;

/**
 * @author Icontrol
 * 
 */
public interface AtalhoServiceLocal {

	public void salvarAtalho(Atalho Atalho,List<AtalhoArdCompComando> atalhoArdCompComandos) throws ServicoException;
	
	public void alterarAtalho(Atalho Atalho,List<AtalhoArdCompComando> atalhoArdCompComandos) throws ServicoException;
	
	public Collection<Atalho> listarAtalhos() throws ServicoException;
	
	public Collection<Atalho> listarAtalhos(Atalho filtro,Paginacao paginacao) throws ServicoException;
	
	public void excluirAtalho(Atalho Atalho) throws ServicoException;
	
	public List<AtalhoArdCompComando> obterAtalhoArdCompComando(long idAtalho) throws ServicoException;
}


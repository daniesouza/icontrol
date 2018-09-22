package br.com.icontrol.business;

import java.util.Collection;
import java.util.List;

import br.com.icontrol.entity.Arduino;
import br.com.icontrol.entity.Grupo;
import br.com.icontrol.entity.GrupoArdCompPermissoes;
import br.com.icontrol.entity.Usuario;
import br.com.icontrol.exceptions.ServicoException;
import br.com.icontrol.util.Paginacao;

/**
 * @author Icontrol
 * 
 */
public interface GrupoServiceLocal {

	public void salvarGrupo(Grupo grupo,List<GrupoArdCompPermissoes> modulosFuncionalidade,
			List<Arduino> arduinos,	List<Usuario> usuarios) throws ServicoException;
	
	public void alterarGrupo(Grupo grupo,List<GrupoArdCompPermissoes> modulosFuncionalidade,
			List<Arduino> arduinos,	List<Usuario> usuarios) throws ServicoException;
	
	public Collection<Grupo> listarGrupos() throws ServicoException;
	
	public Collection<Grupo> listarGrupos(Grupo filtro,Paginacao paginacao) throws ServicoException;
	
	public void excluirGrupo(Grupo grupo) throws ServicoException;
	
	public List<GrupoArdCompPermissoes> obterGrupoArdCompPermissoes(long idGrupo)	throws ServicoException;
}
